package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
import utils.*

import scala.util.{Try, Success, Failure, Either, Left, Right}

private[inference] def resolve(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  if config.isComplete then LogWithRight(log, config)
  else
    val (asst, newConfig) = config.pop()
    if newConfig |- asst then LogWithLeft(log, newConfig :: Nil)
    else
      val newLog = log.addInfo(s"resolving $asst")
      val res = asst match
        case x: SubtypeAssertion =>
          resolveSubtypeAssertion(newLog, newConfig, x)
        case x: IsClassAssertion =>
          resolveIsClassAssertion(newLog, newConfig, x)
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
          resolveEquivalenceAssertion(newLog, newConfig, x)
        case x: ContainmentAssertion =>
          resolveContainmentAssertion(newLog, newConfig, x)
        case x: IsPrimitiveAssertion =>
          resolveIsPrimitiveAssertion(newLog, newConfig, x)
        case x: IsReferenceAssertion =>
          resolveIsReferenceAssertion(newLog, newConfig, x)
        case x: IsIntegralAssertion =>
          resolveIntegralAssertion(newLog, newConfig, x)
        case x: IsNumericAssertion =>
          resolveNumericAssertion(newLog, newConfig, x)
        case x: HasMethodAssertion =>
          resolveHasMethodAssertion(newLog, newConfig, x)
        case _ =>
          ???
      LogWithLeft(res._1, res._2)
