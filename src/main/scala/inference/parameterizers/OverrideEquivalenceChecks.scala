package inference.parameterizers

import configuration.Configuration
import configuration.assertions.OverridesAssertion
import configuration.declaration.*
import configuration.types.*
import utils.*

private def checkOverrideEquivalence(
    log: Log,
    config: Configuration,
    x: String
): LogWithEither[List[Configuration], Configuration] =
  val decl = config.getUnderlyingDeclaration(SomeClassOrInterfaceType(x))
  // get the declaration's finalized methods
  val declMethods = decl.methods
    .map((k, v) => (k -> v.filter(m => !m.isInstanceOf[MethodWithContext])))
  //println(declMethods)
  // get the inherited methods with their respective original erasures
  val inheritedMethodsWithErasures = decl.getAllInheritedMethodsWithErasures(config)
  // println(x)
  // println(inheritedMethodsWithErasures)
  // look for clashes and assert override equivalence
  for (id, v) <- declMethods do
    if inheritedMethodsWithErasures.contains(id) then
      for method <- v do
        for (inheritedMethod, inheritedMethodErasure) <- inheritedMethodsWithErasures(id) do
          if method.signature.erased(decl) == inheritedMethodErasure &&
            !method.overrides(inheritedMethod, config)
          then
            //println(s"$method must override $inheritedMethod")
            return LogWithLeft(
              log.addInfo(s"$method must override $inheritedMethod"),
              (config asserts OverridesAssertion(method, inheritedMethod)) :: Nil
            )
  // println("ok")
  return LogWithRight(log, config)
