package configuration.resolvers

import com.github.javaparser.ast.body.*
import com.github.javaparser.ast.expr.*

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.collection.mutable.Map as MutableMap

import configuration.MutableConfiguration
import configuration.assertions.*
import configuration.types.*
import utils.*
import com.github.javaparser.ast.stmt.*

def resolveStatement(
    log: Log,
    stmt: Statement,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  if stmt.isReturnStmt then resolveReturnStmt(log, stmt.asReturnStmt, config, memo)
  else LogWithSome(log, config)

private def resolveReturnStmt(
    log: Log,
    stmt: ReturnStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      (Option[ClassOrInterfaceDeclaration], Option[MethodDeclaration], Expression),
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  stmt.findAncestor(classOf[MethodDeclaration]).toScala.map(x => x.resolve.getReturnType) match
    case None => LogWithNone(log.addError(s"$stmt not enclosed by method declaration"))
    case Some(returnType) =>
      stmt.getExpression.toScala match
        case None =>
          if returnType.isVoid then LogWithSome(log, config)
          else
            LogWithNone(
              log.addError(s"$stmt must return some value of type ${resolveSolvedType(returnType)}")
            )
        case Some(expr) =>
          val exprType = resolveExpression(log, expr, config, memo)
          exprType.rightmap(ttype =>
            if returnType.isVoid then config._3 += EquivalenceAssertion(ttype, PRIMITIVE_VOID)
            else config._3 += SubtypeAssertion(ttype, resolveSolvedType(returnType))
            config
          )
