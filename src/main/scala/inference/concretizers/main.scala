package inference.concretizers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*
import scala.annotation.tailrec
import configuration.declaration.MissingTypeDeclaration
import scala.util.{Either, Left, Right}
import scala.collection.mutable.{Map as MutableMap, Set as MutableSet}
import inference.misc.expandInferenceVariable
import scala.collection.mutable.ArrayBuffer
class ErasureGraph(
    val adjList: MutableMap[String, MutableSet[String]] = MutableMap()
):
  def addEdge(from: String, to: String): Unit =
    if !adjList.contains(from) then adjList(from) = MutableSet()
    if !adjList.contains(to) then adjList(to) = MutableSet()
    adjList(from) += to
  def EΔΦ(identifier: String): Set[String] =
    def helper(current: String, acc: Set[String] = Set()): Set[String] =
      if current == identifier || acc.contains(current) then return acc
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
    .filter(x => x.isInstanceOf[SubtypeAssertion])
    .map(x => x.asInstanceOf[SubtypeAssertion])
  val eg = ErasureGraph()
  val q  = ArrayBuffer[String]()
  q.addAll(config.delta.keys)
  q.addAll(config.phi1.keys)
  val otherTypes = ceff
    .filter(x => !(x.left.isInstanceOf[Alpha] && x.right.isInstanceOf[Alpha]))
    .map(x => if !x.left.isInstanceOf[Alpha] then x.left.identifier else x.right.identifier)
  q.addAll(otherTypes)
  while !q.isEmpty do
    val c = q.remove(0)
    if !eg.adjList.contains(c) && c != OBJECT.identifier then
      val sups = config.getDirectSupertypes(NormalType(c, 0))
      for s <- sups do
        eg.addEdge(c, s.identifier)
        q += s.identifier
  for c <- ceff do eg.addEdge(c.left.identifier, c.right.identifier)
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
  val newDecls = newTypes.map(x => (x, MissingTypeDeclaration(x.identifier, x.numArgs)))
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
  val ivs = config.phi2.keys.filter(x => x.isInstanceOf[InferenceVariable]).toVector
  if !ivs.isEmpty then
    val iv       = ivs(0)
    val (lg, ls) = expandInferenceVariable(iv.asInstanceOf[InferenceVariable], log, config)
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
  if ec.size == 1 then return concretizeToAll(log, config, lowestAlpha, ec)

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

//   //return LogWithRight(log.addError(upperField.toString + " " + lowestAlpha.toString), config)
//   val oalpha = findHighestAlpha(config)
//   if oalpha.isEmpty then LogWithRight(log.addWarn("concretize not implemented!"), config)
//   else
//     val alpha          = oalpha.get
//     val allConstraints = getAllConstraints(config, alpha)
//     // get the field of sub/super types
//     getFieldOfTypes(alpha, allConstraints, config) match
//       case Right((lowerField, upperField)) =>
//         val lowerFieldString = lowerField.mkString(", ")
//         val upperFieldString = upperField.mkString(", ")
//         // if lower field types are all declared, then the subtypes are all fully declared
//         if lowerField.forall(x => config |- x.isDeclared) then
//           val filteredLowerField = lowerField.filter(y => upperField.forall(x => config |- y <:~ x))
//           if filteredLowerField.isEmpty then
//             println("lol2")
//             LogWithLeft(
//               log.addWarn(
//                 s"not possible to concretize $alpha",
//                 s"lower field: $lowerFieldString\nupper field: $upperFieldString"
//               ),
//               Nil
//             )
//           else
//             // alpha must be one of the types in the lower and upper field
//             val res = (filteredLowerField ++ upperField)
//               // get the identifier and arity of the new type
//               .map(x => (x.identifier, config.getArity(x)))
//               // concretize alpha into each of the possible types
//               .map((id, arity) => alpha.concretizeToReference(id, arity))
//               // replace the existing configuration with those types
//               .map(x => config.replace(alpha.copy(substitutions = Nil), x))
//               .filter(_.isDefined)
//               .map(_.get)
//               .toList
//             LogWithLeft(log.addInfo(s"concretizing $alpha into some fully-declared type"), res)
//         else
//           // it is possible for alpha to be some novel type
//           val variability = upperField.toVector.map(x => config.getArity(x)).foldLeft(0)(_ + _)
//           val newType     = alpha.concretizeToReference(s"UNKNOWN_TYPE_${alpha.id}", variability)
//           val newDecl     = MissingTypeDeclaration(newType.identifier, variability)
//           val newConfig = config
//             .copy(phi1 = config.phi1 + (newType.identifier -> newDecl))
//             .replace(alpha.copy(substitutions = Nil), newType)
//             .map(_ :: Nil)
//             .getOrElse(Nil)
//           // cases where alpha is not this novel type
//           val res = (lowerField ++ upperField)
//             // get the identifier and arity of the new type
//             .map(x => (x.identifier, config.getArity(x)))
//             // concretize alpha into each of the possible types
//             .map((id, arity) => alpha.concretizeToReference(id, arity))
//             // replace the existing configuration with those types
//             .map(x => config.replace(alpha.copy(substitutions = Nil), x))
//             .filter(_.isDefined)
//             .map(_.get)
//             .toList
//           LogWithLeft(
//             log.addInfo(
//               s"concretizing ${alpha.copy(substitutions = Nil)} to some type in the field or $newType",
//               s"lower field: $lowerFieldString\nupper field: $upperFieldString"
//             ),
//             newConfig ::: res
//           )
//       case Left(Some(t)) =>
//         val arity    = config.getArity(t)
//         val newAlpha = alpha.concretizeToReference(t.identifier, arity)
//         val newConfig =
//           config.replace(alpha.copy(substitutions = Nil), newAlpha).map(_ :: Nil).getOrElse(Nil)
//         LogWithLeft(log.addInfo(s"concretizing $alpha to $newAlpha"), newConfig)
//       case Left(None) =>
//         LogWithLeft(log.addWarn(s"cannot concretize $alpha!"), Nil)
// val numParams =
//   knownSupertypes.foldLeft(0)((i, t) => i + t.numArgs) + alpha.parameterChoices.size
// val a = (0 to numParams)
//   // type name, numParams
//   .map(i => (s"UNKNOWN_TYPE_${alpha.id}", i))
//   // type name, numParams, MTD
//   .map(x => (x._1, x._2, MissingTypeDeclaration(x._1, x._2)))
//   // type name, numParams, config with new MTD
//   .map(x => ((x._1, x._2, config.copy(phi1 = config.phi1 + (x._1 -> x._3)))))
//   // new type, config with newMTD
//   .map(x => (alpha.concretizeToReference(x._1, x._2), x._3))
//   // new configs with alphas replaced
//   .map(x => x._2.replace(alpha.copy(substitutions = Nil), x._1))
//   .filter(_.isDefined)
//   .map(_.get)
//   .toList

// private def findHighestAlpha(
//     config: Configuration
// ): Option[Alpha] =
//   config.phi2
//     .find((x, y) => x.isInstanceOf[Alpha])
//     .map(pair => getHighestAlpha(config, pair._1.asInstanceOf[Alpha]))

// @tailrec
// private def getHighestAlpha(
//     config: Configuration,
//     alpha: Alpha,
//     exclusions: Set[Int] = Set()
// ): Alpha =
//   val id = alpha.id
//   if exclusions.contains(id) then alpha
//   else
//     val constraints = getAllConstraints(config, alpha)
//     val supertypes  = getAllSupertypesFromConstraints(alpha, constraints)
//     val a = supertypes
//       .filter(_.isInstanceOf[Alpha])
//       .map(_.asInstanceOf[Alpha])
//       .find(_.id != id)
//     if a.isDefined then getHighestAlpha(config, a.get, exclusions + id)
//     else alpha

// private def getAllConstraints(config: Configuration, alpha: Alpha): Vector[Assertion] =
//   config.phi2
//     .filter((x, y) => x.isInstanceOf[Alpha] && x.asInstanceOf[Alpha].id == alpha.id)
//     .foldLeft(Vector[Assertion]())((x, y) => x ++ y._2.constraintStore)

// private def getAllSupertypesFromConstraints(alpha: Alpha, v: Vector[Assertion]): Vector[Type] =
//   v.filter(_.isInstanceOf[SubtypeAssertion])
//     .map(_.asInstanceOf[SubtypeAssertion])
//     .filter(a => a.left.isInstanceOf[Alpha] && a.left.asInstanceOf[Alpha].id == alpha.id)
//     .map(_.right)

// private def getAllSubtypesFromConstraints(alpha: Alpha, v: Vector[Assertion]) =
//   v.filter(_.isInstanceOf[SubtypeAssertion])
//     .map(_.asInstanceOf[SubtypeAssertion])
//     .filter(a => a.right.isInstanceOf[Alpha] && a.right.asInstanceOf[Alpha].id == alpha.id)
//     .map(_.left)

// private def getFieldOfTypes(
//     alpha: Alpha,
//     constraints: Vector[Assertion],
//     config: Configuration
// ): Either[Option[NormalType], (Set[NormalType], Set[NormalType])] =
//   val allConcreteSupertypes =
//     getAllSupertypesFromConstraints(alpha, constraints).filter(!_.isInstanceOf[Alpha])
//   val lowestSupertypes = getLowestSupertypes(config, allConcreteSupertypes)
//   val allConcreteSubtypes =
//     getAllSubtypesFromConstraints(alpha, constraints).filter(!_.isInstanceOf[Alpha])
//   val highestSubtypes    = getHighestSubtypes(config, allConcreteSubtypes)
//   val intersectionOfSets = lowestSupertypes & highestSubtypes
//   if intersectionOfSets.isEmpty then
//     val supertypesOfSubtypes =
//       highestSubtypes.flatMap(getSupertypesOfSubtype(_, lowestSupertypes, config))
//     Right((supertypesOfSubtypes, lowestSupertypes))
//   else if intersectionOfSets.size == 1 then Left(Some(intersectionOfSets.toVector(0)))
//   else Left(None)

// private def getSupertypesOfSubtype(
//     t: NormalType,
//     bounds: Set[NormalType],
//     config: Configuration
// ): Set[NormalType] =
//   // if any of the bounds are subtypes of t then t should not be included
//   if bounds.map(x => config |- x <:~ t).foldLeft(false)(_ || _) then Set()
//   else
//     val supertypes = config.getFixedDeclaration(t) match
//       case None => config.phi1(t.identifier).supertypes.map(x => NormalType(x.identifier, 0))
//       case Some(decl) =>
//         (decl.extendedTypes ++ decl.implementedTypes).map(x => NormalType(x.identifier, 0))
//     Set(t) ++ supertypes.flatMap(getSupertypesOfSubtype(_, bounds, config))

// private def getLowestSupertypes(config: Configuration, supertypes: Vector[Type]) =
//   // get all the raw types
//   val rawTypes = supertypes.map(x => NormalType(x.identifier, 0)).toSet
//   rawTypes.filter(t =>
//     val others = rawTypes - t
//     !others.map(x => config |- x <:~ t).foldLeft(false)(_ || _)
//   )

// private def getHighestSubtypes(config: Configuration, subtypes: Vector[Type]) =
//   // get all the raw types
//   val rawTypes = subtypes.map(x => NormalType(x.identifier, 0)).toSet
//   rawTypes.filter(t =>
//     val others = rawTypes - t
//     !others.map(x => config |- t <:~ x).foldLeft(false)(_ || _)
//   )
