import configuration.parseConfiguration
import inference.infer
import utils.{Log, LogWithSome, parseCommandLineArgs}
object Main:
  def main(args: Array[String]): Unit =
    parseCommandLineArgs(args) match
      case None => ()
      case Some(config) =>
        LogWithSome(Log(config), config.in)
          .flatMap(parseConfiguration)
//          .flatMap(infer)
          .log
          .flush()
