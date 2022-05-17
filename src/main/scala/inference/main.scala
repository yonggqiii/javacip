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
  val (asst, newOmega) = config.omega.dequeue
  val newLog           = log.addInfo(s"resolving $asst")
  asst match
    case x: SubtypeAssertion => resolveSubtypeAssertion(newLog, config.copy(omega = newOmega), x)
    case x: IsClassAssertion => resolveIsClassAssertion(newLog, config.copy(omega = newOmega), x)
    case x: IsInterfaceAssertion =>
      resolveIsInterfaceAssertion(newLog, config.copy(omega = newOmega), x)
    case x @ DisjunctiveAssertion(assts) =>
      (
        newLog.addInfo(s"expanding $x"),
        assts.map(a => config.copy(omega = newOmega.enqueue(a))).toList
      )
    case x @ ConjunctiveAssertion(assts) =>
      (
        newLog.addInfo(s"expanding $x"),
        config.copy(omega = newOmega.enqueueAll(assts)) :: Nil
      )
    case x: EquivalenceAssertion =>
      resolveEquivalenceAssertion(log, config.copy(omega = newOmega), x)
    case x @ ContainmentAssertion(y, z) =>
      val (ys, zs) = (y.substituted, z.substituted)
      if zs.upwardProjection == zs.downwardProjection then
        val newAsst = EquivalenceAssertion(y, z)
        (
          newLog.addInfo(s"$x reduces to $newAsst"),
          config.copy(omega = newOmega.enqueue(newAsst)) :: Nil
        )
      else
        val newAssts = Vector(
          SubtypeAssertion(ys.upwardProjection, zs.upwardProjection),
          SubtypeAssertion(zs.downwardProjection, ys.upwardProjection)
        )
        (
          newLog.addInfo(s"expanding $x"),
          config.copy(omega = newOmega.enqueueAll(newAssts)) :: Nil
        )
    case _ =>
      ???

private def resolveIsClassAssertion(
    log: Log,
    config: Configuration,
    a: IsClassAssertion
): (Log, List[Configuration]) =
  val IsClassAssertion(t) = a
  val cls                 = t.upwardProjection.substituted
  val fixedAttempt        = config.getFixedDeclaration(cls)
  if !fixedAttempt.isEmpty then
    val fd = fixedAttempt.get
    if fd.isInterface then (log.addWarn(s"$t was defined as an interface but must be a class"), Nil)
    else (log, config :: Nil)
  else
    cls match
      case x: TTypeParameter => (log, config :: Nil)
      case x: SubstitutedReferenceType =>
        if config.phi1(x.identifier).mustBeInterface then
          (log.addWarn(s"$x cannot be both a class and an interface"), Nil)
        else
          val newPhi = config.phi1 + (x.identifier -> config.phi1(x.identifier).asClass)
          (log, config.copy(phi1 = newPhi) :: Nil)
      case _ => ???

private def resolveIsInterfaceAssertion(
    log: Log,
    config: Configuration,
    a: IsInterfaceAssertion
): (Log, List[Configuration]) =
  val IsInterfaceAssertion(t) = a
  val cls                     = t.upwardProjection.substituted
  val fixedAttempt            = config.getFixedDeclaration(cls)
  if !fixedAttempt.isEmpty then
    val fd = fixedAttempt.get
    if !fd.isInterface then
      (log.addWarn(s"$t was defined as a class but must be an interface"), Nil)
    else (log, config :: Nil)
  else
    cls match
      case x: TTypeParameter => (log, config :: Nil)
      case x: SubstitutedReferenceType =>
        if config.phi1(x.identifier).mustBeClass then
          (log.addWarn(s"$x cannot be both a class and an interface"), Nil)
        else
          val newPhi = config.phi1 + (x.identifier -> config.phi1(x.identifier).asInterface)
          (log, config.copy(phi1 = newPhi) :: Nil)
      case _ => ???
