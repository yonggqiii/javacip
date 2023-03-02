package utils

import scala.Console.{RED, GREEN, YELLOW, CYAN, RESET}

/** A log object. Logs immediately in debug mode, stores all messages in verbose mode, dumps
  * non-error message when not in either modes, quiet in benchmark mode
  * @param appConfig
  *   the app configuration
  * @param messages
  *   the messages of the log
  */
final class Log(
    val appConfig: AppConfig,
    val messages: Vector[LogMessage] = Vector()
):
  private def logOrNot(message: LogMessage) = message match
    case _ if appConfig.benchmark => this
    case _: ErrorMessage          => logOrFlush(message)
    case _                        => if appConfig.verbose then logOrFlush(message) else this

  private def logOrFlush(message: LogMessage) =
    if appConfig.debug then
      message.flush()
      this
    else Log(appConfig, messages :+ message)

  /** Adds a success message to the log
    * @param header
    *   the header of the message
    * @param body
    *   the body of the message
    * @return
    *   the resulting log object
    */
  def addSuccess(header: String, body: String = "") =
    logOrNot(SuccessMessage(header, body))

  /** Adds an info message to the log
    * @param header
    *   the header of the message
    * @param body
    *   the body of the message
    * @return
    *   the resulting log object
    */
  def addInfo(header: String, body: String = "") =
    logOrNot(InfoMessage(header, body))

  /** Adds a warning message to the log
    * @param header
    *   the header of the message
    * @param body
    *   the body of the message
    * @return
    *   the resulting log object
    */
  def addWarn(header: String, body: String = "") =
    logOrNot(WarnMessage(header, body))

  /** Adds an error message to the log
    * @param header
    *   the header of the message
    * @param body
    *   the body of the message
    * @return
    *   the resulting log object
    */
  def addError(header: String, body: String = "") =
    logOrNot(ErrorMessage(header, body))

  /** Flushes each message into stdout or stderr */
  def flush() =
    messages.foreach(_.flush())
    Log(appConfig)

/** A message in the log */
sealed trait LogMessage:
  def flush(): Unit

/** A success message
  * @param header
  *   the header of the message
  * @param body
  *   the body of the message
  */
final case class SuccessMessage(val header: String, val body: String) extends LogMessage:
  def flush() =
    println(s"[${GREEN}SUCCESS$RESET] $header")
    if body != "" then println(body)

/** An info message
  * @param header
  *   the header of the message
  * @param body
  *   the body of the message
  */
final case class InfoMessage(val header: String, val body: String) extends LogMessage:
  def flush() =
    println(s"[${CYAN}INFO$RESET] $header")
    if body != "" then println(body)

/** A warning message
  * @param header
  *   the header of the message
  * @param body
  *   the body of the message
  */
final case class WarnMessage(val header: String, val body: String) extends LogMessage:
  def flush() =
    println(s"[${YELLOW}WARN$RESET] $header")
    if body != "" then println(body)

/** An error message
  * @param header
  *   the header of the message
  * @param body
  *   the body of the message
  */
final case class ErrorMessage(val header: String, val body: String) extends LogMessage:
  def flush() =
    println(s"[${RED}ERROR$RESET] $header")
    if body != "" then println(body)
