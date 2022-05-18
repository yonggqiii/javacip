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
  val (a, b)                     = (x.substituted, y.substituted)
  if a.upwardProjection != a.downwardProjection || b.upwardProjection != b.downwardProjection then
    (
      log,
      config.copy(omega =
        config.omega.enqueueAll(
          Vector(
            EquivalenceAssertion(a.upwardProjection, b.upwardProjection),
            EquivalenceAssertion(a.downwardProjection, b.downwardProjection)
          )
        )
      ) :: Nil
    )
  else if a == b then (log, config :: Nil)
  else
    (a, b) match
      case (m: SubstitutedReferenceType, n: SubstitutedReferenceType) =>
        if m.identifier != n.identifier || m.numArgs != n.numArgs then
          (log.addWarn(s"$m != $n"), Nil)
        else
          val newAssts = m.args.zip(n.args).map(EquivalenceAssertion(_, _))
          (
            log.addInfo(s"lol, expanding $asst as assertions on its arguments"),
            config.copy(omega = config.omega.enqueueAll(newAssts)) :: Nil
          )
      case (x: InferenceVariable, y: TypeAfterSubstitution) =>
        val originalConfig = config.copy(omega = config.omega.enqueue(asst))
        x.source match
          case Right(_) =>
            if x.substitutions.isEmpty then
              (
                log.addInfo(s"replacing $x with $y"),
                List(originalConfig.replace(x, y)).filter(!_.isEmpty).map(_.get)
              )
            else ???
          case Left(_) =>
            val choices = x._choices
            (
              log.addInfo(s"expanding ${x.identifier} into its choices"),
              choices
                .map(originalConfig.replace(x.copy(substitutions = Nil), _))
                .filter(!_.isEmpty)
                .map(_.get)
                .toList
            )
      case (y: TypeAfterSubstitution, x: InferenceVariable) =>
        val originalConfig = config.copy(omega = config.omega.enqueue(asst))
        x.source match
          case Right(_) =>
            if x.substitutions.isEmpty then
              val originalConfig = config.copy(omega = config.omega.enqueue(asst))
              (
                log.addInfo(s"replacing $x with $y"),
                List(originalConfig.replace(x, y)).filter(!_.isEmpty).map(_.get)
              )
            else ???
          case Left(_) =>
            val choices = x._choices
            (
              log.addInfo(s"expanding ${x.identifier} into its choices"),
              choices
                .map(originalConfig.replace(x.copy(substitutions = Nil), _))
                .filter(!_.isEmpty)
                .map(_.get)
                .toList
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
      case (x: PrimitiveType, _)        => (log.addWarn(s"$a != $b"), Nil)
      case (_, x: PrimitiveType)        => (log.addWarn(s"$a != $b"), Nil)
      case (x: ArrayType, y: ArrayType) => (log, (config asserts (x.base ~=~ y.base)) :: Nil)
      case (x: ArrayType, _)            => (log.addWarn(s"$a != $b"), Nil)
      case (_, x: ArrayType)            => (log.addWarn(s"$a != $b"), Nil)
      case _                            => ???
