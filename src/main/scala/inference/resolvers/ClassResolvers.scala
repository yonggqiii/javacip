package inference.resolvers

import configuration.Configuration
import configuration.declaration.*
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
      case x: JavaInferenceVariable =>
        if !config.phi2.contains(x) then
          (
            log,
            config.copy(phi2 =
              config.phi2 + (x -> InferenceVariableMemberTable(x, Map(), Map(), true, false))
            ) :: Nil
          )
        else
          val oldTable = config.phi2(x)
          if oldTable.mustBeInterface then
            (log.addWarn(s"$x was already set to be an interface!"), Nil)
          else
            (
              log,
              config.copy(phi2 =
                config.phi2 + (x -> InferenceVariableMemberTable(
                  x,
                  oldTable.attributes,
                  oldTable.methods,
                  true,
                  false
                ))
              ) :: Nil
            )
      case x: Alpha =>
        addToConstraintStore(x, a, log, config)
      case x: PlaceholderType =>
        addToConstraintStore(x, a, log, config)
      case x: PrimitiveType =>
        (log.addWarn(s"$x cannot be a class"), Nil)
      case (Bottom | Wildcard | _: ExtendsWildcardType | _: SuperWildcardType) => ???
      case x: ArrayType => (log.addInfo(s"assume arrays are classes"), config :: Nil)
