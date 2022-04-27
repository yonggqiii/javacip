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
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

// Package imports
import configuration.assertions.*
import configuration.declaration.*
import configuration.resolvers.{
  resolveASTType,
  resolveExpression,
  resolveSolvedType,
  resolveStatement,
  resolveVariableDeclarator
}
import configuration.types.*
import utils.*
import com.github.javaparser.ast.stmt.Statement
// Type aliases
private type MutableDelta = MutableMap[String, FixedDeclaration]
private type MutablePhi = (
    MutableMap[String, MissingTypeDeclaration],
    MutableMap[Type, InferenceVariableMemberTable]
)
private type MutableOmega         = MutableSet[Assertion]
private type MutableConfiguration = (MutableDelta, MutablePhi, MutableOmega)

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
  // Parse the file and generate AST
  val s: LogWithOption[CompilationUnit] =
    parseSource(log.addInfo(s"attempting to parse $filePath..."), filePath)
  val c: MutableConfiguration =
    (MutableMap(), (MutableMap(), MutableMap()), MutableSet())
  val decls =
    s.rightmap(x => (x, x.findAll(classOf[ClassOrInterfaceDeclaration]).asScala.toVector))
  // visit each class
  decls
    .flatMap(visitAll(_, _, c))
    .map((l, c) =>
      (
        l.addSuccess(
          "successfully created configuration", {
            val (d, p, o) = c
            "Delta:\n" + d.values.mkString("\n") + "\n\nPhi:\n" + p._1.values
              .mkString("\n") + "\n" + p._2.values
              .mkString("\n") + "\n\nOmega:\n" + o.mkString("\n")
          }
        ),
        c
      )
    )
    .rightmap(x => Configuration(x._1.toMap, x._2._1.toMap, x._2._2.toMap, x._3.toList))

private def visitAll(
    log: Log,
    cb: (CompilationUnit, Vector[ClassOrInterfaceDeclaration]),
    config: MutableConfiguration
): LogWithOption[MutableConfiguration] =
  val (cu, decls) = cb
  decls.foldLeft(LogWithOption(log, Some(config)))((lwo, decl) =>
    lwo.flatMap((log, config) => visit(decl, config, cu, log))
  )

private def visit(
    c: ClassOrInterfaceDeclaration,
    arg: MutableConfiguration,
    cu: CompilationUnit,
    log: Log
): LogWithOption[MutableConfiguration] =
  val isInterface    = c.isInterface
  val isAbstract     = c.isAbstract || isInterface
  val isFinal        = c.isFinal
  val identifier     = c.getName.getIdentifier
  var finalLog       = log
  val typeParameters = c.getTypeParameters.asScala.toVector
  val actualTypeParameters =
    val res = getActualTypeParameters(finalLog, typeParameters, cu, arg)
    finalLog = res._1
    res._2

  val (extendedTypes, implementedTypes) =
    val res = getSupertypes(finalLog, c, cu, arg)
    finalLog = res._1
    (res._2, res._3)

  val methodTypeParameters =
    val res = getMethodTypeParameters(finalLog, c, cu, arg)
    finalLog = res._1
    res._2

  val newDeclaration = FixedDeclaration(
    identifier,
    actualTypeParameters,
    isFinal,
    isAbstract,
    isInterface,
    extendedTypes,
    implementedTypes,
    methodTypeParameters
  )

  arg._1 += (identifier -> newDeclaration)

  addAssertionsOnBoundsAndSupertypes(newDeclaration, actualTypeParameters, arg)

  // Get all the types being referenced in the program
  finalLog = mapWithLog(
    finalLog,
    c.findAll(classOf[ClassOrInterfaceType]).asScala.toVector
  )((l, t) => resolveASTType(cu, arg, t, l))._1

  // resolve expressions, bodies and statements
  val expressionTypeMemo: MutableMap[Expression, Option[Type]] = MutableMap()
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

private def getActualTypeParameters(
    log: Log,
    typeParams: Vector[TypeParameter],
    cu: CompilationUnit,
    config: MutableConfiguration
) =
  mapWithLog(
    log,
    typeParams.map(x => x.getTypeBound.asScala.toVector)
  )((lg, bds) => mapWithLog(lg, bds)((l, t) => resolveASTType(cu, config, t, l)))

private def getSupertypes(
    log: Log,
    decl: ClassOrInterfaceDeclaration,
    cu: CompilationUnit,
    config: MutableConfiguration
) =
  val e = mapWithLog(log, decl.getExtendedTypes.asScala.toVector)((log, t) =>
    resolveASTType(cu, config, t, log)
  )
  val i = mapWithLog(e._1, decl.getImplementedTypes.asScala.toVector)((log, t) =>
    resolveASTType(cu, config, t, log)
  )
  (i._1, e._2, i._2)

def getMethodTypeParameters(
    log: Log,
    decl: ClassOrInterfaceDeclaration,
    cu: CompilationUnit,
    config: MutableConfiguration
) =
  val res1 = decl
    .findAll(classOf[TypeParameter])
    .asScala
    .map(x => {
      x.findAncestor(classOf[MethodDeclaration]).toScala match
        case Some(y) =>
          (y.resolve.getQualifiedSignature + "#" + x.getNameAsString, x)
        case None => ("", x)
    })
    .filter(_._1 != "")
    .toVector
  val a = mapWithLog(log, res1)((log, st) =>
    val res = mapWithLog(log, st._2.getTypeBound.asScala.toVector)((log, t) =>
      resolveASTType(cu, config, t, log)
    )
    (res._1, (st._1 -> res._2))
  )
  (a._1, a._2.toMap)

private def addAssertionsOnBoundsAndSupertypes(
    decl: FixedDeclaration,
    typeParameters: Vector[Vector[Type]],
    config: MutableConfiguration
) =
  // make sure that type parameter bounds and supertypes are interfaces/classes
  if decl.isInterface then
    for t <- decl.extendedTypes do config._3 += IsInterfaceAssertion(t)
    for t <- decl.implementedTypes do config._3 += IsInterfaceAssertion(t)
  else
    for t <- decl.extendedTypes do config._3 += IsClassAssertion(t)
    for t <- decl.implementedTypes do config._3 += IsInterfaceAssertion(t)

  for tp <- typeParameters do
    if tp.isEmpty then ()
    else
      for bound <- tp.tail do
        bound match
          case x @ NormalType(_, _, _) =>
            config._3 += IsInterfaceAssertion(x)
          case _ => ()
