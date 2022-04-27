package inference

import configuration.Configuration
import utils.*
import scala.annotation.tailrec

def infer(log: Log, config: Configuration): LogWithOption[Configuration] =
  resolve(log, config :: Nil)

private def resolve(log: Log, configs: List[Configuration]): LogWithOption[Configuration] =
  ???
