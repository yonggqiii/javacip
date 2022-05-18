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
    case (Bottom, Bottom) => (log, config :: Nil)
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
    case (a: Alpha, b: PrimitiveType) =>
      (
        log.addInfo(s"concretizing $a to $b"),
        List(config.replace(a.copy(substitutions = Nil), b))
          .filter(!_.isEmpty)
          .map(_.get)
      )
    case (b: PrimitiveType, a: Alpha) =>
      (
        log.addInfo(s"concretizing $a to $b"),
        List(config.replace(a.copy(substitutions = Nil), b))
          .filter(!_.isEmpty)
          .map(_.get)
      )
    case (x: Alpha, y: SubstitutedReferenceType) =>
      val originalConfig = config.copy(omega = config.omega.enqueue(asst))
      val newType = NormalType(
        y.identifier,
        y.numArgs,
        ((0 until y.numArgs)
          .map(i =>
            TypeParameterIndex(y.identifier, i) ->
              InferenceVariableFactory.createInferenceVariable(
                x.source,
                Nil,
                x.canBeBounded,
                x.parameterChoices,
                x.canBeBounded
              )
          )
          .toMap :: Nil).filter(!_.isEmpty)
      )
      (
        log.addInfo(s"concretizing $x to $newType"),
        List(originalConfig.replace(x.copy(substitutions = Nil), newType))
          .filter(!_.isEmpty)
          .map(_.get)
      )
