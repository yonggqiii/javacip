package configuration

// JavaParser imports
import com.github.javaparser.ParseProblemException
import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.{
  ClassOrInterfaceDeclaration,
  MethodDeclaration,
  VariableDeclarator
}
import com.github.javaparser.ast.expr.*
import com.github.javaparser.ast.`type`.{
  ClassOrInterfaceType as ASTClassOrInterfaceType,
  TypeParameter
}
import com.github.javaparser.symbolsolver.JavaSymbolSolver
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver

// Java/Scala imports
import java.io.{File, FileNotFoundException}
import scala.collection.mutable.{Map as MutableMap, Set as MutableSet}
import scala.collection.immutable.Queue
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.annotation.tailrec

// Package imports
import configuration.assertions.*
import configuration.assertions.given
import configuration.declaration.*
import configuration.resolvers.{
  convertResolvedReferenceTypeDeclarationToFixedDeclaration,
  resolveASTType,
  resolveExpression,
  resolveSolvedType,
  resolveStatement,
  resolveVariableDeclarator
}
import configuration.types.*
import utils.*
import com.github.javaparser.ast.stmt.Statement
import com.github.javaparser.ast.body.FieldDeclaration
import scala.collection.mutable.PriorityQueue
import scala.collection.mutable.ArrayBuffer
// Type aliases
private type MutableDelta = MutableMap[String, FixedDeclaration]
private type MutablePhi = (
    MutableMap[String, MissingTypeDeclaration],
    MutableMap[Type, InferenceVariableMemberTable]
)
private type MutableOmega = MutableSet[Assertion]
private type MutablePsi   = MutableSet[Type]
private type MutableTheta = MutableSet[Invocation]
private type MutableConfiguration =
  (MutableDelta, MutablePhi, MutableOmega, MutablePsi, MutableTheta)

/** Parses the configuration of the algorithm given the file path of the Java source code
  * @param log
  *   the log file
  * @param filePath
  *   the path to the incomplete program
  * @return
  *   the resulting configuration of the algorithm
  */
def parseConfiguration(
    log: Log,
    filePath: String
): LogWithOption[Configuration] =
  /*
   * 1) Get the StaticJavaParser with the reflection type solver
   * 2) Try parsing the source code
   * 3a) If it works, then create the mutable configuration
   * 3b) Visit each class/interface declaration in the CompilationUnit,
   *     populating the mutable configuration
   * 3c) Fix the mutable configuration into an immutable configuration
   */

  ///////// Step 1 /////////
  // Use the reflection type solver to solve types in the jdk
  val typeSolver = ReflectionTypeSolver()
  // Create JavaSymbolSolver using the type solver
  val jss = JavaSymbolSolver(typeSolver)
  // Set the parser configuration
  StaticJavaParser.getConfiguration().setSymbolResolver(jss)

  ///////// Step 2 /////////
  // Parse the file and generate AST
  val s: LogWithOption[CompilationUnit] =
    parseSource(log.addInfo(s"attempting to parse $filePath..."), filePath)
  // val o = s.rightmap(x => x.clone)
  ///////// Step 3 /////////
  // 3a
  val c: MutableConfiguration =
    (MutableMap(), (MutableMap(), MutableMap()), MutableSet(), MutableSet(), MutableSet())
  // val decls =
  //   s.rightmap(x => (x, x.findAll(classOf[ClassOrInterfaceDeclaration]).asScala.toVector))
  // // 3b and 3c
  // // visit each class
  // decls
  //   .flatMap(visitAll(_, _, c))
  //   .rightmap(x =>
  //     Configuration(
  //       x._1.toMap,
  //       x._2._1.toMap,
  //       x._2._2.toMap,
  //       PriorityQueue(x._3.toList: _*),
  //       x._4.toSet,
  //       x._5.toSet,
  //       o.opt.get // safe since decls already depends on s
  //     )
  //   )
  //   .map((l, c) =>
  //     println(c)
  //     (l.addSuccess("Successfully built configuration", c.toString), c)
  //   )

  parseSource(log.addInfo(s"attempting to parse $filePath..."), filePath)
    >*> ((log, cu) => fixImpossibleCode(log, cu, c))
    >>= buildDeclarations
    >>= resolveExpressionsAndStatements
    >->= cleanup

private def cleanup(x: (CompilationUnit, MutableConfiguration)): Configuration =
  val (cu, config)         = x
  val allClassOrInterfaces = cu.findAll(classOf[ClassOrInterfaceDeclaration]).asScala
  for decl <- allClassOrInterfaces do
    val name = decl.getNameAsString()
    if config._2._1.contains(name) then cu.remove(decl)
  cu.recalculatePositions()
  Configuration(
    config._1.toMap,
    config._2._1.toMap,
    config._2._2.toMap,
    PriorityQueue(config._3.toList: _*),
    config._4.toSet,
    config._5.toSet,
    cu // safe since decls already depends on s
  )

private def resolveExpressionsAndStatements(
    log: Log,
    x: (CompilationUnit, MutableConfiguration)
): LogWithOption[(CompilationUnit, MutableConfiguration)] =
  val (cu, config) = x
  // resolve expressions, bodies and statements
  val expressionTypeMemo: MutableMap[
    String,
    Option[Type]
  ] = MutableMap()
  val expressions         = cu.findAll(classOf[Expression]).asScala.toVector
  val statements          = cu.findAll(classOf[Statement]).asScala.toVector
  val variableDeclarators = cu.findAll(classOf[VariableDeclarator]).asScala.toVector
  expressions
    .foldLeft(LogWithOption(log, Some(ClassOrInterfaceType("lol"): Type)))((lgi, expr) =>
      lgi.flatMap((lg, i) => resolveExpression(lg, expr, config, expressionTypeMemo))
    )
    .rightmap(_ => config)
    .flatMap((log, config) =>
      statements.foldLeft(LogWithOption(log, Some(config)))((lwo, stmt) =>
        lwo.flatMap((x, y) => resolveStatement(x, stmt, y, expressionTypeMemo))
      )
    )
    .flatMap((log, config) =>
      variableDeclarators.foldLeft(LogWithOption(log, Some(config)))((lwo, decl) =>
        lwo.flatMap((x, y) => resolveVariableDeclarator(x, decl, y, expressionTypeMemo))
      )
    )
    .rightmap(config => (cu, config))

private def fixImpossibleCode(
    log: Log,
    cu: CompilationUnit,
    config: MutableConfiguration
): (Log, (CompilationUnit, MutableConfiguration)) =
  val dummyLog = Log(AppConfig(verbose = false, in = "", out = "", debug = false), Vector())
  val allTypes = resolveAllASTTypes(dummyLog, cu, config)._2
  // get all NameExpr in cu
  val allUnresolvableNameExprs = cu
    .findAll(classOf[NameExpr])
    .asScala
    .filter(x =>
      try
        x.resolve()
        false
      catch case _: Throwable => true
    )
  // build map of name -> nameexprs to see which ones might be static and which ones must not
  val m        = MutableMap[String, ArrayBuffer[NameExpr]]()
  var finalLog = log
  for x <- allUnresolvableNameExprs do
    val s = x.getNameAsString()
    if !m.contains(s) then m(s) = ArrayBuffer()
    m(s).addOne(x)
  for (id, exprs) <- m do
    if !id(0).isUpper || exprs.exists(x =>
        x.getParentNode().toScala match
          case Some(p) => !p.isInstanceOf[FieldAccessExpr] && !p.isInstanceOf[MethodCallExpr]
          case None    => true
      )
    then
      // must not be static because there exists some x that is used as an actual expression
      for expr <- exprs do
        if cannotPossiblyHaveScope(expr, cu, config) then
          finalLog = finalLog.addWarn(s"adding scope to $expr since no scope can be found")
          expr.replace(
            FieldAccessExpr(NameExpr("JavaCIPUnknownScope"), expr.getNameAsString())
          )
          if !config._2._1.contains("JavaCIPUnknownScope") then
            config._2._1("JavaCIPUnknownScope") = MissingTypeDeclaration(
              "JavaCIPUnknownScope",
              Vector(),
              true,
              false,
              Vector(),
              Map(),
              Map(),
              Map(),
              Vector()
            )
            cu.addClass("JavaCIPUnknownScope")
    else
      // let it be a type
      cu.addClass(id)
      config._2._1(id) =
        MissingTypeDeclaration(id, Vector(), true, false, Vector(), Map(), Map(), Map(), Vector())
      finalLog = finalLog.addInfo(s"Letting $id be a class")
  cu.recalculatePositions()
  (finalLog, (cu, config))

private def cannotPossiblyHaveScope(
    expr: NameExpr,
    cu: CompilationUnit,
    config: MutableConfiguration
): Boolean =
  val enclosingDeclaration = expr.findAncestor(classOf[ClassOrInterfaceDeclaration]).get() // lol
  // is interface -- cannot refer to superinterface attribute
  if enclosingDeclaration.isInterface() then return true
  val extendedTypes = enclosingDeclaration.getExtendedTypes().asScala.toVector
  // no extended types -- no supertype attribute to refer to
  if extendedTypes.isEmpty then return true
  val extendedType       = extendedTypes(0)
  val nameOfExtendedType = extendedType.getNameAsString()
  doesNotHaveMissingSupertype(nameOfExtendedType, config)

@tailrec
private def doesNotHaveMissingSupertype(identifier: String, config: MutableConfiguration): Boolean =
  // identifier is missing
  if config._2._1.contains(identifier) then return false
  // identifier is in delta
  if config._1.contains(identifier) then
    val decl = config._1(identifier)
    // interface so can't refer to its attribute
    if decl.isInterface then return true
    val extendedTypes = decl.extendedTypes
    // no extended types; return true immediately
    if extendedTypes.isEmpty then return true
    // recursive call to supertype
    doesNotHaveMissingSupertype(extendedTypes(0).identifier, config)
  else true

private def resolveAllASTTypes(
    log: Log,
    cu: CompilationUnit,
    config: MutableConfiguration
): (Log, Vector[Type]) =
  mapWithLog(
    log,
    cu.findAll(classOf[ASTClassOrInterfaceType]).asScala.toVector
  )((l, t) => resolveASTType(cu, config, t, l))

private def buildDeclarations(
    log: Log,
    x: (CompilationUnit, MutableConfiguration)
): LogWithOption[(CompilationUnit, MutableConfiguration)] =
  val (cu, config) = x
  val decls = cu
    .findAll(classOf[ClassOrInterfaceDeclaration])
    .asScala
    .toVector
    .filter(decl => !config._2._1.contains(decl.getNameAsString()))
  decls.foldLeft(LogWithOption(log, Some((cu, config))))((lwo, decl) =>
    lwo.flatMap((llog, x) => buildDeclaration(llog, decl, x._2, x._1))
  )

private def buildDeclaration(
    log: Log,
    decl: ClassOrInterfaceDeclaration,
    config: MutableConfiguration,
    cu: CompilationUnit
): LogWithOption[(CompilationUnit, MutableConfiguration)] =
  // get modifiers which are not accessible to the
  // ResolvedReferenceTypeDeclaration
  val isAbstract = decl.isAbstract || decl.isInterface
  val isFinal    = decl.isFinal

  // get the declaration
  val declAttempt =
    convertResolvedReferenceTypeDeclarationToFixedDeclaration(decl.resolve(), isAbstract, isFinal)
  if declAttempt.isFailure then
    val failure = declAttempt.failed.get
    return LogWithNone(log.addError(failure.getMessage))
  val newDeclaration = declAttempt.get

  // check for conflicting methods
  val conflictingMethods = newDeclaration.conflictingMethods
  if conflictingMethods.size > 0 then
    return LogWithNone(
      log.addError(
        "The following methods have multiple declarations whose erasures conflict:",
        conflictingMethods.map(x => "\t" + x.toString).mkString("\n")
      )
    )

  // check if there are repeated type declarations
  if config._1.contains(newDeclaration.identifier) then
    return LogWithNone(
      log.addError(s"repeated type declaration ${newDeclaration.identifier}")
    )

  // add declaration to config
  config._1 += (newDeclaration.identifier -> newDeclaration)

  // add assertions to config based on declaration
  addAssertionsOnBoundsAndSupertypes(newDeclaration, config)
  LogWithSome(log, (cu, config))

// private def visitAll(
//     log: Log,
//     cb: (CompilationUnit, Vector[ClassOrInterfaceDeclaration]),
//     config: MutableConfiguration
// ): LogWithOption[MutableConfiguration] =
//   val (cu, decls) = cb
//   decls.foldLeft(LogWithOption(log, Some(config)))((lwo, decl) =>
//     lwo.flatMap(visit(_, decl, _, cu))
//   )

// private def visit(
//     log: Log,
//     c: ClassOrInterfaceDeclaration,
//     arg: MutableConfiguration,
//     cu: CompilationUnit
// ): LogWithOption[MutableConfiguration] =

//   /*
//    * 1) Add all referenced ClassOrInterfaceTypes to the CompilationUnit
//    * 2) Resolve the ClassOrInterfaceDeclaration and convert to FixedDeclaration
//    * 3) Add the FixedDeclaration to Delta
//    * 4) Add some assertions on the supertypes and type parameter bounds
//    * 5) Search through expressions and resolve them
//    */
//   var finalLog = log

//   // Get all the types being referenced in the program
//   finalLog = mapWithLog(
//     finalLog,
//     c.findAll(classOf[ASTClassOrInterfaceType]).asScala.toVector
//   )((l, t) => resolveASTType(cu, arg, t, l))._1

//   // get modifiers which are not accessible to the
//   // ResolvedReferenceTypeDeclaration
//   val isAbstract = c.isAbstract || c.isInterface
//   val isFinal    = c.isFinal

//   // get the declaration
//   val declAttempt =
//     convertResolvedReferenceTypeDeclarationToFixedDeclaration(c.resolve, isAbstract, isFinal)
//   if declAttempt.isFailure then
//     val failure = declAttempt.failed.get
//     return LogWithNone(finalLog.addError(failure.getMessage))
//   val newDeclaration = declAttempt.get

//   val conflictingMethods = newDeclaration.conflictingMethods

//   if conflictingMethods.size > 0 then
//     return LogWithNone(
//       finalLog.addError(
//         "The following methods have multiple declarations whose erasures conflict:",
//         conflictingMethods.map(x => "\t" + x.toString).mkString("\n")
//       )
//     )

//   if arg._1.contains(newDeclaration.identifier) then
//     return LogWithNone(
//       finalLog.addError(s"repeated type declaration ${newDeclaration.identifier}")
//     )

//   arg._1 += (newDeclaration.identifier -> newDeclaration)

//   addAssertionsOnBoundsAndSupertypes(newDeclaration, arg)

//   // resolve expressions, bodies and statements
//   val expressionTypeMemo: MutableMap[
//     String,
//     Option[Type]
//   ] = MutableMap()
//   val expressions         = c.findAll(classOf[Expression]).asScala.toVector
//   val statements          = c.findAll(classOf[Statement]).asScala.toVector
//   val variableDeclarators = c.findAll(classOf[VariableDeclarator]).asScala.toVector
//   expressions
//     .foldLeft(LogWithOption(finalLog, Some(ClassOrInterfaceType("lol"): Type)))((lgi, expr) =>
//       lgi.flatMap((lg, i) => resolveExpression(lg, expr, arg, expressionTypeMemo))
//     )
//     .rightmap(_ => arg)
//     .flatMap((log, config) =>
//       statements.foldLeft(LogWithOption(log, Some(config)))((lwo, stmt) =>
//         lwo.flatMap((x, y) => resolveStatement(x, stmt, y, expressionTypeMemo))
//       )
//     )
//     .flatMap((log, config) =>
//       variableDeclarators.foldLeft(LogWithOption(log, Some(config)))((lwo, decl) =>
//         lwo.flatMap((x, y) => resolveVariableDeclarator(x, decl, y, expressionTypeMemo))
//       )
//     )

private def parseSource(log: Log, filePath: String): LogWithOption[CompilationUnit] =
  scala.util.Try(StaticJavaParser.parse(File(filePath))) match
    case scala.util.Success(x) =>
      LogWithOption(log.addSuccess(s"successfully parsed $filePath"), Some(x))
    case scala.util.Failure(e: FileNotFoundException) =>
      LogWithOption(log.addError(s"$filePath not found"), None)
    case scala.util.Failure(e: ParseProblemException) =>
      LogWithOption(
        log.addError(
          s"$filePath contains syntax errors",
          e.getProblems.asScala.map(_.getVerboseMessage).mkString("\n")
        ),
        None
      )
    case scala.util.Failure(e) =>
      LogWithOption(
        log.addError(
          s"an error occurred when attempting to parse $filePath",
          e.getMessage
        ),
        None
      )

private def addAssertionsOnBoundsAndSupertypes(
    decl: FixedDeclaration,
    config: MutableConfiguration
) =
  // make sure that type parameter bounds and supertypes are interfaces/classes
  if decl.isInterface then
    for t <- decl.extendedTypes do config._3 += IsInterfaceAssertion(t)
    for t <- decl.implementedTypes do config._3 += IsInterfaceAssertion(t)
  else
    for t <- decl.extendedTypes do config._3 += IsClassAssertion(t)
    for t <- decl.implementedTypes do config._3 += IsInterfaceAssertion(t)

  for tp <- decl.typeParameterBounds do
    if tp.isEmpty then ()
    else
      for bound <- tp.tail do
        bound match
          case x: ClassOrInterfaceType =>
            config._3 += IsInterfaceAssertion(x)
          case _ => ()
  for (_, tp) <- decl.methodTypeParameterBounds do
    if tp.isEmpty then ()
    else
      for bound <- tp.tail do
        bound match
          case x: ClassOrInterfaceType =>
            config._3 += IsInterfaceAssertion(x)
          case _ => ()
