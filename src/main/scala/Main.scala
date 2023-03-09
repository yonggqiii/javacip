import java.io.File
import java.io.FileWriter
import com.github.javaparser.ast.CompilationUnit
import configuration.{parseConfiguration, hasGenerics}
import inference.infer
import sourcebuilder.buildSource
import utils.{Log, LogWithSome, parseCommandLineArgs, mapWithLog}
object Main:
  def main(args: Array[String]): Unit =
    parseCommandLineArgs(args) match
      case None => ()
      case Some(config) =>
        LogWithSome(Log(config), config.in)
          // .map(hasGenerics)
          // .consume((log, x) =>
          //   if x then
          //     println(log.appConfig.in)
          //     log
          //   else log
          // )
          .flatMap(parseConfiguration)
          .flatMap(infer)
          .map(buildSource)
          .consume(writeFiles)
          .flush()

  def writeFiles(log: Log, cu: Vector[CompilationUnit]) =
    mapWithLog(log, cu)(writeFile(_, _))._1

  def writeFile(log: Log, cu: CompilationUnit): (Log, Unit) =
    val dirName = log.appConfig.out
    val dir     = File(dirName)
    if dir.isFile then return (log.addError(s"cannot write to $dirName; it is a file!"), ())
    if !dir.isDirectory then dir.mkdir()
    val nameOfFile = cu.getType(0).getNameAsString + ".java"
    val fullFileName =
      if dirName.charAt(dirName.length - 1) == '/' then dirName + nameOfFile
      else dirName + "/" + nameOfFile
    try
      val fw = FileWriter(fullFileName)
      fw.write(cu.toString)
      fw.close()
      return (log.addSuccess(s"successfully written $fullFileName"), ())
    catch
      case e: Throwable => return (log.addError("failed to write $fullFileName", e.getMessage), ())
