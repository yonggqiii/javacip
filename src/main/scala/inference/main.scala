package inference

import configuration.Configuration
import utils.*
import scala.annotation.tailrec
import configuration.assertions.*
import configuration.types.*
import inference.resolvers.*

def infer(log: Log, config: Configuration): LogWithOption[Configuration] =
  resolve(log, config :: Nil)

@tailrec
private def resolve(log: Log, configs: List[Configuration]): LogWithOption[Configuration] =
  configs match
    case Nil => LogWithNone(log.addError(s"Terminating as type errors exist"))
    case config :: remainingConfigs =>
      if config.isComplete then LogWithSome(log.addSuccess(s"Inference complete"), config)
      else
        val (newLog, newConfigs) = resolveOne(log, config)
        resolve(newLog, remainingConfigs ::: newConfigs)

private def resolveOne(log: Log, config: Configuration): (Log, List[Configuration]) =
  val (asst, newConfig) = config.pop()
  if newConfig |- asst then (log, newConfig :: Nil)
  else
    val newLog = log.addInfo(s"resolving $asst")
    asst match
      case x: SubtypeAssertion => resolveSubtypeAssertion(newLog, newConfig, x)
      case x: IsClassAssertion => resolveIsClassAssertion(newLog, newConfig, x)
      case x: IsInterfaceAssertion =>
        resolveIsInterfaceAssertion(newLog, newConfig, x)
      case x @ DisjunctiveAssertion(assts) =>
        (
          newLog.addInfo(s"expanding $x"),
          assts.map(a => newConfig asserts a).toList
        )
      case x @ ConjunctiveAssertion(assts) =>
        (
          newLog.addInfo(s"expanding $x"),
          (newConfig assertsAllOf assts) :: Nil
        )
      case x: EquivalenceAssertion =>
        resolveEquivalenceAssertion(log, newConfig, x)
      case x @ ContainmentAssertion(y, z) =>
        val (ys, zs) = (y.substituted, z.substituted)
        if zs.upwardProjection == zs.downwardProjection then
          val newAsst = y ~=~ z
          (
            newLog.addInfo(s"$x reduces to $newAsst"),
            (newConfig asserts newAsst) :: Nil
          )
        else
          val newAsst = (ys.upwardProjection <:~ zs.upwardProjection) &&
            (zs.downwardProjection <:~ ys.downwardProjection)

          (
            newLog.addInfo(s"expanding $x"),
            (newConfig asserts newAsst) :: Nil
          )
      case _ =>
        ???

private def resolveIsClassAssertion(
    log: Log,
    config: Configuration,
    a: IsClassAssertion
): (Log, List[Configuration]) =
  val IsClassAssertion(t) = a
  if config |- t.isInterface then
    (log.addWarn(s"$t was defined as an interface so it can't be a class"), Nil)
  else
    val substitutedType = t.upwardProjection.substituted
    // substitutedType is for sure missing or unknown
    substitutedType match
      case x: TTypeParameter => (log, config :: Nil) // why?
      case x: SubstitutedReferenceType =>
        val newPhi = config.phi1 + (x.identifier -> config.phi1(x.identifier).asClass)
        (log, config.copy(phi1 = newPhi) :: Nil)
      case _ => ???

private def resolveIsInterfaceAssertion(
    log: Log,
    config: Configuration,
    a: IsInterfaceAssertion
): (Log, List[Configuration]) =
  val IsInterfaceAssertion(t) = a
  if config |- t.isClass then
    (log.addWarn(s"$t was defined as a class so it can't be an interface"), Nil)
  else
    val substitutedType = t.upwardProjection.substituted
    // substitutedType is for sure missing or unknown
    substitutedType match
      case x: TTypeParameter => (log, config :: Nil) // why?
      case x: SubstitutedReferenceType =>
        val newPhi = config.phi1 + (x.identifier -> config.phi1(x.identifier).asInterface)
        (log, config.copy(phi1 = newPhi) :: Nil)
      case _ => ???
