package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandInferenceVariable
import utils.*

private def resolveContainmentAssertion(log: Log, config: Configuration, a: ContainmentAssertion) =
  val ContainmentAssertion(y, z) = a
  val (ys, zs)                   = (y.substituted, z.substituted)
  (ys, zs) match
    case (yy: InferenceVariable, _) =>
      expandInferenceVariable(yy, log, config asserts (yy <=~ zs))
    case (_, yy: InferenceVariable) =>
      expandInferenceVariable(yy, log, config asserts (ys <=~ yy))
    case _ =>
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
