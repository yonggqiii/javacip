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

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import scala.util.{Try, Success, Failure, Either, Left, Right}

def infer(log: Log, config: Configuration): LogWithOption[Configuration] =
  infer(log, config :: Nil)

@tailrec
private def infer(
    log: Log,
    configs: List[Configuration],
    a: Int = 0
): LogWithOption[Configuration] =
  //if a > 2000 then return LogWithNone(log.addError("max hit"))
  //if a % 100 == 0 || a > 1900 then println(s"$a, ${configs.size}")
  configs match
    case Nil => LogWithNone(log.addError(s"Terminating as type errors exist"))
    // case _ =>
    //   val freshLog = Log(log.appConfig)
    //   // val f = (x: Configuration) =>
    //   //   Future {
    //   //     resolve(freshLog, x)
    //   //       .flatMap(concretize)
    //   //       .flatMap(deconflict)
    //   //       .flatMap(parameterizeMembers)
    //   //   }
    //   // val pass = Await.result(Future.traverse(configs)(f), 10.seconds)
    //   // val pass =
    //   //   configs
    //   //     .map(
    //   //       resolve(freshLog, _)
    //   //         .flatMap(concretize)
    //   //         .flatMap(deconflict)
    //   //         .flatMap(parameterizeMembers)
    //   //     )
    //   pass.find(_.isRight) match
    //     case Some(c) => LogWithSome(log.addAll(c.log.messages), c.right)
    //     case None =>
    //       val res = pass.foldLeft(log, List[Configuration]())((lgls, lwe) =>
    //         val (oldLog, oldList) = lgls
    //         val newLog            = lwe.log
    //         val newList           = lwe.left
    //         (oldLog.addAll(newLog.messages), newList ::: oldList)
    //       )
    //       infer(res._1, res._2, a + 1)
    case x :: xs =>
      val res = resolve(log, x).flatMap(concretize).flatMap(deconflict).flatMap(parameterizeMembers)
      if res.isLeft then infer(res.log, res.left ::: xs, a + 1)
      else LogWithSome(res.log, res.right)
