package utils

import scala.util.{Either, Left, Right}

sealed trait LogWithEither[+L, +R]:
  val log: Log
  def map[A](f: (Log, R) => (Log, A)): LogWithEither[L, A]
  def flatMap[A >: L, B](f: (Log, R) => LogWithEither[A, B]): LogWithEither[A, B]
  def isRight: Boolean
  def isLeft: Boolean
  def right: R
  def left: L

case class LogWithRight[+R](log: Log, right: R) extends LogWithEither[Nothing, R]:
  def map[A](f: (Log, R) => (Log, A)): LogWithRight[A] =
    val (newLog, newObj) = f(log, right)
    LogWithRight(newLog, newObj)
  def flatMap[A >: Nothing, B](f: (Log, R) => LogWithEither[A, B]): LogWithEither[A, B] =
    f(log, right)
  def isRight = true
  def isLeft  = false
  def left: Nothing =
    throw java.lang.ClassCastException("cannot cast a LogWithRight to a LogWithLeft!")

case class LogWithLeft[+L](log: Log, left: L) extends LogWithEither[L, Nothing]:
  def map[A](f: (Log, Nothing) => (Log, A)): LogWithLeft[L] = LogWithLeft(log, left)
  def flatMap[A >: L, B](f: (Log, Nothing) => LogWithEither[A, B]): LogWithLeft[A] =
    LogWithLeft(log, left)
  def isRight = false
  def isLeft  = true
  def right: Nothing =
    throw java.lang.ClassCastException("cannot cast a LogWithLeft to a LogWithRight!")
