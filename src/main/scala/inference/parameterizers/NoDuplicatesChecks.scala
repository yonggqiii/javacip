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
  val decl = config.getUnderlyingDeclaration(ClassOrInterfaceType(x))
  for (k, v) <- decl.methods do
    for i <- 0 until v.size do
      for j <- (i + 1) until v.size do
        if !v(i).isInstanceOf[MethodWithContext] &&
          !v(j).isInstanceOf[MethodWithContext] &&
          v(i).signature.erased == v(j).signature.erased
        then
          // println(s"$x failed")
          return LogWithLeft(
            log.addWarn(s"$x has two methods of the same erasure: ${v(i)} and ${v(j)}"),
            Nil
          )
  // println(x)
  return LogWithRight(log, config)
