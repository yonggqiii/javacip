package inference.parameterizers

import scala.collection.mutable.{Map as MutableMap, Set as MutableSet, ArrayBuffer}

import configuration.Configuration
import configuration.assertions.ImplementsMethodAssertion
import configuration.types.*
import configuration.declaration.*
import utils.*
import com.github.javaparser.ast.`type`.TypeParameter

private[inference] def parameterizeMembers(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  val order = getTopologicalOrdering(config)
  combineMembers(log, config, order)
//LogWithRight(log.addWarn("parameterizeMembers not implemented!"), config)

private def combineMembers(
    log: Log,
    config: Configuration,
    types: List[String]
): LogWithEither[List[Configuration], Configuration] = types match
  case Nil => LogWithRight(log.addSuccess("combineMembers complete!"), config)
  case x :: xs =>
    val decl = config.getUnderlyingDeclaration(ClassOrInterfaceType(x))
    val baseType =
      if x(0) == 'ξ' then
        TemporaryType(
          java.lang.Integer.parseInt(x.substring(1)),
          (0 until decl.numParams).map(i => TypeParameterIndex(x, i)).toVector
        )
      else
        ClassOrInterfaceType(
          x,
          (0 until decl.numParams).map(i => TypeParameterIndex(x, i)).toVector
        )
    if !decl.isAbstract then
      // concrete class
      // check implements

      // get the inherited methods
      val inheritedMethods = decl.getAllInheritedMethods(config)
      // from inherited methods obtain the abstract methods
      val abstractMethods = inheritedMethods
        .map((k, v) => (k -> v.filter(m => m.isAbstract)))
        .filter((k, v) => !v.isEmpty)
      // from inherited methods and the declaration's own methods get
      // the concrete methods
      val concreteInheritedMethods = (inheritedMethods
        .map((k, v) => (k -> v.filter(m => !m.isAbstract)))
        .filter((k, v) => !v.isEmpty))
      val concreteMethods = MutableMap[String, ArrayBuffer[Method]]()
      for (k, v) <- decl.methods do
        concreteMethods(k) = ArrayBuffer(v.filter(m => !m.isInstanceOf[MethodWithContext]): _*)
      for (k, v) <- concreteInheritedMethods do
        if !concreteMethods.contains(k) then concreteMethods(k) = ArrayBuffer(v: _*)
        else concreteMethods(k).addAll(v)

      // ensure that all abstract methods are overridden
      for (k, v) <- abstractMethods do
        for abstractMethod <- v do
          if !concreteMethods.contains(k) || !concreteMethods(k).exists(m =>
              m.overrides(abstractMethod, config)
            )
          then
            // none provably override it; assert that some alpha :> decl and alpha implements
            // abstract method
            // find the alpha and ensure it does not ever implement/extend the source of the abstract method
            val possibilities = ArrayBuffer[Type](baseType)
            // val queue         = ???
            val newAlpha = InferenceVariableFactory.createAlpha(
              scala.util.Left(""),
              Nil,
              false,
              (0 until decl.numParams).map(i => TypeParameterIndex(x, i)).toSet
            )
            val newAssertion = ImplementsMethodAssertion(
              baseType,
              abstractMethod
            ) // v(0) is safe because we filtered out empty methods
            val subtypeAssertion = baseType <:~ newAlpha
            val newConfig =
              config.addToPsi(newAlpha).asserts(newAssertion) //.asserts(subtypeAssertion)
            return LogWithLeft(
              log.addInfo(
                s"$x must have access to an overriding implementation of $abstractMethod"
              ),
              newConfig.copy(phi2 =
                newConfig.phi2 + (newAlpha -> InferenceVariableMemberTable(newAlpha))
              ) :: Nil
            )
      return combineMembers(log, config, xs)
    else
      // abstract class or interface
      val inheritedMethods = decl.getAllInheritedMethods(config)
      val abstractMethods = inheritedMethods
        .map((k, v) => (k -> v.filter(m => m.isAbstract)))
        .filter((k, v) => !v.isEmpty)
      println(abstractMethods)
      println(x)
      return combineMembers(log, config, xs)

private def getTopologicalOrdering(config: Configuration): List[String] =
  val vertices = config.delta.keySet | config.phi1.keySet
  // build the erasure graph, containing only the right vertices
  val graph =
    Map(
      vertices
        .map(i =>
          i -> config
            .getUnderlyingDeclaration(ClassOrInterfaceType(i))
            .getDirectAncestors
            .map(_.identifier)
            .filter(x => vertices.contains(x))
        )
        .toSeq: _*
    )
  val counts = MutableMap(graph.map((k, v) => k -> v.size).toSeq: _*)
  if counts.values.min != 0 then ???
  var res: List[String] = Nil
  while !counts.isEmpty do
    val x = counts.find((k, v) => v == 0).get
    res = x._1 :: res
    counts.remove(x._1)
    for (k, v) <- graph do if v.contains(x._1) then counts(k) -= 1
  res.reverse
