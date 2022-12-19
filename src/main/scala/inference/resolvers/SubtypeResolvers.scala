package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.declaration.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
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
    case (i: DisjunctiveType, _) =>
      expandDisjunctiveType(i, log, config asserts a)
    case (_, i: DisjunctiveType) =>
      expandDisjunctiveType(i, log, config asserts a)
    case (_, m: PrimitiveType) =>
      (log.addWarn(s"$sub cannot be a subtype of $sup as $sup is primitive"), Nil)
    case (m: PrimitiveType, _) =>
      (log.addWarn(s"$sub cannot be a subtype of $sup as $sub is primitive"), Nil)
    case (ArrayType(m), ArrayType(n)) =>
      (log, (config asserts (m <:~ n)) :: Nil)
    // TODO: CHANGE THIS
    // array type cannot be sub/super types of other types except the trivial ones
    case (m: ArrayType, _) =>
      `resolve Array <: Type`(m, sup, log, config)
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
    case (m: ClassOrInterfaceType, n: ClassOrInterfaceType) if m.identifier == n.identifier =>
      `resolve τ<...> <: τ<...>`(m, n, log, config)
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
    case (Bottom, _) =>
      (log, config :: Nil)
    case (x, Bottom) =>
      (log.addWarn(s"$x <: $Bottom will always be false"), Nil)
    case x =>
      println(x)
      println(a)
      ???

private def addToConstraintStore(alpha: Alpha, asst: Assertion, log: Log, config: Configuration) =
  val table =
    if config.phi2.contains(alpha) then config.phi2(alpha) else InferenceVariableMemberTable(alpha)
  val newTable = table.addConstraint(asst)
  (
    log.addInfo(s"adding $asst to constraint store in $alpha"),
    (config.copy(phi2 = config.phi2 + (alpha -> newTable))) :: Nil
  )

private def `resolve Array <: Type`(
    subtype: ArrayType,
    supertype: Type,
    log: Log,
    config: Configuration
) =
  (log.addWarn(s"$subtype can only be a subtype of other array types or Object"), Nil)

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
    subtype: ClassOrInterfaceType,
    supertype: ClassOrInterfaceType,
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
    subtype: ClassOrInterfaceType,
    supertype: ClassOrInterfaceType,
    log: Log,
    config: Configuration
): (Log, List[Configuration]) =
  val a                = subtype <:~ supertype
  val (subRaw, supRaw) = (subtype.copy(args = Vector()), supertype.copy(args = Vector()))
  // do not create cyclic inheritances
  if config |- supRaw <:~ subRaw then
    return (log.addWarn(s"$a is false because $supRaw <: $subRaw"), Nil)

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
            log.addInfo(s"$subtype greedily extends $newSupertype"),
            config.copy(phi1 = newPhi1).assertsAllOf(newAssts) :: Nil
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
      .map(_ :: Nil)
      .getOrElse(Nil)
    (log.addInfo(s"$subtype must be an instance of ${supertype.identifier}"), concretizedConfig)
  else addToConstraintStore(subtype, subtype <:~ supertype, log, config)
