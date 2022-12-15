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
  infer(log, config :: Nil)

@tailrec
private def infer(
    log: Log,
    configs: List[Configuration],
    a: Int = 0
): LogWithOption[Configuration] =
  if a > 1000000 then return LogWithNone(log.addError("max hit", configs(0).toString))
  if a % 100 == 0 then println(s"$a, ${configs.size}")
  configs match
    case Nil => LogWithNone(log.addError(s"Terminating as type errors exist"))
    case x :: xs =>
      val res = resolve(log, x) >>= deconflict >>= concretize >>= parameterizeMembers
      if res.isLeft then infer(res.log, res.left ::: xs, a + 1)
      else if !res.right.omega.isEmpty then infer(res.log, res.right :: xs, a + 1)
      else
        LogWithSome(
          res.log.addSuccess(s"successfully inferred compilable configuration", res.right.toString),
          res.right
        )
