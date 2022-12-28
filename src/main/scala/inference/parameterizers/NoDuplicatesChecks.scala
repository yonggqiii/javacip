package inference.parameterizers

import configuration.Configuration
import configuration.declaration.*
import configuration.types.*
import utils.*

private def checkNoDuplicates(
    log: Log,
    config: Configuration,
    x: String
): LogWithEither[List[Configuration], Configuration] =
  val decl = config.getUnderlyingDeclaration(SomeClassOrInterfaceType(x))
  for (k, v) <- decl.methods do
    for i <- 0 until v.size do
      for j <- (i + 1) until v.size do
        // println(s"$x, ${v(i).signature}, ${v(j).signature}")
        //if x == "B" then println(s"checking ${v(i)} and ${v(j)}")
        if !v(i).isInstanceOf[MethodWithContext] &&
          !v(j).isInstanceOf[MethodWithContext] &&
          v(i).signature.erased(decl) == v(j).signature.erased(decl)
        then
          // println(s"$x failed")
          return LogWithLeft(
            log.addWarn(s"$x has two methods of the same erasure: ${v(i)} and ${v(j)}"),
            Nil
          )
  //else if x == "B" then println(s"${v(i)} and ${v(j)} passed")
  //if x == "B" then println(decl)
  // println(s"$x passed")
  return LogWithRight(log, config)
