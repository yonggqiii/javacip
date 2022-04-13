package configuration.resolvers

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.expr.*
import com.github.javaparser.resolution.types.*
import com.github.javaparser.resolution.declarations.*

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.collection.mutable.Map as MutableMap

import configuration.MutableConfiguration
import configuration.declaration.*
import configuration.assertions.*
import configuration.types.*
import utils.*
import com.github.javaparser.resolution.types.ResolvedReferenceType

private[configuration] def resolveExpression(
    log: Log,
    expr: Expression,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]]
): LogWithOption[Type] =
  if memo.contains(expr) then LogWithOption(log, memo(expr))
  else
    val res =
      if expr.isFieldAccessExpr then
        resolveFieldAccessExpr(log, expr.asFieldAccessExpr, config, memo)
      // else if expr.isArrayAccessExpr then
      //   resolveArrayAccessExpr(log, expr.asFieldAccessExpr, config, memo)
      // else if expr.isArrayInitializerExpr then
      //   resolveArrayInitializerExpr(config, expr.asArrayInitializerExpr, memo)
      else if expr.isAssignExpr then resolveAssignExpr(log, expr.asAssignExpr, config, memo)
      // else if expr.isBinaryExpr then
      //   resolveBinaryExpr(config, expr.asBinaryExpr, memo)
      // else if expr.isCastExpr then
      //   resolveCastExpr(config, expr.asCastExpr, memo)
      // else if expr.isClassExpr then
      //   resolveClassExpr(config, expr.asClassExpr, memo)
      // else if expr.isConditionalExpr then
      //   resolveConditionalExpr(config, expr.asConditionalExpr, memo)
      // else if expr.isEnclosedExpr then
      //   resolveEnclosedExpr(config, expr.asEnclosedExpr, memo)
      // else if expr.isInstanceOfExpr then
      //   resolveInstanceOfExpr(config, expr.asInstanceOfExpr, memo)
      // else if expr.isLambdaExpr then
      //   resolveLambdaExpr(config, expr.asLambdaExpr, memo)
      // else if expr.isMethodCallExpr then
      //   resolveMethodCallExpr(config, expr.asMethodCallExpr, memo)
      // else if expr.isMethodReferenceExpr then
      //   resolveMethodReferenceExpr(config, expr.asMethodReferenceExpr, memo)
      else if expr.isNameExpr then resolveNameExpr(log, expr.asNameExpr, config, memo)
      // else if expr.isObjectCreationExpr then
      //   resolveObjectCreationExpr(config, expr.asObjectCreationExpr, memo)
      // else if expr.isPatternExpr then
      //   resolvePatternExpr(config, expr.asPatternExpr, memo)
      // else if expr.isSuperExpr then
      //   resolveSuperExpr(config, expr.asSuperExpr, memo)
      // else if expr.isSwitchExpr then
      //   resolveSwitchExpr(config, expr.asSwitchExpr, memo)
      // else if expr.isThisExpr then
      //   resolveThisExpr(config, expr.asThisExpr, memo)
      // else if expr.isUnaryExpr then
      //   resolveUnaryExpr(config, expr.asUnaryExpr, memo)
      else if expr.isVariableDeclarationExpr then
        resolveVariableDeclarationExpr(
          log,
          expr.asVariableDeclarationExpr,
          config,
          memo
        )
      else if expr.isLiteralExpr then
        LogWithOption(log, Some(resolveSolvedType(expr.calculateResolvedType)))
      else
        println(expr.getClass)
        ???
    memo(expr) = res.opt
    res

private def resolveScope(
    log: Log,
    expr: Expression,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]]
): LogWithOption[Type] =
  resolveExpression(log, expr, config, memo).rightmap(t =>
    t match
      case x @ TypeParameterIndex(source, id, subs) =>
        config._1(source).getErasure(x)
      case x @ TypeParameterName(source, name, subs) =>
        val typeName = source.split("\\.").dropRight(1).mkString(".")
        config._1(typeName).getErasure(x)
      case x @ _ => x
  )

def resolveFieldAccessExpr(
    log: Log,
    expr: FieldAccessExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]]
): LogWithOption[Type] =
  try LogWithOption(log, Some(resolveSolvedType(expr.calculateResolvedType)))
  catch
    case _ =>
      // If it gets here, then obviously either 1) the scope is declared
      // and resolvable but doesnt have such field, or the scope is
      // undeclared
      // get the scope, and if its a type parameter, get its erasure
      // if its a type parameter, it must definitely be in the source file
      val logWithScope =
        resolveScope(log, expr.getScope, config, memo)
          .rightmap(
            _.upwardProjection
          )
          .flatMap((l, t) =>
            if !config._2._1.contains(t.identifier) && !config._2._2
                .contains(t)
            then
              LogWithOption(
                l.addError(
                  s"${t} is declared to not contain ${expr.getName.getIdentifier}"
                ),
                None
              )
            else LogWithOption(l, Some(t))
          )
      getAttrTypeFromMissingScope(logWithScope, expr, config, memo)

def getAttrTypeFromMissingScope(
    logWithScope: LogWithOption[Type],
    expr: com.github.javaparser.ast.nodeTypes.NodeWithSimpleName[?],
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]]
) =
  logWithScope.map((log, scope) =>
    // add assertion to omega
    config._3 += IsClassAssertion(scope)
    // get substitutions in scope
    val context = scope.substitutions
    // get identifier of attribute
    val attributeIdentifier = expr.getName.getIdentifier
    // get type declaration of scope
    val declaredType: Either[MissingTypeDeclaration, InferenceVariableMemberTable] =
      if config._2._1.contains(scope.identifier) then
        scala.util.Left(config._2._1(scope.identifier))
      else scala.util.Right(config._2._2(scope))
    // attempt to get the attribute from the declaration
    val attrAttempt: Option[Type] = declaredType match
      case scala.util.Left(x)  => x.getAttribute(attributeIdentifier, context)
      case scala.util.Right(x) => x.getAttribute(attributeIdentifier, context)

    attrAttempt
      .map((log, _))
      .getOrElse({
        // create the new declared type of the attribute
        val newIV = InferenceVariableFactory.createAttributeInferenceVariable(
          declaredType match
            case scala.util.Left(x)  => scala.util.Left(x.identifier)
            case scala.util.Right(x) => scala.util.Right(x.typet)
        )
        // get the actual type of the attribute
        val attrType: Type = newIV.addSubstitutionLists(context)
        // add attribute to the type declaration and add to phi
        declaredType match
          case scala.util.Left(dt) =>
            val newDT = dt.addAttribute(attributeIdentifier, newIV)
            config._2._1(scope.identifier) = newDT
          case scala.util.Right(it) =>
            val newDT = it.addAttribute(attributeIdentifier, newIV)
            config._2._2(scope) = newDT
        // add the inference variable member table to phi
        val newInferenceDT = InferenceVariableMemberTable(attrType)
        config._2._2(attrType) = newInferenceDT
        // return the result
        (
          log.addInfo(
            s"added attribute ${attributeIdentifier}: ${attrType} to ${scope.identifier}"
          ),
          attrType
        )
      })
  )

def resolveArrayAccessExpr(
    config: MutableConfiguration,
    expr: ArrayAccessExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???
def resolveArrayInitializerExpr(
    config: MutableConfiguration,
    expr: ArrayInitializerExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolveAssignExpr(
    log: Log,
    expr: AssignExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]]
): LogWithOption[Type] =
  // TODO handle operators
  val t = expr.getTarget
  val v = expr.getValue
  resolveExpression(log, v, config, memo).flatMap((log, v) =>
    resolveExpression(log, t, config, memo).rightmap(t =>
      config._3 += SubtypeAssertion(v, t)
      t
    )
  )
def resolveBinaryExpr(
    config: MutableConfiguration,
    expr: BinaryExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???
def resolveCastExpr(
    config: MutableConfiguration,
    expr: CastExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolveClassExpr(
    config: MutableConfiguration,
    expr: ClassExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolveConditionalExpr(
    config: MutableConfiguration,
    expr: ConditionalExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???
def resolveEnclosedExpr(
    config: MutableConfiguration,
    expr: EnclosedExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolveInstanceOfExpr(
    config: MutableConfiguration,
    expr: InstanceOfExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolveLambdaExpr(
    config: MutableConfiguration,
    expr: LambdaExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???
def resolveMethodCallExpr(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]]
): LogWithOption[Type] =
  try LogWithOption(log, Some(resolveSolvedType(expr.calculateResolvedType)))
  catch
    case e: Throwable =>
      /*
       * cases:
       * 1) scope can be found, is an inference variable
       * 2) scope can be found, is not an inference variable (can be resolved)
       * 3) scope cannot be found, enclosing class/interface is scope
       *
       * then,
       *
       * 3a) t is not fully declared
       * 3b) t is fully declared, method cannot be found
       * 3c) t is fully declared, one method is found but arguments are unsolved
       * 3d) t is fully declared, more than one method is found and arguments are unsolved
       */
      expr.getScope.toScala match
        case Some(x) =>
          // there is a scope.
          scala.util.Try(x.calculateResolvedType) match
            case scala.util.Success(resolvedType) =>
              // scope can be fully resolved
              resolveMethodFromResolvedType(
                log,
                expr,
                config,
                memo,
                resolvedType
              )
            case scala.util.Failure(_) =>
              // scope cannot be fully resolved, likely an inference variable
              resolveExpression(log, x, config, memo).flatMap(
                resolveMethodFromInferenceVariable(_, expr, config, memo, _)
              )
        case None =>
          // no scope.
          expr.findAncestor(classOf[ClassOrInterfaceDeclaration]).toScala match
            case None =>
              LogWithOption(
                log.addError(
                  s"${expr.getNameAsString} is not found in class or interface declaration!"
                ),
                None
              )
            case Some(decl) =>
              scala.util.Try(decl.resolve) match
                case scala.util.Success(resolvedDecl) =>
                  resolveMethodFromResolvedDeclaration(
                    log,
                    expr,
                    config,
                    memo,
                    resolvedDecl
                  )
                case scala.util.Failure(_) =>
                  LogWithOption(
                    log.addError(
                      s"${decl.getFullyQualifiedName} cannot be resolved?"
                    ),
                    None
                  )
def resolveMethodFromResolvedType(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]],
    scope: ResolvedType
): LogWithOption[Type] =
  if scope.isPrimitive then LogWithOption(log.addError(s"${scope} cannot have methods!"), None)
  else if scope.isTypeVariable then
    resolveMethodFromResolvedTypeParameter(log, expr, config, memo, scope.asTypeParameter)
  else resolveMethodFromResolvedReferenceType(log, expr, config, memo, scope.asReferenceType)

def resolveMethodFromResolvedTypeParameter(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]],
    scope: ResolvedTypeParameterDeclaration
): LogWithOption[Type] =
  // get erasure of the type parameter
  val bounds = scope.getBounds.asScala.map(_.getType)
  ???
def resolveMethodFromResolvedReferenceType(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]],
    scope: ResolvedReferenceType
): LogWithOption[Type] = ???

def resolveMethodFromResolvedDeclaration(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]],
    scope: ResolvedReferenceTypeDeclaration
): LogWithOption[Type] = ???

def resolveMethodFromInferenceVariable(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]],
    scope: Type
): LogWithOption[Type] = ???

def resolveMethodReferenceExpr(
    config: MutableConfiguration,
    expr: MethodReferenceExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolveNameExpr(
    log: Log,
    expr: NameExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]]
): LogWithOption[Type] =
  try LogWithOption(log, Some(resolveSolvedType(expr.calculateResolvedType)))
  catch
    case e: Throwable =>
      getAttrTypeFromMissingScope(
        // get the scope of the field access.
        (expr.findAncestor(classOf[ClassOrInterfaceDeclaration]).toScala match
          // expr not found in class or interface declaration?
          case None =>
            LogWithOption(
              log.addError(
                s"${expr} not found in class or interface declaration"
              ),
              None
            )
          case Some(x) =>
            val decl = config._1(x.getName.getIdentifier)
            // not necessarily true right? could be static final
            if decl.isInterface then
              LogWithOption(
                log.addError(
                  s"${decl.identifier} cannot have instance-level attributes!"
                ),
                None
              )
            else if decl.extendedTypes.size == 0 then
              LogWithOption(
                log.addError(
                  s"scope of ${expr.getName.getIdentifier} not found; it cannot exist!"
                ),
                None
              )
            else LogWithOption(log, Some(decl.extendedTypes(0)))
        ).flatMap((l, t) =>
          if !config._2._1.contains(
              t.upwardProjection.identifier
            ) && !config._2._2.contains(t.upwardProjection)
          then
            LogWithOption(
              l.addError(
                s"${t.upwardProjection.identifier} is declared but does not contain ${expr.getNameAsString}"
              ),
              None
            )
          else LogWithOption(l, Some(t))
        ),
        expr,
        config,
        memo
      )

def resolveObjectCreationExpr(
    config: MutableConfiguration,
    expr: ObjectCreationExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolvePatternExpr(
    config: MutableConfiguration,
    expr: PatternExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???
def resolveSuperExpr(
    config: MutableConfiguration,
    expr: SuperExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolveSwitchExpr(
    config: MutableConfiguration,
    expr: SwitchExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???
def resolveThisExpr(
    config: MutableConfiguration,
    expr: ThisExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolveUnaryExpr(
    config: MutableConfiguration,
    expr: UnaryExpr,
    memo: MutableMap[Expression, Type]
): Type = ???
def resolveVariableDeclarationExpr(
    log: Log,
    expr: VariableDeclarationExpr,
    config: MutableConfiguration,
    memo: MutableMap[Expression, Option[Type]]
): LogWithOption[Type] =
  LogWithOption(log, Some(resolveSolvedType(expr.calculateResolvedType)))
