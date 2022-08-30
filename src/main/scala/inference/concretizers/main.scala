package inference.concretizers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*
import scala.annotation.tailrec
import configuration.declaration.MissingTypeDeclaration
import scala.util.{Either, Left, Right}
import scala.collection.mutable.{Map as MutableMap, Set as MutableSet}
import inference.misc.expandDisjunctiveType
import scala.collection.mutable.ArrayBuffer
class ErasureGraph(
    val adjList: MutableMap[String, MutableSet[String]] = MutableMap()
):
  def addEdge(from: String, to: String): Unit =
    if !adjList.contains(from) then adjList(from) = MutableSet()
    if !adjList.contains(to) then adjList(to) = MutableSet()
    adjList(from) += to
  def addVertex(node: String): Unit =
    if !adjList.contains(node) then adjList(node) = MutableSet()
  def EΔΦ(identifier: String): Set[String] =
    def helper(current: String, acc: Set[String] = Set()): Set[String] =
      if current == identifier then return acc
      if acc.contains(current) then return Set()
      val neighbours              = adjList(current)
      val res: MutableSet[String] = MutableSet()
      for i <- neighbours do
        val res_i = helper(i, acc + current)
        res.addAll(res_i)
      return res.toSet
    val startingNeighbours      = adjList(identifier)
    val res: MutableSet[String] = MutableSet()
    for i <- startingNeighbours do
      val res_i = helper(i)
      res.addAll(res_i)
    return res.toSet + identifier
  def path(from: String, to: String): Boolean =
    val q       = ArrayBuffer[String](from)
    val visited = MutableSet[String]()
    while !q.isEmpty do
      val current = q.remove(0)
      if current == to then return true
      visited.add(current)
      q.addAll(adjList(current))
    return false

private def createErasureGraph(config: Configuration): ErasureGraph =
  val alphas = config.phi2.keys.filter(x => x.isInstanceOf[Alpha]).toVector
  val ceff = alphas
    .flatMap(x => config.phi2(x).constraintStore)
    .filter(x => x.isInstanceOf[SubtypeAssertion] || x.isInstanceOf[EquivalenceAssertion])
  val eg = ErasureGraph()
  val q  = ArrayBuffer[String]()
  q.addAll(config.delta.keys)
  q.addAll(config.phi1.keys)
  val otherTypes = ceff
    .map(x =>
      val (left, right) =
        if x.isInstanceOf[SubtypeAssertion] then
          val sub = x.asInstanceOf[SubtypeAssertion]
          (sub.left, sub.right)
        else
          val equiv = x.asInstanceOf[EquivalenceAssertion]
          (equiv.left, equiv.right)
      if !left.isInstanceOf[Alpha] then Some(left.identifier)
      else if !right.isInstanceOf[Alpha] then Some(right.identifier)
      else None
    )
    .filter(_.isDefined)
    .map(_.get)
  q.addAll(otherTypes)
  // add all the concrete types
  while !q.isEmpty do
    val c = q.remove(0)
    if !eg.adjList.contains(c) && c != OBJECT.identifier then
      val sups = config.getDirectSupertypes(NormalType(c, 0))
      for s <- sups do
        eg.addEdge(c, s.identifier)
        q += s.identifier
  // add the alphas
  for c <- ceff do
    if c.isInstanceOf[SubtypeAssertion] then
      val sub = c.asInstanceOf[SubtypeAssertion]
      eg.addEdge(sub.left.identifier, sub.right.identifier)
    else
      val equiv = c.asInstanceOf[EquivalenceAssertion]
      eg.addEdge(equiv.left.identifier, equiv.right.identifier)
      eg.addEdge(equiv.right.identifier, equiv.left.identifier)
  for a <- alphas do eg.addEdge(a.identifier, OBJECT.identifier)
  eg

private def concretizeToUnknown(
    log: Log,
    config: Configuration,
    alpha: String,
    maxArity: Int
): List[Configuration] =
  val realAlpha =
    config.phi2.keys.filter(x => x.identifier == alpha).toVector(0).asInstanceOf[Alpha]
  val newArities = Vector(0, 1, 2, 3, maxArity)
  val newTypes =
    newArities.map(arity => realAlpha.concretizeToReference(s"UNKNOWN_TYPE_${realAlpha.id}", arity))
  val newDecls =
    newTypes.map(x => (x, MissingTypeDeclaration(x.identifier).ofParameters(x.numArgs)))
  val newConfigs = newDecls.map { case (t, d) =>
    config
      .copy(phi1 = config.phi1 + (t.identifier -> d))
      .replace(realAlpha.copy(substitutions = Nil), t)
  } filter { x =>
    x.isDefined
  } map { x =>
    x.get
  }
  newConfigs.toList

private def concretizeToAll(
    log: Log,
    config: Configuration,
    alpha: String,
    types: Set[String]
): LogWithLeft[List[Configuration]] =
  val realAlpha =
    config.phi2.keys.filter(x => x.identifier == alpha).toVector(0).asInstanceOf[Alpha]
  val res = types
    // get the arity of the new type
    .map(x => (x, config.getArity(NormalType(x, 0))))
    // concretize alpha into each of the possible types
    .map((id, arity) => realAlpha.concretizeToReference(id, arity))
    // replace the existing configuration with those types
    .map(x => config.replace(realAlpha.copy(substitutions = Nil), x))
    .filter(_.isDefined)
    .map(_.get)
    .toList
  LogWithLeft(
    log.addInfo(
      s"concretizing ${alpha} to one of these types",
      types.mkString(", ")
    ),
    res
  )

def concretize(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  // case of having inference variables to concretize
  val ivs = config.phi2.keys.filter(x => x.isInstanceOf[DisjunctiveType]).toVector
  if !ivs.isEmpty then
    val iv       = ivs(0)
    val (lg, ls) = expandDisjunctiveType(iv.asInstanceOf[DisjunctiveType], log, config)
    return LogWithLeft(lg, ls)

  // construct erasure graph
  val erasureGraph = createErasureGraph(config)
  // get all relevant vertices
  val alphas      = config.phi2.keys.filter(x => x.isInstanceOf[Alpha]).map(x => x.identifier).toSet
  val allVertices = erasureGraph.adjList.keys.toSet
  val allConcretes = allVertices.diff(alphas)
  // nothing to do!
  if alphas.isEmpty then return LogWithRight(log.addWarn("concretize not implemented!"), config)
  // find A*
  val lowestAlpha = alphas
    .filter(x =>
      alphas.forall(y =>
        y == x ||
          erasureGraph.EΔΦ(x).contains(y) ||
          !erasureGraph.path(y, x)
      )
    )
    .toVector(0)
  // find e and partition to ec and ea
  val e  = erasureGraph.EΔΦ(lowestAlpha)
  val ec = e.filter(c => allConcretes.contains(c))
  val ea = e.diff(ec)

  // fail if |ec| > 1
  if ec.size > 1 then
    return LogWithLeft(
      log.addWarn(
        s"cannot concretize $lowestAlpha since it is in a cycle with more than one type:",
        ec.mkString(" and ")
      ),
      Nil
    )

  // immediately concretize if |ec| = 1
  if ec.size == 1 then
    return concretizeToAll(
      log.addInfo(s"$lowestAlpha must only be of one type:"),
      config,
      lowestAlpha,
      ec
    )

  // get L and U
  val lowerField = allConcretes.filter(t =>
    ea.exists(a => erasureGraph.path(t, a)) &&
      !ea.exists(a =>
        allConcretes.exists(s => s != t && erasureGraph.path(t, s) && erasureGraph.path(s, a))
      )
  )
  val upperField = allConcretes.filter(t =>
    ea.exists(a => erasureGraph.path(a, t)) &&
      !ea.exists(a =>
        allConcretes.exists(s => s != t && erasureGraph.path(a, s) && erasureGraph.path(s, t))
      )
  )

  // get lfd
  val lfd = lowerField.filter(t => config.isFullyDeclared(NormalType(t, 0)))

  // subtype is fully-declared, we know exactly what A* must be
  if lfd.size > 0 then
    // get glb of lfd onwards
    val glblfd = allConcretes.filter(t => lfd.forall(l => l == t || erasureGraph.path(l, t)))
    // if any in upper field are not present in lfd, fail
    val failCase = upperField.filter(t => !glblfd.contains(t))
    if !failCase.isEmpty then
      return LogWithLeft(
        log.addWarn(
          s"cannot concretize $lowestAlpha since it is a supertype of fully-declared types, but a subtype of at least one type who is not a supertype of those fully-declared types.\nFully declared types: $glblfd \nConflicting supertypes: $failCase"
        ),
        Nil
      )
    // otherwise, get Cfd
    val cfd = glblfd.filter(t => upperField.forall(u => t == u || erasureGraph.path(t, u)))
    return concretizeToAll(
      log.addInfo(s"concretizing $lowestAlpha into some fully-declared type"),
      config,
      lowestAlpha,
      cfd
    )

  // here |lfd| == 0. find C
  val c = allConcretes
    .filter(t =>
      lowerField.exists(l => erasureGraph.path(l, t) || l == t) && upperField.forall(u =>
        t == u || !erasureGraph.path(u, t)
      )
    )
    .union(lowerField)
    .union(upperField)

  // concretize to each first
  val res1                        = concretizeToAll(log, config, lowestAlpha, c)
  val (newLog, configsFromKnowns) = (res1.log, res1.left)

  // what is the max number of parameters????
  val choices = e.map(a =>
    val alpha        = config.phi2.keys.find(x => x.identifier == a).get.asInstanceOf[Alpha]
    val paramChoices = alpha.parameterChoices.size
    if alpha.canBeBounded then (paramChoices + 1) * 3
    else paramChoices
  )

  val iStar = choices.foldLeft(0)((x, y) => (x + 1) * (y + 1) - 1)

  val res2 = concretizeToUnknown(log, config, lowestAlpha, iStar)

  return LogWithLeft(
    newLog.addInfo(s"or $lowestAlpha is of an unknown type with $iStar maximum arity"),
    configsFromKnowns ::: res2
  )
