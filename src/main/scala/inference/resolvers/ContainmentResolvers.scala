package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*

private def resolveContainmentAssertion(log: Log, config: Configuration, a: ContainmentAssertion) =
  val ContainmentAssertion(y, z) = a
  val (ys, zs)                   = (y.substituted, z.substituted)
  if zs.upwardProjection == zs.downwardProjection then
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
