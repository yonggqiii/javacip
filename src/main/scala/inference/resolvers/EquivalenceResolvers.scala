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
      case (x: SubstitutedReferenceType, y: SubstitutedReferenceType) =>
        if x.identifier != y.identifier || x.numArgs != y.numArgs then
          (log.addWarn(s"$x != $y"), Nil)
        else
          val newAssts = x.args.zip(y.args).map(EquivalenceAssertion(_, _))
          (
            log.addInfo(s"expanding $asst as assertions on its arguments"),
            config.copy(omega = config.omega.enqueueAll(newAssts)) :: Nil
          )
      case (x: InferenceVariable, y: Type) =>
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
      case (y: Type, x: InferenceVariable) =>
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
      case _ => ???
