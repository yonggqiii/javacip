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
import scala.util.{Try, Success, Failure}
import scala.collection.mutable.Map as MutableMap
import scala.collection.mutable.Set as MutableSet
import scala.collection.mutable.{Queue, ArrayBuffer}
import configuration.Configuration
import configuration.MutableConfiguration
import configuration.declaration.*
import configuration.assertions.*
import configuration.types.*
import configuration.Invocation
import utils.*
import scala.annotation.tailrec
import com.github.javaparser.ast.comments.BlockComment

private[configuration] def resolveExpression(
    log: Log,
    expr: Expression,
    config: MutableConfiguration,
    memo: MutableMap[String, Option[Type]]
): LogWithOption[Type] =
  val rng = expr.getRange().toScala.get.toString
  if memo.contains(rng) then LogWithOption(log, memo(rng))
  else
    // println(expr)
    // if expr.isNameExpr() then
    //   val t = expr.replace(
    //     FieldAccessExpr(
    //       NameExpr("JavaCIPUnknownVariableStore"),
    //       expr.asNameExpr().getNameAsString()
    //     )
    //   )
    //   println(t)
    //   println(expr)
    // println(expr.getClass())
    val res =
      if expr.isFieldAccessExpr then
        resolveFieldAccessExpr(log, expr.asFieldAccessExpr, config, memo)
      else if expr.isArrayAccessExpr then
        resolveArrayAccessExpr(log, expr.asArrayAccessExpr, config, memo)
      else if expr.isArrayCreationExpr() then
        resolveArrayCreationExpr(log, expr.asArrayCreationExpr(), config, memo)
      else if expr.isArrayInitializerExpr then
        resolveArrayInitializerExpr(log, expr.asArrayInitializerExpr, config, memo)
      else if expr.isAssignExpr then resolveAssignExpr(log, expr.asAssignExpr, config, memo)
      else if expr.isBinaryExpr then resolveBinaryExpr(log, expr.asBinaryExpr, config, memo)
      else if expr.isCastExpr then resolveCastExpr(log, expr.asCastExpr, config, memo)
      else if expr.isClassExpr then resolveClassExpr(log, expr.asClassExpr, config, memo)
      else if expr.isConditionalExpr then
        resolveConditionalExpr(log, expr.asConditionalExpr, config, memo)
      else if expr.isEnclosedExpr then resolveEnclosedExpr(log, expr.asEnclosedExpr, config, memo)
      else if expr.isInstanceOfExpr then
        resolveInstanceOfExpr(log, expr.asInstanceOfExpr, config, memo)
      else if expr.isLambdaExpr then
        LogWithNone(log.addError(s"JavaCIP does not support lambda expressions: $expr"))
      else if expr.isMethodCallExpr then
        resolveMethodCallExpr(log, expr.asMethodCallExpr, config, memo)
      else if expr.isMethodReferenceExpr then
        LogWithNone(log.addError(s"JavaCIP does not support method reference expressions: $expr"))
      else if expr.isNameExpr then resolveNameExpr(log, expr.asNameExpr, config, memo)
      else if expr.isObjectCreationExpr then
        resolveObjectCreationExpr(log, expr.asObjectCreationExpr(), config, memo)
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
    memo(rng) = res.opt
    res

def resolveArrayCreationExpr(
    log: Log,
    expr: ArrayCreationExpr,
    config: MutableConfiguration,
    memo: MutableMap[String, Option[Type]]
): LogWithOption[Type] =
  val levels      = expr.getLevels()
  val elementType = resolveSolvedType(expr.getElementType().resolve())
  var res         = elementType
  for e <- levels.asScala do
    e.getDimension().toScala match
      case Some(dim) =>
        val t = resolveExpression(log, dim, config, memo)
        t match
          case LogWithSome(log, some) => config._3 += some =:~ PRIMITIVE_INT
          case LogWithNone(log)       => ()
      case None => ()
    res = ArrayType(res)
  expr.getInitializer().toScala match
    case Some(initializer) =>
      resolveExpression(log, initializer, config, memo).consume((l, t) =>
        config._3 += t ~=~ res
        log
      )
    case None => ()
  LogWithSome(log, res)

private def resolveScope(
    log: Log,
    expr: Expression,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  resolveExpression(log, expr, config, memo).rightmap(t =>
    t match
      case x: TTypeParameter =>
        config._1(x.containingTypeIdentifier).getLeftmostReferenceTypeBoundOfTypeParameter(x)
      case x => x.upwardProjection
  )

private def getAttributeTypeOrContainer(
    config: MutableConfiguration,
    currentType: ClassOrInterfaceType,
    attributeName: String
): Option[Either[Type, ClassOrInterfaceType]] =
  if config._2._1.contains(currentType.identifier) then return Some(Right(currentType))
  // is declared
  if !config._1.contains(currentType.identifier) then
    // is reflection type
    try
      val emptyConfig = Configuration.createEmptyConfiguration()
      val x = emptyConfig
        .getSubstitutedDeclaration(currentType)
      val attributes = x.getAccessibleAttributes(emptyConfig, PUBLIC)
      if !attributes.contains(attributeName) then return None
      return Some(Left(attributes(attributeName).`type`))
    catch
      case _: Throwable =>
        return None
  // in delta
  val (_, context) = currentType.expansion
  val ud           = config._1(currentType.identifier)
  if !ud.attributes.contains(attributeName) then
    if !ud.isClass || ud.extendedTypes.isEmpty then return None
    return getAttributeTypeOrContainer(
      config,
      ud.extendedTypes(0).substitute(context),
      attributeName
    )
  return Some(Left(ud.attributes(attributeName).`type`.substitute(context)))

private def resolveFieldAccessExpr(
    log: Log,
    expr: FieldAccessExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  resolveScope(log, expr.getScope, config, memo).flatMap((log, uncapturedScope) =>
    config._6 += uncapturedScope
    val scope = uncapturedScope.captured
    if scope.isInstanceOf[ArrayType] then LogWithOption(log, Some(PRIMITIVE_INT))
    else if !scope.isInstanceOf[InferenceVariable] then
      val attrTypeOrContainer = getAttributeTypeOrContainer(
        config,
        scope.asInstanceOf[ClassOrInterfaceType],
        expr.getNameAsString
      )
      attrTypeOrContainer match
        case None => LogWithNone(log.addError(s"cannot find ${expr.getNameAsString()} in $scope!"))
        case Some(x) =>
          x match
            case Left(attrType) => LogWithSome(log, attrType)
            case Right(missingType) =>
              getAttrTypeFromMissingScope(LogWithSome(log, missingType), expr, config, memo)
    else
      if !config._2._2.contains(scope) then
        config._2._2(scope) = InferenceVariableMemberTable(scope)
      getAttrTypeFromMissingScope(LogWithSome(log, scope), expr, config, memo)
  )

private def getAttrTypeFromMissingScope(
    logWithScope: LogWithOption[Type],
    expr: com.github.javaparser.ast.nodeTypes.NodeWithSimpleName[?],
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
) =
  logWithScope.map((log, scope) =>
    config._6 += scope
    // add assertion to omega
    config._3 += IsClassAssertion(scope)
    // get substitutions in scope
    val (_, context) = scope.expansion
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
            if scope.static then Set()
            else
              val numParams = x.numParams
              (0 until numParams).map(TypeParameterIndex(x.identifier, _)).toSet
          case scala.util.Right(_) => Set()
        val newIV = declaredType match
          case scala.util.Left(x) =>
            InferenceVariableFactory
              .createDisjunctiveTypeWithPrimitives(source, Nil, true, parameterChoices)
          case scala.util.Right(x) => InferenceVariableFactory.createPlaceholderType()
        config._4 += newIV
        // get the actual type of the attribute
        val attrType: Type = newIV.substitute(context)
        // add attribute to the type declaration and add to phi
        declaredType match
          case scala.util.Left(dt) =>
            val newDT = dt.addAttribute(attributeIdentifier, newIV, scope.static)
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
    log: Log,
    expr: ArrayAccessExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  val name  = expr.getName
  val index = expr.getIndex
  resolveExpression(log, name, config, memo).flatMap((log, name) =>
    resolveExpression(log, index, config, memo).rightmap(index =>
      // array indices must be an integer
      config._3 += (index =:~ PRIMITIVE_INT)
      name match
        case ArrayType(base) => base
        case _ =>
          val paramChoices = getParamChoicesFromExpression(expr)
          val rt           = createDisjunctiveTypeWithPrimitivesFromContext(expr, config)
          // nameexpr must be an instance of rt[]
          config._3 += (name <:~ ArrayType(rt))
          rt
    )
  )

private def resolveArrayInitializerExpr(
    log: Log,
    expr: ArrayInitializerExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  val elementType = createDisjunctiveTypeWithPrimitivesFromContext(expr, config)
  val res         = ArrayType(elementType)
  config._4 += elementType
  config._4 += res
  val values = expr.getValues().asScala.toVector
  flatMapWithLog(log, values)((log, v) => resolveExpression(log, v, config, memo))
    .rightvmap[Type, Unit](t =>
      config._3 += t =:~ elementType
      ()
    )
  LogWithSome(log, res)

private def resolveAssignExpr(
    log: Log,
    expr: AssignExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  val t = expr.getTarget
  val v = expr.getValue
  resolveExpression(log, v, config, memo).flatMap((log, v) =>
    resolveExpression(log, t, config, memo).rightmap(t =>
      config._6 += v
      config._6 += t
      expr.getOperator match
        case AssignExpr.Operator.ASSIGN =>
          // right <: left
          config._3 += (t ~:= v) // SubtypeAssertion(v, t)
        case AssignExpr.Operator.BINARY_AND | AssignExpr.Operator.BINARY_OR |
            AssignExpr.Operator.XOR =>
          config._3 += (IsIntegralAssertion(v) &&
            IsIntegralAssertion(t)) ||
            (v =:~ PRIMITIVE_BOOLEAN &&
              t =:~ PRIMITIVE_BOOLEAN)
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
            t ~=~ STRING ||
            v ~=~ STRING
      t
    )
  )

private def resolveBinaryExpr(
    log: Log,
    expr: BinaryExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  val left  = expr.getLeft
  val right = expr.getRight
  resolveExpression(log, left, config, memo).flatMap((log, left) =>
    resolveExpression(log, right, config, memo).rightmap(right =>
      config._6 += left
      config._6 += right
      expr.getOperator match
        case BinaryExpr.Operator.AND | BinaryExpr.Operator.OR =>
          config._3 += (left =:~ PRIMITIVE_BOOLEAN && right =:~ PRIMITIVE_BOOLEAN)
          PRIMITIVE_BOOLEAN
        case BinaryExpr.Operator.BINARY_AND | BinaryExpr.Operator.BINARY_OR |
            BinaryExpr.Operator.XOR =>
          val rt = InferenceVariableFactory.createPrimitivesOnlyDisjunctiveType()
          config._4 += rt
          config._2._2(rt) = InferenceVariableMemberTable(rt)
          config._3 += (IsIntegralAssertion(left) &&
            IsIntegralAssertion(right) &&
            IsIntegralAssertion(rt)) ||
            ((left =:~ PRIMITIVE_BOOLEAN) &&
              (right =:~ PRIMITIVE_BOOLEAN) &&
              (rt ~=~ PRIMITIVE_BOOLEAN))
          rt
        case BinaryExpr.Operator.DIVIDE | BinaryExpr.Operator.MINUS | BinaryExpr.Operator.MULTIPLY |
            BinaryExpr.Operator.REMAINDER =>
          val rt = InferenceVariableFactory.createPrimitivesOnlyDisjunctiveType()
          config._4 += rt
          config._2._2(rt) = InferenceVariableMemberTable(rt)
          config._3 += IsNumericAssertion(left) &&
            IsNumericAssertion(right) &&
            IsNumericAssertion(rt) &&
            left =:~ rt &&
            right =:~ rt
          rt
        case BinaryExpr.Operator.EQUALS | BinaryExpr.Operator.NOT_EQUALS =>
          config._3 += (left =:~ right) || (right =:~ left)
          PRIMITIVE_BOOLEAN
        case BinaryExpr.Operator.GREATER | BinaryExpr.Operator.GREATER_EQUALS |
            BinaryExpr.Operator.LESS | BinaryExpr.Operator.LESS_EQUALS =>
          config._3 += IsNumericAssertion(left) && IsNumericAssertion(right)
          PRIMITIVE_BOOLEAN
        case BinaryExpr.Operator.LEFT_SHIFT | BinaryExpr.Operator.SIGNED_RIGHT_SHIFT |
            BinaryExpr.Operator.UNSIGNED_RIGHT_SHIFT =>
          val rt = InferenceVariableFactory.createPrimitivesOnlyDisjunctiveType()
          config._4 += rt
          config._2._2(rt) = InferenceVariableMemberTable(rt)
          config._3 += IsIntegralAssertion(left) &&
            IsIntegralAssertion(right) &&
            IsIntegralAssertion(rt) &&
            (left =:~ rt) &&
            (right =:~ rt)
          rt
        case BinaryExpr.Operator.PLUS =>
          val rt = InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
            scala.util.Left(""),
            Nil,
            false,
            Set(),
            false
          )
          config._4 += rt
          config._2._2(rt) = InferenceVariableMemberTable(rt)
          config._3 += (IsNumericAssertion(left) &&
            IsNumericAssertion(right) &&
            IsNumericAssertion(rt) &&
            (left =:~ rt) &&
            (right =:~ rt)) ||
            (((left ~=~ STRING) ||
              (right ~=~ STRING)) &&
              (rt ~=~ STRING))
          rt
    )
  )

private def resolveCastExpr(
    log: Log,
    expr: CastExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  resolveExpression(log, expr.getExpression, config, memo).rightmap(e =>
    config._6 += e
    val target = resolveSolvedType(expr.getType.resolve)
    config._3 += e =:~ target || target =:~ e
    target
  )

private def resolveClassExpr(
    log: Log,
    expr: ClassExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  LogWithSome(log, resolveSolvedType(expr.calculateResolvedType))

private def resolveConditionalExpr(
    log: Log,
    expr: ConditionalExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  val cond        = expr.getCondition()
  val trueBranch  = expr.getThenExpr()
  val falseBranch = expr.getElseExpr()
  resolveExpression(log, cond, config, memo).flatMap((log, condType) =>
    resolveExpression(log, trueBranch, config, memo).flatMap((log, trueType) =>
      resolveExpression(log, falseBranch, config, memo).rightmap(falseType =>
        config._6 ++= Vector(condType, trueType, falseType)
        // create the right inference variable
        val result = createDisjunctiveTypeWithPrimitivesFromContext(expr, config)
        config._4 += result
        config._3 += condType =:~ PRIMITIVE_BOOLEAN &&
          trueType =:~ result &&
          falseType =:~ result
        result
      )
    )
  )

private def resolveEnclosedExpr(
    log: Log,
    expr: EnclosedExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] = resolveExpression(log, expr.getInner, config, memo)

private def resolveInstanceOfExpr(
    log: Log,
    expr: InstanceOfExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  resolveExpression(log, expr.getExpression, config, memo).rightmap(e =>
    config._6 += e
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

private def resolveStaticMethodCallExpr(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[String, Option[Type]],
    scope: ResolvedType
): LogWithOption[Type] =
  val nameOfMethod   = expr.getNameAsString
  val numOfArguments = expr.getArguments.size
  // make sure scope is static
  val resolvedScope = resolveSolvedType(scope).asInstanceOf[ClassOrInterfaceType].toStaticType
  val isMissing     = config._2._1.contains(resolvedScope.identifier)
  // make sure relevant methods are static; only when scope is declared
  val relevantMethods =
    if !isMissing then
      getAllRelevantMethods(resolvedScope, config, nameOfMethod, numOfArguments)
        .filter(_.isStatic)
    else Vector()

  // call-site parameter choices
  val paramChoices = getParamChoicesFromExpression(expr)

  // get the types of the arguments suplied to the method
  val originalArguments = flatMapWithLog(log, expr.getArguments.asScala.toVector)(
    resolveExpression(_, _, config, memo)
  )
  originalArguments.rightmap(v => v.foreach(x => config._6 += x))
  // case of no methods and scope is not missing
  if relevantMethods.size == 0 && !isMissing then
    LogWithOption(
      log.addError(
        s"$expr cannot be resolved",
        s"static method declaration for ${nameOfMethod} cannot be" +
          s" found in declared ${resolvedScope}"
      ),
      None
    )
  // case of only one method
  else if relevantMethods.size == 1 then
    // get the method in question since we unambiguously must be referring
    // to this method
    val method = relevantMethods(0)
    originalArguments
      .rightmap(v => (v -> method.callWith(v, paramChoices)))
      .rightmap((args, x) =>
        x._1.foreach(y => config._3 += y)
        // TODO make this a static invocation
        config._5 += Invocation(resolvedScope, nameOfMethod, args, x._2, paramChoices)
        x._2
      )

  // case of scope being a missing type
  else if isMissing then
    originalArguments.rightmap(args =>
      val missingDeclaration = config._2._1(resolvedScope.identifier)
      val returnType         = createReturnTypeFromContext(expr, config)
      config._4 += returnType
      // create a new declaration by adding the method
      config._2._1 += (missingDeclaration.identifier -> missingDeclaration.addMethod(
        nameOfMethod,
        args,
        returnType,
        Vector(),
        PUBLIC,
        false,
        true,
        false,
        paramChoices,
        Map()
      ))
      config._5 += Invocation(
        resolvedScope,
        nameOfMethod,
        args,
        returnType,
        paramChoices
      )
      returnType
    )
  else
    // we're not sure what the return type should be, so we have a placeholder
    // and encode the choices using a disjunctive assertion of conjuctive assertions
    val returnType = InferenceVariableFactory.createPlaceholderType()
    config._4 += returnType
    originalArguments.rightmap(v =>
      config._5 += Invocation(resolvedScope, nameOfMethod, v, returnType, paramChoices)
    )
    val missingReturnType = createReturnTypeFromContext(expr, config)
    config._4 += missingReturnType
    val ambiguousDeclaredMethods = originalArguments
      .rightmap(args => relevantMethods.map(method => method.callWith(args, paramChoices)))
      .rightvmap[(Vector[Assertion], Type), ConjunctiveAssertion]((argsAssts, rt) =>
        ConjunctiveAssertion(argsAssts :+ (returnType ~=~ rt))
      )
    ambiguousDeclaredMethods.rightmap(assts =>
      config._3 += DisjunctiveAssertion(assts)
      returnType
    )

private def resolveMethodCallExpr(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  // check for static invocation
  expr.getScope.toScala
    .filter(_.isInstanceOf[NameExpr])
    .map(_.asInstanceOf[NameExpr])
    .map(x => (Try(x.resolve().getType()), Try(x.calculateResolvedType()))) match
    case Some(Failure(_), Success(x)) =>
      return resolveStaticMethodCallExpr(log, expr, config, memo, x)
    case _ => ()

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
          resolveExpression(log, x, config, memo).flatMap((newLog, t) =>
            t match
              case x: InferenceVariable =>
                resolveMethodFromDisjunctiveType(
                  newLog,
                  expr,
                  config,
                  memo,
                  x.captured.asInstanceOf[InferenceVariable]
                )
              case _ => ???
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
              resolveMethodFromResolvedType(
                log,
                expr,
                config,
                memo,
                new ReferenceTypeImpl(resolvedDecl, ReflectionTypeSolver()) // does this work?
              )
            case scala.util.Failure(_) =>
              LogWithNone(log.addError(s"${decl.getFullyQualifiedName} cannot be resolved?"))

private def resolveMethodFromResolvedType(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ],
    scope: ResolvedType
): LogWithOption[Type] =
  if scope.isPrimitive then LogWithOption(log.addError(s"${scope} cannot have methods!"), None)
  else if scope.isTypeVariable then
    val bounds = getAllBoundsOfResolvedTypeParameterDeclaration(scope.asTypeParameter)
    resolveMethodFromABunchOfResolvedReferenceTypes(
      log,
      expr,
      config,
      memo,
      bounds,
      resolveSolvedType(scope).captured
    )
  else
    resolveMethodFromABunchOfResolvedReferenceTypes(
      log,
      expr,
      config,
      memo,
      Vector(scope.asReferenceType),
      resolveSolvedType(scope).captured
    )

private def getAllKnownSuperTypesOfOneType(
    config: MutableConfiguration,
    t: ClassOrInterfaceType,
    exclusions: Set[String]
): Set[ClassOrInterfaceType] =
  if exclusions.contains(t.identifier) then return Set()
  // is it declared?
  if !config._2._1.contains(t.identifier) && !config._1.contains(t.identifier) then
    // reflection
    val ec = Configuration.createEmptyConfiguration()
    return ec.getAllKnownSupertypes(t).map(_.asInstanceOf[ClassOrInterfaceType]) + t
  val decl: Declaration =
    if config._2._1.contains(t.identifier) then config._2._1(t.identifier)
    else config._1(t.identifier)
  val (_, context) = t.expansion
  val s =
    decl.getDirectAncestors
      .map(_.asInstanceOf[ClassOrInterfaceType])
      .map(_.substitute(context))
      .toSet
  //getAllKnownSupertypes(config, s, exclusions + t.identifier)
  s.flatMap(x => getAllKnownSuperTypesOfOneType(config, x, exclusions + (t.identifier))) + t

def getAllRelevantMethods(
    t: ClassOrInterfaceType,
    config: MutableConfiguration,
    nameOfMethod: String,
    numArgs: Int
): Vector[Method] =
  val sd =
    if !config._1.contains(t.identifier) then
      val c = Configuration.createEmptyConfiguration()
      c.getSubstitutedDeclaration(t)
    else
      val ud           = config._1(t.identifier)
      val (_, context) = t.expansion
      ud.substitute(context)
  val methods = sd.methods
  if !methods.contains(nameOfMethod) then Vector()
  else methods(nameOfMethod).filter(m => m.callableWithNArgs(numArgs))

private def resolveMethodFromABunchOfResolvedReferenceTypes(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ],
    scope: Vector[ResolvedReferenceType],
    originalScope: Type
): LogWithOption[Type] =
  // get all the supertypes where methods may be defined
  val capturedScopes = scope
    .map(resolveSolvedType(_))
    .map(_.captured)
    .map(_.asInstanceOf[ClassOrInterfaceType])
    .toSet[ClassOrInterfaceType]
    .flatMap(x => getAllKnownSuperTypesOfOneType(config, x, Set()))
  val (missingScopes, fixedScopes) =
    capturedScopes.partition(x => config._2._1.contains(x.identifier))
  // val allSupertypes = scope
  //   .flatMap(x => x.getAllAncestors.asScala.toVector :+ x)
  //   .toSet
  val nameOfMethod   = expr.getNameAsString
  val numOfArguments = expr.getArguments.size
  // get all the relevant methods, i.e. the methods in the supertypes
  // that are of the same name and arity
  // val relevantMethods = allSupertypes.toSet
  //   .flatMap(getMethodsFromResolvedReferenceType(_))
  //   .filter(x =>
  //     x.getName == nameOfMethod && (x.getParamTypes.size == numOfArguments || (x
  //       .getDeclaration()
  //       .hasVariadicParameter() && x.getParamTypes.size < numOfArguments))
  //   )
  //   .toVector
  val relevantMethods = fixedScopes
    .flatMap(getAllRelevantMethods(_, config, nameOfMethod, numOfArguments))
    .toVector
  val paramChoices = getParamChoicesFromExpression(expr)
  // get all of the missing supertypes
  // val missingSupertypes = allSupertypes
  //   .filter(x => config._2._1.contains(x.getId))
  //   .toVector

  // get the types of the arguments suplied to the method
  val originalArguments = flatMapWithLog(log, expr.getArguments.asScala.toVector)(
    resolveExpression(_, _, config, memo)
  )
  originalArguments.rightmap(v => v.foreach(x => config._6 += x))
  // case of no methods and no missing supertypes
  if relevantMethods.size == 0 && missingScopes.size == 0 then
    LogWithOption(
      log.addError(
        s"$expr cannot be resolved",
        s"method declaration for ${nameOfMethod} cannot be" +
          s" found in fully-declared ${originalScope}"
      ),
      None
    )
  // case of only one method and no missing supertypes
  else if relevantMethods.size == 1 && missingScopes.size == 0 then
    // get the method in question since we unambiguously must be referring
    // to this method
    val method = relevantMethods(0)
    originalArguments
      .rightmap(v => (v -> method.callWith(v, paramChoices)))
      .rightmap((args, x) =>
        x._1.foreach(y => config._3 += y)
        config._5 += Invocation(originalScope, nameOfMethod, args, x._2, paramChoices)
        x._2
      )

  // originalArguments
  //   .rightmap(v => (v -> matchMethodCallToMethodUsage(method, v, expr, config)))
  //   .rightmap((args, x) =>
  //     x._1.foreach(y => config._3 += y)
  //     config._5 += Invocation(originalScope, nameOfMethod, args, x._2, paramChoices)
  //     x._2
  //   )

  // case of no methods and only one missing supertype
  else if relevantMethods.size == 0 && missingScopes.size == 1 then
    // get and/or add the method to the declaration since we must be
    // umambiguously referring to this method
    // the type itself; safe since its a missing type, must be a ClassOrInterfaceType
    val missingTType = missingScopes.toVector(0)
    //resolveSolvedType(missingScopes(0)).asInstanceOf[ClassOrInterfaceType]
    // the declaration of the type
    val missingDeclaration = config._2._1(missingTType.identifier)
    // the context (type arguments) of this type
    val (_, context) = missingTType.expansion

    val z = originalArguments.rightflatMap(argTypes =>
      missingDeclaration.getMethodReturnType(nameOfMethod, argTypes, paramChoices, context)
    )
    z match
      case LogWithSome(_, _) => z
      case LogWithNone(_)    =>
        // either the original arguments were None, or the method doesn't exist
        // in the declaration yet
        originalArguments.rightmap(args =>
          val returnType = createReturnTypeFromContext(expr, config)
          config._4 += returnType
          // create the new declaration by adding the method
          config._2._1 += (missingDeclaration.identifier -> missingDeclaration
            .addMethod(
              nameOfMethod,
              args,
              returnType,
              Vector(),
              PUBLIC,
              false,
              false,
              false,
              paramChoices,
              context
            ))
          config._5 += Invocation(
            originalScope,
            nameOfMethod,
            args,
            returnType,
            paramChoices
          )
          returnType
        )
  else
    // we're not sure what the return type should be, so we have a placeholder
    // and encode the choices using a disjunctive assertion of conjuctive assertions
    val returnType = InferenceVariableFactory.createPlaceholderType()
    config._4 += returnType
    originalArguments.rightmap(v =>
      config._5 += Invocation(originalScope, nameOfMethod, v, returnType, paramChoices)
    )
    val missingReturnType = createReturnTypeFromContext(expr, config)
    config._4 += missingReturnType
    // val returnType = createReturnTypeFromContext(expr, config)
    // config._4 += returnType
    // originalArguments.rightmap(v =>
    //   config._5 += Invocation(originalScope, nameOfMethod, v, returnType, paramChoices)
    // )
    // assertions from the bunch of declared methods (might be empty)
    // val ambiguousDeclaredMethods = originalArguments
    //   .rightmap(args => relevantMethods.map(method => matchMethodCallToMethodUsage(method, args, expr, config))
    //   )
    //   .rightvmap[(Vector[Assertion], Type), ConjunctiveAssertion](tp =>
    //     val (assts, rt) = tp
    //     ConjunctiveAssertion(assts :+ (returnType ~=~ rt))
    //   )
    val ambiguousDeclaredMethods = originalArguments
      .rightmap(args => relevantMethods.map(method => method.callWith(args, paramChoices)))
      .rightvmap[(Vector[Assertion], Type), ConjunctiveAssertion]((argsAssts, rt) =>
        ConjunctiveAssertion(argsAssts :+ (returnType ~=~ rt))
      )

    val ambiguousMissingTypes = originalArguments.rightmap(args =>
      // create conjunctive assertions
      missingScopes.map(rrt =>
        // val missingTType = resolveSolvedType(rrt).asInstanceOf[ClassOrInterfaceType]
        HasMethodAssertion(
          rrt,
          nameOfMethod,
          args,
          missingReturnType,
          paramChoices
        ) && returnType ~=~ missingReturnType
      )
    )
    (ambiguousDeclaredMethods, ambiguousMissingTypes) match
      case (LogWithSome(log1, conjassts), LogWithSome(log2, hmassts)) =>
        config._3 += DisjunctiveAssertion(conjassts ++ hmassts)
        LogWithSome(log1, returnType)
      case (LogWithNone(log), _) => LogWithNone(log)
      case (_, LogWithNone(log)) => LogWithNone(log)

private def createReturnTypeFromContext(
    expr: Expression,
    config: MutableConfiguration
): VoidableDisjunctiveType =
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
    .createVoidableDisjunctiveType(source, Nil, true, parameterChoices)
  config._2._2(iv) = InferenceVariableMemberTable(iv)
  iv

private def createDisjunctiveTypeWithPrimitivesFromContext(
    expr: Expression,
    config: MutableConfiguration
): DisjunctiveTypeWithPrimitives =
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
    .createDisjunctiveTypeWithPrimitives(source, Nil, true, parameterChoices)
  config._2._2(iv) = InferenceVariableMemberTable(iv)
  iv

private def createTypeArgumentFromContext(
    expr: Expression,
    config: MutableConfiguration,
    boundedness: Boolean = true
): ReferenceOnlyDisjunctiveType =
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
    .createDisjunctiveType(source, Nil, boundedness, parameterChoices, boundedness)
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

private def resolveMethodFromDisjunctiveType(
    log: Log,
    expr: MethodCallExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ],
    scope: InferenceVariable
): LogWithOption[Type] =
  val table =
    if config._2._2.contains(scope) then config._2._2(scope)
    else InferenceVariableMemberTable(scope)
  val originalArguments =
    flatMapWithLog(log, expr.getArguments.asScala.toVector)(resolveExpression(_, _, config, memo))
  val methodName = expr.getNameAsString
  originalArguments.rightmap(v =>
    v.foreach(x => config._6 += x)
    // case where method is actually found
    table.methods(methodName).find(m => m.signature.formalParameters == originalArguments) match
      case Some(m) => m.returnType
      case None =>
        val returnType = createReturnTypeFromContext(expr, config)
        config._4 += returnType
        val callSiteParameterChoices = getParamChoicesFromExpression(expr)
        val newTable = table.addMethod(
          methodName,
          v,
          returnType,
          Vector(),
          PUBLIC,
          false,
          false,
          false,
          callSiteParameterChoices
        )
        config._2._2(scope) = newTable
        config._5 += Invocation(
          scope,
          methodName,
          v,
          returnType,
          callSiteParameterChoices
        )
        returnType
  )

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
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  // non static obvious
  try LogWithOption(log, Some(resolveSolvedType(expr.resolve().getType())))
  catch
    case _: Throwable =>
      // static obvious
      try LogWithOption(log, Some(resolveSolvedType(expr.calculateResolvedType()).toStaticType))
      catch
        case _: Throwable =>
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

private def getArity(identifier: String, config: MutableConfiguration): Int =
  if config._2._1.contains(identifier) then config._2._1(identifier).numParams
  else if config._1.contains(identifier) then config._1(identifier).numParams
  else
    Configuration
      .createEmptyConfiguration()
      .getUnderlyingDeclaration(ClassOrInterfaceType(identifier))
      .numParams

private def resolveObjectCreationExpr(
    log: Log,
    expr: ObjectCreationExpr,
    config: MutableConfiguration,
    memo: MutableMap[String, Option[Type]]
): LogWithOption[Type] =
  val res =
    if expr.getType().isUsingDiamondOperator() then
      val name    = expr.getType().getNameAsString()
      val numArgs = getArity(name, config)
      val x = ClassOrInterfaceType(
        name,
        (0 until numArgs).map(i => createTypeArgumentFromContext(expr, config, false)).toVector
      )
      config._4 ++= x.args
      x
    else resolveSolvedType(expr.getType().resolve()).asInstanceOf[ClassOrInterfaceType]
  config._3 += res.isClass
  val constructorArgs = flatMapWithLog(log, expr.getArguments().asScala.toVector)((l, a) =>
    resolveExpression(l, a, config, memo).rightmap(x =>
      config._6 += x
      x
    )
  )
  val callSiteParameterChoices = getParamChoicesFromExpression(expr)
  if config._2._1.contains(res.identifier) then
    // is missing
    constructorArgs.rightmap(v =>
      config._3 += HasConstructorAssertion(res, v, callSiteParameterChoices)
    )
  else
    // is declared
    val constructors =
      if config._1.contains(res.identifier) then
        config._1(res.identifier).substitute(res.expansion._2).constructors
      else Configuration.createEmptyConfiguration().getSubstitutedDeclaration(res).constructors
    constructorArgs.rightmap(args =>
      val relevantConstructors =
        constructors.filter(c => c.callableWithNArgs(args.size)).map(c => c.asNArgs(args.size))
      config._3 += DisjunctiveAssertion(
        relevantConstructors.map(c =>
          ConjunctiveAssertion(c.callWith(args, callSiteParameterChoices))
        )
      )
    )
  LogWithSome(log, res)

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
      String,
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
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  LogWithSome(log, resolveSolvedType(expr.calculateResolvedType))

private def resolveUnaryExpr(
    log: Log,
    expr: UnaryExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  resolveExpression(log, expr.getExpression, config, memo).rightmap(x =>
    config._6 += x
    expr.getOperator match
      case UnaryExpr.Operator.BITWISE_COMPLEMENT =>
        val rt = InferenceVariableFactory.createPrimitivesOnlyDisjunctiveType()
        config._4 += rt
        config._2._2(rt) = InferenceVariableMemberTable(rt)
        config._3 += IsIntegralAssertion(x) &&
          IsIntegralAssertion(rt) &&
          (x =:~ rt)
        rt
      case UnaryExpr.Operator.LOGICAL_COMPLEMENT =>
        config._3 += x =:~ PRIMITIVE_BOOLEAN
        PRIMITIVE_BOOLEAN
      case UnaryExpr.Operator.MINUS | UnaryExpr.Operator.PLUS |
          UnaryExpr.Operator.POSTFIX_DECREMENT | UnaryExpr.Operator.POSTFIX_INCREMENT |
          UnaryExpr.Operator.PREFIX_DECREMENT | UnaryExpr.Operator.PREFIX_INCREMENT =>
        val rt = InferenceVariableFactory.createPrimitivesOnlyDisjunctiveType()
        config._4 += rt
        config._2._2(rt) = InferenceVariableMemberTable(rt)
        config._3 += IsNumericAssertion(x) &&
          IsNumericAssertion(rt) &&
          (x =:~ rt)
        rt
  )

private def resolveVariableDeclarationExpr(
    log: Log,
    expr: VariableDeclarationExpr,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[Type] =
  // try LogWithOption(log, Some(resolveSolvedType(expr.calculateResolvedType)))
  // catch
  //   case _ =>
  //     println(expr)
  //     LogWithNone(log)
  LogWithSome(log, Bottom)
