package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveTypeToReference
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
      case x: TTypeParameter           => (log, config :: Nil) // why?
      case x: SomeClassOrInterfaceType =>
        // x is definitely missing
        val newPhi = config.phi1 + (x.identifier -> config.phi1(x.identifier).asClass)
        (log, config.copy(phi1 = newPhi) :: Nil)
      case x: DisjunctiveType =>
        expandDisjunctiveTypeToReference(x, log, config asserts a)
      case x: Alpha =>
        addToConstraintStore(x, a, log, config)
      case x: PlaceholderType =>
        addToConstraintStore(x, a, log, config)
      case x: PrimitiveType =>
        (log.addWarn(s"$x cannot be a class"), Nil)
      case (Bottom | Wildcard | _: ExtendsWildcardType | _: SuperWildcardType) => ???
      case x: ArrayType => (log.addInfo(s"assume arrays are classes"), config :: Nil)
