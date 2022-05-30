package configuration.types
import configuration.assertions.*
import scala.Console.{RED, RESET}

val OBJECT = NormalType("java.lang.Object", 0)
val BOXED_INT = NormalType("java.lang.Integer", 0)
val BOXED_SHORT = NormalType("java.lang.Short", 0)
val BOXED_LONG = NormalType("java.lang.Long", 0)
val BOXED_BYTE = NormalType("java.lang.Byte", 0)
val BOXED_CHAR = NormalType("java.lang.Character", 0)
val BOXED_FLOAT = NormalType("java.lang.Float", 0)
val BOXED_DOUBLE = NormalType("java.lang.Double", 0)
val BOXED_BOOLEAN = NormalType("java.lang.Boolean", 0)
val BOXED_VOID = NormalType("java.lang.Void", 0)

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

  /** The upward projection of this type
    */
  val upwardProjection: Type

  /** The downward projection of this type
    */
  val downwardProjection: Type

  /** The type arguments to this type */
  val args: Vector[Type]

  /** Asserts that this type is a supertype of another type
   * @param that the type that this is a supertype of
   * @return the resulting SubtypeAssertion
   */
  def ~:>(that: Type) = SubtypeAssertion(that, this)

  /** Asserts that this type is a subtype of another type
   * @param that the type that this is a subtype of
   * @return the resulting SubtypeAssertion
   */
  def <:~(that: Type) = SubtypeAssertion(this, that)

  /**
   * Asserts that this type is equivalent to another type
   * @param that the type that this is equivalent to
   * @return the resulting EquivalenceAssertion
   */
  def ~=~(that: Type) = EquivalenceAssertion(this, that)

  /**
   * Asserts that this type is contained by another type
   * @param that the type that this is contained by
   * @return the resulting ContainmentAssertion
   */
  def <=~(that: Type) = ContainmentAssertion(this, that)

  /**
   * Asserts that this type contains another type
   * @param that the type that this contains
   * @return the resulting ContainmentAssertion
   */
  def ~=>(that: Type) = ContainmentAssertion(that, this)

  /** Asserts that this type is a class
   * @return the resulting IsClassAssertion
   */
  def isClass = IsClassAssertion(this)

  /** Asserts that this type is an interface
   * @return the resulting IsInterfaceAssertion
   */
  def isInterface = IsInterfaceAssertion(this)

  /** Asserts that this type is declared
   * @return the resulting IsDeclaredAssertion
   */
  def isDeclared = IsDeclaredAssertion(this)

  /** Asserts that this type is missing
   * @return the resulting IsMissingAssertion
   */
  def isMissing = IsMissingAssertion(this)

  /** Asserts that this type is primitive
   * @return the resulting IsPrimitiveAssertion
   */
  def isPrimitive = IsPrimitiveAssertion(this)

  /** Asserts that this type is a reference type
   * @return the resulting IsReferenceAssertion
   */
  def isReference = IsReferenceAssertion(this)

  /** Determines if this type contains any inference variables, alphas or placeholder types */
  def isSomehowUnknown: Boolean

  /** Appends some substitution lists to this type
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the type after adding the substitution lists
    */
  def addSubstitutionLists(substitutionList: SubstitutionList): Type

  /** Replace any occurrences of an old type in this type
   * and all its substitutions/members with a new type
   * @param oldType the old type to be replaced
   * @param newType the new type after replacement
   * @return the resulting type
   */
  def replace(oldType: ReplaceableType, newType: Type): Type

  /**
   * Returns the equivalent type after substituting as much as possible;
   * for example, List<T>[T -> Integer] will become List<Integer>.
   * Replaceable types will not be substituted
   */
  def substituted: TypeAfterSubstitution

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

sealed trait TypeAfterSubstitution extends Type

final case class ArrayType(
  base: Type,
) extends TypeAfterSubstitution:
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
  def isSomehowUnknown = base.isSomehowUnknown

final case class NormalType(
    identifier: String,
    numArgs: Int,
    substitutions: SubstitutionList = Nil
) extends Type:
  val upwardProjection: NormalType   = this
  val downwardProjection: NormalType = this
  def addSubstitutionLists(substitutions: SubstitutionList): NormalType =
    if numArgs > 0 then
      copy(substitutions = (this.substitutions ::: substitutions).filter(!_.isEmpty))
    else this
  def replace(oldType: ReplaceableType, newType: Type): NormalType =
    copy(substitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType))))
  def substituted = SubstitutedReferenceType(
    identifier,
    args.map(_.substituted))
  val args = (0 until numArgs)
    .map(TypeParameterIndex(identifier, _))
    .map(_.addSubstitutionLists(substitutions))
    .toVector
  def isSomehowUnknown = substituted.isSomehowUnknown

object PrimitiveType:
    def apply(identifier: String): PrimitiveType = identifier match
      case "byte" => PRIMITIVE_BYTE
      case "short" => PRIMITIVE_SHORT
      case "char" => PRIMITIVE_CHAR
      case "int" => PRIMITIVE_INT
      case "long" => PRIMITIVE_LONG
      case "float" => PRIMITIVE_FLOAT
      case "double" => PRIMITIVE_DOUBLE
      case "boolean" => PRIMITIVE_BOOLEAN
      case "void" => PRIMITIVE_VOID
      case _ => ???

sealed trait PrimitiveType extends TypeAfterSubstitution:
  val boxed: NormalType
  val numArgs = 0
  val substitutions = Nil
  def addSubstitutionLists(substitutions: SubstitutionList): PrimitiveType = this
  val upwardProjection: PrimitiveType = this
  val downwardProjection: PrimitiveType = this
  def replace(oldType: ReplaceableType, newType: Type): PrimitiveType = this
  def substituted = this
  val args = Vector()
  def isSomehowUnknown = false
  def widened: Set[PrimitiveType]
  def isAssignableBy: Set[Type]

case object PRIMITIVE_INT extends PrimitiveType:
  val identifier = "int"
  val boxed = BOXED_INT
  def widened = Set(PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_SHORT, PRIMITIVE_CHAR, PRIMITIVE_BYTE, PRIMITIVE_INT).flatMap(x => Set(x, x.boxed))

case object PRIMITIVE_BYTE extends PrimitiveType:
  val identifier = "byte"
  val boxed = BOXED_BYTE
  def widened = Set(PRIMITIVE_CHAR, PRIMITIVE_SHORT, PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

case object PRIMITIVE_SHORT extends PrimitiveType:
  val identifier = "short"
  val boxed = BOXED_SHORT
  def widened = Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_SHORT, PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

case object PRIMITIVE_CHAR extends PrimitiveType:
  val identifier = "char"
  val boxed = BOXED_CHAR
  def widened = Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_CHAR, PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

case object PRIMITIVE_LONG extends PrimitiveType:
  val identifier = "long"
  val boxed = BOXED_LONG
  def widened = Set(PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_LONG, PRIMITIVE_SHORT, PRIMITIVE_INT, PRIMITIVE_CHAR, PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

case object PRIMITIVE_FLOAT extends PrimitiveType:
  val identifier = "float"
  val boxed = BOXED_FLOAT
  def widened = Set(PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_FLOAT, PRIMITIVE_LONG, PRIMITIVE_SHORT, PRIMITIVE_INT, PRIMITIVE_CHAR, PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

case object PRIMITIVE_DOUBLE extends PrimitiveType:
  val identifier = "double"
  val boxed = BOXED_DOUBLE
  def widened = Set()
  def isAssignableBy =
    Set(PRIMITIVE_DOUBLE, PRIMITIVE_FLOAT, PRIMITIVE_LONG, PRIMITIVE_SHORT, PRIMITIVE_INT, PRIMITIVE_CHAR, PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

case object PRIMITIVE_BOOLEAN extends PrimitiveType:
  val identifier = "boolean"
  val boxed = BOXED_BOOLEAN
  def widened = Set()
  def isAssignableBy =
    Set(PRIMITIVE_BOOLEAN).flatMap(x => Set(x, x.boxed))

case object PRIMITIVE_VOID extends PrimitiveType:
  val identifier = "void"
  val boxed = BOXED_VOID
  def widened = Set()
  def isAssignableBy =
    Set(PRIMITIVE_VOID).flatMap(x => Set(x, x.boxed))

case object Bottom extends TypeAfterSubstitution:
  val upwardProjection: Bottom.type = this
  val downwardProjection: Bottom.type = this
  val identifier = "⊥"
  val substitutions: SubstitutionList = Nil
  def addSubstitutionLists(substitutions: SubstitutionList): Bottom.type = this
  val numArgs = 0
  def replace(oldType: ReplaceableType, newType: Type): Bottom.type = this
  def substituted = this
  val args = Vector()
  def isSomehowUnknown = false
case object Wildcard extends TypeAfterSubstitution:
  val upwardProjection: NormalType = OBJECT
  val downwardProjection: Bottom.type = Bottom
  val identifier = "?"
  val numArgs = 0
  val substitutions: SubstitutionList = Nil
  def addSubstitutionLists(substitutions: SubstitutionList): Wildcard.type = this
  def replace(oldType: ReplaceableType, newType: Type): Wildcard.type = this
  def substituted = this
  val args = Vector()
  def isSomehowUnknown = false

final case class ExtendsWildcardType(
    upper: Type
) extends TypeAfterSubstitution:
  val identifier                      = ""
  val substitutions: SubstitutionList = Nil
  val numArgs                         = 0
  val upwardProjection = upper.upwardProjection
  val downwardProjection: Bottom.type = Bottom
  def addSubstitutionLists(substitutions: SubstitutionList): ExtendsWildcardType =
    ExtendsWildcardType(upper.addSubstitutionLists(substitutions))
  def replace(oldType: ReplaceableType, newType: Type): ExtendsWildcardType =
    copy(upper = upper.replace(oldType, newType))
  def substituted = copy(upper = upper.substituted)
  val args = Vector()
  def isSomehowUnknown = upwardProjection.isSomehowUnknown

final case class SuperWildcardType(
    lower: Type
) extends TypeAfterSubstitution:
  val identifier                      = ""
  val numArgs                         = 0
  val substitutions: SubstitutionList = Nil
  val upwardProjection: NormalType = OBJECT
  val downwardProjection =
    lower.downwardProjection
  def addSubstitutionLists(substitutions: SubstitutionList) =
    copy(lower.addSubstitutionLists(substitutions))
  def replace(oldType: ReplaceableType, newType: Type): SuperWildcardType =
    copy(lower = lower.replace(oldType, newType))
  def substituted = copy(lower.substituted)
  val args = Vector()
  def isSomehowUnknown = downwardProjection.isSomehowUnknown

sealed trait TTypeParameter extends TypeAfterSubstitution:
    def containingTypeIdentifier: String

final case class TypeParameterIndex(
    source: String,
    index: Int,
    substitutions: SubstitutionList = Nil
) extends TTypeParameter:
  def containingTypeIdentifier = source
  val numArgs            = 0
  val identifier         = s"$source#${(84 + index).toChar.toString}"
  val upwardProjection: TypeParameterIndex   = this
  val downwardProjection: TypeParameterIndex = this
  def addSubstitutionLists(substitutions: SubstitutionList): TypeParameterIndex =
    copy(substitutions = this.substitutions ::: substitutions)
  def replace(oldType: ReplaceableType, newType: Type): TypeParameterIndex =
    copy(substitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType))))
  def substituted: TypeAfterSubstitution = substitutions match
    case Nil => this
    case x :: xs =>
      if x.contains(copy(substitutions = Nil)) then
        x(copy(substitutions = Nil)).addSubstitutionLists(xs).substituted
      else
        copy(substitutions = xs).substituted
  val args = Vector()
  def isSomehowUnknown = substitutions.isEmpty || substituted.isSomehowUnknown

final case class TypeParameterName(
    sourceType: String,
    source: String,
    qualifiedName: String,
    substitutions: SubstitutionList = Nil
) extends TTypeParameter:
  def containingTypeIdentifier = sourceType
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
  def substituted: TypeAfterSubstitution = substitutions match
    case Nil => this
    case x :: xs =>
      if x.contains(copy(substitutions = Nil)) then
        x(copy(substitutions = Nil)).addSubstitutionLists(xs).substituted
      else
        copy(substitutions = xs).substituted
  val args = Vector()
  def isSomehowUnknown = substitutions.isEmpty || substituted.isSomehowUnknown

sealed trait ReplaceableType extends TypeAfterSubstitution:
  val id: Int
  def isSomehowUnknown = true

final case class AnyReplaceable(id: Int, substitutions: SubstitutionList = Nil) extends ReplaceableType:
  val numArgs = 0
  val identifier         = s"τ$id"
  val upwardProjection: AnyReplaceable = this
  val downwardProjection: AnyReplaceable = this
  def addSubstitutionLists(substitutions: SubstitutionList): AnyReplaceable =
    copy(substitutions = this.substitutions ::: substitutions)
  def replace(oldType: ReplaceableType, newType: Type): Type =
      newType.addSubstitutionLists(substitutions)
  def substituted = this
  val args = Vector()


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
  def concretizeToReference(identifier: String, numArgs: Int): NormalType =
    NormalType(
      identifier,
      numArgs,
      ((0 until numArgs)
         .map(i =>
           TypeParameterIndex(identifier, i) ->
             InferenceVariableFactory.createInferenceVariable(
               source,
               Nil,
               canBeBounded,
               parameterChoices,
               canBeBounded
             )
         ).toMap :: Nil).filter(!_.isEmpty)
      )


final case class SubstitutedReferenceType(
  identifier: String,
  args: Vector[Type]
) extends TypeAfterSubstitution:
  val numArgs = args.size
  val substitutions: SubstitutionList = (0 until numArgs).map(i => (TypeParameterIndex(identifier, i) -> args(i))).toMap :: Nil
  val upwardProjection = this
  val downwardProjection = this
  def addSubstitutionLists(substitutions: SubstitutionList) =
    NormalType(
      identifier,
      numArgs,
      this.substitutions
    ).addSubstitutionLists(substitutions)
  def replace(oldType: ReplaceableType, newType: Type) =
    copy(args = args.map(_.replace(oldType, newType)))
  def substituted = this
  override def toString =
    val a = if args.size == 0 then "" else "<" + args.mkString(", ") + ">"
    s"$identifier$a"
  def isSomehowUnknown = args.foldLeft(false)(_ || _.isSomehowUnknown)

object InferenceVariableFactory:
  var id = 0
  def createInferenceVariable(source: scala.util.Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeSubsequentlyBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set(),
    canBeBounded: Boolean = false,
  ) =
    id += 1
    val choices: Set[Type] =
      if !canBeBounded then
        parameterChoices ++ Set[Type](createAlpha(source, Nil, canBeSubsequentlyBounded || canBeBounded, parameterChoices))
      else
        val boundedParams = parameterChoices.flatMap(x => Set(x, SuperWildcardType(x), ExtendsWildcardType(x)))
        val alpha = createAlpha(source, Nil, true, parameterChoices)
        boundedParams ++ Set(Wildcard,
          alpha,
          SuperWildcardType(alpha),
          ExtendsWildcardType(alpha)
        )
    InferenceVariable(id, source, substitutions, canBeSubsequentlyBounded, parameterChoices, choices)

  def createAlpha(source: scala.util.Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set()
  ) =
    id += 1
    Alpha(id, source, substitutions, canBeBounded, parameterChoices)
  def createAnyReplaceable(): AnyReplaceable =
    id += 1
    AnyReplaceable(id)
