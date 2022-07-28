package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
import utils.*

private def resolveContainmentAssertion(log: Log, config: Configuration, a: ContainmentAssertion) =
  val ContainmentAssertion(y, z) = a
  val (ys, zs)                   = (y.substituted, z.substituted)
  (ys, zs) match
    case (yy: DisjunctiveType, _) =>
      expandDisjunctiveType(yy, log, config asserts (yy <=~ zs))
    case (_, yy: DisjunctiveType) =>
      expandDisjunctiveType(yy, log, config asserts (ys <=~ yy))
    case _ =>
      if !zs.isInstanceOf[ExtendsWildcardType] &&
        !zs.isInstanceOf[SuperWildcardType]
        && zs != Wildcard
      then
        val newAsst = y ~=~ z
        (
          log.addInfo(s"$a reduces to $newAsst"),
          (config asserts newAsst) :: Nil
        )
      else
        val newAsst = (ys.upwardProjection <:~ zs.upwardProjection) &&
          (zs.downwardProjection <:~ ys.downwardProjection)

        (
          log.addInfo(s"expanding $a"),
          (config asserts newAsst) :: Nil
        )
