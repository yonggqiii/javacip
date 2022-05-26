package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*
import configuration.declaration.InferenceVariableMemberTable

private def resolveArraySubArray(
    subtype: ArrayType,
    supertype: ArrayType,
    log: Log,
    config: Configuration
) =
  val (i, j)       = (subtype.base, supertype.base)
  val newAssertion = (i <:~ j && i.isReference && j.isReference) || (i.isPrimitive && i ~=~ j)
  (log, (config asserts newAssertion) :: Nil)

private def expandInferenceVariable(i: InferenceVariable, log: Log, config: Configuration) =
  i.source match
    case Left(_) =>
      val choices = i._choices
      (
        log.addInfo(s"expanding ${i.identifier} into its choices"),
        choices
          .map(config.replace(i.copy(substitutions = Nil), _))
          .filter(!_.isEmpty)
          .map(_.get)
          .toList
      )
    case Right(_) =>
      (
        log.addInfo(
          "returning $a back to config as insufficient information is available"
        ),
        config :: Nil
      )

private def addToConstraintStore(alpha: Alpha, asst: Assertion, log: Log, config: Configuration) =
  val table =
    if config.phi2.contains(alpha) then config.phi2(alpha) else InferenceVariableMemberTable(alpha)
  val newTable = table.addConstraint(asst)
  (
    log.addInfo(s"adding $asst to constraint store in $alpha"),
    (config.copy(phi2 = config.phi2 + (alpha -> newTable))) :: Nil
  )

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
      case (i: InferenceVariable, _)    => expandInferenceVariable(i, log, config asserts a)
      case (_, i: InferenceVariable)    => expandInferenceVariable(i, log, config asserts a)
      case (_, alpha: Alpha)            => addToConstraintStore(alpha, sub <:~ alpha, log, config)
      case (alpha: Alpha, _)            => addToConstraintStore(alpha, alpha <:~ sup, log, config)
      case (_, m: PrimitiveType)        => resolveTypeSubPrimitive(sub, m, log, config)
      case (m: PrimitiveType, _)        => resolvePrimitiveSubType(m, sup, log, config)
      case (m: ArrayType, n: ArrayType) => resolveArraySubArray(m, n, log, config)
      // array type cannot be sub/super types of other types except the trivial ones
      case (ArrayType(_), _) =>
        (log.addWarn(s"$x can only be a subtype of other array types or Object"), Nil)
      case (_, ArrayType(_)) =>
        (log.addWarn(s"$y can only be a supertype of other array types or Bottom"), Nil)
      // left type parameter can be reduces to assertions on its bounds
      case (m: TTypeParameter, n: TTypeParameter) =>
        // already proven that m </: n
        (log.addWarn(s"$n is not one of the bounds of $m"), Nil)
      case (m: TTypeParameter, _) =>
        val source = m.containingTypeIdentifier
        val fixed  = config.getFixedDeclaration(NormalType(source, 0, Nil))
        if !fixed.isEmpty then
          val decl  = fixed.get
          val assts = DisjunctiveAssertion(decl.getAllBounds(m).map(_ <:~ y).toVector)
          (
            log.addInfo(s"expanding assertion on $x to assertions on its bounds"),
            (config asserts assts) :: Nil
          )
        else if config.phi1.contains(source) then
          (
            log.addWarn(s"bounds of $x is always Object because it is found in a missing type"),
            Nil
          )
        else (log.addWarn(s"unable to find the declaration where $sub is declared"), Nil)
      case (_, _: TTypeParameter) =>
        (
          log.addWarn(
            s"it is not possible for $x <: $y as $y is a type parameter, unless $x itself is a type parameter"
          ),
          Nil
        )
      // case of two substituted reference types where raw is the same
      case (m, n): (SubstitutedReferenceType, SubstitutedReferenceType)
          if m.identifier == n.identifier =>
        if sup.args.size == 0 || sub.args.size == 0 then
          (log.addInfo(s"$m <: $n is trivially true as at least one is raw"), config :: Nil)
        else if sup.args.size != sup.args.size then
          (
            log.addWarn(s"$sub and $sup have same raw type but different number of arguments?"),
            Nil
          )
        else
          val newAssertions =
            sub.args.zip(sup.args).map(x => x._1 <=~ x._2)
          (log, (config assertsAllOf newAssertions) :: Nil)
      // case of subtype being a missing type and supertype is a known type
      case (m: SubstitutedReferenceType, n: SubstitutedReferenceType) =>
        config upcast (m, n) match
          case Some(newType) => (log, (config asserts (newType <:~ n)) :: Nil)
          case None =>
            if config |- m.isMissing then
              // make greedily extend
              val supIsClass     = config |- n.isClass
              val subIsInterface = config |- m.isInterface
              if supIsClass && subIsInterface then
                (log.addWarn(s"interface $m cannot be a subtype of class $n"), Nil)
              else if (config |- n.isDeclared) && config.getFixedDeclaration(n).get.isFinal then
                (log.addWarn(s"$m cannot extend $n because $n is declared final"), Nil)
              else
                val newSupertype = NormalType(
                  n.identifier,
                  n.numArgs,
                  ((0 until n.numArgs)
                    .map(i =>
                      (TypeParameterIndex(n.identifier, i) -> InferenceVariableFactory
                        .createInferenceVariable(
                          Left(m.identifier),
                          Nil,
                          false,
                          (0 until m.numArgs).map(TypeParameterIndex(m.identifier, _)).toSet,
                          false
                        ))
                    )
                    .toMap :: Nil).filter(!_.isEmpty)
                )
                val newPhi1 = config.phi1 + (m.identifier -> config
                  .phi1(m.identifier)
                  .greedilyExtends(newSupertype))
                val newAssts =
                  if supIsClass then Vector(a, m.isClass)
                  else if subIsInterface then Vector(a, n.isInterface)
                  else Vector(a)
                (
                  log.addInfo(s"$m greedily extends $newSupertype"),
                  config.copy(phi1 = newPhi1).assertsAllOf(newAssts) :: Nil
                )
            else
              val missingAncestors = config upcastToMissingAncestors m
              if missingAncestors.isEmpty then
                (log.addWarn(s"$m <: $n cannot be true because $m is fully declared"), Nil)
              else
                val newAsst = DisjunctiveAssertion(missingAncestors.map(_ <:~ n))
                (log, (config asserts newAsst) :: Nil)
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
private def resolveTypeSubPrimitive(
    subtype: Type,
    supertype: PrimitiveType,
    log: Log,
    config: Configuration
) =
  // subtype must be one of the types that is assignable by this primitive type
  val newAssertion = DisjunctiveAssertion(supertype.isAssignableBy.map(subtype ~=~ _).toVector)
  (log, (config asserts newAssertion) :: Nil)

private def resolvePrimitiveSubType(
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
