package utils

import scala.util.{Either, Left, Right}

/** A log with an right-biased either object */
sealed trait LogWithEither[+L, +R]:
  /** The log attached to the object */
  val log: Log

  /** A right-biased map on the either object
    * @param f
    *   the function to map the right object
    * @return
    *   the resulting LogWithEither object
    */
  def map[A](f: (Log, R) => (Log, A)): LogWithEither[L, A]

  /** A right-biased flatMap on the either object
    * @param f
    *   the function to flatMap the right object
    * @return
    *   the resulting LogWithEither object
    */
  def flatMap[A >: L, B](f: (Log, R) => LogWithEither[A, B]): LogWithEither[A, B]

  /** Alias for flatMap
    * @param f
    *   the function to flatMap the right object
    * @return
    *   the resulting LogWithEither object
    */
  def >>=[A >: L, B](f: (Log, R) => LogWithEither[A, B]): LogWithEither[A, B] = flatMap(f)

  /** Determines if the either contains a Right */
  def isRight: Boolean

  /** Determines if the either contains a Left */
  def isLeft: Boolean

  /** Obtains the right object
    * @throws a
    *   java.lang.ClassCastException if it is a Left
    */
  def right: R

  /** Obtains the left object
    * @throws a
    *   java.lang.ClassCastException if it is a Right
    */
  def left: L

/** A log with a right */
case class LogWithRight[+R](log: Log, right: R) extends LogWithEither[Nothing, R]:

  def map[A](f: (Log, R) => (Log, A)): LogWithRight[A] =
    val (newLog, newObj) = f(log, right)
    LogWithRight(newLog, newObj)

  def flatMap[A >: Nothing, B](f: (Log, R) => LogWithEither[A, B]): LogWithEither[A, B] =
    f(log, right)

  def isRight = true

  def isLeft = false

  def left: Nothing =
    throw java.lang.ClassCastException("cannot cast a LogWithRight to a LogWithLeft!")

/** A log with a left */
case class LogWithLeft[+L](log: Log, left: L) extends LogWithEither[L, Nothing]:

  def map[A](f: (Log, Nothing) => (Log, A)): LogWithLeft[L] = LogWithLeft(log, left)

  def flatMap[A >: L, B](f: (Log, Nothing) => LogWithEither[A, B]): LogWithLeft[A] =
    LogWithLeft(log, left)

  def isRight = false

  def isLeft = true

  def right: Nothing =
    throw java.lang.ClassCastException("cannot cast a LogWithLeft to a LogWithRight!")
