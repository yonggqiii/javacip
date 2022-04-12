package utils

case class AppConfig(
    verbose: Boolean = false,
    in: String = ".",
    out: String = ".",
    debug: Boolean = true
)
