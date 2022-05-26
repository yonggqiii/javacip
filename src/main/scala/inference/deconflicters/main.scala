package inference.deconflicters

import configuration.Configuration
import utils.*

private[inference] def deconflict(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  LogWithRight(log.addWarn("deconflict not implemented!"), config)
