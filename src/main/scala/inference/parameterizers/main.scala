package inference.parameterizers

import scala.collection.mutable.{Map as MutableMap, Set as MutableSet, ArrayBuffer}

import configuration.Configuration
import configuration.assertions.ImplementsMethodAssertion
import configuration.types.*
import configuration.declaration.*
import utils.*

private[inference] def parameterizeMembers(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  val order = getTopologicalOrdering(config)
  //println(s"start: $order")
  combineMembers(log, config, order)
//LogWithRight(log.addWarn("parameterizeMembers not implemented!"), config)

private def combineMembers(
    log: Log,
    config: Configuration,
    types: List[String]
): LogWithEither[List[Configuration], Configuration] = types match
  case Nil => LogWithRight(log.addSuccess("combineMembers complete!"), config)
  case x :: xs =>
    checkImplements(log, config, x) >>= ((l, c) => checkNoDuplicates(l, c, x))
      >>= ((l, c) => checkOverrideEquivalence(l, c, x))
      >>= ((l, c) => finalizeMethod(l, c, x))
      >>= ((l, c) => finalizeConstructor(l, c, x))
      >>= ((l, c) => combineMembers(l, c, xs))

private def getTopologicalOrdering(config: Configuration): List[String] =
  val vertices = config.delta.keySet | config.phi1.keySet
  // build the erasure graph, containing only the right vertices
  val graph =
    Map(
      vertices
        .map(i =>
          i -> config
            .getUnderlyingDeclaration(SomeClassOrInterfaceType(i))
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
