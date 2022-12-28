package inference.typecheckers

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

import scala.annotation.tailrec

import sourcebuilder.buildSource
import configuration.Configuration
import configuration.Invocation
import configuration.assertions.*
import configuration.types.*
import utils.*
import configuration.declaration.DEFAULT
import inference.resolvers.resolve
import inference.concretizers.concretize
import inference.deconflicters.deconflict

private[inference] def typecheck(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  // here, all assertions are provable
  for invocation <- config.theta do
    val Invocation(source, methodName, args, returnType, paramChoices) = invocation
    // why underlying declartion? val decl       = config.getUnderlyingDeclaration(source.asInstanceOf[SomeClassOrInterfaceType])
    val decl       = config.getSubstitutedDeclaration(source.asInstanceOf[SomeClassOrInterfaceType])
    val allMethods = decl.getAccessibleMethods(config, DEFAULT)
    if !allMethods.contains(methodName) then
      return LogWithLeft(log.addWarn(s"$source does not contain $methodName!"), Nil)
    val possibleMethods = allMethods(methodName)
      .filter(v => v.callableWithNArgs(args.size))
      .map(_.asNArgs(args.size))
      .filter(m =>
        val (assts, _) = m.callWith(args, paramChoices)
        checkPasses(config assertsAllOf assts)
      )
      .toSet
    println("start")
    val mostSpecificMethod = possibleMethods.find(m =>
      val others = possibleMethods - m
      others.forall(other =>
        // assert all m is compatible with other
        val args       = m.signature.formalParameters
        val (assts, _) = other.callWith(args, m.typeParameterBounds.map(_._1).toSet)
        checkPasses(config assertsAllOf assts)
      )
    )
    println(mostSpecificMethod)
  // println(newCU)
  LogWithRight(log, config)

private def checkPasses(config: Configuration): Boolean =
  val log = Log(AppConfig(verbose = true, in = "", out = "", debug = true), Vector())
  miniResolve(log, config :: Nil)

@tailrec
private def miniResolve(log: Log, ls: List[Configuration]): Boolean = ls match
  case Nil => false
  case x :: xs =>
    resolve(log, x) >>= deconflict >>= concretize match
      case _: LogWithRight[Configuration] => true
      case LogWithLeft(l, ls)             => miniResolve(l, ls ::: xs)
