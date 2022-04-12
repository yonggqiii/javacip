package utils

import scopt.OParser

private val builder = OParser.builder[AppConfig]
private val cmdOptsParser =
  import builder.*
  OParser.sequence(
    programName("javacpp"),
    head("javacpp", "0.0.x"),
    opt[Unit]('v', "verbose")
      .action((x, c) => c.copy(verbose = true))
      .text("verbose mode"),
    opt[String]('o', "output")
      .required()
      .valueName("<file>")
      .action((x, c) => c.copy(out = x))
      .text("file path to write stubs"),
    arg[String]("<input file>")
      .required()
      .action((x, c) => c.copy(in = x))
      .text("target of compilation"),
    opt[Unit]('d', "debug")
      .action((x, c) => c.copy(verbose = true, debug = true))
      .text("debug mode")
  )

def parseCommandLineArgs(args: Array[String]) =
  OParser.parse(cmdOptsParser, args, AppConfig())
