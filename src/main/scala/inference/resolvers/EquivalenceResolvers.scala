package inference.resolvers
import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
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
      val originalConfig = config asserts asst
      a.source match
        case Right(_) =>
          // what?
          if a.substitutions.isEmpty then
            (
              log.addInfo(s"replacing $x with $y"),
              List(originalConfig.replace(a, y)).filter(!_.isEmpty).map(_.get)
            )
          else ???
        case Left(_) =>
          val choices = a._choices
          (
            log.addInfo(s"expanding ${a.identifier} into its choices"),
            choices
              .map(originalConfig.replace(a.copy(substitutions = Nil), _))
              .filter(!_.isEmpty)
              .map(_.get)
              .toList
          )
    case (_, a: InferenceVariable) =>
      val originalConfig = config asserts asst
      a.source match
        case Right(_) =>
          // what?
          if a.substitutions.isEmpty then
            (
              log.addInfo(s"replacing $a with $x"),
              List(originalConfig.replace(a, x)).filter(!_.isEmpty).map(_.get)
            )
          else ???
        case Left(_) =>
          val choices = a._choices
          (
            log.addInfo(s"expanding ${a.identifier} into its choices"),
            choices
              .map(originalConfig.replace(a.copy(substitutions = Nil), _))
              .filter(!_.isEmpty)
              .map(_.get)
              .toList
          )
    case (a: Alpha, b: PrimitiveType) => concretizeAlphaToPrimitive(log, config, a, b)
    case (b: PrimitiveType, a: Alpha) => concretizeAlphaToPrimitive(log, config, a, b)
    case (x: Alpha, y: SubstitutedReferenceType) =>
      concretizeAlphaToReference(log, config asserts asst, x, y)
    case (y: SubstitutedReferenceType, x: Alpha) =>
      concretizeAlphaToReference(log, config asserts asst, x, y)
    case (x: SubstitutedReferenceType, y: SubstitutedReferenceType) =>
      if x.identifier != y.identifier || x.numArgs != y.numArgs then (log.addWarn(s"$x != $y"), Nil)
      else if x.numArgs == 0 then (log, Nil)
      else
        val newAsst = x.args.zip(y.args).map(_ ~=~ _)
        (log, (config assertsAllOf newAsst) :: Nil)

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
  val newType = NormalType(
    s.identifier,
    s.numArgs,
    ((0 until s.numArgs)
      .map(i =>
        TypeParameterIndex(s.identifier, i) ->
          InferenceVariableFactory.createInferenceVariable(
            a.source,
            Nil,
            a.canBeBounded,
            a.parameterChoices,
            a.canBeBounded
          )
      )
      .toMap :: Nil).filter(!_.isEmpty)
  )
  (
    log.addInfo(s"concretizing $a to $newType"),
    List(config.replace(a.copy(substitutions = Nil), newType))
      .filter(!_.isEmpty)
      .map(_.get)
  )
