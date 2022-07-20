package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*

private def resolveIntegralAssertion(log: Log, config: Configuration, a: IsIntegralAssertion) =
  val IsIntegralAssertion(t) = a
  val newAssertion =
    t ~=~ PRIMITIVE_BYTE || t ~=~ PRIMITIVE_CHAR ||
      t ~=~ PRIMITIVE_INT || t ~=~ PRIMITIVE_LONG ||
      t ~=~ PRIMITIVE_SHORT || t ~=~ BOXED_BYTE ||
      t ~=~ BOXED_CHAR || t ~=~ BOXED_INT ||
      t ~=~ BOXED_LONG || t ~=~ BOXED_SHORT
  (log.addInfo(s"$t is some integral type"), (config asserts newAssertion) :: Nil)
