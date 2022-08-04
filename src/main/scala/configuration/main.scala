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
import com.github.javaparser.ast.`type`.{ClassOrInterfaceType, TypeParameter}
import com.github.javaparser.symbolsolver.JavaSymbolSolver
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver

// Java/Scala imports
import java.io.{File, FileNotFoundException}
import scala.collection.mutable.{Map as MutableMap, Set as MutableSet}
import scala.collection.immutable.Queue
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

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
// Type aliases
private type MutableDelta = MutableMap[String, FixedDeclaration]
private type MutablePhi = (
    MutableMap[String, MissingTypeDeclaration],
    MutableMap[Type, InferenceVariableMemberTable]
)
private type MutableOmega         = MutableSet[Assertion]
private type MutableConfiguration = (MutableDelta, MutablePhi, MutableOmega)

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
  val o = s.rightmap(x => x.clone)
  ///////// Step 3 /////////
  // 3a
  val c: MutableConfiguration =
    (MutableMap(), (MutableMap(), MutableMap()), MutableSet())
  val decls =
    s.rightmap(x => (x, x.findAll(classOf[ClassOrInterfaceDeclaration]).asScala.toVector))
  // 3b and 3c
  // visit each class
  decls
    .flatMap(visitAll(_, _, c))
    .rightmap(x =>
      Configuration(
        x._1.toMap,
        x._2._1.toMap,
        x._2._2.toMap,
        PriorityQueue(x._3.toList: _*),
        o.opt.get // safe since decls already depends on s
      )
    )
    .map((l, c) => (l.addSuccess("Successfully built configuration", c.toString), c))

private def visitAll(
    log: Log,
    cb: (CompilationUnit, Vector[ClassOrInterfaceDeclaration]),
    config: MutableConfiguration
): LogWithOption[MutableConfiguration] =
  val (cu, decls) = cb
  decls.foldLeft(LogWithOption(log, Some(config)))((lwo, decl) =>
    lwo.flatMap(visit(_, decl, _, cu))
  )

private def visit(
    log: Log,
    c: ClassOrInterfaceDeclaration,
    arg: MutableConfiguration,
    cu: CompilationUnit
): LogWithOption[MutableConfiguration] =

  /*
   * 1) Add all referenced ClassOrInterfaceTypes to the CompilationUnit
   * 2) Resolve the ClassOrInterfaceDeclaration and convert to FixedDeclaration
   * 3) Add the FixedDeclaration to Delta
   * 4) Add some assertions on the supertypes and type parameter bounds
   * 5) Search through expressions and resolve them
   */
  var finalLog = log

  // Get all the types being referenced in the program
  finalLog = mapWithLog(
    finalLog,
    c.findAll(classOf[ClassOrInterfaceType]).asScala.toVector
  )((l, t) => resolveASTType(cu, arg, t, l))._1

  // get modifiers which are not accessible to the
  // ResolvedReferenceTypeDeclaration
  val isAbstract = c.isAbstract || c.isInterface
  val isFinal    = c.isFinal

  // get the declaration
  val declAttempt =
    convertResolvedReferenceTypeDeclarationToFixedDeclaration(c.resolve, isAbstract, isFinal)
  if declAttempt.isFailure then
    val failure = declAttempt.failed.get
    return LogWithNone(finalLog.addError(failure.getMessage))
  val newDeclaration = declAttempt.get

  val conflictingMethods = newDeclaration.conflictingMethods

  if conflictingMethods.size > 0 then
    return LogWithNone(
      finalLog.addError(
        "The following methods have multiple declarations whose erasures conflict:",
        conflictingMethods.keys.mkString(", ")
      )
    )

  if arg._1.contains(newDeclaration.identifier) then
    return LogWithNone(
      finalLog.addError(s"repeated type declaration ${newDeclaration.identifier}")
    )

  arg._1 += (newDeclaration.identifier -> newDeclaration)

  addAssertionsOnBoundsAndSupertypes(newDeclaration, arg)

  // resolve expressions, bodies and statements
  val expressionTypeMemo: MutableMap[
    (
        Option[ClassOrInterfaceDeclaration],
        Option[MethodDeclaration],
        Expression
    ),
    Option[Type]
  ] = MutableMap()
  val expressions         = c.findAll(classOf[Expression]).asScala.toVector
  val statements          = c.findAll(classOf[Statement]).asScala.toVector
  val variableDeclarators = c.findAll(classOf[VariableDeclarator]).asScala.toVector
  expressions
    .foldLeft(LogWithOption(finalLog, Some(NormalType("", 0): Type)))((lgi, expr) =>
      lgi.flatMap((lg, i) => resolveExpression(lg, expr, arg, expressionTypeMemo))
    )
    .rightmap(_ => arg)
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

  for tp <- decl.typeParameters do
    if tp.isEmpty then ()
    else
      for bound <- tp.tail do
        bound match
          case x @ NormalType(_, _, _) =>
            config._3 += IsInterfaceAssertion(x)
          case _ => ()
  for (_, tp) <- decl.methodTypeParameterBounds do
    if tp.isEmpty then ()
    else
      for bound <- tp.tail do
        bound match
          case x: NormalType =>
            config._3 += IsInterfaceAssertion(x)
          case _ => ()
