package configuration.resolvers

// JavaParser imports
import com.github.javaparser.ast.{CompilationUnit, NodeList}
import com.github.javaparser.ast.`type`.{
  ArrayType as ASTArrayType,
  ClassOrInterfaceType,
  IntersectionType as ASTIntersectionType,
  PrimitiveType as ASTPrimitiveType,
  ReferenceType,
  Type as ASTType,
  TypeParameter,
  WildcardType
}
import com.github.javaparser.resolution.UnsolvedSymbolException

// Java/Scala imports
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.collection.mutable.Map as MutableMap

// Package imports
import configuration.{MutableConfiguration}
import configuration.declaration.MissingTypeDeclaration
import configuration.types.*
import utils.{Log, mapWithLog}

private[configuration] def resolveASTType(
    cu: CompilationUnit,
    config: MutableConfiguration,
    typeToConvert: ASTType,
    log: Log
): (Log, Type) =
  // Just check what is the actual type of the type to convert then handle
  // them accordingly.
  if typeToConvert.isArrayType then
    resolveArrayType(
      cu,
      config,
      typeToConvert.asArrayType,
      log
    )
  else if typeToConvert.isClassOrInterfaceType then
    resolveClassOrInterfaceType(
      cu,
      config,
      typeToConvert.asClassOrInterfaceType,
      log
    )
  else if typeToConvert.isIntersectionType then
    resolveIntersectionType(
      cu,
      config,
      typeToConvert.asIntersectionType,
      log
    )
  else if typeToConvert.isWildcardType then
    resolveWildcardType(
      cu,
      config,
      typeToConvert.asWildcardType,
      log
    )
  else if typeToConvert.isPrimitiveType then
    resolvePrimitiveType(cu, config, typeToConvert.asPrimitiveType, log)
  else if typeToConvert.isVoidType then (log, PRIMITIVE_VOID)
  else ??? //TODO make this safe.

private def resolvePrimitiveType(
    cu: CompilationUnit,
    config: MutableConfiguration,
    t: ASTPrimitiveType,
    log: Log
): (Log, Type) =
  (log, PrimitiveType(t.asString))

private def resolveArrayType(
    cu: CompilationUnit,
    config: MutableConfiguration,
    typeToConvert: ASTArrayType,
    log: Log
): (Log, Type) =
  val (newLog, t) = resolveASTType(cu, config, typeToConvert.getComponentType, log)
  (newLog, ArrayType(t))

private def resolveClassOrInterfaceType(
    cu: CompilationUnit,
    config: MutableConfiguration,
    typeToConvert: ClassOrInterfaceType,
    log: Log
): (Log, Type) =
  // First we obtain the name of the type
  val identifier = typeToConvert.getName.getIdentifier
  // Resolve all the arguments.
  val (newLog, arguments) =
    // Get the AST type arguments in a vector
    val typeargs =
      typeToConvert.getTypeArguments.toScala match
        case Some(x) => x.asScala.toVector
        case None    => Vector()
    // resolve each sequentially and collect them in a LogWithOption[Vector]
    mapWithLog(log, typeargs)((l, t) => resolveASTType(cu, config, t, l))

  // the substitutions of the type to convert
  val substitutions: Map[TTypeParameter, Type] =
    (0 until arguments.size).foldLeft(Map[TTypeParameter, Type]())((m, i) =>
      m + (TypeParameterIndex(identifier, i) -> arguments(i))
    )

  try (log, resolveSolvedType(typeToConvert.resolve()))
  catch
    case e: UnsolvedSymbolException =>
      // here it must mean that type to convert itself (not its arguments)
      // does not exist

      // Add typeToConvert as an interface
      val newInterface = cu.addInterface(identifier)
      val newInterfaceTypeParameters = NodeList(
        (0 until arguments.size)
          .map(i => TypeParameter((84 + i).toChar.toString))
          .asJavaCollection
      )
      newInterface.setTypeParameters(newInterfaceTypeParameters)

      // Add to Phi
      config._2._1(identifier) = MissingTypeDeclaration(identifier, arguments.size)

      // Add to logs and return type
      (
        newLog.addInfo(
          s"type $identifier not found in source code; adding to Phi"
        ),
        NormalType(
          identifier,
          arguments.size,
          if arguments.size == 0 then Nil else List(substitutions)
        )
      )

private def resolveIntersectionType(
    cu: CompilationUnit,
    config: MutableConfiguration,
    typeToConvert: ASTIntersectionType,
    log: Log
): (Log, Type) = ??? // TODO

private def resolveWildcardType(
    cu: CompilationUnit,
    config: MutableConfiguration,
    typeToConvert: WildcardType,
    log: Log
): (Log, Type) =
  // super or extends?
  (
    typeToConvert.getSuperType.toScala,
    typeToConvert.getExtendedType.toScala
  ) match
    case (Some(x), _) =>
      val (newLog, bound) = resolveASTType(cu, config, x, log)
      (newLog, SuperWildcardType(bound))
    case (_, Some(x)) =>
      val (newLog, bound) = resolveASTType(cu, config, x, log)
      (newLog, ExtendsWildcardType(bound))

    case (None, None) => (log, Wildcard)
