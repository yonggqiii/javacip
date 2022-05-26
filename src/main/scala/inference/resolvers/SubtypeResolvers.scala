package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.declaration.*
import configuration.types.*
import inference.misc.expandInferenceVariable
import utils.*

private[inference] def resolveSubtypeAssertion(
    log: Log,
    config: Configuration,
    a: SubtypeAssertion
): (Log, List[Configuration]) =
  val SubtypeAssertion(x, y) = a
  val (sub, sup)             = (x.substituted.upwardProjection, y.substituted.downwardProjection)
  // trivial cases
  if sub == OBJECT.substituted || sup == Bottom then
    (log.addWarn(s"$x <: $y can never be true"), Nil)
  else
    (sub, sup) match
      case (i: InferenceVariable, _) =>
        expandInferenceVariable(i, log, config asserts a)
      case (_, i: InferenceVariable) =>
        expandInferenceVariable(i, log, config asserts a)
      case (_, alpha: Alpha) =>
        addToConstraintStore(alpha, sub <:~ alpha, log, config)
      case (alpha: Alpha, _) =>
        addToConstraintStore(alpha, alpha <:~ sup, log, config)
      case (_, m: PrimitiveType) =>
        `resolve Type <: Primitive`(sub, m, log, config)
      case (m: PrimitiveType, _) =>
        `resolve Primitive <: Type`(m, sup, log, config)
      case (m: ArrayType, n: ArrayType) =>
        `resolve Array <: Array`(m, n, log, config)
      // array type cannot be sub/super types of other types except the trivial ones
      case (m: ArrayType, _) =>
        `resolve Array <: Type`(m, sup, log, config)
      case (_, m: ArrayType) =>
        `resolve Type <: Array`(sub, m, log, config)
      // left type parameter can be reduces to assertions on its bounds
      case (m: TTypeParameter, n: TTypeParameter) =>
        `resolve TypeParam <: TypeParam`(m, n, log, config)
      case (m: TTypeParameter, _) =>
        `resolve TypeParam <: Type`(m, sup, log, config)
      case (_, m: TTypeParameter) =>
        `resolve Type <: TypeParam`(sub, m, log, config)
      case (m, n): (SubstitutedReferenceType, SubstitutedReferenceType)
          if m.identifier == n.identifier =>
        `resolve τ<...> <: τ<...>`(m, n, log, config)
      case (m: SubstitutedReferenceType, n: SubstitutedReferenceType) =>
        `resolve Reference <: Reference`(m, n, log, config)
      // case of subtype being a normal reference type and is declared
      case (m: SubstitutedReferenceType, _) if !config.getFixedDeclaration(m).isEmpty =>
        ???

/** Case of Type <: PrimitiveType
  * @param subtype
  *   the subtype of the primitive type
  * @param supertype
  *   the supertype
  * @return
  *   the resulting log and new configurations
  */
private def `resolve Type <: Primitive`(
    subtype: Type,
    supertype: PrimitiveType,
    log: Log,
    config: Configuration
) =
  // subtype must be one of the types that is assignable by this primitive type
  val newAssertion = DisjunctiveAssertion(supertype.isAssignableBy.map(subtype ~=~ _).toVector)
  (log, (config asserts newAssertion) :: Nil)

private def `resolve Primitive <: Type`(
    subtype: PrimitiveType,
    supertype: Type,
    log: Log,
    config: Configuration
) =
  // box case
  val box = subtype.boxed <:~ supertype
  // widen case
  val widen = DisjunctiveAssertion((subtype.widened + subtype).map(supertype ~=~ _).toVector)
  (log, (config asserts (widen || box)) :: Nil)

private def `resolve Array <: Array`(
    subtype: ArrayType,
    supertype: ArrayType,
    log: Log,
    config: Configuration
) =
  val (i, j)       = (subtype.base, supertype.base)
  val newAssertion = (i <:~ j && i.isReference && j.isReference) || (i.isPrimitive && i ~=~ j)
  (log, (config asserts newAssertion) :: Nil)

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

private def `resolve TypeParam <: TypeParam`(
    subtype: TTypeParameter,
    supertype: TTypeParameter,
    log: Log,
    config: Configuration
) =
  // already proved that m </: n
  (log.addWarn(s"$supertype is not one of the bounds of $subtype"), Nil)

private def `resolve TypeParam <: Type`(
    subtype: TTypeParameter,
    supertype: Type,
    log: Log,
    config: Configuration
) =
  val source = subtype.containingTypeIdentifier
  val fixed  = config.getFixedDeclaration(NormalType(source, 0, Nil))
  if !fixed.isEmpty then
    val decl  = fixed.get
    val assts = DisjunctiveAssertion(decl.getAllBounds(subtype).map(_ <:~ supertype).toVector)
    (
      log.addInfo(s"expanding assertion on $subtype to assertions on its bounds"),
      (config asserts assts) :: Nil
    )
  else if config.phi1.contains(source) then
    (
      log.addWarn(s"bounds of $subtype is always Object because it is found in a missing type"),
      Nil
    )
  else (log.addWarn(s"unable to find the declaration where $subtype is declared"), Nil)

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
    subtype: SubstitutedReferenceType,
    supertype: SubstitutedReferenceType,
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
    subtype: SubstitutedReferenceType,
    supertype: SubstitutedReferenceType,
    log: Log,
    config: Configuration
) =
  val a = subtype <:~ supertype
  config upcast (subtype, supertype) match
    case Some(newType) => (log, (config asserts (newType <:~ supertype)) :: Nil)
    case None =>
      if config |- subtype.isMissing then
        // make greedily extend
        val supIsClass     = config |- supertype.isClass
        val subIsInterface = config |- subtype.isInterface
        if supIsClass && subIsInterface then
          (log.addWarn(s"interface $subtype cannot be a subtype of class $supertype"), Nil)
        else if (config |- supertype.isDeclared) && config
            .getFixedDeclaration(supertype)
            .get
            .isFinal
        then
          (
            log.addWarn(s"$subtype cannot extend $supertype because $supertype is declared final"),
            Nil
          )
        else
          val newSupertype = NormalType(
            supertype.identifier,
            supertype.numArgs,
            ((0 until supertype.numArgs)
              .map(i =>
                (TypeParameterIndex(supertype.identifier, i) -> InferenceVariableFactory
                  .createInferenceVariable(
                    Left(subtype.identifier),
                    Nil,
                    false,
                    (0 until subtype.numArgs).map(TypeParameterIndex(subtype.identifier, _)).toSet,
                    false
                  ))
              )
              .toMap :: Nil).filter(!_.isEmpty)
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
