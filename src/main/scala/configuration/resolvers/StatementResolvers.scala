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
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  if stmt.isAssertStmt then resolveAssertStmt(log, stmt.asAssertStmt, config, memo)
  else if stmt.isDoStmt then resolveDoStmt(log, stmt.asDoStmt, config, memo)
  else if stmt.isExplicitConstructorInvocationStmt then
    resolveExplicitConstructorInvocationStmt(
      log,
      stmt.asExplicitConstructorInvocationStmt,
      config,
      memo
    )
  else if stmt.isForEachStmt then resolveForEachStmt(log, stmt.asForEachStmt, config, memo)
  else if stmt.isForStmt then resolveForStmt(log, stmt.asForStmt, config, memo)
  else if stmt.isIfStmt then resolveIfStmt(log, stmt.asIfStmt, config, memo)
  else if stmt.isReturnStmt then resolveReturnStmt(log, stmt.asReturnStmt, config, memo)
  else if stmt.isSwitchStmt then resolveSwitchStmt(log, stmt.asSwitchStmt, config, memo)
  else if stmt.isThrowStmt then resolveThrowStmt(log, stmt.asThrowStmt, config, memo)
  else if stmt.isTryStmt then resolveTryStmt(log, stmt.asTryStmt, config, memo)
  else if stmt.isWhileStmt then resolveWhileStmt(log, stmt.asWhileStmt, config, memo)
  else LogWithSome(log, config)

private def resolveAssertStmt(
    log: Log,
    stmt: AssertStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  resolveExpression(log, stmt.getCheck, config, memo)
    .flatMap((log, checkType) =>
      config._6 += checkType
      val message = stmt.getMessage.toScala
        .map(e => resolveExpression(log, e, config, memo))
        .getOrElse(LogWithSome(log, STRING))
      message.rightmap(messageType =>
        config._3 += checkType =:~ PRIMITIVE_BOOLEAN
        config._3 += messageType ~=~ STRING
      )
    )
    .rightmap(x => config)

private def resolveDoStmt(
    log: Log,
    stmt: DoStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  resolveExpression(log, stmt.getCondition, config, memo)
    .rightmap(condType =>
      config._3 += condType =:~ PRIMITIVE_BOOLEAN
      config._6 += condType
    )
    .rightmap(x => config)

private def resolveExplicitConstructorInvocationStmt(
    log: Log,
    stmt: ExplicitConstructorInvocationStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] = ???

private def resolveForEachStmt(
    log: Log,
    stmt: ForEachStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  val iterable = stmt.getIterable
  val varType = resolveSolvedType(
    stmt.getVariable.getVariables().asScala.toVector(0).getType().resolve()
  )
  resolveExpression(log, iterable, config, memo)
    .rightmap(iterableType =>
      // two cases: first case where it is some Iterable<T>
      val iterableTTypeArg   = createTypeArgumentFromContext(iterable, config)
      val iterableTType      = ClassOrInterfaceType("java.lang.Iterable", Vector(iterableTTypeArg))
      val iterableTAssertion = (iterableType <:~ iterableTType) && (iterableTTypeArg =:~ varType)
      // second case where it is array
      val arrayElementType = createDisjunctiveTypeWithPrimitivesFromContext(iterable, config)
      val arrayType        = ArrayType(arrayElementType)
      val arrayAssertion   = (arrayElementType =:~ varType) && (iterableType ~=~ arrayType)
      // one of these two must be true
      config._3 += iterableTAssertion || arrayAssertion
      config._4 += iterableTTypeArg
      config._4 += arrayElementType
    )
    // .flatMap((log, iterableType) =>
    //   resolveExpression(log, loopVariable, config, memo).rightmap(varType =>
    //     // two cases: first case where it is some Iterable<T>
    //     val iterableTTypeArg = createTypeArgumentFromContext(iterable, config)
    //     val iterableTType    = ClassOrInterfaceType("java.lang.Iterable", Vector(iterableTTypeArg))
    //     val iterableTAssertion = (iterableType <:~ iterableTType) && (iterableTTypeArg =:~ varType)
    //     // second case where it is array
    //     val arrayElementType = createDisjunctiveTypeWithPrimitivesFromContext(iterable, config)
    //     val arrayType        = ArrayType(arrayElementType)
    //     val arrayAssertion   = (arrayElementType =:~ varType) && (iterableType <:~ arrayType)
    //     // one of these two must be true
    //     config._3 += iterableTAssertion || arrayAssertion
    //     config._4 += iterableTTypeArg
    //     config._4 += arrayElementType
    //   )
    // )
    .rightmap(x => config)

private def resolveForStmt(
    log: Log,
    stmt: ForStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  stmt.getCompare.toScala
    .map(cmp =>
      resolveExpression(log, cmp, config, memo)
        .rightmap(t =>
          config._3 += (t =:~ PRIMITIVE_BOOLEAN)
          config
        )
    )
    .getOrElse(LogWithSome(log, config))

private def resolveIfStmt(
    log: Log,
    stmt: IfStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  resolveExpression(log, stmt.getCondition, config, memo).rightmap(t =>
    config._3 += (t =:~ PRIMITIVE_BOOLEAN)
    config
  )

private def resolveSwitchStmt(
    log: Log,
    stmt: SwitchStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  // constant and enums
  val entries =
    stmt.getEntries.asScala.flatMap(switchEntry => switchEntry.getLabels.asScala).toVector
  val entryTypes =
    flatMapWithLog(log, entries)((log, expr) => resolveExpression(log, expr, config, memo))
  entryTypes.flatMap((log, entryTypes) =>
    resolveExpression(log, stmt.getSelector, config, memo).rightmap(selectorType =>
      val intType = selectorType =:~ PRIMITIVE_INT &&
        ConjunctiveAssertion(entryTypes.map(_ =:~ PRIMITIVE_INT))
      val stringType = selectorType ~=~ STRING &&
        ConjunctiveAssertion(entryTypes.map(_ ~=~ STRING))
      config._3 += intType || stringType
      config
    )
  )

private def resolveThrowStmt(
    log: Log,
    stmt: ThrowStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  resolveExpression(log, stmt.getExpression, config, memo).rightmap(exprType =>
    config._3 += exprType =:~ ClassOrInterfaceType("java.lang.RuntimeException")
    config
  )

private def resolveTryStmt(
    log: Log,
    stmt: TryStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  val resources = stmt.getResources.asScala.toVector
  val catchClauses = stmt.getCatchClauses.asScala
    .map(x => resolveSolvedType(x.getParameter.getType.resolve))
    .toVector
  flatMapWithLog(log, resources)(resolveExpression(_, _, config, memo)).rightmap(v =>
    config._3 += ConjunctiveAssertion(
      v.map(x => x <:~ ClassOrInterfaceType("java.lang.AutoCloseable"))
    )
    config._3 += ConjunctiveAssertion(
      catchClauses.map(x => x <:~ ClassOrInterfaceType("java.lang.RuntimeException"))
    )
    config
  )

private def resolveWhileStmt(
    log: Log,
    stmt: WhileStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
      Option[Type]
    ]
): LogWithOption[MutableConfiguration] =
  resolveExpression(log, stmt.getCondition, config, memo).rightmap(t =>
    config._3 += (t =:~ PRIMITIVE_BOOLEAN)
    config
  )

private def resolveReturnStmt(
    log: Log,
    stmt: ReturnStmt,
    config: MutableConfiguration,
    memo: MutableMap[
      String,
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
          exprType.flatMap((log, ttype) =>
            if returnType.isVoid then LogWithNone(log.addError(s"$stmt cannot have return type!"))
            else
              config._3 += (ttype =:~ resolveSolvedType(returnType))
              LogWithSome(log, config)
          )
// exprType.rightmap(ttype =>
//   if returnType.isVoid then config._3 += (ttype ~=~ PRIMITIVE_VOID) || (ttype ~=~ BOXED_VOID)
//   else config._3 += SubtypeAssertion(ttype, resolveSolvedType(returnType))
//   config
// )
