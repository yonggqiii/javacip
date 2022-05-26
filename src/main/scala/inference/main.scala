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
