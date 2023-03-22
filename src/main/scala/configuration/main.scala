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
  (MutableDelta, MutablePhi, MutableOmega, MutablePsi, MutableTheta, MutableSet[Type])

def hasGenerics(log: Log, filePath: String): (Log, Boolean) =
  // Use the reflection type solver to solve types in the jdk
  val typeSolver = ReflectionTypeSolver()
  // Create JavaSymbolSolver using the type solver
  val jss = JavaSymbolSolver(typeSolver)
  // Set the parser configuration
  StaticJavaParser.getConfiguration().setSymbolResolver(jss)
  parseSource(log.addInfo(s"attempting to parse $filePath..."), filePath) >->= findGenerics match
    case LogWithSome(log, some) => (log, some)
    case LogWithNone(log)       => (log, false)

def findGenerics(cu: CompilationUnit): Boolean =
  cu.findAll(classOf[ASTClassOrInterfaceType])
    .asScala
    .toVector
    .exists(t =>
      t.getTypeArguments().toScala match
        case Some(x) => !x.asScala.isEmpty
        case None    => false
    )

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
  // Use the reflection type solver to solve types in the jdk
  val typeSolver = ReflectionTypeSolver()
  // Create JavaSymbolSolver using the type solver
  val jss = JavaSymbolSolver(typeSolver)
  // Set the parser configuration
  StaticJavaParser.getConfiguration().setSymbolResolver(jss)

  val c: MutableConfiguration =
    (
      MutableMap(),
      (MutableMap(), MutableMap()),
      MutableSet(),
      MutableSet(),
      MutableSet(),
      MutableSet()
    )

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
    cu, // safe since decls already depends on s,
    scala.collection.mutable.Map(),
    Map(),
    Map(),
    Map(),
    config._6.toSet
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
  // resolve all throws declarations in method declarations
  cu.findAll(classOf[MethodDeclaration])
    .asScala
    .flatMap(_.getThrownExceptions().asScala)
    .toVector
    .map(t => resolveSolvedType(t.resolve))
    .foreach(t => config._3 += t <:~ ClassOrInterfaceType("java.lang.Throwable"))
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
        x.calculateResolvedType()
        false
      catch case _: Throwable => true
    )
  // println(allUnresolvableNameExprs.toVector)
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
      ) || id.toUpperCase() == id
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
      // let it be a type (doesn't have to be a class, right?)
      cu.addInterface(id)
      config._2._1(id) =
        MissingTypeDeclaration(id, Vector(), false, false, Vector(), Map(), Map(), Map(), Vector())
      finalLog = finalLog.addInfo(s"Letting $id be a type")
  // now check the methods
  val allMethodsWithoutScope =
    cu.findAll(classOf[MethodCallExpr]).asScala.filter(expr => expr.getScope().toScala.isEmpty)
  //println(allMethodsWithoutScope.map(x => x.getNameAsString()))
  val allMethodDeclarations =
    cu.findAll(classOf[MethodDeclaration])
      .asScala
      .toSet // .asScala.map(x => x.getNameAsString()).toSet
  for expr <- allMethodsWithoutScope do
    if !allMethodDeclarations.exists(p =>
        p.getNameAsString() == expr
          .getNameAsString() && p.getParameters().asScala.size == expr.getArguments().size
      )
    then
//    if !allMethodDeclarations.contains(expr.getNameAsString()) then
      expr.getTypeArguments.toScala match
        case Some(x) =>
          expr.replace(
            MethodCallExpr(NameExpr("JavaCIPUnknownScope"), x, expr.getName(), expr.getArguments())
          )
        case None =>
          expr.replace(
            MethodCallExpr(NameExpr("JavaCIPUnknownScope"), expr.getName(), expr.getArguments())
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
  // remove all the marker annotation exprs
  val annotations = cu.findAll(classOf[MarkerAnnotationExpr]).asScala
  for node <- annotations do node.remove()
  (finalLog, (StaticJavaParser.parse(cu.toString()), config))

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
