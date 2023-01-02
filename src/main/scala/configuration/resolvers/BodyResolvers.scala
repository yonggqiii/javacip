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

def resolveVariableDeclarator(
    log: Log,
    declarator: VariableDeclarator,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  declarator.getInitializer.toScala match
    case None => LogWithOption(log, Some(config))
    case Some(expr) =>
      val typeOfInitializer = resolveExpression(log, expr, config, memo)
      val typeOfVariable    = resolveSolvedType(declarator.getType.resolve)
      typeOfInitializer.rightmap(t =>
        config._3 += (typeOfVariable ~:= t) //SubtypeAssertion(t, typeOfVariable)
        config
      )
