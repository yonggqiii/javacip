package inference

import configuration.Configuration
import utils.*
import scala.annotation.tailrec
import configuration.assertions.*
import configuration.types.*
import inference.resolvers.resolve
import inference.concretizers.concretize
import inference.deconflicters.deconflict
import inference.parameterizers.parameterizeMembers

import scala.util.{Try, Success, Failure, Either, Left, Right}

def infer(log: Log, config: Configuration): LogWithOption[Configuration] =
  println(
    s"Config Transitions | No. Configs | Current Breadth | Current Depth"
  )
  val res = infer(log, addAllToConfigs(Map(), config :: Nil))
  println()
  res

@tailrec
private def infer(
    log: Log,
    configs: Map[Int, List[Configuration]],
    a: Int = 0
): LogWithOption[Configuration] =
  getConfig(configs) match
    case None => LogWithNone(log.addError(s"Terminating as type errors exist"))
    case Some((x, xs)) =>
      if a > 1000000 then return LogWithNone(log.addError("max hit", x.toString))
      if a % 1000 == 0 && !log.appConfig.debug && !log.appConfig.verbose then
        print(
          "\r" + f"$a%18d | ${configs.map((k, v) => v.size).sum}%11d | ${x.maxBreadth}%15d | ${x.maxDepth}%14d"
        )
      if x.maxBreadth > 3 || x.maxDepth > 3 then infer(log, xs, a + 1)
      else
        val res = resolve(log, x) >>= deconflict >>= concretize >>= parameterizeMembers
        if res.isLeft then
          val allConfigs = addAllToConfigs(xs, res.left)
          infer(res.log, allConfigs, a + 1)
        else
          LogWithSome(
            res.log
              .addSuccess(s"successfully inferred compilable configuration", res.right.toString),
            res.right
          )

private def getConfig(
    configs: Map[Int, List[Configuration]]
): Option[(Configuration, Map[Int, List[Configuration]])] =
  val x = configs.filter((k, v) => !v.isEmpty)
  if x.isEmpty then None
  else
    val minKey = x.keySet.min
    val first  = x(minKey).head
    val second = x + (minKey -> x(minKey).tail)
    Some((first, second))

@tailrec
private def addAllToConfigs(
    configs: Map[Int, List[Configuration]],
    newConfigs: List[Configuration]
): Map[Int, List[Configuration]] =
  newConfigs match
    case Nil => configs
    case x :: xs =>
      val key = x.maxBreadth * x.maxDepth
      if !configs.contains(key) then addAllToConfigs(configs + (key -> (x :: Nil)), xs)
      else addAllToConfigs(configs + (key                           -> (x :: configs(key))), xs)
