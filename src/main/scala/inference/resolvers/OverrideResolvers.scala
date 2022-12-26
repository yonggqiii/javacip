package inference.resolvers

import scala.collection.mutable.ArrayBuffer

import configuration.Configuration
import configuration.assertions.*
import utils.*

private[inference] def resolveOverridesAssertion(
    log: Log,
    config: Configuration,
    asst: OverridesAssertion
): (Log, List[Configuration]) =
  val OverridesAssertion(overriding, overridden) = asst
  val newAssertions                              = ArrayBuffer[Assertion]()
  if overriding.typeParameterBounds.size != overridden.typeParameterBounds.size then
    return (
      log.addWarn(
        s"$overriding cannot override $overridden because they have a different number of type parameters"
      ),
      Nil
    )
  val tpMap =
    overriding.typeParameterBounds.map(_._1).zip(overridden.typeParameterBounds.map(_._1)).toMap
  // equality of bounds
  for i <- 0 until overriding.typeParameterBounds.size do
    val leftBounds  = overriding.typeParameterBounds(i)
    val rightBounds = overridden.typeParameterBounds(i)
    if leftBounds._2.size != rightBounds._2.size then
      return (
        log.addWarn(
          s"$overriding cannot override $overridden because ${leftBounds._1} and ${rightBounds._1} have a different number of bounds"
        ),
        Nil
      )
    newAssertions ++= leftBounds._2.map(x => x.substitute(tpMap)).zip(rightBounds._2).map(_ ~=~ _)
  // equality of formal parameters
  if overriding.signature.formalParameters.size != overridden.signature.formalParameters.size then
    return (
      log.addWarn(
        s"$overriding cannot override $overridden because they have a different number of formal parameters"
      ),
      Nil
    )
  newAssertions ++= overriding.signature.formalParameters
    .map(_.substitute(tpMap))
    .zip(overridden.signature.formalParameters)
    .map(_ ~=~ _)

  // return-type substitutability
  val newOverridingReturnType = overriding.returnType.substitute(tpMap)
  newAssertions += newOverridingReturnType ~=~ overridden.returnType || newOverridingReturnType <:~ overridden.returnType
  (log, (config assertsAllOf newAssertions) :: Nil)
