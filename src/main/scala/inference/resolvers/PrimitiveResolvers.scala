package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*

private def resolveIsPrimitiveAssertion(
    log: Log,
    config: Configuration,
    a: IsPrimitiveAssertion
): (Log, List[Configuration]) =
  val IsPrimitiveAssertion(t) = a
  t match
    case x: DisjunctiveType => (log.addWarn(s"$x cannot be primitive"), Nil)
    case x: Alpha           => (log.addWarn(s"$x cannot be primitive"), Nil)
    case x: DisjunctiveTypeWithPrimitives =>
      (log, (config asserts (x in PRIMITIVES.toSet[Type])) :: Nil)
    case x: VoidableDisjunctiveType =>
      (log, (config asserts (x in PRIMITIVES.toSet[Type].+(PRIMITIVE_VOID))) :: Nil)
    case n: InferenceVariable =>
      val newAssertion = (n ~=~ PRIMITIVE_BOOLEAN ||
        n ~=~ PRIMITIVE_BYTE ||
        n ~=~ PRIMITIVE_CHAR ||
        n ~=~ PRIMITIVE_DOUBLE ||
        n ~=~ PRIMITIVE_FLOAT ||
        n ~=~ PRIMITIVE_INT ||
        n ~=~ PRIMITIVE_LONG ||
        n ~=~ PRIMITIVE_SHORT ||
        n ~=~ PRIMITIVE_VOID)
      (log, (config asserts newAssertion) :: Nil)
    case _ =>
      (log.addWarn(s"$t is not primitive"), Nil)
