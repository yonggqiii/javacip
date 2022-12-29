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
  val newConfig = config.fix
  for invocation <- newConfig.theta do
    val Invocation(source, methodName, args, returnType, paramChoices) = invocation
    // why underlying declartion? val decl       = config.getUnderlyingDeclaration(source.asInstanceOf[SomeClassOrInterfaceType])
    val decl = newConfig.getSubstitutedDeclaration(source.asInstanceOf[SomeClassOrInterfaceType])
    val allMethods = decl.getAccessibleMethods(newConfig, DEFAULT)
    if !allMethods.contains(methodName) then
      return LogWithLeft(log.addWarn(s"$source does not contain $methodName!"), Nil)
    val possibleMethods = allMethods(methodName)
      .filter(v => v.callableWithNArgs(args.size))
      .map(_.asNArgs(args.size))
      .filter(m =>
        val (assts, _) = m.callWith(args, paramChoices)
        checkPasses(newConfig assertsAllOf assts)
      )
      .toSet
    //println(s"start for $invocation")
    val mostSpecificMethod = possibleMethods.find(m =>
      val others = possibleMethods - m
      others.forall(other =>
        // assert all m is compatible with other
        val args       = m.signature.formalParameters
        val (assts, _) = other.callWith(args, m.typeParameterBounds.map(_._1).toSet)
        checkPasses(newConfig assertsAllOf assts)
      )
    )
    mostSpecificMethod match
      case None =>
        // println("lol")
        return LogWithLeft(
          log.addError(s"no most-specific method can be found for invocation $invocation!"),
          Nil
        )
      case Some(msm) =>
        val (assts, rt) = msm.callWith(args, paramChoices)
        if !checkPasses(
            newConfig assertsAllOf assts asserts ((rt <:~ returnType) || (rt <<~= returnType))
          )
        then
          return LogWithLeft(
            log.addError(
              s"most-specific method $msm for invocation $invocation does not have matching return types!"
            ),
            Nil
          )
  // println(newCU)
  LogWithRight(log, config)

private def checkPasses(config: Configuration): Boolean =
  val log = Log(AppConfig(verbose = false, in = "", out = "", debug = false), Vector())
  miniResolve(log, config :: Nil)

@tailrec
private def miniResolve(log: Log, ls: List[Configuration]): Boolean = ls match
  case Nil => false
  case x :: xs =>
    resolve(log, x) >>= deconflict >>= concretize match
      case _: LogWithRight[Configuration] => true
      case LogWithLeft(l, ls)             => miniResolve(l, ls ::: xs)
