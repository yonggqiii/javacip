package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*

private def resolveNumericAssertion(log: Log, config: Configuration, a: IsNumericAssertion) =
  val IsNumericAssertion(t) = a
  val newAssertion =
    t ~=~ PRIMITIVE_BYTE || t ~=~ PRIMITIVE_CHAR ||
      t ~=~ PRIMITIVE_DOUBLE || t ~=~ PRIMITIVE_FLOAT ||
      t ~=~ PRIMITIVE_INT || t ~=~ PRIMITIVE_LONG ||
      t ~=~ PRIMITIVE_SHORT || t ~=~ BOXED_BYTE ||
      t ~=~ BOXED_CHAR || t ~=~ BOXED_DOUBLE ||
      t ~=~ BOXED_FLOAT || t ~=~ BOXED_INT ||
      t ~=~ BOXED_LONG || t ~=~ BOXED_SHORT
  (log.addInfo(s"$t is some numeric type"), (config asserts newAssertion) :: Nil)
