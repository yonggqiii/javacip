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
    val adjList: MutableMap[String, MutableSet[String]] = MutableMap(),
    val erasureGroupCache: MutableMap[String, Set[String]] = MutableMap()
):
  override def toString() =
    adjList.map((a, b) => s"$a: [${b.mkString(", ")}]").mkString("\n")
  def addEdge(from: String, to: String): Unit =
    if !adjList.contains(from) then adjList(from) = MutableSet()
    if !adjList.contains(to) then adjList(to) = MutableSet()
    adjList(from) += to
  def addVertex(node: String): Unit =
    if !adjList.contains(node) then adjList(node) = MutableSet()
  def EΔΦ(identifier: String): Set[String] =
    if !erasureGroupCache.contains(identifier) then
      val vertices = adjList.keySet.toSet
      val group =
        vertices.filter(x => x == identifier || (path(x, identifier) && path(identifier, x)))
      erasureGroupCache(identifier) = group
    erasureGroupCache(identifier)
  def path(from: String, to: String): Boolean =
    val q       = ArrayBuffer[String](from)
    val visited = MutableSet[String]()
    while !q.isEmpty do
      val current = q.remove(q.size - 1)
      if !visited.contains(current) then
        if current == to then return true
        visited.add(current)
        q.addAll(adjList(current))
    return false

private def createErasureGraph(config: Configuration): ErasureGraph =
  val alphas = config.psi.filter(x => x.isInstanceOf[Alpha]).map(_.asInstanceOf[Alpha]).toVector
//    config.phi2.keys.filter(x => x.isInstanceOf[Alpha]).map(_.asInstanceOf[Alpha]).toVector

  val ceff = alphas
    .flatMap(x =>
      if config.constraintStore.contains(x.identifier) then config.constraintStore(x.identifier)
      else Vector()
    )
    .filter { x =>
      x match
        case x @ CompatibilityAssertion(a, b) =>
          if !a.isInstanceOf[PlaceholderType] && !b.isInstanceOf[PlaceholderType] then
            throw new RuntimeException(s"$x is still here lmao")
          else false
        case SubtypeAssertion(left, right) =>
          !left.isInstanceOf[PlaceholderType] && !right.isInstanceOf[PlaceholderType]
        case EquivalenceAssertion(left, right) =>
          !left.isInstanceOf[PlaceholderType] && !right.isInstanceOf[PlaceholderType]
        case _ => false
    }
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
      if !left.isInstanceOf[Alpha] && !left.isInstanceOf[PlaceholderType] then Some(left.identifier)
      else if !right.isInstanceOf[Alpha] && !right.isInstanceOf[PlaceholderType] then
        Some(right.identifier)
      else None
    )
    .filter(_.isDefined)
    .map(_.get)
  q.addAll(otherTypes)
  val visited = MutableSet[String]()
  // add all the concrete types
  while !q.isEmpty do
    val c = q.remove(0)
    if !visited.contains(c) then
      visited.add(c)
      val sups =
        config
          .getSubstitutedDeclaration(SomeClassOrInterfaceType(c))
          .getDirectAncestors //getDirectSupertypes(NormalType(c, 0))
      for s <- sups do
        // if s.identifier == OBJECT.identifier then println(s"$c heya")
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
    ea: Set[String],
    exclusions: Set[String]
): LogWithLeft[List[Configuration]] =
  val realAlphas = config.psi.filter(x => ea.contains(x.identifier)).map(_.asInstanceOf[Alpha])
  val newArities = (0 to 3).toVector
  val tempType   = InferenceVariableFactory.createTemporaryType()
  val tempDecl   = new MissingTypeDeclaration(tempType.identifier)
  val res: Vector[Configuration] =
    newArities
      .map(i => (tempType, tempDecl.ofParameters(i)))
      .map { case (tt, mtd) =>
        realAlphas.foldLeft(
          Some(config.copy(phi1 = config.phi1 + (tt.identifier -> mtd))): Option[Configuration]
        )((config, alpha) =>
          config match
            case None => None
            case Some(c) =>
              val newType = alpha.concretizeToTemporary(tt.id, mtd.numParams)
              c.addAllToPsi(newType.args)
                .addExclusions(tt, exclusions.map(x => SomeClassOrInterfaceType(x)))
                .replace(alpha, newType)
        )
      } filter { x =>
      x.isDefined
    } map { x =>
      x.get
    }
  LogWithLeft(log.addInfo(s"or ${ea.mkString(", ")} is of an unknown type"), res.toList)

private def concretizeToKnown(
    log: Log,
    config: Configuration,
    ea: Set[String],
    types: Set[String]
): LogWithLeft[List[Configuration]] =
  val realAlphas = config.psi.filter(x => ea.contains(x.identifier)).map(_.asInstanceOf[Alpha])
  val res: Set[Configuration] = types
    // get the arity of the new type
    .map(x => (x, config.getUnderlyingDeclaration(SomeClassOrInterfaceType(x)).numParams))
    .map { case (id, arity) =>
      realAlphas.foldLeft(Some(config): Option[Configuration])((config, alpha) =>
        config match
          case None => None
          case Some(c) =>
            val newType = alpha.concretizeToReference(id, arity)
            c.addAllToPsi(newType.args).replace(alpha, newType)
      )
    }
    .filter { x => x.isDefined }
    .map { x => x.get }
  LogWithLeft(
    log.addInfo(
      s"concretizing ${realAlphas.mkString(", ")} to one of these types",
      types.mkString(", ")
    ),
    res.toList
  )

def concretize(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  val erasureGraph = createErasureGraph(config)
  // get all relevant vertices
  val alphas       = config.psi.filter(x => x.isInstanceOf[Alpha]).map(x => x.identifier).toSet
  val allVertices  = erasureGraph.adjList.keys.toSet
  val allConcretes = allVertices.diff(alphas)
  // nothing to do!
  if alphas.isEmpty then return LogWithRight(log.addWarn("concretize not implemented!"), config)
  // find some alpha
  val lowestAlpha = alphas.toVector(0)

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
    return concretizeToKnown(
      log.addInfo(s"${ea.mkString(", ")} must only be of one type:"),
      config,
      ea,
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
  val lfd = lowerField.filter(t => config.isFullyDeclared(SomeClassOrInterfaceType(t)))

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
    return concretizeToKnown(
      log.addInfo(s"concretizing $lowestAlpha into some fully-declared type"),
      config,
      ea,
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
  val exclusions = allConcretes.filter(t =>
    c.contains(t) || lowerField.exists(l => erasureGraph.path(t, l)) || upperField.exists(u =>
      erasureGraph.path(u, t)
    )
  )
  // concretize to each first
  val res1 = concretizeToKnown(log, config, ea, c)
  val realEA = config.psi
    .filter(x => x.isInstanceOf[Alpha])
    .map(x => x.asInstanceOf[Alpha])
    .filter(x => ea.contains(x.identifier))
  if realEA.exists(x => !x.canBeTemporaryType) then return res1
  val (newLog, configsFromKnowns) = (res1.log, res1.left)

  val res2 = concretizeToUnknown(newLog, config, ea, exclusions)

  return LogWithLeft(
    res2.log.addInfo(s"${(res2.left ::: configsFromKnowns).size}"),
    configsFromKnowns ::: res2.left
  )

// return LogWithLeft(
//   newLog.addInfo(s"or $lowestAlpha is of an unknown type"),
//   configsFromKnowns ::: res2
// )
