package configuration.types

import scala.Console.{RED, RESET}
import configuration.declaration.MissingDeclaration

// Type aliases
private type SubstitutionList = List[Map[TTypeParameter, Type]]

/** Represents some type in the program
  */
sealed trait Type:
  /** The identifier of the type
    */
  val identifier: String

  /** The substitutions this type has
    */
  val substitutions: SubstitutionList

  /** The number of type arguments this type needs to have
    */
  val numArgs: Int

  /** Appends some substitution lists to this type
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the type after adding the substitution lists
    */
  def addSubstitutionLists(substitutionList: SubstitutionList): Type

  /** The upward projection of this type
    */
  val upwardProjection: Type

  /** The downward projection of this type
    */
  val downwardProjection: Type

  override def toString: String =
    val start =
      if upwardProjection != downwardProjection then
        s"[${downwardProjection.toString}, ${upwardProjection.toString}]"
      else identifier
    val argumentList =
      if numArgs == 0 then ""
      else
        "<" + (0 until numArgs)
          .map(TypeParameterIndex(identifier, _))
          .mkString(", ") + ">"
    val subsFn: Map[TTypeParameter, Type] => String = subs => subs.mkString(", ")
    val subsstring = substitutions.map(subs => s"[${subsFn(subs)}]").mkString
    s"$start$argumentList$subsstring"

final case class NormalType(
    identifier: String,
    numArgs: Int,
    substitutions: SubstitutionList = Nil
) extends Type:
  def addSubstitutionLists(substitutions: SubstitutionList): Type =
    NormalType(identifier, numArgs, this.substitutions ::: substitutions)
  val upwardProjection   = this
  val downwardProjection = this

final case class PrimitiveType(
    identifier: String
) extends Type:
  val numArgs                                                     = 0
  val substitutions                                               = Nil
  def addSubstitutionLists(substitutions: SubstitutionList): Type = this
  val upwardProjection                                            = this
  val downwardProjection                                          = this

case object Bottom extends Type:
  val upwardProjection                                      = this
  val downwardProjection                                    = this
  val identifier                                            = "BOTTOM"
  val substitutions: SubstitutionList                       = Nil
  def addSubstitutionLists(substitutions: SubstitutionList) = this
  val numArgs                                               = 0

case object Wildcard extends Type:
  val upwardProjection                                      = NormalType("Object", 0, Nil)
  val downwardProjection                                    = Bottom
  val identifier                                            = "?"
  val numArgs                                               = 0
  val substitutions: SubstitutionList                       = Nil
  def addSubstitutionLists(substitutions: SubstitutionList) = this

final case class ExtendsWildcardType(
    upper: Type,
    _substitutions: SubstitutionList = Nil
) extends Type:
  val identifier                      = ""
  val substitutions: SubstitutionList = Nil
  val numArgs                         = 0
  val upwardProjection =
    upper.addSubstitutionLists(_substitutions).upwardProjection
  val downwardProjection = Bottom
  def addSubstitutionLists(substitutions: SubstitutionList) =
    ExtendsWildcardType(upwardProjection, substitutions)

final case class SuperWildcardType(
    lower: Type,
    _substitutions: SubstitutionList = Nil
) extends Type:
  val identifier                      = ""
  val numArgs                         = 0
  val substitutions: SubstitutionList = Nil
  val upwardProjection                = NormalType("Object", 0, Nil)
  val downwardProjection =
    lower.addSubstitutionLists(_substitutions).downwardProjection
  def addSubstitutionLists(substitutions: SubstitutionList) =
    SuperWildcardType(downwardProjection, substitutions)

final case class IntervalType(
    lower: Type,
    upper: Type,
    _substitutions: SubstitutionList = Nil
) extends Type:
  val identifier                      = ""
  val substitutions: SubstitutionList = Nil
  val numArgs                         = 0
  val upwardProjection =
    upper.addSubstitutionLists(_substitutions).upwardProjection
  val downwardProjection =
    lower.addSubstitutionLists(_substitutions).downwardProjection
  def addSubstitutionLists(substitutions: SubstitutionList) =
    IntervalType(downwardProjection, upwardProjection, substitutions)

sealed trait TTypeParameter extends Type

final case class TypeParameterIndex(
    source: String,
    index: Int,
    substitutions: SubstitutionList = Nil
) extends TTypeParameter:
  val numArgs            = 0
  val identifier         = s"$source#${(84 + index).toChar.toString}"
  val upwardProjection   = this
  val downwardProjection = this
  def addSubstitutionLists(substitutions: SubstitutionList) =
    TypeParameterIndex(source, index, this.substitutions ::: substitutions)

final case class TypeParameterName(
    source: String,
    qualifiedName: String,
    substitutions: SubstitutionList = Nil
) extends TTypeParameter:
  val identifier         = s"$source#$qualifiedName"
  val numArgs            = 0
  val upwardProjection   = this
  val downwardProjection = this
  def addSubstitutionLists(substitutions: SubstitutionList) =
    TypeParameterName(
      source,
      qualifiedName,
      this.substitutions ::: substitutions
    )

final case class IntersectionType(
    types: Vector[Type]
) extends Type:
  val numArgs            = 0
  val identifier         = ""
  val upwardProjection   = this
  val downwardProjection = this
  val substitutions      = Nil
  def addSubstitutionLists(substitutions: SubstitutionList) =
    IntersectionType(types.map(_.addSubstitutionLists(substitutions)))

final case class InferenceVariable(
    id: Int,
    source: Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeSubsequentlyBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set(),
    _choices: Set[Type] = Set()
) extends Type:
  val numArgs            = 0
  val identifier         = s"τ$id($_choices)"
  val upwardProjection   = this
  val downwardProjection = this
  def addSubstitutionLists(substitutions: SubstitutionList) =
    InferenceVariable(
      id,
      source,
      this.substitutions ::: substitutions,
      canBeSubsequentlyBounded,
      parameterChoices,
      _choices
    )
  def choices = _choices.map(_.addSubstitutionLists(substitutions))

final case class Alpha(
    id: Int,
    source: Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set()
) extends Type:
  val numArgs            = 0
  val identifier         = s"α$id"
  val upwardProjection   = this
  val downwardProjection = this
  def addSubstitutionLists(substitutions: SubstitutionList) =
    Alpha(
      id,
      source,
      this.substitutions ::: substitutions,
      canBeBounded,
      parameterChoices
    )

object InferenceVariableFactory:
  var id = 0
  def createInferenceVariable(source: scala.util.Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeSubsequentlyBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set(),
    canBeBounded: Boolean = false,
  ) =
    id += 1
    val choices =
      if !canBeBounded then
        parameterChoices ++ Set[Type](createAlpha(source, Nil, canBeSubsequentlyBounded || canBeBounded, parameterChoices))
      else
        val boundedParams = parameterChoices.flatMap(x => Set(x, SuperWildcardType(x), ExtendsWildcardType(x)))
        boundedParams ++ Set(Wildcard,
          createAlpha(source, Nil, true, parameterChoices),
          SuperWildcardType(createAlpha(source, Nil, true, parameterChoices)),
          ExtendsWildcardType(createAlpha(source, Nil, true, parameterChoices))
        )
    InferenceVariable(id, source, substitutions, canBeSubsequentlyBounded, parameterChoices, choices)

  def createAlpha(source: scala.util.Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set()
  ) =
    id += 1
    Alpha(id, source, substitutions, canBeBounded, parameterChoices)
