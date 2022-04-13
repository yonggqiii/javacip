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
  resolveVariableDeclarator
}
import configuration.types.*
import utils.*
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
): LogWithOption[MutableConfiguration] =
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
  /* The goal is to add the class/interface declaration to Delta.
   * To do so, first, we obtain some basic information about the class/interface.*/
  val isInterface = c.isInterface
  val isAbstract  = c.isAbstract || isInterface
  val isFinal     = c.isFinal
  val identifier  = c.getName.getIdentifier
  var finalLog    = log
  /* Next, we need to obtain the type parameters of the class/interface.
   * The type parameters may have bounds, so we need to obtain the them
   * as types and/or add them to the AST for the symbol solver to work.
   * To do so, we use the resolveASTType method. */
  val typeParameters = c.getTypeParameters.asScala.toVector
  val actualTypeParameters =
    val res = mapWithLog(
      log,
      typeParameters.map(x => x.getTypeBound.asScala.toVector)
    )((lg, bds) => mapWithLog(lg, bds)((l, t) => resolveASTType(cu, arg, t, l)))
    finalLog = res._1
    res._2

  /* We also obtain the supertypes of the class/interface. Again, we may
   * need to add them to the AST for the symbol solver to work. */
  val extendedTypes =
    val res =
      mapWithLog(finalLog, c.getExtendedTypes.asScala.toVector)((log, t) =>
        resolveASTType(cu, arg, t, log)
      )
    finalLog = res._1
    res._2
  val implementedTypes =
    val res =
      mapWithLog(finalLog, c.getImplementedTypes.asScala.toVector)((log, t) =>
        resolveASTType(cu, arg, t, log)
      )
    finalLog = res._1
    res._2

  val methodTypeParameters = c
    .findAll(classOf[TypeParameter])
    .asScala
    .map(x => {
      x.findAncestor(classOf[MethodDeclaration]).toScala match
        case Some(y) =>
          (y.resolve.getQualifiedSignature + "#" + x.getNameAsString, x)
        case None => ("", x)
    })
    .filter(_._1 != "")
    .map(x => {
      val bounds =
        val res = mapWithLog(finalLog, x._2.getTypeBound.asScala.toVector)((log, t) =>
          resolveASTType(cu, arg, t, log)
        )
        finalLog = res._1
        res._2
      (x._1, bounds)
    })
    .foldLeft(Map[String, Vector[Type]]())(_ + _)

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
  // add the declaration to delta
  arg._1 += (identifier -> newDeclaration)

  // make sure that type parameter bounds and supertypes are interfaces/classes
  if isInterface then
    for t <- newDeclaration.extendedTypes do arg._3 += IsInterfaceAssertion(t)
    for t <- newDeclaration.implementedTypes do arg._3 += IsInterfaceAssertion(t)
  else
    for t <- newDeclaration.extendedTypes do arg._3 += IsClassAssertion(t)
    for t <- newDeclaration.implementedTypes do arg._3 += IsInterfaceAssertion(t)

  for tp <- actualTypeParameters do
    if tp.isEmpty then ()
    else
      for bound <- tp.tail do
        bound match
          case x @ NormalType(_, _, _) =>
            arg._3 += IsInterfaceAssertion(x)
          case _ => ()

  // Get all the types being referenced in the program
  finalLog = mapWithLog(
    finalLog,
    c.findAll(classOf[ClassOrInterfaceType]).asScala.toVector
  )((l, t) => resolveASTType(cu, arg, t, l))._1

  val expressionTypeMemo: MutableMap[Expression, Option[Type]] = MutableMap()
// Get all the FieldAccessExprs in the program
  val expressions = c.findAll(classOf[Expression]).asScala.toVector
  val resolvedExpressions = expressions
    .foldLeft(LogWithOption(finalLog, Some(NormalType("", 0): Type)))((lgi, expr) =>
      lgi.flatMap((lg, i) => resolveExpression(lg, expr, arg, expressionTypeMemo))
    )
    .rightmap(_ => arg)
  val variableDeclarators =
    c.findAll(classOf[VariableDeclarator]).asScala.toVector

  variableDeclarators.foldLeft(resolvedExpressions)((lgc, vd) =>
    lgc.flatMap((lg, c) => resolveVariableDeclarator(lg, vd, c, expressionTypeMemo))
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
