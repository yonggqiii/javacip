package inference.typecheckers

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

import sourcebuilder.buildSource
import configuration.Configuration
import configuration.Invocation
import configuration.assertions.*
import configuration.types.*
import utils.*
import configuration.declaration.DEFAULT

private[inference] def typecheck(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  for invocation <- config.theta do
    val Invocation(source, methodName, args, returnType, paramChoices) = invocation
    val decl       = config.getUnderlyingDeclaration(source.asInstanceOf[SomeClassOrInterfaceType])
    val allMethods = decl.getAccessibleMethods(config, DEFAULT)
    if !allMethods.contains(methodName) then
      return LogWithLeft(log.addWarn(s"$source does not contain $methodName!"), Nil)
    val possibleMethods = allMethods(methodName)
      .filter(v => v.callableWithNArgs(args.size))
      .map(_.asNArgs(args.size))
  // println(newCU)
  LogWithRight(log, config)
