package inference

import configuration.Configuration
import utils.*
import scala.annotation.tailrec
import configuration.assertions.*
import configuration.types.*
import inference.resolvers.*

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import scala.util.{Try, Success, Failure, Either, Left, Right}

def infer(log: Log, config: Configuration): LogWithOption[Configuration] =
  infer(log, config :: Nil)

private def concretize(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  LogWithRight(log.addWarn("concretize not implemented!"), config)

private def deconflict(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  LogWithRight(log.addWarn("deconflict not implemented!"), config)

private def parameterizeMembers(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  LogWithRight(log.addWarn("parameterizeMembers not implemented!"), config)

@tailrec
private def infer(log: Log, configs: List[Configuration]): LogWithOption[Configuration] =
  configs match
    case Nil => LogWithNone(log.addError(s"Terminating as type errors exist"))
    case _ =>
      val freshLog = Log(log.appConfig)
      val f = (x: Configuration) =>
        Future {
          resolve(freshLog, x)
        } andThen { x =>
          x match
            case Success(res)       => res.flatMap(concretize)
            case Failure(throwable) => LogWithLeft(freshLog.addError(throwable.getMessage), Nil)
        } andThen { x =>
          x match
            case Success(res)       => res.flatMap(deconflict)
            case Failure(throwable) => LogWithLeft(freshLog.addError(throwable.getMessage), Nil)
        } andThen { x =>
          x match
            case Success(res)       => res.flatMap(parameterizeMembers)
            case Failure(throwable) => LogWithLeft(freshLog.addError(throwable.getMessage), Nil)
        }
      val pass = Await.result(Future.traverse(configs)(f), 10.seconds)
      pass.find(_.isRight) match
        case Some(c) => LogWithSome(log.addAll(c.log.messages), c.right)
        case None =>
          val res = pass.foldLeft(log, List[Configuration]())((lgls, lwe) =>
            val (oldLog, oldList) = lgls
            val newLog            = lwe.log
            val newList           = lwe.left
            (oldLog.addAll(newLog.messages), newList ::: oldList)
          )
          infer(res._1, res._2)

private def resolve(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  if config.omega.isEmpty then LogWithRight(log, config)
  else
    val (asst, newConfig) = config.pop()
    val newLog            = log.addInfo(s"resolving $asst")
    if newConfig |- asst then LogWithLeft(newLog, newConfig :: Nil)
    else
      val res = asst match
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
      LogWithLeft(res._1, res._2)

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
