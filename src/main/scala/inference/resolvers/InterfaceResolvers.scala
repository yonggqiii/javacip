package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
import utils.*

private def resolveIsInterfaceAssertion(
    log: Log,
    config: Configuration,
    a: IsInterfaceAssertion
): (Log, List[Configuration]) =
  val IsInterfaceAssertion(t) = a
  if config |- t.isClass then
    (log.addWarn(s"$t was defined as a class so it can't be an interface"), Nil)
  else
    val substitutedType = t.upwardProjection
    // substitutedType is for sure missing or unknown
    substitutedType match
      case x: TTypeParameter => (log, config :: Nil) // why?
      case x: ClassOrInterfaceType =>
        val newPhi = config.phi1 + (x.identifier -> config.phi1(x.identifier).asInterface)
        (log, config.copy(phi1 = newPhi) :: Nil)
      case x: DisjunctiveType =>
        expandDisjunctiveType(x, log, config asserts a)
      case x: Alpha =>
        addToConstraintStore(x, a, log, config)
      case x: PrimitiveType =>
        (log.addWarn(s"$x cannot be an interface"), Nil)
