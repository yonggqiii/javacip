package inference.concretizers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*
import scala.annotation.tailrec
import configuration.declaration.MissingTypeDeclaration
import scala.util.{Either, Left, Right}

def concretize(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  // look for alpha
  val oalpha = findHighestAlpha(config)
  if oalpha.isEmpty then LogWithRight(log.addWarn("concretize not implemented!"), config)
  else
    val alpha          = oalpha.get
    val allConstraints = getAllConstraints(config, alpha)
    // get the field of sub/super types
    getFieldOfTypes(alpha, allConstraints, config) match
      case Right((lowerField, upperField)) =>
        val lowerFieldString = lowerField.mkString(", ")
        val upperFieldString = upperField.mkString(", ")
        // if lower field types are all declared, then the subtypes are all fully declared
        if lowerField.forall(x => config |- x.isDeclared) then
          if lowerField.exists(x =>
              !upperField.isEmpty && upperField.forall(y => !(config |- x <:~ y))
            )
          then
            val lowerFieldString = lowerField.mkString(", ")
            val upperFieldString = upperField.mkString(", ")
            LogWithLeft(
              log.addWarn(
                s"not possible to concretize $alpha",
                s"lower field: $lowerFieldString\nupper field: $upperFieldString"
              ),
              Nil
            )
          else
            // alpha must be one of the types in the lower and upper field
            val res = (lowerField ++ upperField)
              // get the identifier and arity of the new type
              .map(x => (x.identifier, config.getArity(x)))
              // concretize alpha into each of the possible types
              .map((id, arity) => alpha.concretizeToReference(id, arity))
              // replace the existing configuration with those types
              .map(x => config.replace(alpha.copy(substitutions = Nil), x))
              .filter(_.isDefined)
              .map(_.get)
              .toList
            LogWithLeft(log.addInfo(s"concretizing $alpha into some fully-declared type"), res)
        else
          // it is possible for alpha to be some novel type
          val variability = upperField.toVector.map(x => config.getArity(x)).foldLeft(0)(_ + _)
          val newType     = alpha.concretizeToReference(s"UNKNOWN_TYPE_${alpha.id}", variability)
          val newDecl     = MissingTypeDeclaration(newType.identifier, variability)
          val newConfig = config
            .copy(phi1 = config.phi1 + (newType.identifier -> newDecl))
            .replace(alpha.copy(substitutions = Nil), newType)
            .map(_ :: Nil)
            .getOrElse(Nil)
          // cases where alpha is not this novel type
          val res = (lowerField ++ upperField)
            // get the identifier and arity of the new type
            .map(x => (x.identifier, config.getArity(x)))
            // concretize alpha into each of the possible types
            .map((id, arity) => alpha.concretizeToReference(id, arity))
            // replace the existing configuration with those types
            .map(x => config.replace(alpha.copy(substitutions = Nil), x))
            .filter(_.isDefined)
            .map(_.get)
            .toList
          LogWithLeft(
            log.addInfo(
              s"concretizing ${alpha.copy(substitutions = Nil)} to some type in the field or $newType",
              s"lower field: $lowerFieldString\nupper field: $upperFieldString"
            ),
            newConfig ::: res
          )
      case Left(Some(t)) =>
        val arity    = config.getArity(t)
        val newAlpha = alpha.concretizeToReference(t.identifier, arity)
        val newConfig =
          config.replace(alpha.copy(substitutions = Nil), newAlpha).map(_ :: Nil).getOrElse(Nil)
        LogWithLeft(log.addInfo(s"concretizing $alpha to $newAlpha"), newConfig)
      case Left(None) =>
        LogWithLeft(log.addWarn(s"cannot concretize $alpha!"), Nil)
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

private def findHighestAlpha(
    config: Configuration
): Option[Alpha] =
  config.phi2
    .find((x, y) => x.isInstanceOf[Alpha])
    .map(pair => getHighestAlpha(config, pair._1.asInstanceOf[Alpha]))

@tailrec
private def getHighestAlpha(
    config: Configuration,
    alpha: Alpha,
    exclusions: Set[Int] = Set()
): Alpha =
  val id = alpha.id
  if exclusions.contains(id) then alpha
  else
    val constraints = getAllConstraints(config, alpha)
    val supertypes  = getAllSupertypesFromConstraints(alpha, constraints)
    val a = supertypes
      .filter(_.isInstanceOf[Alpha])
      .map(_.asInstanceOf[Alpha])
      .find(_.id != id)
    if a.isDefined then getHighestAlpha(config, a.get, exclusions + id)
    else alpha

private def getAllConstraints(config: Configuration, alpha: Alpha): Vector[Assertion] =
  config.phi2
    .filter((x, y) => x.isInstanceOf[Alpha] && x.asInstanceOf[Alpha].id == alpha.id)
    .foldLeft(Vector[Assertion]())((x, y) => x ++ y._2.constraintStore)

private def getAllSupertypesFromConstraints(alpha: Alpha, v: Vector[Assertion]): Vector[Type] =
  v.filter(_.isInstanceOf[SubtypeAssertion])
    .map(_.asInstanceOf[SubtypeAssertion])
    .filter(a => a.left.isInstanceOf[Alpha] && a.left.asInstanceOf[Alpha].id == alpha.id)
    .map(_.right)

private def getAllSubtypesFromConstraints(alpha: Alpha, v: Vector[Assertion]) =
  v.filter(_.isInstanceOf[SubtypeAssertion])
    .map(_.asInstanceOf[SubtypeAssertion])
    .filter(a => a.right.isInstanceOf[Alpha] && a.right.asInstanceOf[Alpha].id == alpha.id)
    .map(_.left)

private def getFieldOfTypes(
    alpha: Alpha,
    constraints: Vector[Assertion],
    config: Configuration
): Either[Option[NormalType], (Set[NormalType], Set[NormalType])] =
  val allConcreteSupertypes =
    getAllSupertypesFromConstraints(alpha, constraints).filter(!_.isInstanceOf[Alpha])
  val lowestSupertypes = getLowestSupertypes(config, allConcreteSupertypes)
  val allConcreteSubtypes =
    getAllSubtypesFromConstraints(alpha, constraints).filter(!_.isInstanceOf[Alpha])
  val highestSubtypes    = getHighestSubtypes(config, allConcreteSubtypes)
  val intersectionOfSets = lowestSupertypes & highestSubtypes
  if intersectionOfSets.isEmpty then
    val supertypesOfSubtypes =
      highestSubtypes.flatMap(getSupertypesOfSubtype(_, lowestSupertypes, config))
    Right((supertypesOfSubtypes, lowestSupertypes))
  else if intersectionOfSets.size == 1 then Left(Some(intersectionOfSets.toVector(0)))
  else Left(None)

private def getSupertypesOfSubtype(
    t: NormalType,
    bounds: Set[NormalType],
    config: Configuration
): Set[NormalType] =
  // if any of the bounds are subtypes of t then t should not be included
  if bounds.map(x => config |- x <:~ t).foldLeft(false)(_ || _) then Set()
  else
    val supertypes = config.getFixedDeclaration(t) match
      case None => config.phi1(t.identifier).supertypes.map(x => NormalType(x.identifier, 0))
      case Some(decl) =>
        (decl.extendedTypes ++ decl.implementedTypes).map(x => NormalType(x.identifier, 0))
    Set(t) ++ supertypes.flatMap(getSupertypesOfSubtype(_, bounds, config))

private def getLowestSupertypes(config: Configuration, supertypes: Vector[Type]) =
  // get all the raw types
  val rawTypes = supertypes.map(x => NormalType(x.identifier, 0)).toSet
  rawTypes.filter(t =>
    val others = rawTypes - t
    !others.map(x => config |- x <:~ t).foldLeft(false)(_ || _)
  )

private def getHighestSubtypes(config: Configuration, subtypes: Vector[Type]) =
  // get all the raw types
  val rawTypes = subtypes.map(x => NormalType(x.identifier, 0)).toSet
  rawTypes.filter(t =>
    val others = rawTypes - t
    !others.map(x => config |- t <:~ x).foldLeft(false)(_ || _)
  )
