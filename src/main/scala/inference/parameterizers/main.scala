package inference.parameterizers

import configuration.Configuration
import utils.*

private[inference] def parameterizeMembers(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  LogWithRight(log.addWarn("parameterizeMembers not implemented!"), config)
