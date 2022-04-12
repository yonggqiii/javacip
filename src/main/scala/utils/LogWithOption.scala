package utils

sealed trait LogWithOption[+A]:
  /** Maps this objection using a function that takes in a log and A and
    * produces a log with B
    * @param f
    *   the function to map
    * @return
    *   the resulting LogWithOption object after mapping
    */
  def map[B](f: (Log, A) => (Log, B)): LogWithOption[B]

  /** Maps the option object using a function from A to B
    * @param the
    *   function to map the option object
    * @return
    *   the resulting LogWithOption object
    */
  def rightmap[B](f: A => B): LogWithOption[B]
  def flatMap[B](f: (Log, A) => LogWithOption[B]): LogWithOption[B]
  def rightflatMap[B](f: A => Option[B]): LogWithOption[B]
  val log: Log
  def opt: Option[A]

case class LogWithSome[+A](log: Log, some: A) extends LogWithOption[A]:
  def map[B](f: (Log, A) => (Log, B)): LogWithOption[B] =
    val (newLog, newObj) = f(log, some)
    LogWithSome(newLog, newObj)
  def rightmap[B](f: A => B): LogWithOption[B] = LogWithSome(log, f(some))
  def flatMap[B](f: (Log, A) => LogWithOption[B]): LogWithOption[B] =
    f(log, some)
  def rightflatMap[B](f: A => Option[B]): LogWithOption[B] =
    LogWithOption(log, f(some))
  def opt = Some(some)

case class LogWithNone[+A](log: Log) extends LogWithOption[A]:
  def map[B](f: (Log, A) => (Log, B)): LogWithOption[B] = LogWithNone(log)
  def rightmap[B](f: A => B): LogWithOption[B]          = LogWithNone(log)
  def flatMap[B](f: (Log, A) => LogWithOption[B]): LogWithOption[B] =
    LogWithNone(log)
  def rightflatMap[B](f: A => Option[B]): LogWithOption[B] = LogWithNone(log)
  def opt                                                  = None
object LogWithOption:
  def apply[A](log: Log, option: Option[A]): LogWithOption[A] = option match
    case Some(x) => LogWithSome(log, x)
    case None    => LogWithNone(log)
