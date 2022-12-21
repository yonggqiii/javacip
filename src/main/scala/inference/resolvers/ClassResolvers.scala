package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
import utils.*

private def resolveIsClassAssertion(
    log: Log,
    config: Configuration,
    a: IsClassAssertion
): (Log, List[Configuration]) =
  val IsClassAssertion(t) = a
  if config |- t.isInterface then
    (log.addWarn(s"$t was defined as an interface so it can't be a class"), Nil)
  else
    val substitutedType = t.upwardProjection
    substitutedType match
      case x: TTypeParameter       => (log, config :: Nil) // why?
      case x: ClassOrInterfaceType =>
        // x is definitely missing
        val newPhi = config.phi1 + (x.identifier -> config.phi1(x.identifier).asClass)
        (log, config.copy(phi1 = newPhi) :: Nil)
      case x: DisjunctiveType =>
        expandDisjunctiveType(x, log, config asserts a)
      case x: Alpha =>
        addToConstraintStore(x, a, log, config)
      case x: PlaceholderType =>
        addToConstraintStore(x, a, log, config)
      case x: PrimitiveType =>
        (log.addWarn(s"$x cannot be a class"), Nil)
