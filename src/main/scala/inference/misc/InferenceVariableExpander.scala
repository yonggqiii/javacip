package inference.misc

import configuration.Configuration
import configuration.types.InferenceVariable
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
