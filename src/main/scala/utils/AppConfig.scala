package utils

/** A type containing the compiler options passed in by the user
  * @param verbose
  *   verbose mode; shows all log messages after execution
  * @param in
  *   the input file
  * @param out
  *   the java file to write to
  * @param debug
  *   debug mode; shows all log messages as they are generated
  */
case class AppConfig(
    verbose: Boolean = false,
    in: String = ".",
    out: String = ".",
    debug: Boolean = false,
    benchmark: Boolean = false
)
