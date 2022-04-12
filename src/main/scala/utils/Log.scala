package utils

import scala.Console.{RED, GREEN, YELLOW, CYAN, RESET}

final class Log(
    val appConfig: AppConfig,
    val messages: Vector[LogMessage] = Vector()
):
  private def logOrNot(message: LogMessage) = message match
    case _: ErrorMessage => logOrFlush(message)
    case _ => if appConfig.verbose then logOrFlush(message) else this

  private def logOrFlush(message: LogMessage) =
    if appConfig.debug then
      message.flush()
      this
    else Log(appConfig, messages :+ message)

  def addSuccess(header: String, body: String = "") =
    logOrNot(SuccessMessage(header, body))
  def addInfo(header: String, body: String = "") =
    logOrNot(InfoMessage(header, body))
  def addWarn(header: String, body: String = "") =
    logOrNot(WarnMessage(header, body))
  def addError(header: String, body: String = "") =
    logOrNot(ErrorMessage(header, body))
  def flush() =
    messages.foreach(_.flush())
    Log(appConfig)

sealed trait LogMessage:
  def flush(): Unit
final case class SuccessMessage(val header: String, val body: String)
    extends LogMessage:
  def flush() =
    println(s"[${GREEN}SUCCESS$RESET] $header")
    if body != "" then println(body)
final case class InfoMessage(val header: String, val body: String)
    extends LogMessage:
  def flush() =
    println(s"[${CYAN}INFO$RESET] $header")
    if body != "" then println(body)
final case class WarnMessage(val header: String, val body: String)
    extends LogMessage:
  def flush() =
    println(s"[${YELLOW}WARN$RESET] $header")
    if body != "" then println(body)
final case class ErrorMessage(val header: String, val body: String)
    extends LogMessage:
  def flush() =
    println(s"[${RED}ERROR$RESET] $header")
    if body != "" then println(body)
