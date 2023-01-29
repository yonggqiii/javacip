package utils

/** A type that contains a log object and an option object */
sealed trait LogWithOption[+A]:
  def >>=[B](f: (Log, A) => LogWithOption[B]): LogWithOption[B] = flatMap(f)
  def >*>[B](f: (Log, A) => (Log, B)): LogWithOption[B]         = map(f)
  def >->=[B](f: A => B): LogWithOption[B]                      = rightmap(f)

  /** Maps this object using a function that takes in a log and A and produces a log with B
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

  /** Flatmaps this object using a function that takes in a log and an A and produces a
    * LogWithOption[B]
    * @param f
    *   the function to flatMap
    * @return
    *   the resulting LogWithOption object after flatMapping
    */
  def flatMap[B](f: (Log, A) => LogWithOption[B]): LogWithOption[B]

  /** flatMaps the Option object
    * @param the
    *   function to flatMap the A
    * @return
    *   the resulting LogWithOption object
    */
  def rightflatMap[B](f: A => Option[B]): LogWithOption[B]

  /** The log attached to this object
    */
  val log: Log

  /** The Option[A] attached to this object
    */
  def opt: Option[A]

  /** Consumes each element in A assuming A is some Vector[B].
    * @param f
    *   the function to consume the elements in the vector
    * @param ev
    *   the implicit evidence that A is some Vector[B]
    */
  def foreach[B](f: B => Unit)(using ev: A <:< Vector[B]): Unit

  /** Maps A as a Vector[B] into a Vector[C]
    * @param f
    *   the function to map each element of A assuming A is some Vector[C]
    * @param ev
    *   the evidence that A is some Vector[B]
    * @return
    *   the resulting LogWithOption after mapping each element in the Vector
    */
  def rightvmap[B, C](f: B => C)(using ev: A <:< Vector[B]): LogWithOption[Vector[C]]

  /** Consumes the option object if it is present
    * @param f
    *   the consumer
    * @return
    *   the log object after the object has been consumed
    */
  def consume(f: (Log, A) => Log): Log

/** A type who has a log and a Some[A] */
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

  def foreach[B](f: B => Unit)(using x: A <:< Vector[B]): Unit =
    val v = x(some)
    v.foreach(f)

  def rightvmap[B, C](f: B => C)(using x: A <:< Vector[B]): LogWithOption[Vector[C]] =
    val vector = x(some)
    LogWithSome(log, vector.map(f))

  def consume(f: (Log, A) => Log): Log =
    f(log, some)

/** A type who has a log and a None */
case class LogWithNone[+A](log: Log) extends LogWithOption[A]:
  def map[B](f: (Log, A) => (Log, B)): LogWithOption[B] = LogWithNone(log)

  def rightmap[B](f: A => B): LogWithOption[B] = LogWithNone(log)

  def flatMap[B](f: (Log, A) => LogWithOption[B]): LogWithOption[B] = LogWithNone(log)

  def rightflatMap[B](f: A => Option[B]): LogWithOption[B] = LogWithNone(log)

  def opt = None

  def foreach[B](f: B => Unit)(using x: A <:< Vector[B]): Unit = ()

  def rightvmap[B, C](f: B => C)(using x: A <:< Vector[B]): LogWithOption[Vector[C]] =
    LogWithNone(log)

  def consume(f: (Log, A) => Log): Log = log

/** Companion object to the LogWithOption class */
object LogWithOption:
  /** Creates a LogWithOption object given a log and an option
    * @param log
    *   the log
    * @param option
    *   the option
    * @return
    *   the resulting LogWithOption type
    */
  def apply[A](log: Log, option: Option[A]): LogWithOption[A] = option match
    case Some(x) => LogWithSome(log, x)
    case None    => LogWithNone(log)
