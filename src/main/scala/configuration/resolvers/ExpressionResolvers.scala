package configuration.resolvers

import com.github.javaparser.ast.body.{ClassOrInterfaceDeclaration, MethodDeclaration}
import com.github.javaparser.ast.expr.*
import com.github.javaparser.utils.Pair
import com.github.javaparser.resolution.MethodUsage
import com.github.javaparser.resolution.types.*
import com.github.javaparser.resolution.declarations.*
import com.github.javaparser.symbolsolver.model.typesystem.ReferenceTypeImpl
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.collection.mutable.Map as MutableMap
import scala.collection.mutable.Set as MutableSet
import scala.collection.mutable.{Queue, ArrayBuffer}
import configuration.MutableConfiguration
import configuration.declaration.*
import configuration.assertions.*
import configuration.types.*
import utils.*
import scala.annotation.tailrec

private[configuration] def resolveExpression(
    log: Log,
    expr: Expression,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  val containingMethod = expr.findAncestor(classOf[MethodDeclaration]).toScala
  val containingClass  = expr.findAncestor(classOf[ClassOrInterfaceDeclaration]).toScala
  if memo.contains((containingClass, containingMethod, expr)) then
    LogWithOption(log, memo((containingClass, containingMethod, expr)))
  else
    val res =
      if expr.isFieldAccessExpr then
        resolveFieldAccessExpr(log, expr.asFieldAccessExpr, config, memo)
      // else if expr.isArrayAccessExpr then
      //   resolveArrayAccessExpr(log, expr.asFieldAccessExpr, config, memo)
      // else if expr.isArrayInitializerExpr then
      //   resolveArrayInitializerExpr(config, expr.asArrayInitializerExpr, memo)
      else if expr.isAssignExpr then resolveAssignExpr(log, expr.asAssignExpr, config, memo)
      else if expr.isBinaryExpr then resolveBinaryExpr(log, expr.asBinaryExpr, config, memo)
      else if expr.isCastExpr then resolveCastExpr(log, expr.asCastExpr, config, memo)
      else if expr.isClassExpr then resolveClassExpr(log, expr.asClassExpr, config, memo)
      else if expr.isConditionalExpr then
        resolveConditionalExpr(log, expr.asConditionalExpr, config, memo)
      else if expr.isEnclosedExpr then resolveEnclosedExpr(log, expr.asEnclosedExpr, config, memo)
      else if expr.isInstanceOfExpr then
        resolveInstanceOfExpr(log, expr.asInstanceOfExpr, config, memo)
      // else if expr.isLambdaExpr then
      //   resolveLambdaExpr(config, expr.asLambdaExpr, memo)
      else if expr.isMethodCallExpr then
        resolveMethodCallExpr(log, expr.asMethodCallExpr, config, memo)
      // else if expr.isMethodReferenceExpr then
      //   resolveMethodReferenceExpr(config, expr.asMethodReferenceExpr, memo)
      else if expr.isNameExpr then resolveNameExpr(log, expr.asNameExpr, config, memo)
      // else if expr.isObjectCreationExpr then
      //   resolveObjectCreationExpr(config, expr.asObjectCreationExpr, memo)
      // else if expr.isPatternExpr then
      //   resolvePatternExpr(config, expr.asPatternExpr, memo)
      else if expr.isSuperExpr then resolveSuperExpr(log, expr.asSuperExpr, config, memo)
      // else if expr.isSwitchExpr then
      //   resolveSwitchExpr(config, expr.asSwitchExpr, memo)
      else if expr.isThisExpr then resolveThisExpr(log, expr.asThisExpr, config, memo)
      else if expr.isUnaryExpr then resolveUnaryExpr(log, expr.asUnaryExpr, config, memo)
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
    memo((containingClass, containingMethod, expr)) = res.opt
    res

private def resolveScope(
    log: Log,
    expr: Expression,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  resolveExpression(log, expr, config, memo).rightmap(t =>
    t match
      case x @ TypeParameterIndex(source, id, subs) =>
        config._1(source).getErasure(x)
      case x @ TypeParameterName(sourceType, source, name, subs) =>
        config._1(sourceType).getErasure(x)
      case x @ _ => x
  )

private def resolveFieldAccessExpr(
    log: Log,
    expr: FieldAccessExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
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
            if t.isInstanceOf[InferenceVariable] && !config._2._2.contains(t) then
              config._2._2(t) = InferenceVariableMemberTable(t)
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

private def getAttrTypeFromMissingScope(
    logWithScope: LogWithOption[Type],
    expr: com.github.javaparser.ast.nodeTypes.NodeWithSimpleName[?],
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
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
      case scala.util.Left(x)  => x.getAttributeType(attributeIdentifier, context)
      case scala.util.Right(x) => x.getAttributeType(attributeIdentifier, context)

    attrAttempt
      .map((log, _))
      .getOrElse({
        // create the new declared type of the attribute
        val source = declaredType match
          case scala.util.Left(x)  => scala.util.Left(x.identifier)
          case scala.util.Right(x) => scala.util.Right(x.typet)
        val parameterChoices: Set[TTypeParameter] = declaredType match
          case scala.util.Left(x) =>
            val numParams = x.numParams
            (0 until numParams).map(TypeParameterIndex(x.identifier, _)).toSet
          case scala.util.Right(_) => Set()
        val newIV = declaredType match
          case scala.util.Left(x) =>
            InferenceVariableFactory
              .createDisjunctiveType(source, Nil, true, parameterChoices, false)
          case scala.util.Right(x) => InferenceVariableFactory.createPlaceholderType()
        // get the actual type of the attribute
        val attrType: Type = newIV.addSubstitutionLists(context)
        // add attribute to the type declaration and add to phi
        declaredType match
          case scala.util.Left(dt) =>
            val newDT = dt.addAttribute(attributeIdentifier, newIV, false)
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

private def resolveArrayAccessExpr(
    config: MutableConfiguration,
    expr: ArrayAccessExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???

private def resolveArrayInitializerExpr(
    config: MutableConfiguration,
    expr: ArrayInitializerExpr,
    memo: MutableMap[Expression, Type]
): Type = ???

private def resolveAssignExpr(
    log: Log,
    expr: AssignExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  val t = expr.getTarget
  val v = expr.getValue
  resolveExpression(log, v, config, memo).flatMap((log, v) =>
    resolveExpression(log, t, config, memo).rightmap(t =>
      expr.getOperator match
        case AssignExpr.Operator.ASSIGN =>
          // right <: left
          config._3 += SubtypeAssertion(v, t)
        case AssignExpr.Operator.BINARY_AND | AssignExpr.Operator.BINARY_OR |
            AssignExpr.Operator.XOR =>
          config._3 += (IsIntegralAssertion(v) &&
            IsIntegralAssertion(t)) ||
            (v <:~ PRIMITIVE_BOOLEAN &&
              t <:~ PRIMITIVE_BOOLEAN)
        case AssignExpr.Operator.LEFT_SHIFT | AssignExpr.Operator.SIGNED_RIGHT_SHIFT |
            AssignExpr.Operator.UNSIGNED_RIGHT_SHIFT =>
          config._3 += IsIntegralAssertion(v) && IsIntegralAssertion(t)
        case AssignExpr.Operator.DIVIDE | AssignExpr.Operator.MINUS | AssignExpr.Operator.MULTIPLY |
            AssignExpr.Operator.REMAINDER =>
          config._3 += IsNumericAssertion(v)
          config._3 += IsNumericAssertion(t)
        case AssignExpr.Operator.PLUS =>
          config._3 += (IsNumericAssertion(v)
            && IsNumericAssertion(t)) ||
            t ~=~ NormalType("java.lang.String", 0) ||
            v ~=~ NormalType("java.lang.String", 0)
      t
    )
  )
  expr.getOperator match
    case AssignExpr.Operator.ASSIGN =>
      // right <: left
      resolveExpression(log, v, config, memo).flatMap((log, v) =>
        resolveExpression(log, t, config, memo).rightmap(t =>
          config._3 += SubtypeAssertion(v, t)
          t
        )
      )
    case AssignExpr.Operator.BINARY_AND | AssignExpr.Operator.BINARY_OR | AssignExpr.Operator.XOR =>
      // both sides of op are integrals or booleans
      resolveExpression(log, v, config, memo).flatMap((log, v) =>
        resolveExpression(log, t, config, memo).rightmap(t =>
          config._3 += (IsIntegralAssertion(v) && IsIntegralAssertion(
            t
          )) || (v <:~ PRIMITIVE_BOOLEAN && t <:~ PRIMITIVE_BOOLEAN)
          t
        )
      )
    case AssignExpr.Operator.LEFT_SHIFT | AssignExpr.Operator.SIGNED_RIGHT_SHIFT |
        AssignExpr.Operator.UNSIGNED_RIGHT_SHIFT =>
      // both sides of op are integrals
      resolveExpression(log, v, config, memo).flatMap((log, v) =>
        resolveExpression(log, t, config, memo).rightmap(t =>
          config._3 += IsIntegralAssertion(v) && IsIntegralAssertion(t)
          t
        )
      )
    case AssignExpr.Operator.DIVIDE | AssignExpr.Operator.MINUS | AssignExpr.Operator.MULTIPLY |
        AssignExpr.Operator.REMAINDER =>
      resolveExpression(log, v, config, memo).flatMap((log, v) =>
        resolveExpression(log, t, config, memo).rightmap(t =>
          config._3 += IsNumericAssertion(v)
          config._3 += IsNumericAssertion(t)
          t
        )
      )
    case AssignExpr.Operator.PLUS =>
      resolveExpression(log, v, config, memo).flatMap((log, v) =>
        resolveExpression(log, t, config, memo).rightmap(t =>
          config._3 += (IsNumericAssertion(v)
            && IsNumericAssertion(t)) ||
            t ~=~ NormalType("java.lang.String", 0)
          t
        )
      )

private def resolveBinaryExpr(
    log: Log,
    expr: BinaryExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  val left  = expr.getLeft
  val right = expr.getRight
  resolveExpression(log, left, config, memo).flatMap((log, left) =>
    resolveExpression(log, right, config, memo).rightmap(right =>
      expr.getOperator match
        case BinaryExpr.Operator.AND | BinaryExpr.Operator.OR =>
          config._3 += (left <:~ PRIMITIVE_BOOLEAN && right <:~ PRIMITIVE_BOOLEAN)
          PRIMITIVE_BOOLEAN
        case BinaryExpr.Operator.BINARY_AND | BinaryExpr.Operator.BINARY_OR |
            BinaryExpr.Operator.XOR =>
          val rt = InferenceVariableFactory.createAlpha(Left(""), Nil, false, Set())
          config._2._2(rt) = InferenceVariableMemberTable(rt)
          config._3 += (IsIntegralAssertion(left) &&
            IsIntegralAssertion(right) &&
            IsIntegralAssertion(rt)) ||
            (left <:~ PRIMITIVE_BOOLEAN &&
              right <:~ PRIMITIVE_BOOLEAN &&
              rt ~=~ PRIMITIVE_BOOLEAN)
          rt
        case BinaryExpr.Operator.DIVIDE | BinaryExpr.Operator.MINUS | BinaryExpr.Operator.MULTIPLY |
            BinaryExpr.Operator.REMAINDER =>
          val rt = InferenceVariableFactory.createAlpha(Left(""), Nil, false, Set())
          config._2._2(rt) = InferenceVariableMemberTable(rt)
          config._3 += IsNumericAssertion(left) &&
            IsNumericAssertion(right) &&
            IsNumericAssertion(rt) &&
            left <:~ rt &&
            right <:~ rt
          rt
        case BinaryExpr.Operator.EQUALS | BinaryExpr.Operator.NOT_EQUALS =>
          config._3 += left <:~ right || right <:~ left
          PRIMITIVE_BOOLEAN
        case BinaryExpr.Operator.GREATER | BinaryExpr.Operator.GREATER_EQUALS |
            BinaryExpr.Operator.LESS | BinaryExpr.Operator.LESS_EQUALS =>
          config._3 += IsNumericAssertion(left) && IsNumericAssertion(right)
          PRIMITIVE_BOOLEAN
        case BinaryExpr.Operator.LEFT_SHIFT | BinaryExpr.Operator.SIGNED_RIGHT_SHIFT |
            BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT =>
          val rt = InferenceVariableFactory.createAlpha(Left(""), Nil, false, Set())
          config._2._2(rt) = InferenceVariableMemberTable(rt)
          config._3 += IsIntegralAssertion(left) &&
            IsIntegralAssertion(right) &&
            IsIntegralAssertion(rt) &&
            left <:~ rt &&
            right <:~ rt
          rt
        case BinaryExpr.Operator.PLUS =>
          val rt = InferenceVariableFactory.createAlpha(Left(""), Nil, false, Set())
          config._2._2(rt) = InferenceVariableMemberTable(rt)
          config._3 += (IsNumericAssertion(left) &&
            IsNumericAssertion(right) &&
            IsNumericAssertion(rt) &&
            left <:~ rt &&
            right <:~ rt) ||
            ((left ~=~ NormalType("java.lang.String", 0) ||
              right ~=~ NormalType("java.lang.String", 0)) &&
              rt ~=~ NormalType("java.lang.String", 0))
          rt
    )
  )

private def resolveCastExpr(
    log: Log,
    expr: CastExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  resolveExpression(log, expr.getExpression, config, memo).rightmap(e =>
    val target = resolveSolvedType(expr.getType.resolve)
    config._3 += e <:~ target || target <:~ e
    target
  )

private def resolveClassExpr(
    log: Log,
    expr: ClassExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  LogWithSome(log, resolveSolvedType(expr.calculateResolvedType))

private def resolveConditionalExpr(
    log: Log,
    expr: ConditionalExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  val cond        = expr.getCondition()
  val trueBranch  = expr.getThenExpr()
  val falseBranch = expr.getElseExpr()
  resolveExpression(log, cond, config, memo).flatMap((log, condType) =>
    resolveExpression(log, trueBranch, config, memo).flatMap((log, trueType) =>
      resolveExpression(log, falseBranch, config, memo).rightmap(falseType =>
        // create the right inference variable
        val result = createDisjunctiveTypeFromContext(expr, config)
        config._3 += condType <:~ PRIMITIVE_BOOLEAN &&
          trueType <:~ result &&
          falseType <:~ result
        result
      )
    )
  )

private def resolveEnclosedExpr(
    log: Log,
    expr: EnclosedExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] = resolveExpression(log, expr.getInner, config, memo)

private def resolveInstanceOfExpr(
    log: Log,
    expr: InstanceOfExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  resolveExpression(log, expr.getExpression, config, memo).rightmap(e =>
    val target = resolveSolvedType(expr.getType.resolve)
    config._3 += e <:~ target || target <:~ e
    PRIMITIVE_BOOLEAN
  )

private def resolveLambdaExpr(
    config: MutableConfiguration,
    expr: LambdaExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???

private def resolveMethodCallExpr(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  try LogWithOption(log, Some(resolveSolvedType(expr.calculateResolvedType)))
  catch
    case e: Throwable =>
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
                resolveMethodFromDisjunctiveType(_, expr, config, memo, _)
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
                  LogWithNone(log.addError(s"${decl.getFullyQualifiedName} cannot be resolved?"))

private def resolveMethodFromResolvedType(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ],
    scope: ResolvedType
): LogWithOption[Type] =
  if scope.isPrimitive then
    // LogWithOption(log.addError(s"${scope} cannot have methods!"), None)
    resolveMethodFromABunchOfResolvedReferenceTypes(
      log,
      expr,
      config,
      memo,
      Vector(
        com.github.javaparser.symbolsolver.resolution.typeinference.TypeHelper
          .toBoxedType(scope.asPrimitive)
          .asReferenceType
      )
    )
  else if scope.isTypeVariable then
    val bounds = getAllBoundsOfResolvedTypeParameterDeclaration(scope.asTypeParameter)
    resolveMethodFromABunchOfResolvedReferenceTypes(log, expr, config, memo, bounds)
  else
    resolveMethodFromABunchOfResolvedReferenceTypes(
      log,
      expr,
      config,
      memo,
      Vector(scope.asReferenceType)
    )

private def getMethodsFromResolvedReferenceType(t: ResolvedReferenceType): Set[MethodUsage] =
  val tpMap   = t.getTypeParametersMap.asScala.toList
  val methods = t.getDeclaredMethods.asScala.toSet
  methods.map(substituteTypeParametersInMethodUsage(tpMap, _))

@tailrec
private def substituteTypeParametersInMethodUsage(
    tpMap: List[Pair[ResolvedTypeParameterDeclaration, ResolvedType]],
    m: MethodUsage
): MethodUsage = tpMap match
  case x :: xs => substituteTypeParametersInMethodUsage(xs, m.replaceTypeParameter(x.a, x.b))
  case Nil     => m

private def resolveMethodFromABunchOfResolvedReferenceTypes(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ],
    scope: Vector[ResolvedReferenceType]
): LogWithOption[Type] =
  // get all the supertypes where methods may be defined
  val allSupertypes = scope
    .flatMap(x => x.getAllAncestors.asScala.toVector :+ x)
    .toSet
  val nameOfMethod   = expr.getNameAsString
  val numOfArguments = expr.getArguments.size
  // get all the relevant methods, i.e. the methods in the supertypes
  // that are of the same name and arity
  val relevantMethods = allSupertypes.toSet
    .flatMap(getMethodsFromResolvedReferenceType(_))
    .filter(x => x.getName == nameOfMethod && x.getParamTypes.size == numOfArguments)
    .toVector

  // get all of the missing supertypes
  val missingSupertypes = allSupertypes
    .filter(x => config._2._1.contains(x.getId))
    .toVector

  // get the types of the arguments suplied to the method
  val originalArguments = flatMapWithLog(log, expr.getArguments.asScala.toVector)(
    resolveExpression(_, _, config, memo)
  )
  // case of no methods and no missing supertypes
  if relevantMethods.size == 0 && missingSupertypes.size == 0 then
    LogWithOption(
      log.addError(
        s"$expr cannot be resolved",
        s"method declaration for ${nameOfMethod} cannot be" +
          s" found in fully-declared ${if scope.size == 1 then scope(0) else scope}"
      ),
      None
    )
  // case of only one method and no missing supertypes
  else if relevantMethods.size == 1 && missingSupertypes.size == 0 then
    // get the method in question since we unambiguously must be referring
    // to this method
    val method = relevantMethods(0)
    originalArguments
      .rightmap(matchMethodCallToMethodUsage(method, _, expr, config))
      .rightmap(x =>
        x._1.foreach(y => config._3 += y)
        x._2
      )
  // case of no methods and only one missing supertype
  else if relevantMethods.size == 0 && missingSupertypes.size == 1 then
    // get and/or add the method to the declaration since we must be
    // umambiguously referring to this method
    // the type itself
    val missingTType = resolveSolvedType(missingSupertypes(0))
    // the declaration of the type
    val missingDeclaration = config._2._1(missingSupertypes(0).getId)
    // the context (type arguments) of this type
    val context = missingTType.substitutions
    val z = originalArguments.rightflatMap(argTypes =>
      missingDeclaration.getMethodReturnType(nameOfMethod, argTypes, context)
    )
    z match
      case LogWithSome(_, _) => z
      case LogWithNone(_)    =>
        // either the original arguments were None, or the method doesn't exist
        // in the declaration yet
        originalArguments.rightmap(args =>
          val returnType = createDisjunctiveTypeFromContext(expr, config)
          // create the new declaration by adding the method
          config._2._1 += (missingDeclaration.identifier -> missingDeclaration
            .addMethod(
              nameOfMethod,
              args,
              returnType,
              Map(),
              PUBLIC,
              false,
              false,
              false,
              context
            ))
          returnType
        )
  else
    // we're not sure what the return type should be, so we have a placeholder
    // and encode the choices using a dijunctive assertion of conjuctive assertions
    val returnType = createDisjunctiveTypeFromContext(expr, config)
    // assertions from the bunch of declared methods (might be empty)
    val ambiguousDeclaredMethods = originalArguments
      .rightmap(args =>
        relevantMethods.map(method => matchMethodCallToMethodUsage(method, args, expr, config))
      )
      .rightvmap[(Vector[SubtypeAssertion], Type), ConjunctiveAssertion](tp =>
        val (assts, rt) = tp
        ConjunctiveAssertion(assts :+ EquivalenceAssertion(returnType, rt))
      )
    val ambiguousMissingTypes = originalArguments.rightmap(args =>
      // create conjunctive assertions
      missingSupertypes.map(rrt =>
        val missingTType = resolveSolvedType(rrt)
        HasMethodAssertion(missingTType, nameOfMethod, args, returnType)
      )
    )
    (ambiguousDeclaredMethods, ambiguousMissingTypes) match
      case (LogWithSome(log1, conjassts), LogWithSome(log2, hmassts)) =>
        config._3 += DisjunctiveAssertion(conjassts ++ hmassts)
        LogWithSome(log1, returnType)
      case (LogWithNone(log), _) => LogWithNone(log)
      case (_, LogWithNone(log)) => LogWithNone(log)

private def createDisjunctiveTypeFromContext(
    expr: Expression,
    config: MutableConfiguration
): DisjunctiveType =
  // get the parameter choices
  val parameterChoices = getParamChoicesFromExpression(expr)
  val source = Left(
    expr
      .findAncestor(classOf[MethodDeclaration])
      .toScala
      .map(_.resolve.getQualifiedSignature)
      .getOrElse(
        expr
          .findAncestor(classOf[ClassOrInterfaceDeclaration])
          // scary
          .get
          .getNameAsString
      )
  )

  val iv = InferenceVariableFactory
    .createDisjunctiveType(source, Nil, true, parameterChoices, false)
  config._2._2(iv) = InferenceVariableMemberTable(iv)
  iv

def getParamChoicesFromExpression(expr: Expression) =
  // get the parameter choices
  val methodTypeParams = expr
    .findAncestor(classOf[MethodDeclaration])
    .toScala
    .map(_.getTypeParameters.asScala.toVector.map(x => x.resolve))
    .getOrElse(Vector())
  val classTypeParams =
    val decl = expr.findAncestor(classOf[ClassOrInterfaceDeclaration]).toScala
    decl match
      case None => Vector()
      case Some(x) =>
        val declParams = x.getTypeParameters.asScala.toVector
        (0 until declParams.length)
          .filter(i =>
            !methodTypeParams
              .map(_.asTypeParameter.getName)
              .contains(declParams(i).getNameAsString)
          )
          .map(i => TypeParameterIndex(x.getName.getIdentifier, i))
  methodTypeParams.map(resolveSolvedTypeVariable(_)).toSet ++ classTypeParams

private def matchMethodCallToMethodUsage(
    method: MethodUsage,
    arguments: Vector[Type],
    expr: MethodCallExpr,
    config: MutableConfiguration
) =
  // get the type parameters of the method
  val methodDeclaration = method.getDeclaration
  val containingType    = methodDeclaration.declaringType
  val typeParams        = methodDeclaration.getTypeParameters.asScala.toVector
  // substitute type parameters with inference variables
  val substitutionList: List[Map[TTypeParameter, Type]] = (typeParams
    .map(tpd =>
      // create the type parameter
      val p = TypeParameterName(
        containingType.getQualifiedName,
        tpd.getContainerId,
        tpd.getName
      )
      // create the inference variable
      val iv = createDisjunctiveTypeFromContext(expr, config)
      // add new inference variable to phi
      config._2._2(iv) = InferenceVariableMemberTable(iv)
      // return mapping
      p -> iv
    )
    .toMap :: Nil).filter(x => x.size > 0)
  // get the types of the formal parameters
  val actualMethodTypes = method.getParamTypes.asScala.map(resolveSolvedType(_))
  // size of originalArguments is definitely the same as actualMethodTypes
  val x = arguments
    .zip(actualMethodTypes)
    .map(x => SubtypeAssertion(x._1, x._2))
  (x, resolveSolvedType(method.returnType).addSubstitutionLists(substitutionList))

private def getAllBoundsOfResolvedTypeParameterDeclaration(
    tp: ResolvedTypeParameterDeclaration
): Vector[ResolvedReferenceType] =
  // get erasure of the type parameter via BFS
  val frontier = Queue[ResolvedType](tp.getBounds.asScala.map(_.getType).toSeq: _*)
  val res      = ArrayBuffer[ResolvedReferenceType]()
  val visited  = MutableSet[ResolvedType]()
  while !frontier.isEmpty do
    val typet = frontier.dequeue
    if !visited.contains(typet) then
      visited.add(typet)
      if !typet.isTypeVariable then res.addOne(typet.asReferenceType)
      else frontier.addAll(typet.asTypeParameter.getBounds.asScala.map(_.getType))
  if res.isEmpty then
    val rts = ReflectionTypeSolver()
    Vector(ReferenceTypeImpl(rts.getSolvedJavaLangObject, rts))
  else res.toVector

private def resolveMethodFromResolvedDeclaration(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ],
    scope: ResolvedReferenceTypeDeclaration
): LogWithOption[Type] = ???

private def resolveMethodFromDisjunctiveType(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ],
    scope: Type
): LogWithOption[Type] = ???

private def resolveMethodReferenceExpr(
    config: MutableConfiguration,
    expr: MethodReferenceExpr,
    memo: MutableMap[Expression, Type]
): Type = ???

private def resolveNameExpr(
    log: Log,
    expr: NameExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
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

private def resolveObjectCreationExpr(
    config: MutableConfiguration,
    expr: ObjectCreationExpr,
    memo: MutableMap[Expression, Type]
): Type = ???

private def resolvePatternExpr(
    config: MutableConfiguration,
    expr: PatternExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???

private def resolveSuperExpr(
    log: Log,
    expr: SuperExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  try LogWithSome(log, resolveSolvedType(expr.calculateResolvedType))
  catch
    case e: Throwable =>
      LogWithNone(
        log.addError(s"super can only be present in a type with a supertype", expr.toString)
      )

private def resolveSwitchExpr(
    config: MutableConfiguration,
    expr: SwitchExpr,
    memo: MutableMap[Expression, Type]
): Type =
  ???

private def resolveThisExpr(
    log: Log,
    expr: ThisExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  LogWithSome(log, resolveSolvedType(expr.calculateResolvedType))

private def resolveUnaryExpr(
    log: Log,
    expr: UnaryExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  resolveExpression(log, expr.getExpression, config, memo).rightmap(x =>
    expr.getOperator match
      case UnaryExpr.Operator.BITWISE_COMPLEMENT =>
        val rt = InferenceVariableFactory.createAlpha(Left(""), Nil, false, Set())
        config._2._2(rt) = InferenceVariableMemberTable(rt)
        config._3 += IsIntegralAssertion(x) &&
          IsIntegralAssertion(rt) &&
          x <:~ rt
        rt
      case UnaryExpr.Operator.LOGICAL_COMPLEMENT =>
        config._3 += x <:~ PRIMITIVE_BOOLEAN
        PRIMITIVE_BOOLEAN
      case UnaryExpr.Operator.MINUS | UnaryExpr.Operator.PLUS |
          UnaryExpr.Operator.POSTFIX_DECREMENT | UnaryExpr.Operator.POSTFIX_INCREMENT |
          UnaryExpr.Operator.PREFIX_DECREMENT | UnaryExpr.Operator.PREFIX_INCREMENT =>
        val rt = InferenceVariableFactory.createAlpha(Left(""), Nil, false, Set())
        config._2._2(rt) = InferenceVariableMemberTable(rt)
        config._3 += IsNumericAssertion(x) &&
          IsNumericAssertion(rt) &&
          x <:~ rt
        rt
  )

private def resolveVariableDeclarationExpr(
    log: Log,
    expr: VariableDeclarationExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[Type] =
  LogWithOption(log, Some(resolveSolvedType(expr.calculateResolvedType)))
