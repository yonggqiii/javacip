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
  val (asst, newConfig) = config.pop()
  val newLog            = log.addInfo(s"resolving $asst")
  if newConfig |- asst then (newLog, newConfig :: Nil)
  else
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
      case IsPrimitiveAssertion(t) =>
        t.substituted match
          case n: ReplaceableType =>
            val newAssertion = (n ~=~ PRIMITIVE_BOOLEAN ||
              n ~=~ PRIMITIVE_BYTE ||
              n ~=~ PRIMITIVE_CHAR ||
              n ~=~ PRIMITIVE_DOUBLE ||
              n ~=~ PRIMITIVE_FLOAT ||
              n ~=~ PRIMITIVE_INT ||
              n ~=~ PRIMITIVE_LONG ||
              n ~=~ PRIMITIVE_SHORT ||
              n ~=~ PRIMITIVE_VOID)
            (log, (config asserts newAssertion) :: Nil)
          case _ =>
            (newLog.addWarn(s"$t is not primitive"), Nil)
      case IsReferenceAssertion(t) =>
        t.substituted match
          case m: InferenceVariable =>
            val originalConfig = config asserts asst
            m.source match
              case Left(_) =>
                val choices = m._choices
                (
                  log.addInfo(s"expanding ${m.identifier} into its choices"),
                  choices
                    .map(originalConfig.replace(m.copy(substitutions = Nil), _))
                    .filter(!_.isEmpty)
                    .map(_.get)
                    .toList
                )
              case Right(_) =>
                (
                  log.addInfo(
                    s"returning $asst back to config as insufficient information is available"
                  ),
                  originalConfig :: Nil
                )
          case _: PrimitiveType => (log.addWarn(s"$t is not a reference type"), Nil)

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
      case x: InferenceVariable =>
        val originalConfig = config asserts a
        x.source match
          case Left(_) =>
            val choices = x._choices
            (
              log.addInfo(s"expanding ${x.identifier} into its choices"),
              choices
                .map(originalConfig.replace(x.copy(substitutions = Nil), _))
                .filter(!_.isEmpty)
                .map(_.get)
                .toList
            )
          case Right(_) =>
            (
              log.addInfo(s"returning $a back to config as insufficient information is available"),
              originalConfig :: Nil
            )

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
