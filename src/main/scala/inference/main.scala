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
        resolve(newLog, newConfigs ::: remainingConfigs)

private def resolveOne(log: Log, config: Configuration): (Log, List[Configuration]) =
  val (asst, newOmega) = config.omega.dequeue
  asst match
    case x: SubtypeAssertion => resolveSubtypeAssertion(log, config.copy(omega = newOmega), x)
    case x: IsClassAssertion => resolveIsClassAssertion(log, config.copy(omega = newOmega), x)
    case x @ DisjunctiveAssertion(assts) =>
      (
        log.addInfo(s"expanding $x"),
        assts.map(a => config.copy(omega = newOmega.enqueue(a))).toList
      )
    case x @ ConjunctiveAssertion(assts) =>
      (
        log.addInfo(s"expanding $x"),
        config.copy(omega = newOmega.enqueueAll(assts)) :: Nil
      )
    case _ => ???

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
    if fd.isInterface then
      (log.addError(s"$t was defined as an interface but must be a class"), Nil)
    else (log, config :: Nil)
  else ???
