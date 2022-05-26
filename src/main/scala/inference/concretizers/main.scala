package inference.concretizers

import configuration.Configuration
import configuration.types.*
import utils.*

private[inference] def concretize(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  // look for alpha
  val potentialAlpha = config.phi2.find((x, y) => x.isInstanceOf[Alpha])
  LogWithRight(log.addWarn("concretize not implemented!"), config)
