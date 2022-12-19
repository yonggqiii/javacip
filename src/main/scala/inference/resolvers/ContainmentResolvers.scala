package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
import utils.*

private def resolveContainmentAssertion(log: Log, config: Configuration, a: ContainmentAssertion) =
  val ContainmentAssertion(ys, zs) = a
  (ys, zs) match
    case (yy: DisjunctiveType, _) =>
      expandDisjunctiveType(yy, log, config asserts (yy <=~ zs))
    case (_, yy: DisjunctiveType) =>
      expandDisjunctiveType(yy, log, config asserts (ys <=~ yy))
    case (_, Wildcard) => (log, config :: Nil)
    case (_, ExtendsWildcardType(upper)) =>
      val newAsst = ys <:~ upper
      (log.addInfo(s"$a reduces to $newAsst"), (config asserts newAsst) :: Nil)
    case (_, SuperWildcardType(lower)) =>
      val newAsst = lower <:~ ys
      (log.addInfo(s"$a reduces to $newAsst"), (config asserts newAsst) :: Nil)
    case _ =>
      val newAsst = ys <:~ zs && zs <:~ ys
      (
        log.addInfo(s"$a reduces to $newAsst"),
        (config asserts newAsst) :: Nil
      )
