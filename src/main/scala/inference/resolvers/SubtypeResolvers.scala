package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.declaration.*
import configuration.types.*
import inference.misc.*
import utils.*

private[inference] def resolveSubtypeAssertion(
    log: Log,
    config: Configuration,
    a: SubtypeAssertion
): (Log, List[Configuration]) =
  val SubtypeAssertion(x, y) = a
  val (sub, sup)             = (x.upwardProjection, y.downwardProjection)
  // trivial cases
  (sub, sup) match
    case (Bottom, _) =>
      (log, config :: Nil)
    case (x, Bottom) =>
      (log.addWarn(s"$x <: $Bottom will always be false"), Nil)
    case (x: PrimitivesOnlyDisjunctiveType, _) =>
      (log.addWarn(s"primitives cannot be involved in subtype relations"), Nil)
    case (_, x: PrimitivesOnlyDisjunctiveType) =>
      (log.addWarn(s"primitives cannot be involved in subtype relations"), Nil)
    case (i: DisjunctiveType, _) =>
      expandDisjunctiveTypeToReference(i, log, config asserts a)
    case (_, i: DisjunctiveType) =>
      expandDisjunctiveTypeToReference(i, log, config asserts a)
    case (_, m: PrimitiveType) =>
      (log.addWarn(s"$sub cannot be a subtype of $sup as $sup is primitive"), Nil)
    case (m: PrimitiveType, _) =>
      (log.addWarn(s"$sub cannot be a subtype of $sup as $sub is primitive"), Nil)
    case (ArrayType(m), ArrayType(n)) =>
      (log, (config asserts (m <:~ n || (m.isPrimitive && m ~=~ n))) :: Nil)
    case (ArrayType(m), n: SomeClassOrInterfaceType) =>
      val newConfig = config asserts (n ~=~ OBJECT || n ~=~ ClassOrInterfaceType(
        "java.lang.Cloneable"
      ) || n ~=~ ClassOrInterfaceType("java.io.Serializable"))
      (log, newConfig :: Nil)
    case (m: SomeClassOrInterfaceType, n: ArrayType) =>
      (log.addWarn(s"$m <: $n is trivially not true"), Nil)
    case (alpha: Alpha, arr: ArrayType) =>
      `resolve Alpha <: Array`(alpha, arr, log, config, a)
    // TODO: CHANGE THIS
    case (_, m: ArrayType) =>
      `resolve Type <: Array`(sub, m, log, config)
    // left type parameter can be reduces to assertions on its bounds
    case (m: TTypeParameter, n: TTypeParameter) =>
      (log.addWarn(s"$n is not one of the bounds of $m"), Nil)
    case (m: TTypeParameter, _) =>
      `resolve TypeParam <: Type`(m, sup, log, config)
    case (_, m: TTypeParameter) =>
      `resolve Type <: TypeParam`(sub, m, log, config)
    case (m: SomeClassOrInterfaceType, n: SomeClassOrInterfaceType)
        if m.identifier == n.identifier =>
      `resolve τ<...> <: τ<...>`(m, n, log, config)
    case (m: TemporaryType, n: SomeClassOrInterfaceType) =>
      val combineAttempt    = config.asserts(m <:~ n).combineTemporaryType(m, n)
      val nonCombineAttempt = `resolve Reference <: Reference`(m, n, log, config.addExclusion(m, n))
      combineAttempt match
        case None => nonCombineAttempt
        case Some(x) =>
          val (log, left) = nonCombineAttempt
          (log.addInfo(s"successfully combined $m with $n"), x :: left)
    case (m: SomeClassOrInterfaceType, n: TemporaryType) =>
      val combineAttempt    = config.asserts(m <:~ n).combineTemporaryType(n, m)
      val nonCombineAttempt = `resolve Reference <: Reference`(m, n, log, config.addExclusion(n, m))
      combineAttempt match
        case None => nonCombineAttempt
        case Some(x) =>
          val (log, left) = nonCombineAttempt
          (log.addInfo(s"successfully combined $n with $m"), x :: left)

    case (m: ClassOrInterfaceType, n: ClassOrInterfaceType) =>
      `resolve Reference <: Reference`(m, n, log, config)
    case (m: Alpha, n: ClassOrInterfaceType) =>
      `resolve Alpha <: Ref`(m, n, log, config)
    case (a1: Alpha, a2: Alpha) =>
      val (l, c) = addToConstraintStore(a1, a1 <:~ a2, log, config)
      addToConstraintStore(a2, a1 <:~ a2, l, c.head)
    case (_, alpha: Alpha) =>
      addToConstraintStore(alpha, sub <:~ alpha, log, config)
    case (alpha: Alpha, _) =>
      addToConstraintStore(alpha, alpha <:~ sup, log, config)
    case (delta: PlaceholderType, _) =>
      addToConstraintStore(delta, a, log, config)
    case (_, delta: PlaceholderType) =>
      addToConstraintStore(delta, a, log, config)
    case x =>
      println(x)
      println(a)
      ???

private def addToConstraintStore(
    alpha: Alpha | PlaceholderType,
    asst: Assertion,
    log: Log,
    config: Configuration
) =
  (
    log.addInfo(s"adding $asst to constraint store"),
    (config.addToConstraintStore(alpha, asst)) :: Nil
  )

private def `resolve Alpha <: Array`(
    subtype: Alpha,
    supertype: ArrayType,
    log: Log,
    config: Configuration,
    a: Assertion
) =
  val base = supertype.base
  base.downwardProjection match
    case Bottom | _: PlaceholderType                              => (log.addError(s"wtf? $a"), Nil)
    case _: ExtendsWildcardType | _: SuperWildcardType | Wildcard => ???
    case _: PrimitiveType | _: PrimitivesOnlyDisjunctiveType | _: BoxesOnlyDisjunctiveType =>
      // if the array type's element is a primitive type, the alpha must be as well
      (
        log.addInfo(s"concretizing $subtype to $supertype"),
        (config.replace(subtype, supertype) :: Nil)
          .filter(_.isDefined)
          .map(_.get)
      )
    case _: ClassOrInterfaceType | _: ReferenceOnlyDisjunctiveType | _: Alpha | _: TTypeParameter |
        _: ArrayType | _: TemporaryType =>
      val newElementType = InferenceVariableFactory.createDisjunctiveType(
        subtype.source,
        Nil,
        subtype.canBeBounded,
        subtype.parameterChoices,
        false
      )
      val newArr = ArrayType(newElementType)
      (
        log.addInfo(s"concretizing $subtype to $newArr"),
        ((config asserts a).addToPsi(newElementType).replace(subtype, newArr) :: Nil)
          .filter(_.isDefined)
          .map(_.get)
      )
    case _ =>
      val newElementType = InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
        subtype.source,
        Nil,
        subtype.canBeBounded,
        subtype.parameterChoices
      )
      val newArr = ArrayType(newElementType)
      (
        log.addInfo(s"concretizing $subtype to $newArr"),
        ((config asserts a).addToPsi(newElementType).replace(subtype, newArr) :: Nil)
          .filter(_.isDefined)
          .map(_.get)
      )

private def `resolve Type <: Array`(
    subtype: Type,
    supertype: ArrayType,
    log: Log,
    config: Configuration
) =
  (log.addWarn(s"$supertype can only be a supertype of other array types or Bottom"), Nil)

private def `resolve TypeParam <: Type`(
    subtype: TTypeParameter,
    supertype: Type,
    log: Log,
    config: Configuration
) =
  val source = subtype.containingTypeIdentifier
  val decl   = config.getUnderlyingDeclaration(ClassOrInterfaceType(source))
  val bounds = decl.getAllReferenceTypeBoundsOfTypeParameter(subtype)
  (
    log.addInfo(s"expanding assertion on $subtype to assertions on its bounds"),
    (config asserts DisjunctiveAssertion(bounds.map(_ <:~ supertype))) :: Nil
  )

private def `resolve Type <: TypeParam`(
    subtype: Type,
    supertype: TTypeParameter,
    log: Log,
    config: Configuration
) =
  (
    log.addWarn(
      s"it is not possible for $subtype <: $supertype as $supertype is a type parameter, unless $subtype itself is a type parameter"
    ),
    Nil
  )

private def `resolve τ<...> <: τ<...>`(
    subtype: SomeClassOrInterfaceType,
    supertype: SomeClassOrInterfaceType,
    log: Log,
    config: Configuration
) =
  if supertype.args.size == 0 || subtype.args.size == 0 then
    (log.addInfo(s"$subtype <: $supertype is trivially true as at least one is raw"), config :: Nil)
  else if supertype.args.size != subtype.args.size then
    (
      log.addWarn(s"$subtype and $supertype have same raw type but different number of arguments?"),
      Nil
    )
  else
    val newAssertions =
      subtype.args.zip(supertype.args).map(x => x._1 <=~ x._2)
    (log, (config assertsAllOf newAssertions) :: Nil)

private def `resolve Reference <: Reference`(
    subtype: SomeClassOrInterfaceType,
    supertype: SomeClassOrInterfaceType,
    log: Log,
    config: Configuration
): (Log, List[Configuration]) =
  val a                = subtype <:~ supertype
  val (subRaw, supRaw) = (subtype.raw, supertype.raw)
  // do not create cyclic inheritances
  if config |- supRaw <:~ subRaw then
    return (log.addWarn(s"$a is false because $supRaw <: $subRaw"), Nil)
  log.addInfo(s"$subtype upcasts to ${config.upcast(subtype, supertype)}")
  // upcast the subtype to the supertype
  config upcast (subtype, supertype) match
    // upcasting success, easy
    case Some(newType) => (log, (config asserts (newType <:~ supertype)) :: Nil)
    case None =>
      if config |- subtype.isMissing then
        val supIsClass     = config |- supertype.isClass
        val subIsInterface = config |- subtype.isInterface
        // subinterface cannot be a subtype of class
        if supIsClass && subIsInterface then
          (log.addWarn(s"interface $subtype cannot be a subtype of class $supertype"), Nil)
        // subtype cannot extend final supertype
        else if (config |- supertype.isDeclared) && config
            .getUnderlyingDeclaration(supertype)
            .isFinal
        then
          (
            log.addWarn(
              s"$subtype cannot extend $supertype because $supertype is declared final"
            ),
            Nil
          )
        else
          // make greedily extend
          val supertypeNumParams = config.getUnderlyingDeclaration(supertype).numParams
          val newSupertype = ClassOrInterfaceType(
            supertype.identifier,
            (0 until supertypeNumParams)
              .map(i =>
                InferenceVariableFactory.createDisjunctiveType(
                  Left(subtype.identifier),
                  Nil,
                  false,
                  (0 until subtype.numArgs)
                    .map(TypeParameterIndex(subtype.identifier, _))
                    .toSet,
                  false
                )
              )
              .toVector
          )
          val newPhi1 = config.phi1 + (subtype.identifier -> config
            .phi1(subtype.identifier)
            .greedilyExtends(newSupertype))
          val newAssts =
            if supIsClass then Vector(a, subtype.isClass)
            else if subIsInterface then Vector(a, supertype.isInterface)
            else Vector(a)
          (
            log
              .addInfo(s"$subtype greedily extends $newSupertype"),
            config.copy(phi1 = newPhi1).addAllToPsi(newSupertype.args).assertsAllOf(newAssts) :: Nil
          )
      else
        val missingAncestors = config upcastToMissingAncestors subtype
        if missingAncestors.isEmpty then
          (
            log.addWarn(
              s"$subtype <: $supertype cannot be true because $subtype is fully declared"
            ),
            Nil
          )
        else
          val newAsst = DisjunctiveAssertion(missingAncestors.map(_ <:~ supertype))
          (log, (config asserts newAsst) :: Nil)

private def `resolve Alpha <: Ref`(
    subtype: Alpha,
    supertype: ClassOrInterfaceType,
    log: Log,
    config: Configuration
) =
  val numParams        = config.getUnderlyingDeclaration(supertype).numParams
  val newAlpha         = subtype.concretizeToReference(supertype.identifier, numParams)
  val supertypeIsFinal = config.getUnderlyingDeclaration(supertype).isFinal
  if supertypeIsFinal then
    val concretizedConfig = config
      .asserts(subtype <:~ supertype)
      .replace(subtype.copy(substitutions = Nil), newAlpha)
      .map(_.addAllToPsi(newAlpha.args))
      .map(_ :: Nil)
      .getOrElse(Nil)
    (log.addInfo(s"$subtype must be an instance of ${supertype.identifier}"), concretizedConfig)
  else addToConstraintStore(subtype, subtype <:~ supertype, log, config)
