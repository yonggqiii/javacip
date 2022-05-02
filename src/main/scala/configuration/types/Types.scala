package configuration.types

import scala.Console.{RED, RESET}

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

  /** Replace any occurrences of an old type in this type
   * and all its substitutions/members with a new type
   * @param oldType the old type to be replaced
   * @param newType the new type after replacement
   * @return the resulting type
   */
  def replace(oldType: ReplaceableType, newType: Type): Type

  def substituted: Type

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

  val args: Vector[Type]

final case class ArrayType(
  base: Type,
) extends Type:
  override def toString =
      base.toString + "[]"
  val identifier = base.identifier + "[]"
  val numArgs = 0
  val elementType = base
  val upwardProjection: ArrayType = this
  val downwardProjection: ArrayType = this
  val substitutions = Nil
  def addSubstitutionLists(substitutions: SubstitutionList): ArrayType =
    copy(base = base.addSubstitutionLists(substitutions))
  def replace(oldType: ReplaceableType, newType: Type): ArrayType =
    copy(base = base.replace(oldType, newType))
  def substituted = copy(base = base.substituted)
  val args = Vector()

final case class NormalType(
    identifier: String,
    numArgs: Int,
    substitutions: SubstitutionList = Nil
) extends Type:
  val upwardProjection: NormalType   = this
  val downwardProjection: NormalType = this
  def addSubstitutionLists(substitutions: SubstitutionList): NormalType =
    copy(substitutions = this.substitutions ::: substitutions)
  def replace(oldType: ReplaceableType, newType: Type): NormalType =
    copy(substitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType))))
  def substituted = SubstitutedReferenceType(
    identifier,
    args.map(_.substituted))
  val args = (0 until numArgs)
    .map(TypeParameterIndex(identifier, _))
    .map(_.addSubstitutionLists(substitutions))
    .toVector

final case class PrimitiveType(
    identifier: String
) extends Type:
  val numArgs = 0
  val substitutions = Nil
  def addSubstitutionLists(substitutions: SubstitutionList): PrimitiveType = this
  val upwardProjection: PrimitiveType = this
  val downwardProjection: PrimitiveType = this
  def replace(oldType: ReplaceableType, newType: Type): PrimitiveType = this
  def substituted = this
  val args = Vector()

case object Bottom extends Type:
  val upwardProjection: Bottom.type = this
  val downwardProjection: Bottom.type = this
  val identifier = "⊥"
  val substitutions: SubstitutionList = Nil
  def addSubstitutionLists(substitutions: SubstitutionList): Bottom.type = this
  val numArgs = 0
  def replace(oldType: ReplaceableType, newType: Type): Bottom.type = this
  def substituted = this
  val args = Vector()

case object Wildcard extends Type:
  val upwardProjection: NormalType = NormalType("java.lang.Object", 0, Nil)
  val downwardProjection: Bottom.type = Bottom
  val identifier = "?"
  val numArgs = 0
  val substitutions: SubstitutionList = Nil
  def addSubstitutionLists(substitutions: SubstitutionList): Wildcard.type = this
  def replace(oldType: ReplaceableType, newType: Type): Wildcard.type = this
  def substituted = this
  val args = Vector()

final case class ExtendsWildcardType(
    upper: Type,
    _substitutions: SubstitutionList = Nil
) extends Type:
  val identifier                      = ""
  val substitutions: SubstitutionList = Nil
  val numArgs                         = 0
  val upwardProjection =
    upper.addSubstitutionLists(_substitutions).upwardProjection
  val downwardProjection: Bottom.type = Bottom
  def addSubstitutionLists(substitutions: SubstitutionList): ExtendsWildcardType =
    ExtendsWildcardType(upwardProjection, substitutions)
  def replace(oldType: ReplaceableType, newType: Type): ExtendsWildcardType =
    copy(
      upper = upper.replace(oldType, newType),
      _substitutions = _substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    )
  def substituted = copy(upper = upper.addSubstitutionLists(_substitutions).substituted, _substitutions = Nil)
  val args = Vector()

final case class SuperWildcardType(
    lower: Type,
    _substitutions: SubstitutionList = Nil
) extends Type:
  val identifier                      = ""
  val numArgs                         = 0
  val substitutions: SubstitutionList = Nil
  val upwardProjection: NormalType = NormalType("java.lang.Object", 0, Nil)
  val downwardProjection =
    lower.addSubstitutionLists(_substitutions).downwardProjection
  def addSubstitutionLists(substitutions: SubstitutionList) =
    SuperWildcardType(downwardProjection, substitutions)
  def replace(oldType: ReplaceableType, newType: Type): SuperWildcardType =
    copy(
      lower = lower.replace(oldType, newType),
      _substitutions = _substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    )
  def substituted = copy(lower = lower.addSubstitutionLists(_substitutions).substituted, _substitutions = Nil)
  val args = Vector()

sealed trait TTypeParameter extends Type

final case class TypeParameterIndex(
    source: String,
    index: Int,
    substitutions: SubstitutionList = Nil
) extends TTypeParameter:
  val numArgs            = 0
  val identifier         = s"$source#${(84 + index).toChar.toString}"
  val upwardProjection: TypeParameterIndex   = this
  val downwardProjection: TypeParameterIndex = this
  def addSubstitutionLists(substitutions: SubstitutionList): TypeParameterIndex =
    copy(substitutions = this.substitutions ::: substitutions)
  def replace(oldType: ReplaceableType, newType: Type): TypeParameterIndex =
    copy(substitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType))))
  def substituted: Type = substitutions match
    case Nil => this
    case x :: xs =>
      if x.contains(copy(substitutions = Nil)) then
        x(copy(substitutions = Nil)).addSubstitutionLists(xs).substituted
      else
        copy(substitutions = xs).substituted
  val args = Vector()

final case class TypeParameterName(
    sourceType: String,
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
      sourceType,
      source,
      qualifiedName,
      this.substitutions ::: substitutions
    )
  def replace(oldType: ReplaceableType, newType: Type): TypeParameterName =
    copy(substitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType))))
  def substituted: Type = substitutions match
    case Nil => this
    case x :: xs =>
      if x.contains(copy(substitutions = Nil)) then
        x(copy(substitutions = Nil)).addSubstitutionLists(xs).substituted
      else
        copy(substitutions = xs).substituted
  val args = Vector()

sealed trait ReplaceableType extends Type:
    val id: Int

final case class InferenceVariable(
    id: Int,
    source: Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeSubsequentlyBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set(),
    _choices: Set[Type] = Set()
) extends ReplaceableType:
  val numArgs            = 0
  val identifier         = s"τ$id($_choices)"
  val upwardProjection: InferenceVariable   = this
  val downwardProjection: InferenceVariable = this
  def addSubstitutionLists(substitutions: SubstitutionList): InferenceVariable =
    copy(substitutions = this.substitutions ::: substitutions)
  def choices = _choices.map(_.addSubstitutionLists(substitutions))
  def replace(oldType: ReplaceableType, newType: Type): Type =
    val newChoices = _choices.map(_.replace(oldType, newType))
    val newSource = source.map(_.replace(oldType, newType))
    val newSubstitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    if id != oldType.id then
      copy(source = newSource, substitutions = newSubstitutions, _choices = newChoices)
    else
      newType.addSubstitutionLists(substitutions)
  def substituted = this
  val args = Vector()

final case class Alpha(
    id: Int,
    source: Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set()
) extends ReplaceableType:
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
  def replace(oldType: ReplaceableType, newType: Type): Type =
    val newSource = source.map(_.replace(oldType, newType))
    val newSubstitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    if id != oldType.id then
      copy(source = newSource, substitutions = newSubstitutions)
    else
      newType.addSubstitutionLists(substitutions)
  def substituted = this
  val args = Vector()

final case class SubstitutedReferenceType(
  identifier: String,
  args: Vector[Type]
) extends Type:
  val numArgs = args.size
  val substitutions: SubstitutionList = (0 until numArgs).map(i => (TypeParameterIndex(identifier, i) -> args(i))).toMap :: Nil
  val upwardProjection = this
  val downwardProjection = this
  def addSubstitutionLists(substitutions: SubstitutionList) =
    NormalType(
      identifier,
      numArgs,
      substitutions
    ).addSubstitutionLists(substitutions)
  def replace(oldType: ReplaceableType, newType: Type) =
    copy(args = args.map(_.replace(oldType, newType)))
  def substituted = this
  override def toString =
    val a = if args.size == 0 then "" else "<" + args.mkString(", ") + ">"
    s"$identifier$a"

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
