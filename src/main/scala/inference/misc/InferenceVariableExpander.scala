package inference.misc

import configuration.Configuration
import configuration.types.InferenceVariable
import configuration.assertions.*
import utils.*

private[inference] def expandInferenceVariable(
    i: InferenceVariable,
    log: Log,
    config: Configuration
) =
  i.source match
    case Left(_) =>
      val choices = i._choices
      (
        log.addInfo(s"expanding ${i.identifier} into its choices", choices.toString),
        choices
          .map(config.replace(i.copy(substitutions = Nil), _))
          .filter(!_.isEmpty)
          .map(_.get)
          .toList
      )
    case Right(x) =>
      (log.addError(s"$x\n$i"), Nil)

private[inference] def returnToConfig(a: Assertion, log: Log, config: Configuration) =
  (
    log.addInfo(
      s"returning $a to omega since it involves some replaceable type whose source is not known"
    ),
    (config asserts a) :: Nil
  )
