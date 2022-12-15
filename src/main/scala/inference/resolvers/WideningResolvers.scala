package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
import utils.*

private def resolveWideningAssertion(log: Log, config: Configuration, a: WideningAssertion) =
  val WideningAssertion(left, right) = a
  (
    log.addInfo(s"$left $right in <~="),
    (config asserts ((left, right) in WIDENING_RELATION)) :: Nil
  )
