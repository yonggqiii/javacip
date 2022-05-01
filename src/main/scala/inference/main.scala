package inference

import configuration.Configuration
import utils.*
import scala.annotation.tailrec
import configuration.assertions.*
import configuration.types.*

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
    case _ => ???

private def resolveSubtypeAssertion(
    log: Log,
    config: Configuration,
    a: SubtypeAssertion
): (Log, List[Configuration]) =
  val SubtypeAssertion(x, y) = a
  val (sub, sup)             = (x.upwardProjection.substituted, y.downwardProjection.substituted)
  if sub == sup || sup == NormalType("java.lang.Object", 0, Nil).substituted || sub == Bottom then
    (log, config :: Nil)
  else if sub == NormalType("java.lang.Object", 0, Nil).substituted then
    val newAssertion =
      EquivalenceAssertion(y.downwardProjection, NormalType("java.lang.Object", 0, Nil))
    (
      log.addInfo(s"replaced $a with $newAssertion"),
      config.copy(omega = config.omega.enqueue(newAssertion)) :: Nil
    )
  else if sup == Bottom then
    val newAssertion = EquivalenceAssertion(x.upwardProjection, Bottom)
    (
      log.addInfo(s"replaced $a with $newAssertion"),
      config.copy(omega = config.omega.enqueue(newAssertion)) :: Nil
    )
  else if !config.getFixedDeclaration(sub).isEmpty then
    resolveLeftFixedSubtypeAssertion(log, config, x, y)
  else ???

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

private def resolveLeftFixedSubtypeAssertion(
    log: Log,
    config: Configuration,
    subtype: Type,
    supertype: Type
): (Log, List[Configuration]) =
  val newAssertion = config
    .getFixedDeclaration(subtype.upwardProjection.substituted)
    .map(decl =>
      val supertypes =
        val tmp = (decl.extendedTypes ++ decl.implementedTypes).map(x =>
          x.addSubstitutionLists(
            subtype.upwardProjection.substituted.substitutions.filter(x => !x.isEmpty)
          )
        )
        if tmp.isEmpty then Vector(NormalType("java.lang.Object", 0, Nil)) else tmp
      supertypes.map(x => SubtypeAssertion(x, supertype.downwardProjection))
    )
    .map(DisjunctiveAssertion(_))
    .get
  val newConfig = config.copy(omega = config.omega.enqueue(newAssertion))
  (
    log.addInfo(s"replacing ${SubtypeAssertion(subtype, supertype)} with $newAssertion"),
    newConfig :: Nil
  )
