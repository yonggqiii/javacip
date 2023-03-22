package utils

import scopt.OParser

private val builder = OParser.builder[AppConfig]
private val cmdOptsParser =
  import builder.*
  OParser.sequence(
    programName("javacip"),
    head("javacip", "1.0.0"),
    opt[Unit]('v', "verbose")
      .action((x, c) => c.copy(verbose = true))
      .text("verbose mode"),
    opt[String]('o', "output")
      .required()
      .valueName("<file>")
      .action((x, c) => c.copy(out = x))
      .text("file path to write stubs"),
    opt[Unit]('b', "benchmark")
      .action((x, c) => c.copy(benchmark = true))
      .text("benchmark mode"),
    arg[String]("<input file>")
      .required()
      .action((x, c) => c.copy(in = x))
      .text("target of compilation"),
    opt[Unit]('d', "debug")
      .action((x, c) => c.copy(verbose = true, debug = true))
      .text("debug mode"),
    opt[Int]('i', "iterations")
      .action((x, c) => c.copy(numIterations = x))
      .text("maximum number of compiler iterations"),
    opt[Int]("maxBreadth")
      .action((x, c) => c.copy(maxBreadth = x))
      .text("maximum breadth of generated types"),
    opt[Int]("maxDepth")
      .action((x, c) => c.copy(maxDepth = x))
      .text("maximum depth of generated types"),
    opt[Unit]('h', "heuristic")
      .action((x, c) => c.copy(heuristicSearch = true))
      .text("search in ascending order by heuristic value"),
    opt[Unit]('n', "noOverloading")
      .action((x, c) => c.copy(noOverloading = true))
      .text("bans method/constructor overloading in generated types")
  )

/** parses the command line arguments into the compiler
  * @param args
  *   the array of command line arguments
  * @return
  *   the resulting AppConfig
  */
def parseCommandLineArgs(args: Array[String]) =
  OParser.parse(cmdOptsParser, args, AppConfig())
