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
      if v(i).isInstanceOf[Method] && v(i).isUnreasonable then
        return LogWithLeft(log.addWarn(s"$x is unreasonable!"), Nil)
      for j <- (i + 1) until v.size do
        if !v(i).isInstanceOf[MethodWithContext] &&
          !v(j).isInstanceOf[MethodWithContext] &&
          v(i).signature.erased(decl) == v(j).signature.erased(decl)
        then
          return LogWithLeft(
            log.addWarn(s"$x has two methods of the same erasure: ${v(i)} and ${v(j)}"),
            Nil
          )
  for i <- 0 until decl.constructors.size do
    for j <- (i + 1) until decl.constructors.size do
      val c1 = decl.constructors(i)
      val c2 = decl.constructors(j)
      if !c1.isInstanceOf[ConstructorWithContext] &&
        !c2.isInstanceOf[ConstructorWithContext] &&
        c1.signature.erased(decl) == c2.signature.erased(decl)
      then
        return LogWithLeft(
          log.addWarn(s"$x has two constructors of the same erasure: $c1 and $c2"),
          Nil
        )

  return LogWithRight(log, config)
