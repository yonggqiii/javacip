package inference.resolvers
import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandInferenceVariable
import utils.*

private[inference] def resolveEquivalenceAssertion(
    log: Log,
    config: Configuration,
    asst: EquivalenceAssertion
): (Log, List[Configuration]) =
  val EquivalenceAssertion(x, y) = asst
  (x.substituted, y.substituted) match
    case (Bottom, Bottom)     => (log, config :: Nil)
    case (Wildcard, Wildcard) => (log, config :: Nil)
    case (a: ExtendsWildcardType, b: ExtendsWildcardType) =>
      (log, (config asserts (a.upwardProjection ~=~ b.upwardProjection)) :: Nil)
    case (a: SuperWildcardType, b: SuperWildcardType) =>
      (log, (config asserts (a.downwardProjection ~=~ b.downwardProjection)) :: Nil)
    case (a: ArrayType, b: ArrayType) =>
      (log, (config asserts (a.base ~=~ b.base)) :: Nil)
    case (a: PrimitiveType, b: PrimitiveType) =>
      // definitely false
      (log.addWarn(s"$x != $y"), Nil)
    case (a: TTypeParameter, b: TTypeParameter) =>
      // definitely false
      (log.addWarn(s"$x != $y"), Nil)
    case (a: InferenceVariable, _) =>
      expandInferenceVariable(a, log, config asserts asst)
    case (_, a: InferenceVariable) =>
      expandInferenceVariable(a, log, config asserts asst)
    case (a: Alpha, b: PrimitiveType) =>
      concretizeAlphaToPrimitive(log, config, a, b)
    case (b: PrimitiveType, a: Alpha) =>
      concretizeAlphaToPrimitive(log, config, a, b)
    case (x: Alpha, y: SubstitutedReferenceType) =>
      concretizeAlphaToReference(log, config asserts asst, x, y)
    case (y: SubstitutedReferenceType, x: Alpha) =>
      concretizeAlphaToReference(log, config asserts asst, x, y)
    case (x: SubstitutedReferenceType, y: SubstitutedReferenceType) =>
      resolveReferenceEquivalences(x, y, log, config)

private[inference] def concretizeAlphaToPrimitive(
    log: Log,
    config: Configuration,
    a: Alpha,
    p: PrimitiveType
): (Log, List[Configuration]) =
  (
    log.addInfo(s"concretizing $a to $p"),
    List(config.replace(a.copy(substitutions = Nil), p))
      .filter(!_.isEmpty)
      .map(_.get)
  )

private[inference] def concretizeAlphaToReference(
    log: Log,
    config: Configuration,
    a: Alpha,
    s: SubstitutedReferenceType
): (Log, List[Configuration]) =
  val newType = a.concretizeToReference(s.identifier, s.numArgs)

  (
    log.addInfo(s"concretizing $a to $newType"),
    List(
      config.replace(a.copy(substitutions = Nil), newType)
    )
      .filter(!_.isEmpty)
      .map(_.get)
  )

private def resolveReferenceEquivalences(
    x: SubstitutedReferenceType,
    y: SubstitutedReferenceType,
    log: Log,
    config: Configuration
) =
  if x.identifier != y.identifier || x.numArgs != y.numArgs then (log.addWarn(s"$x != $y"), Nil)
  else if x.numArgs == 0 then (log, Nil)
  else
    val newAsst = x.args.zip(y.args).map(_ ~=~ _)
    (log, (config assertsAllOf newAsst) :: Nil)
