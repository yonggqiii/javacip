ThisBuild / scalaVersion := "3.0.2"

val scalatest = "org.scalatest" %% "scalatest-funsuite" % "3.2.11"
val javaparser =
  "com.github.javaparser" % "javaparser-symbol-solver-core" % "3.24.0"
val scopt = "com.github.scopt" %% "scopt" % "4.0.1"

lazy val app = (project in file("."))
  .settings(
    name                            := "JavaCIP",
    libraryDependencies += scalatest % "test",
    libraryDependencies += javaparser,
    libraryDependencies += scopt,
    assembly / mainClass       := Some("Main"),
    assembly / assemblyJarName := "javacip-0.0.1.jar"
  )
