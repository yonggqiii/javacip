package configuration.types

import configuration.basetraits.*
import configuration.assertions.*

import scala.Console.{RED, RESET}

/** The `java.lang.Object` type */
val OBJECT = ClassOrInterfaceType("java.lang.Object")

/** The `java.lang.String` type */
val STRING = ClassOrInterfaceType("java.lang.String")

/** The `java.lang.Integer` type */
val BOXED_INT = ClassOrInterfaceType("java.lang.Integer")

/** The `java.lang.Short` type */
val BOXED_SHORT = ClassOrInterfaceType("java.lang.Short")

/** The `java.lang.Long` type */
val BOXED_LONG = ClassOrInterfaceType("java.lang.Long")

/** The `java.lang.Byte` type */
val BOXED_BYTE = ClassOrInterfaceType("java.lang.Byte")

/** The `java.lang.Character` type */
val BOXED_CHAR = ClassOrInterfaceType("java.lang.Character")

/** The `java.lang.Float` type */
val BOXED_FLOAT = ClassOrInterfaceType("java.lang.Float")

/** The `java.lang.Double` type */
val BOXED_DOUBLE = ClassOrInterfaceType("java.lang.Double")

/** The `java.lang.Boolean` type */
val BOXED_BOOLEAN = ClassOrInterfaceType("java.lang.Boolean")

/** The `java.lang.Void` type */
val BOXED_VOID = ClassOrInterfaceType("java.lang.Void")

/** The set of boxed primitive types */
val BOXES: Set[ClassOrInterfaceType] = Set(
  BOXED_BOOLEAN,
  BOXED_BYTE,
  BOXED_CHAR,
  BOXED_DOUBLE,
  BOXED_FLOAT,
  BOXED_INT,
  BOXED_LONG,
  BOXED_SHORT
)

extension (t: (Type, Type))
  def in(set: Set[(Type, Type)]): DisjunctiveAssertion =
    DisjunctiveAssertion(set.toVector map { case (l, r) => (t._1 ~=~ l) && (t._2 ~=~ r) })

// Type aliases
/** Represents a list of subtitutions--maps from type parameters to types */
type SubstitutionList = List[Substitution]
type Substitution     = Map[TTypeParameter, Type]

/** Base trait for Type operations
  * @tparam RAW
  *   the type of the raw type
  * @tparam FIX
  *   the type of the fixed type
  * @tparam CAPT
  *   the type of the captured type
  * @tparam WILDCAPT
  *   the type of the wildcard captured type
  * @tparam UP
  *   the type of the upward projection
  * @tparam DOWN
  *   the type of the downward projection
  * @tparam SUB
  *   the type after substitution
  * @tparam EXP
  *   the type of the expansion
  * @tparam REP
  *   the type after replacement
  * @tparam COM
  *   the type after combining
  * @tparam REORDER
  *   the type after reordering the type parameters
  */
sealed trait TypeOps[
    +RAW <: Type,
    +FIX <: Type,
    +CAPT <: Type,
    +WILDCAPT <: Type,
    +UP <: Type,
    +DOWN <: Type,
    +SUB <: Type,
    +EXP <: Type,
    +REP <: Type,
    +COM <: Type,
    +REORDER <: Type
] extends Raw[RAW],
      Fixable[FIX],
      Capturable[CAPT],
      WildcardCapturable[WILDCAPT],
      UpwardProjectable[UP],
      DownwardProjectable[DOWN],
      Substitutable[SUB],
      Expandable[EXP],
      Replaceable[REP],
      Combineable[COM],
      Reorderable[REORDER]

private type BaseTypeOps = TypeOps[Type, Type, Type, Type, Type, Type, Type, Type, Type, Type, Type]

/** Represents some type in the program */
sealed trait Type extends BaseTypeOps:
  /** The identifier of the type
    */
  val identifier: String

  /** The number of type arguments this type needs to have
    */
  val numArgs: Int

  /** The type arguments to this type */
  val args: Vector[Type]

  /** whether this type is the static type or not */
  val static: Boolean

  /** Asserts that some other type is compatible with this type
    * @param that
    *   the other type that is compatible with this type
    * @return
    *   the resulting CompatibilityAssertion
    */
  def ~:=(that: Type) = CompatibilityAssertion(this, that)

  /** The breadth of this type
    * @return
    *   the breadth of this type
    */
  def breadth: Int

  /** The depth of this type
    * @return
    *   the depth of this type
    */
  def depth: Int

  /** Converts this type into the static type (the type of this type)
    * @return
    *   the static type of this type
    */
  def toStaticType: Type

  /** Determines if this type contains any inference variables, alphas or placeholder types */
  def isSomehowUnknown: Boolean

  /** Asserts that this type is compatible with another type
    * @param that
    *   the other type that this type is compatible with
    * @return
    *   the resulting CompatibilityAssertion
    */
  def =:~(that: Type) = CompatibilityAssertion(that, this)

  /** Asserts that this type widens to some other type
    * @param that
    *   the type that this type widens to
    * @return
    *   the resulting WideningAssertion
    */
  def <<~=(that: Type) = WideningAssertion(this, that)

  /** Assertions that some other type widens to this type
    * @param that
    *   the type that widens to this type
    * @return
    *   the resulting WideningAssertion
    */
  def =~>>(that: Type) = WideningAssertion(that, this)

  /** Asserts that this type is a supertype of another type
    * @param that
    *   the type that this is a supertype of
    * @return
    *   the resulting SubtypeAssertion
    */
  def ~:>(that: Type) = SubtypeAssertion(that, this)

  /** Asserts that this type is a subtype of another type
    * @param that
    *   the type that this is a subtype of
    * @return
    *   the resulting SubtypeAssertion
    */
  def <:~(that: Type) = SubtypeAssertion(this, that)

  /** Asserts that this type is equivalent to another type
    * @param that
    *   the type that this is equivalent to
    * @return
    *   the resulting EquivalenceAssertion
    */
  def ~=~(that: Type) = EquivalenceAssertion(this, that)

  /** Asserts that this type is contained by another type
    * @param that
    *   the type that this is contained by
    * @return
    *   the resulting ContainmentAssertion
    */
  def <=~(that: Type) = ContainmentAssertion(this, that)

  /** Asserts that this type contains another type
    * @param that
    *   the type that this contains
    * @return
    *   the resulting ContainmentAssertion
    */
  def ~=>(that: Type) = ContainmentAssertion(that, this)

  /** Asserts that this type is a class
    * @return
    *   the resulting IsClassAssertion
    */
  def isClass = IsClassAssertion(this)

  /** Asserts that this type is an interface
    * @return
    *   the resulting IsInterfaceAssertion
    */
  def isInterface = IsInterfaceAssertion(this)

  /** Asserts that this type is declared
    * @return
    *   the resulting IsDeclaredAssertion
    */
  def isDeclared = IsDeclaredAssertion(this)

  /** Asserts that this type is missing
    * @return
    *   the resulting IsMissingAssertion
    */
  def isMissing = IsMissingAssertion(this)

  /** Asserts that this type is primitive
    * @return
    *   the resulting IsPrimitiveAssertion
    */
  def isPrimitive = IsPrimitiveAssertion(this)

  /** Asserts that this type is a member of a set of types
    * @param set
    *   the set of types
    * @return
    *   the corresponding disjunction
    */
  def in(set: Set[Type]): DisjunctiveAssertion =
    DisjunctiveAssertion(set.toVector.map(this ~=~ _))

  /** Checks if this type strictly occurs in, but is not equal to, another type
    * @param that
    *   the other type
    * @return
    *   true if this strictly occurs in that, false otherwise
    */
  def ⊂(that: Type): Boolean = that match
    case x: SomeClassOrInterfaceType => x.args.exists(y => this ⊆ y) // why not possible?
    case ArrayType(x)                => this ⊆ x
    case x: PrimitiveType            => false
    case Bottom | Wildcard           => false
    case x: ExtendsWildcardType      => this ⊆ x.upper
    case x: SuperWildcardType        => this ⊆ x.lower
    case x: TTypeParameter           => false
    case x: InferenceVariable        => false
    case x: Capture                  => (this ⊆ x.lowerBound) || (this ⊆ x.upperBound)

  /** Checks if this type occurs in or is equal to another type
    * @param that
    *   the other type
    * @return
    *   true if this occurs in or is equal to that, false otherwise
    */
  def ⊆(that: Type): Boolean =
    this == that || this ⊂ that

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
    s"$start$argumentList${if static then " static" else ""}"

private type CaptureOps =
  TypeOps[
    Capture,
    Capture,
    Capture,
    Capture,
    Type,
    Type,
    Capture,
    Capture,
    Capture,
    Capture,
    Capture
  ]

/** A type after Java capture conversion
  * @param id
  *   the ID of this type
  * @param lowerBound
  *   the lower bound of this captured type
  * @param upperBound
  *   the upper bound of this captured type
  * @param static
  *   whether this represents the static type
  */
case class Capture(id: Int, lowerBound: Type, upperBound: Type, static: Boolean = false)
    extends Type,
      CaptureOps:
  /** The identifier of a capture is `CAP<id>` */
  override val identifier = s"CAP$id"

  /** Captures have no type arguments */
  override val args = Vector()

  /** Captures have no type arguments */
  override val numArgs = 0

  /** The upward projection of a capture is the upward projection of its upper bound */
  override val upwardProjection = upperBound.upwardProjection

  /** The downward projection of a capture is the downward projection of its lower bound */
  override val downwardProjection = lowerBound.downwardProjection

  /** Converting capture to a static type is simply the equivalent where `static = true` */
  override def toStaticType: Capture = copy(static = true)

  /** The breadth of a capture is the max between the breadth of the lower and upper bounds */
  override def breadth = lowerBound.breadth.max(upperBound.breadth)

  /** The depth of a capture is 1 + the max between the depth of the lower and upper bounds */
  override def depth = lowerBound.depth.max(upperBound.depth) + 1

  /** Capturing a capture is itself */
  override def captured = this

  /** Wildcard-capturing a capture is itself */
  override def wildcardCaptured = this

  /** Combines all occurrences of a temporary type in the lower and upper bounds of this capture
    * with a new type
    * @param oldType
    *   the temporary type to combine
    * @param newType
    *   the new type to combine with
    * @return
    *   the resulting capture
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): Capture =
    copy(
      lowerBound = lowerBound.combineTemporaryType(oldType, newType),
      upperBound = upperBound.combineTemporaryType(oldType, newType)
    )

  /** The expansion of a capture is itself and the empty substitution */
  override def expansion = (this, Map())

  /** The raw type of a capture is itself */
  override def raw = this

  /** Performs a substitution on the upper and lower bounds of this capture
    * @param function
    *   the substitution function
    * @return
    *   the resulting capture
    */
  override def substitute(function: Substitution): Capture = copy(
    lowerBound = lowerBound.substitute(function),
    upperBound = upperBound.substitute(function)
  )

  /** Determines if one of the bounds of this capture is somehow unknown */
  override def isSomehowUnknown: Boolean =
    lowerBound.isSomehowUnknown || upperBound.isSomehowUnknown

  /** Reorders the type parameters of the bounds of this capture
    * @param scheme
    *   the scheme to reorder the type parameters by
    * @return
    *   the resulting capture
    */
  override def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Capture = copy(
    lowerBound = lowerBound.reorderTypeParameters(scheme),
    upperBound = upperBound.reorderTypeParameters(scheme)
  )

  /** Fixes the bounds of this capture */
  override def fix: Capture = copy(lowerBound = lowerBound.fix, upperBound = upperBound.fix)

  /** Performs a replacement on the bounds of this capture
    * @param oldType
    *   the old type to replace
    * @param newType
    *   the new type to replace the old type with
    * @return
    *   the resulting capture
    */
  override def replace(oldType: InferenceVariable, newType: Type): Capture = copy(
    lowerBound = lowerBound.replace(oldType, newType),
    upperBound = upperBound.replace(oldType, newType)
  )

  override def toString() = s"$identifier extends $upperBound super $lowerBound"

private type JavaInferenceVariableOps = TypeOps[
  JavaInferenceVariable,
  JavaInferenceVariable,
  JavaInferenceVariable,
  JavaInferenceVariable,
  JavaInferenceVariable,
  JavaInferenceVariable,
  JavaInferenceVariable,
  JavaInferenceVariable,
  Type,
  JavaInferenceVariable,
  JavaInferenceVariable
]

/** An inference variable used in Java's type inference
  * @param id
  *   the ID of this inference variable
  * @param paramChoices
  *   the set of parameters this inference variable could be/contained
  * @param canBeSubsequentlyBounded
  *   whether after substitution this type can contain bounded/unbounded wildcards
  */
case class JavaInferenceVariable(
    id: Int,
    paramChoices: Set[TTypeParameter],
    canBeSubsequentlyBounded: Boolean
) extends InferenceVariable,
      JavaInferenceVariableOps:
  /** The identifier of a java inference variable is `iv<id>` */
  override val identifier = s"iv$id"

  /** There will never be the static type of a java inference variable */
  override val static: Boolean = false

  /** Java inference variables have no type arguments */
  override val args: Vector[Type] = Vector()

  /** Java inference variables have no type arguments */
  override val numArgs: Int = 0

  /** The upward projection of a java inference variable is itself */
  override val upwardProjection: JavaInferenceVariable = this

  /** The downward projection of a java inference variable is itself */
  override val downwardProjection: JavaInferenceVariable = this

  /** Java inference variables have no substitutions */
  override val substitutions: SubstitutionList = Nil

  /** Java inference variables will never be captured */
  override val toBeCaptured: Boolean = false

  /** Java inference variables will never be wildcard-captured */
  override val toBeWildcardCaptured: Boolean = false

  /** The raw type of a Java inference variable is itself
    * @return
    *   itself
    */
  override def raw = this

  /** The expansion of a java inference variable is itself and the empty substitution function
    * @return
    *   itself and the empty substitution function
    */
  override def expansion = (this, Map())

  /** The static type of a java inference variable is itself
    * @return
    *   itself
    */
  override def toStaticType: JavaInferenceVariable = this

  /** Java inference variables do not contain any temporary types
    * @param oldType
    *   the type to combine
    * @param newType
    *   the type to combine with
    * @return
    *   itself
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): JavaInferenceVariable = this

  /** Fixing a java inference variable just produces itself
    * @return
    *   itself
    */
  override def fix: JavaInferenceVariable = this

  /** Performs a replacement on this inference variable if it is the target of replacement
    * @param oldType
    *   the type to replace
    * @param newType
    *   the type to replace it with
    * @return
    *   the resulting type
    */
  override def replace(oldType: InferenceVariable, newType: Type): Type =
    if oldType == this then newType else this

  /** Performing a substitution on this java inference variable does nothing
    * @param function
    *   the substitution function
    * @return
    *   itself
    */
  override def substitute(function: Substitution): JavaInferenceVariable = this

  /** Capturing a java inference variable produces itself
    * @return
    *   itself
    */
  override def captured: JavaInferenceVariable = this

  /** Wildcard-capturing a java inference variable produces itself
    * @return
    *   itself
    */
  override def wildcardCaptured: JavaInferenceVariable = this

  /** Java inference variables do not have any type parameters, so there is nothing to reorder
    * @param scheme
    *   the reordering scheme
    * @return
    *   itself
    */
  override def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): JavaInferenceVariable =
    this

private type PrimitiveTypeOps = TypeOps[
  PrimitiveType,
  PrimitiveType,
  PrimitiveType,
  PrimitiveType,
  PrimitiveType,
  PrimitiveType,
  PrimitiveType,
  PrimitiveType,
  PrimitiveType,
  PrimitiveType,
  PrimitiveType
]

/** The primitive types */
sealed trait PrimitiveType extends Type, PrimitiveTypeOps:
  /** The boxed type of this primitive type */
  val boxed: ClassOrInterfaceType

  /** Primitive types have no type arguments */
  override val args = Vector()

  /** Primitive types have no type arguments */
  override val numArgs = 0

  /** The upward projection of any primitive type is itself */
  override val upwardProjection: PrimitiveType = this

  /** The downward projection of any primitive type is itself */
  override val downwardProjection: PrimitiveType = this

  /** Primitive types will never be the static type */
  override val static: Boolean = false

  /** the types which this primitive type can widen to */
  def widened: Set[PrimitiveType]

  /** the types which can be assigned into this type */
  def isAssignableBy: Set[Type] = widenedFrom ++ boxedSources

  def widenedFrom: Set[PrimitiveType]
  def boxedSources: Set[ClassOrInterfaceType] = widenedFrom.map(_.boxed)

  override def toStaticType: PrimitiveType     = this
  override def fix: PrimitiveType              = this
  override def captured: PrimitiveType         = this
  override def wildcardCaptured: PrimitiveType = this
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): PrimitiveType = this
  override def breadth                                                           = 0
  override def depth                                                             = 0
  override def replace(oldType: InferenceVariable, newType: Type): PrimitiveType = this
  override def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): PrimitiveType =
    this
  override def isSomehowUnknown   = false
  override def raw: PrimitiveType = this

  /** Substitutions on a primitive type do not change it */
  override def substitute(function: Substitution): PrimitiveType = this

  /** The expansion of any primitive type is just itself and an empty function */
  override def expansion: (PrimitiveType, Substitution) = (this, Map())

/** An array
  * @param base
  *   the type of the elements of this array
  */
final case class ArrayType(
    base: Type
) extends Type:
  def toStaticType: ArrayType     = this
  val static: Boolean             = false
  def captured: ArrayType         = copy(base = base.captured)
  def wildcardCaptured: ArrayType = copy(base = base.wildcardCaptured)
  def breadth                     = 0
  def depth                       = base.depth + 1
  override def toString =
    base.toString + "[]"
  def fix: ArrayType                = copy(base = base.fix)
  val identifier                    = base.identifier + "[]"
  val numArgs                       = 0
  val upwardProjection: ArrayType   = this
  val downwardProjection: ArrayType = this
  def replace(oldType: InferenceVariable, newType: Type): ArrayType =
    copy(base = base.replace(oldType, newType))
  val args             = Vector()
  def isSomehowUnknown = base.isSomehowUnknown

  def combineTemporaryType(oldType: TemporaryType, newType: SomeClassOrInterfaceType): ArrayType =
    copy(base = base.combineTemporaryType(oldType, newType))

  /** Returns the type after making its base raw
    * @return
    *   the resulting raw type
    */
  def raw: ArrayType = copy(base = base.raw)

  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]) =
    copy(base = base.reorderTypeParameters(scheme))

  def substitute(function: Substitution): ArrayType = copy(base = base.substitute(function))

  /** Nothing */
  def expansion: (ArrayType, Substitution) = (this, Map())

sealed trait SomeClassOrInterfaceType extends Type, Raw[SomeClassOrInterfaceType]:
  def toStaticType: SomeClassOrInterfaceType
  // fixing either a ClassOrInterfaceType or TemporaryType always yields a ClassOrInterfaceType
  def fix: ClassOrInterfaceType
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): SomeClassOrInterfaceType
  val upwardProjection: SomeClassOrInterfaceType
  val downwardProjection: SomeClassOrInterfaceType
  def replace(oldType: InferenceVariable, newType: Type): SomeClassOrInterfaceType
  def substitute(function: Substitution): SomeClassOrInterfaceType
  def expansion: (SomeClassOrInterfaceType, Substitution)
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): SomeClassOrInterfaceType

object SomeClassOrInterfaceType:
  def apply(identifier: String, args: Vector[Type] = Vector()): SomeClassOrInterfaceType =
    if identifier.length() == 0 then ClassOrInterfaceType(identifier, args)
    else if identifier.charAt(0) == 'ξ' then
      TemporaryType(Integer.parseInt(identifier.substring(1)), args)
    else ClassOrInterfaceType(identifier, args)

final case class ClassOrInterfaceType(
    identifier: String,
    args: Vector[Type],
    static: Boolean = false
) extends SomeClassOrInterfaceType,
      Raw[ClassOrInterfaceType]:
  def toStaticType: ClassOrInterfaceType = copy(static = true)
  def fix: ClassOrInterfaceType          = copy(args = args.map(_.fix))
  if identifier.size == 0 || identifier.charAt(0) == 'ξ' then
    println(identifier)
    ???
  def wildcardCaptured: ClassOrInterfaceType = this
  def captured: ClassOrInterfaceType         = copy(identifier, args.map(_.wildcardCaptured))
  def breadth                                = args.size
  def depth   = if args.size == 0 then 0 else args.map(x => (x.depth + 1)).max
  val numArgs = args.size
  override def toString =
    val a = if args.size == 0 then "" else "<" + args.mkString(", ") + ">"
    s"$identifier$a${if static then ".type" else ""}"
  def isSomehowUnknown          = args.exists(_.isSomehowUnknown)
  def raw: ClassOrInterfaceType = copy(args = Vector())
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): ClassOrInterfaceType =
    copy(args = args.map(_.reorderTypeParameters(scheme)))
  val upwardProjection: ClassOrInterfaceType   = this
  val downwardProjection: ClassOrInterfaceType = this
  def replace(oldType: InferenceVariable, newType: Type): ClassOrInterfaceType =
    copy(args = args.map(_.replace(oldType, newType)))
  def substitute(function: Substitution): ClassOrInterfaceType =
    copy(args = args.map(_.substitute(function)))
  def expansion: (ClassOrInterfaceType, Substitution) =
    val base = copy(args = (0 until numArgs).map(TypeParameterIndex(identifier, _)).toVector)
    val subs = (0 until numArgs)
      .map[(TTypeParameter, Type)](i => (TypeParameterIndex(identifier, i) -> args(i)))
      .toMap
    (base, subs)
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ClassOrInterfaceType =
    copy(args = args.map(_.combineTemporaryType(oldType, newType)))

object ClassOrInterfaceType:
  def apply(identifier: String): ClassOrInterfaceType =
    new ClassOrInterfaceType(identifier, Vector())

/** Companion object to [[configuration.types.PrimitiveType]]. */
object PrimitiveType:
  /** Creates a primitive given its name
    * @param identifier
    *   the name of the primitive type
    * @return
    *   the resulting primitive type
    */
  def apply(identifier: String): PrimitiveType = identifier match
    case "byte"    => PRIMITIVE_BYTE
    case "short"   => PRIMITIVE_SHORT
    case "char"    => PRIMITIVE_CHAR
    case "int"     => PRIMITIVE_INT
    case "long"    => PRIMITIVE_LONG
    case "float"   => PRIMITIVE_FLOAT
    case "double"  => PRIMITIVE_DOUBLE
    case "boolean" => PRIMITIVE_BOOLEAN
    case "void"    => PRIMITIVE_VOID
    case _ => ??? // definitely fails

/** the `int` type */
case object PRIMITIVE_INT extends PrimitiveType:
  val identifier  = "int"
  val boxed       = BOXED_INT
  def widened     = Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def widenedFrom = Set(PRIMITIVE_SHORT, PRIMITIVE_CHAR, PRIMITIVE_BYTE, PRIMITIVE_INT)

// def isAssignableBy =
//   Set(PRIMITIVE_SHORT, PRIMITIVE_CHAR, PRIMITIVE_BYTE, PRIMITIVE_INT).flatMap(x =>
//     Set(x, x.boxed)
//   )

/** the `byte` type */
case object PRIMITIVE_BYTE extends PrimitiveType:
  val identifier = "byte"
  val boxed      = BOXED_BYTE
  def widened = Set(
    PRIMITIVE_BYTE,
    // PRIMITIVE_CHAR,
    PRIMITIVE_SHORT,
    PRIMITIVE_INT,
    PRIMITIVE_LONG,
    PRIMITIVE_FLOAT,
    PRIMITIVE_DOUBLE
  )
  def widenedFrom = Set(PRIMITIVE_BYTE)
// def isAssignableBy =
//   Set(PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

/** the `short` type */
case object PRIMITIVE_SHORT extends PrimitiveType:
  val identifier = "short"
  val boxed      = BOXED_SHORT
  def widened =
    Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE, PRIMITIVE_SHORT)
  def widenedFrom = Set(PRIMITIVE_SHORT, PRIMITIVE_BYTE)
// def isAssignableBy =
//   Set(PRIMITIVE_SHORT, PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

/** the `char` type */
case object PRIMITIVE_CHAR extends PrimitiveType:
  val identifier = "char"
  val boxed      = BOXED_CHAR
  def widened =
    Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE, PRIMITIVE_CHAR)
  def widenedFrom = Set(PRIMITIVE_CHAR, PRIMITIVE_BYTE)
// def isAssignableBy =
//   Set(PRIMITIVE_CHAR, PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

/** the `long` type */
case object PRIMITIVE_LONG extends PrimitiveType:
  val identifier = "long"
  val boxed      = BOXED_LONG
  def widened    = Set(PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE, PRIMITIVE_LONG)
  def widenedFrom =
    Set(PRIMITIVE_LONG, PRIMITIVE_SHORT, PRIMITIVE_INT, PRIMITIVE_CHAR, PRIMITIVE_BYTE)
// def isAssignableBy =
//   Set(PRIMITIVE_LONG, PRIMITIVE_SHORT, PRIMITIVE_INT, PRIMITIVE_CHAR, PRIMITIVE_BYTE).flatMap(x =>
//     Set(x, x.boxed)
//   )

/** the `float` type */
case object PRIMITIVE_FLOAT extends PrimitiveType:
  val identifier = "float"
  val boxed      = BOXED_FLOAT
  def widened    = Set(PRIMITIVE_DOUBLE, PRIMITIVE_FLOAT)
  def widenedFrom = Set(
    PRIMITIVE_FLOAT,
    PRIMITIVE_LONG,
    PRIMITIVE_SHORT,
    PRIMITIVE_INT,
    PRIMITIVE_CHAR,
    PRIMITIVE_BYTE
  )
// def isAssignableBy =
//   Set(
//     PRIMITIVE_FLOAT,
//     PRIMITIVE_LONG,
//     PRIMITIVE_SHORT,
//     PRIMITIVE_INT,
//     PRIMITIVE_CHAR,
//     PRIMITIVE_BYTE
//   ).flatMap(x => Set(x, x.boxed))

/** the `double` type */
case object PRIMITIVE_DOUBLE extends PrimitiveType:
  val identifier = "double"
  val boxed      = BOXED_DOUBLE
  def widened    = Set(PRIMITIVE_DOUBLE)
  def widenedFrom = Set(
    PRIMITIVE_DOUBLE,
    PRIMITIVE_FLOAT,
    PRIMITIVE_LONG,
    PRIMITIVE_SHORT,
    PRIMITIVE_INT,
    PRIMITIVE_CHAR,
    PRIMITIVE_BYTE
  )
// override def isAssignableBy =
//   Set(
//     PRIMITIVE_DOUBLE,
//     PRIMITIVE_FLOAT,
//     PRIMITIVE_LONG,
//     PRIMITIVE_SHORT,
//     PRIMITIVE_INT,
//     PRIMITIVE_CHAR,
//     PRIMITIVE_BYTE
//   ).flatMap(x => Set(x, x.boxed))

/** the `boolean` type */
case object PRIMITIVE_BOOLEAN extends PrimitiveType:
  val identifier  = "boolean"
  val boxed       = BOXED_BOOLEAN
  def widened     = Set(PRIMITIVE_BOOLEAN)
  def widenedFrom = widened
// // def isAssignableBy =
//   Set(PRIMITIVE_BOOLEAN).flatMap(x => Set(x, x.boxed))

/** the `void` type */
case object PRIMITIVE_VOID extends PrimitiveType:
  val identifier  = "void"
  val boxed       = BOXED_VOID
  def widened     = Set(PRIMITIVE_VOID)
  def widenedFrom = widened
// def isAssignableBy =
//   Set(PRIMITIVE_VOID).flatMap(x => Set(x, x.boxed))

/** The bottom type, usually a null */
case object Bottom extends Type:
  val static: Boolean                                                                 = false
  def toStaticType: Bottom.type                                                       = this
  def fix: Bottom.type                                                                = this
  def captured: Bottom.type                                                           = this
  def wildcardCaptured: Bottom.type                                                   = this
  def breadth                                                                         = 0
  def depth                                                                           = 0
  val upwardProjection: Bottom.type                                                   = this
  val downwardProjection: Bottom.type                                                 = this
  val identifier                                                                      = "⊥"
  val numArgs                                                                         = 0
  def replace(oldType: InferenceVariable, newType: Type): Bottom.type                 = this
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Bottom.type = this
  val args                                                                            = Vector()
  def isSomehowUnknown                                                                = false
  def raw: Bottom.type                                                                = this
  def expansion: (Bottom.type, Substitution)          = (this, Map())
  def substitute(function: Substitution): Bottom.type = this
  def combineTemporaryType(oldType: TemporaryType, newType: SomeClassOrInterfaceType): Bottom.type =
    this

/** the `?` type */
case object Wildcard extends Type:
  val static                      = false
  def toStaticType: Wildcard.type = this
  def captured: Wildcard.type     = this
  def wildcardCaptured: Type      = InferenceVariableFactory.createCapture(Bottom, OBJECT)
  def breadth                     = 0
  def depth                       = 0
  val upwardProjection: ClassOrInterfaceType                                            = OBJECT
  val downwardProjection: Bottom.type                                                   = Bottom
  val identifier                                                                        = "?"
  val numArgs                                                                           = 0
  def replace(oldType: InferenceVariable, newType: Type): Wildcard.type                 = this
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Wildcard.type = this
  val args                                                                              = Vector()
  def isSomehowUnknown                                                                  = false
  override def toString                                                                 = identifier
  def raw: Wildcard.type                                                                = this
  def substitute(function: Substitution): Wildcard.type                                 = this
  def expansion: (Wildcard.type, Substitution) = (this, Map())
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): Wildcard.type = this
  def fix: Wildcard.type = this

/** the `? extends Foo` type */
final case class ExtendsWildcardType(
    upper: Type
) extends Type:
  val static: Boolean                   = false
  def toStaticType: ExtendsWildcardType = this
  def fix: ExtendsWildcardType          = copy(upper = upper.fix)
  def captured: ExtendsWildcardType     = this
  def wildcardCaptured: Capture         = InferenceVariableFactory.createCapture(Bottom, upper)
  def breadth                           = upper.breadth
  def depth                             = upper.depth
  val identifier                        = ""
  val numArgs                           = 0
  val upwardProjection                  = upper.upwardProjection
  val downwardProjection: Bottom.type   = Bottom
  def replace(oldType: InferenceVariable, newType: Type): ExtendsWildcardType =
    copy(upper = upper.replace(oldType, newType))
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): ExtendsWildcardType =
    copy(upper = upper.reorderTypeParameters(scheme))
  val args              = Vector()
  def isSomehowUnknown  = upper.isSomehowUnknown
  override def toString = s"? extends $upper"
  def substitute(function: Substitution): ExtendsWildcardType =
    copy(upper = upper.substitute(function))
  def expansion: (ExtendsWildcardType, Substitution) = (this, Map())
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ExtendsWildcardType = copy(upper = upper.combineTemporaryType(oldType, newType))

  /** Returns the type after making the extended type raw
    * @return
    *   the resulting type
    */
  def raw: ExtendsWildcardType = copy(upper = upper.raw)

/** the `? super Foo` type */
final case class SuperWildcardType(
    lower: Type
) extends Type:
  val static: Boolean                        = false
  def toStaticType: SuperWildcardType        = this
  def fix: SuperWildcardType                 = copy(lower = lower.fix)
  def captured: SuperWildcardType            = this
  def wildcardCaptured: Capture              = InferenceVariableFactory.createCapture(lower, OBJECT)
  def breadth                                = lower.breadth
  def depth                                  = lower.depth
  val identifier                             = ""
  val numArgs                                = 0
  val substitutions: SubstitutionList        = Nil
  val upwardProjection: ClassOrInterfaceType = OBJECT
  val downwardProjection =
    lower.downwardProjection
  def replace(oldType: InferenceVariable, newType: Type): SuperWildcardType =
    copy(lower = lower.replace(oldType, newType))
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): SuperWildcardType =
    copy(lower = lower.reorderTypeParameters(scheme))
  val args              = Vector()
  def isSomehowUnknown  = lower.isSomehowUnknown
  override def toString = s"? super ${lower}"
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): SuperWildcardType = copy(lower = lower.combineTemporaryType(oldType, newType))

  /** Returns the type after making the extending type raw
    * @return
    *   the resulting type
    */
  def raw: SuperWildcardType = copy(lower.raw)
  def substitute(function: Substitution): SuperWildcardType =
    copy(lower = lower.substitute(function))
  def expansion: (SuperWildcardType, Substitution) = (this, Map())

/** A type parameter of some sort */
sealed trait TTypeParameter extends Type:
  val static: Boolean         = false
  def toStaticType: this.type = this
  def fix: TTypeParameter
  def captured: TTypeParameter         = this
  def wildcardCaptured: TTypeParameter = this
  def breadth                          = 0
  def depth                            = 0
  val upwardProjection: TTypeParameter
  val downwardProjection: TTypeParameter
  def replace(oldType: InferenceVariable, newType: Type): TTypeParameter
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): TTypeParameter =
    if scheme contains this then scheme(this) else this
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): TTypeParameter

  /** The type who contains this type parameter */
  def containingTypeIdentifier: String
  def isSomehowUnknown                     = false
  def raw: this.type                       = this
  def expansion: (this.type, Substitution) = (this, Map())
  def substitute(function: Substitution): Type =
    if function contains this then function(this) else this

/** A type parameter by index (something like de Bruijn indexing)
  * @param source
  *   the type containing this parameter
  * @param index
  *   the index of this parameter
  * @param substitutions
  *   the substitutions of this type
  */
final case class TypeParameterIndex(
    source: String,
    index: Int
) extends TTypeParameter:
  def fix: TypeParameterIndex  = copy(source = SomeClassOrInterfaceType(source).fix.identifier)
  def containingTypeIdentifier = source
  val numArgs                  = 0
  val identifier               = s"$source#${(84 + index).toChar.toString}"
  val upwardProjection: TypeParameterIndex                                   = this
  val downwardProjection: TypeParameterIndex                                 = this
  def replace(oldType: InferenceVariable, newType: Type): TypeParameterIndex = this
  val args                                                                   = Vector()
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): TypeParameterIndex =
    if source != oldType.identifier then this else copy(source = newType.identifier)

/** A type parameter by name
  * @param sourceType
  *   the type containing this type parameter
  * @param source
  *   the full container id (including the method signature)
  * @param qualifiedName
  *   the name of the type parameter
  * @param substitutions
  *   the substitutions to this type parameter
  */
final case class TypeParameterName(
    sourceType: String,
    source: String,
    qualifiedName: String
) extends TTypeParameter:
  def fix: TypeParameterName   = this
  def containingTypeIdentifier = sourceType
  val identifier               = s"$source#$qualifiedName"
  val numArgs                  = 0
  val upwardProjection         = this
  val downwardProjection       = this
  def replace(oldType: InferenceVariable, newType: Type): TypeParameterName = this
  // copy(substitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType))))
  val args = Vector()
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): TypeParameterName =
    if sourceType != oldType.identifier then this
    else
      copy(
        sourceType = newType.identifier,
        source = newType.identifier + source.substring(sourceType.length, source.length)
      )

/** Some placeholder for inference */
sealed trait InferenceVariable extends Type:
  val toBeCaptured: Boolean
  val toBeWildcardCaptured: Boolean

  /** the ID of this type */
  val id: Int
  def isSomehowUnknown = true

  /** The raw type of an inference variable is simply itself
    * @return
    *   itself
    */
  def raw: InferenceVariable = this
  val substitutions: SubstitutionList
  def expansion: (InferenceVariable, Substitution) = (this, Map())
  def breadth                                      = 0
  def depth                                        = 0
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): Type

sealed trait DisjunctiveType extends InferenceVariable:
  val choices: Vector[Type]
  val canBeSubsequentlyBounded: Boolean
  val parameterChoices: Set[TTypeParameter]
  val canBeUnknown: Boolean
  val static: Boolean         = false
  def toStaticType: this.type = this

final case class TemporaryType(
    id: Int,
    args: Vector[Type],
    static: Boolean = false
) extends SomeClassOrInterfaceType:
  override def toStaticType: TemporaryType = copy(static = true)
  def fix: ClassOrInterfaceType            = ClassOrInterfaceType(s"UNKNOWN_$id", args.map(_.fix))
  val toBeCaptured: Boolean                = false
  val toBeWildcardCaptured: Boolean        = false
  def captured                             = copy(args = args.map(_.wildcardCaptured))
  def wildcardCaptured: TemporaryType      = this
  override def breadth                     = args.size
  override def depth = if args.size == 0 then 0 else args.map(x => (x.depth + 1)).max
  val numArgs        = args.size
  val identifier     = s"ξ$id"
  override def toString =
    val a = if args.size == 0 then "" else "<" + args.mkString(", ") + ">"
    s"$identifier$a"
  override def isSomehowUnknown   = args.exists(_.isSomehowUnknown)
  override def raw: TemporaryType = copy(args = Vector())
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): TemporaryType =
    copy(args = args.map(_.reorderTypeParameters(scheme)))
  val upwardProjection: TemporaryType   = this
  val downwardProjection: TemporaryType = this
  def replace(oldType: InferenceVariable, newType: Type): TemporaryType =
    copy(args = args.map(_.replace(oldType, newType)))
  def substitute(function: Substitution): TemporaryType =
    copy(args = args.map(_.substitute(function)))
  override def expansion: (TemporaryType, Substitution) =
    val base = copy(args = (0 until numArgs).map(TypeParameterIndex(identifier, _)).toVector)
    val subs = (0 until numArgs)
      .map[(TTypeParameter, Type)](i => (TypeParameterIndex(identifier, i) -> args(i)))
      .toMap
    (base, subs)
  val substitutions = Nil
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): SomeClassOrInterfaceType =
    if oldType.id != id then copy(args = args.map(_.combineTemporaryType(oldType, newType)))
    else
      SomeClassOrInterfaceType(
        newType.identifier,
        args.map(_.combineTemporaryType(oldType, newType))
      )

/** Some random placeholder type
  * @param id
  *   the ID of this type
  */
final case class PlaceholderType(
    id: Int,
    toBeCaptured: Boolean = false,
    toBeWildcardCaptured: Boolean = false
) extends InferenceVariable:
  val static: Boolean                     = false
  def toStaticType: PlaceholderType       = this
  def fix: PlaceholderType                = ???
  val substitutions                       = Nil
  val numArgs                             = 0
  val identifier                          = s"δ$id"
  val upwardProjection: PlaceholderType   = this
  val downwardProjection: PlaceholderType = this
  def replace(oldType: InferenceVariable, newType: Type): Type =
    if id != oldType.id then this
    else if toBeCaptured then newType.captured
    else if toBeWildcardCaptured then newType.wildcardCaptured
    else newType
  def captured: PlaceholderType         = copy(toBeCaptured = true)
  def wildcardCaptured: PlaceholderType = copy(toBeWildcardCaptured = true)
  val args                              = Vector()
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): PlaceholderType = this
  def substitute(function: Substitution): PlaceholderType                                 = this
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): PlaceholderType = this

/** A disjunctive type is a type who could be one of several possible other reference types
  * (including Type Parameters)
  * @param id
  *   the ID of this type
  * @param source
  *   the containing type, it is a [[scala.util.Left]] if the containing type is not an inference
  *   variable, [[scala.util.Right]] otherwise
  * @param substitutions
  *   the substitutions of this type
  * @param canBeSubsequentlyBounded
  *   whether the possible types can have arguments who are (bounded) wildcards
  * @param parameterChoices
  *   the possible parameters of which this type can contain
  * @param _choices
  *   the actual choices of this type
  */
final case class ReferenceOnlyDisjunctiveType(
    id: Int,
    source: Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeSubsequentlyBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set(),
    choices: Vector[Type] = Vector(),
    canBeUnknown: Boolean = true,
    toBeCaptured: Boolean = false,
    toBeWildcardCaptured: Boolean = false
) extends DisjunctiveType:
  def fix: ReferenceOnlyDisjunctiveType = ???
  def captured: Type                    = copy(toBeCaptured = true)
  def wildcardCaptured: Type            = copy(toBeWildcardCaptured = true)
  val numArgs                           = 0
  val identifier =
    val str = choices.mkString(", ")
    s"τ$id=V{$str}"
  override def toString() =
    val subsFn: Map[TTypeParameter, Type] => String = subs => subs.mkString(", ")
    val subsstring = substitutions.map(subs => s"{${subsFn(subs)}}").mkString
    s"$identifier$subsstring"
  val upwardProjection: ReferenceOnlyDisjunctiveType   = this
  val downwardProjection: ReferenceOnlyDisjunctiveType = this
  def substitute(function: Substitution): ReferenceOnlyDisjunctiveType =
    copy(substitutions = (this.substitutions ::: (function :: Nil)).filter(!_.isEmpty))
  def replace(oldType: InferenceVariable, newType: Type): Type =
    val newChoices       = choices.map(_.replace(oldType, newType))
    val newSource        = source.map(_.replace(oldType, newType))
    val newSubstitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    if id != oldType.id then
      copy(source = newSource, substitutions = newSubstitutions, choices = newChoices)
    else if toBeCaptured then substitutions.foldLeft(newType)((t, s) => t.substitute(s)).captured
    else if toBeWildcardCaptured then
      substitutions.foldLeft(newType)((t, s) => t.substitute(s)).wildcardCaptured
    else substitutions.foldLeft(newType)((t, s) => t.substitute(s))
  val args = Vector()
  override def ⊆(that: Type): Boolean = that match
    case x: ReferenceOnlyDisjunctiveType => this.id == x.id
    case _                               => super.⊆(that)
  def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): ReferenceOnlyDisjunctiveType =
    copy(
      source = source.map(_.reorderTypeParameters(scheme)),
      parameterChoices = parameterChoices.map(_.reorderTypeParameters(scheme)),
      choices = choices.map(_.reorderTypeParameters(scheme)),
      substitutions = substitutions.map(m =>
        m.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
      )
    )
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ReferenceOnlyDisjunctiveType =
    val newSource =
      if source.isLeft && source.asInstanceOf[Left[String, Type]].value == oldType.identifier then
        Left(newType.identifier)
      else source
    val newSubstitutions = substitutions.map(m =>
      m.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.combineTemporaryType(oldType, newType))
      )
    )
    val newParameterChoices = parameterChoices.map(_.combineTemporaryType(oldType, newType))
    val newChoices          = choices.map(_.combineTemporaryType(oldType, newType))
    copy(
      source = newSource,
      substitutions = newSubstitutions,
      parameterChoices = newParameterChoices,
      choices = newChoices
    )

final case class PrimitivesOnlyDisjunctiveType(id: Int) extends DisjunctiveType:
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): PrimitivesOnlyDisjunctiveType = this
  val canBeSubsequentlyBounded: Boolean     = false
  val canBeUnknown: Boolean                 = false
  val parameterChoices: Set[TTypeParameter] = Set()
  def fix: PrimitivesOnlyDisjunctiveType    = this
  def replace(oldType: InferenceVariable, newType: Type): Type =
    if id != oldType.id then this else newType
  val choices                                         = PRIMITIVES.toVector
  val toBeCaptured: Boolean                           = false
  val toBeWildcardCaptured: Boolean                   = false
  def captured: PrimitivesOnlyDisjunctiveType         = this
  def wildcardCaptured: PrimitivesOnlyDisjunctiveType = this
  val args                                            = Vector()
  def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): PrimitivesOnlyDisjunctiveType = this
  val numArgs = 0
  val identifier =
    val str = choices.mkString(", ")
    s"τ$id=V{$str}"
  override def toString()                = identifier
  val upwardProjection                   = this
  val downwardProjection                 = this
  def substitute(function: Substitution) = this
  val substitutions                      = Nil

final case class BoxesOnlyDisjunctiveType(id: Int) extends DisjunctiveType:
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): BoxesOnlyDisjunctiveType = this
  val canBeSubsequentlyBounded: Boolean          = false
  val canBeUnknown: Boolean                      = false
  val parameterChoices: Set[TTypeParameter]      = Set()
  def fix: BoxesOnlyDisjunctiveType              = ???
  val toBeCaptured: Boolean                      = false
  val toBeWildcardCaptured: Boolean              = false
  def captured: BoxesOnlyDisjunctiveType         = this
  def wildcardCaptured: BoxesOnlyDisjunctiveType = this
  def replace(oldType: InferenceVariable, newType: Type): Type =
    if id != oldType.id then this else newType
  val choices = BOXES.toVector
  val args    = Vector()
  def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): BoxesOnlyDisjunctiveType = this
  val numArgs = 0
  val identifier =
    val str = choices.mkString(", ")
    s"τ$id=V{$str}"
  override def toString()                = identifier
  val upwardProjection                   = this
  val downwardProjection                 = this
  def substitute(function: Substitution) = this
  val substitutions                      = Nil

final case class DisjunctiveTypeWithPrimitives(
    id: Int,
    substitutions: SubstitutionList = Nil,
    parameterChoices: Set[TTypeParameter] = Set(),
    choices: Vector[Type] = Vector[Type]() ++ PRIMITIVES,
    canBeSubsequentlyBounded: Boolean = true,
    canBeUnknown: Boolean = true,
    toBeCaptured: Boolean = false,
    toBeWildcardCaptured: Boolean = true
) extends DisjunctiveType:
  def fix                                             = ???
  def captured: DisjunctiveTypeWithPrimitives         = copy(toBeCaptured = true)
  def wildcardCaptured: DisjunctiveTypeWithPrimitives = copy(toBeWildcardCaptured = true)
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): DisjunctiveTypeWithPrimitives =
    val newSubstitutions = substitutions.map(m =>
      m.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.combineTemporaryType(oldType, newType))
      )
    )
    val newParameterChoices = parameterChoices.map(_.combineTemporaryType(oldType, newType))
    val newChoices          = choices.map(_.combineTemporaryType(oldType, newType))
    copy(
      substitutions = newSubstitutions,
      parameterChoices = newParameterChoices,
      choices = newChoices
    )
  def replace(oldType: InferenceVariable, newType: Type): Type =
    val newChoices       = choices.map(_.replace(oldType, newType))
    val newSubstitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    if id != oldType.id then copy(substitutions = newSubstitutions, choices = newChoices)
    else if toBeCaptured then substitutions.foldLeft(newType)((t, s) => t.substitute(s)).captured
    else if toBeWildcardCaptured then
      substitutions.foldLeft(newType)((t, s) => t.substitute(s)).wildcardCaptured
    else substitutions.foldLeft(newType)((t, s) => t.substitute(s))
  val args = Vector()
  def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): DisjunctiveTypeWithPrimitives =
    copy(
      substitutions = substitutions.map(m =>
        m.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
      ),
      parameterChoices = parameterChoices.map(_.reorderTypeParameters(scheme)),
      choices = choices.map(_.reorderTypeParameters(scheme))
    )
  val numArgs: Int = 0
  val identifier =
    val str = choices.mkString(", ")
    s"τ$id=V{$str}"
  override def toString() =
    val subsFn: Map[TTypeParameter, Type] => String = subs => subs.mkString(", ")
    val subsstring = substitutions.map(subs => s"{${subsFn(subs)}}").mkString
    s"$identifier$subsstring"
  val upwardProjection: DisjunctiveTypeWithPrimitives   = this
  val downwardProjection: DisjunctiveTypeWithPrimitives = this
  def substitute(function: Substitution): DisjunctiveTypeWithPrimitives =
    copy(substitutions = (this.substitutions ::: (function :: Nil)).filter(!_.isEmpty))

final case class VoidableDisjunctiveType(
    id: Int,
    substitutions: SubstitutionList = Nil,
    parameterChoices: Set[TTypeParameter] = Set(),
    choices: Vector[Type] = Vector[Type]() ++ PRIMITIVES :+ PRIMITIVE_VOID,
    canBeUnknown: Boolean,
    toBeCaptured: Boolean = false,
    toBeWildcardCaptured: Boolean = false
) extends DisjunctiveType:
  val canBeSubsequentlyBounded: Boolean         = true // why not
  def fix                                       = ???
  def captured: VoidableDisjunctiveType         = copy(toBeCaptured = true)
  def wildcardCaptured: VoidableDisjunctiveType = copy(toBeWildcardCaptured = true)
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): VoidableDisjunctiveType =
    val newSubstitutions = substitutions.map(m =>
      m.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.combineTemporaryType(oldType, newType))
      )
    )
    val newParameterChoices = parameterChoices.map(_.combineTemporaryType(oldType, newType))
    val newChoices          = choices.map(_.combineTemporaryType(oldType, newType))
    copy(
      substitutions = newSubstitutions,
      parameterChoices = newParameterChoices,
      choices = newChoices
    )
  def replace(oldType: InferenceVariable, newType: Type): Type =
    val newChoices       = choices.map(_.replace(oldType, newType))
    val newSubstitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    if id != oldType.id then copy(substitutions = newSubstitutions, choices = newChoices)
    //else substitutions.foldLeft(newType)((t, s) => t.substitute(s))
    else if toBeCaptured then substitutions.foldLeft(newType)((t, s) => t.substitute(s)).captured
    else if toBeWildcardCaptured then
      substitutions.foldLeft(newType)((t, s) => t.substitute(s)).wildcardCaptured
    else substitutions.foldLeft(newType)((t, s) => t.substitute(s))
  val args = Vector()
  def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): VoidableDisjunctiveType =
    copy(
      substitutions = substitutions.map(m =>
        m.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
      ),
      parameterChoices = parameterChoices.map(_.reorderTypeParameters(scheme)),
      choices = choices.map(_.reorderTypeParameters(scheme))
    )
  val numArgs: Int = 0
  val identifier =
    val str = choices.mkString(", ")
    s"τ$id=V{$str}"
  override def toString() =
    val subsFn: Map[TTypeParameter, Type] => String = subs => subs.mkString(", ")
    val subsstring = substitutions.map(subs => s"{${subsFn(subs)}}").mkString
    s"$identifier$subsstring"
  val upwardProjection: VoidableDisjunctiveType   = this
  val downwardProjection: VoidableDisjunctiveType = this
  def substitute(function: Substitution): VoidableDisjunctiveType =
    copy(substitutions = (this.substitutions ::: (function :: Nil)).filter(!_.isEmpty))

/** Some unknown concrete reference type
  * @param id
  *   the ID of this alpha
  * @param source
  *   the containing type
  * @param substitutions
  *   any substitutions to this type
  * @param canBeBounded
  *   whether the arguments to this type can be (bounded) wildcards
  * @param parameterChoices
  *   the parameters this type can contain
  */
final case class Alpha(
    id: Int,
    source: Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set(),
    toBeCaptured: Boolean = false,
    canBeTemporaryType: Boolean = true
) extends InferenceVariable:
  val static: Boolean               = false
  def toStaticType: Alpha           = this
  def fix                           = ???
  val toBeWildcardCaptured: Boolean = false
  def captured: Alpha               = copy(toBeCaptured = true)
  def wildcardCaptured: Alpha       = this
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): Alpha =
    val newSource =
      if source.isLeft && source.asInstanceOf[Left[String, Type]].value == oldType.identifier then
        Left(newType.identifier)
      else source
    val newSubstitutions = substitutions.map(m =>
      m.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.combineTemporaryType(oldType, newType))
      )
    )
    val newParameterChoices = parameterChoices.map(_.combineTemporaryType(oldType, newType))
    copy(
      source = newSource,
      substitutions = newSubstitutions,
      parameterChoices = newParameterChoices
    )
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Alpha =
    copy(
      source = source.map(_.reorderTypeParameters(scheme)),
      parameterChoices = parameterChoices.map(_.reorderTypeParameters(scheme)),
      substitutions = substitutions.map(m =>
        m.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
      )
    )
  val numArgs            = 0
  val identifier         = s"α$id"
  val upwardProjection   = this
  val downwardProjection = this
  override def toString() =
    val subsFn: Map[TTypeParameter, Type] => String = subs => subs.mkString(", ")
    val subsstring = substitutions.map(subs => s"{${subsFn(subs)}}").mkString
    s"$identifier$subsstring"

  def replace(oldType: InferenceVariable, newType: Type): Type =
    val newSource        = source.map(_.replace(oldType, newType))
    val newSubstitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    if id != oldType.id then copy(source = newSource, substitutions = newSubstitutions)
    else if toBeCaptured then substitutions.foldLeft(newType)((t, s) => t.substitute(s)).captured
    else if toBeWildcardCaptured then
      substitutions.foldLeft(newType)((t, s) => t.substitute(s)).wildcardCaptured
    else substitutions.foldLeft(newType)((t, s) => t.substitute(s))
  def substituted = this
  val args        = Vector()
  def substitute(function: Substitution): Alpha =
    copy(substitutions = (substitutions ::: (function :: Nil)).filter(!_.isEmpty))

  /** Given an identifier of a type and the arity of its type constructor, convert this alpha into
    * an instance of that type
    * @param identifier
    *   the type to concretize into
    * @param numArgs
    *   the arity of the new type's type constructor, or effectively, the number of arguments this
    *   type should have
    * @return
    *   the resulting type
    */
  def concretizeToReference(identifier: String, numArgs: Int): SomeClassOrInterfaceType =
    SomeClassOrInterfaceType(
      identifier,
      (0 until numArgs)
        .map(i =>
          InferenceVariableFactory.createDisjunctiveType(
            source,
            Nil,
            canBeBounded,
            parameterChoices,
            canBeBounded,
            canBeTemporaryType
          )
        )
        .toVector
    )

  def concretizeToTemporary(typeId: Int, numArgs: Int): (TemporaryType) =
    TemporaryType(
      typeId,
      (0 until numArgs)
        .map(i =>
          InferenceVariableFactory.createDisjunctiveType(
            source,
            Nil,
            canBeBounded,
            parameterChoices,
            canBeBounded,
            canBeTemporaryType
          )
        )
        .toVector
    )

  override def ⊆(that: Type): Boolean = that match
    case x: Alpha => this.id == x.id
    case _        => super.⊆(that)

/** Object that contains helper methods to create inference variables
  */
object InferenceVariableFactory:
  private var id = 0

  def createTemporaryType(): TemporaryType =
    id += 1
    TemporaryType(id, Vector())

  /** Creates a disjunctive type
    * @param source
    *   the containing type of this disjunctive type; it is a [[scala.util.Left]] if the containing
    *   type is known (not an inference variable), a [[scala.util.Right]] otherwise
    * @param substitutions
    *   the substitutions to the resulting inference variable
    * @param canBeSubsequentlyBounded
    *   whether or not the arguments to any of the types in the disjunction can be (bounded)
    *   wildcards
    * @param parameterChoices
    *   the parameters who the disjunctive type can contain
    * @param canBeBounded
    *   whether or not the types in the disjunction can themselves be (bounded) wildcards
    * @return
    *   the resulting disjunctive type
    */
  def createDisjunctiveType(
      source: scala.util.Either[String, Type],
      substitutions: SubstitutionList = Nil,
      canBeSubsequentlyBounded: Boolean = false,
      parameterChoices: Set[TTypeParameter] = Set(),
      canBeBounded: Boolean = false,
      canBeUnknown: Boolean = true
  ) =
    id += 1
    val myID = id
    val choices: Vector[Type] =
      if !canBeBounded then
        parameterChoices.toVector :+ createAlpha(
          source,
          Nil,
          canBeSubsequentlyBounded || canBeBounded,
          parameterChoices,
          canBeUnknown
        )
      else
        val alpha       = createAlpha(source, Nil, true, parameterChoices, canBeUnknown)
        val baseChoices = parameterChoices.toVector :+ alpha
        val supers      = baseChoices.map(SuperWildcardType(_))
        val extend      = baseChoices.map(ExtendsWildcardType(_))
        (baseChoices :+ Wildcard) ++ supers ++ extend
    ReferenceOnlyDisjunctiveType(
      myID,
      source,
      substitutions,
      canBeSubsequentlyBounded,
      parameterChoices,
      choices,
      canBeUnknown
    )

  def createDisjunctiveTypeWithPrimitives(
      source: scala.util.Either[String, Type],
      substitutions: SubstitutionList = Nil,
      canBeSubsequentlyBounded: Boolean = false,
      parameterChoices: Set[TTypeParameter] = Set(),
      canBeUnknown: Boolean = true
  ) =
    id += 1
    val myID = id
    val choices: Vector[Type] =
      PRIMITIVES_ASCENDING_NO_VOID ++ parameterChoices.toVector :+ createAlpha(
        source,
        Nil,
        canBeSubsequentlyBounded,
        parameterChoices,
        canBeUnknown
      )
    DisjunctiveTypeWithPrimitives(
      myID,
      substitutions,
      parameterChoices,
      choices,
      canBeSubsequentlyBounded,
      canBeUnknown
    )

  def createCapture(lowerBound: Type, upperBound: Type) =
    id += 1
    Capture(id, lowerBound, upperBound)

  def createVoidableDisjunctiveType(
      source: scala.util.Either[String, Type],
      substitutions: SubstitutionList = Nil,
      canBeSubsequentlyBounded: Boolean = true,
      parameterChoices: Set[TTypeParameter] = Set(),
      canBeUnknown: Boolean = true
  ) =
    id += 1
    val myID = id
    val choices: Vector[Type] =
      PRIMITIVES_DESCENDING ++ parameterChoices.toVector :+ createAlpha(
        source,
        Nil,
        canBeSubsequentlyBounded,
        parameterChoices,
        canBeUnknown
      )
    VoidableDisjunctiveType(
      myID,
      substitutions,
      parameterChoices,
      choices,
      canBeUnknown
    )

  def createPrimitivesOnlyDisjunctiveType() =
    id += 1
    PrimitivesOnlyDisjunctiveType(id)

  def createBoxesOnlyDisjunctiveType() =
    id += 1
    BoxesOnlyDisjunctiveType(id)

  def createJavaInferenceVariable(
      paramChoices: Set[TTypeParameter],
      canBeSubsequentlyBounded: Boolean = false
  ) =
    id += 1
    JavaInferenceVariable(id, paramChoices, canBeSubsequentlyBounded)

  /** Creates a new alpha
    * @param source
    *   the containing type of the alpha; it is a [[scala.util.Left]] if the containing type is not
    *   an inference variable, a [[scala.util.Right]] otherwise
    * @param substitutions
    *   the substitutions to this alpha
    * @param canBeBounded
    *   whether the arguments to this alpha can be (bounded) wildcards
    * @param parameterChoices
    *   the parameters who the arguments to this alpha can contain
    * @return
    *   the resulting alpha
    */
  def createAlpha(
      source: scala.util.Either[String, Type],
      substitutions: SubstitutionList = Nil,
      canBeBounded: Boolean = false,
      parameterChoices: Set[TTypeParameter] = Set(),
      canBeUnknown: Boolean = true
  ) =
    id += 1
    Alpha(id, source, substitutions, canBeBounded, parameterChoices, false, canBeUnknown)

  /** Creates a generic placeholder type
    * @return
    *   the resulting placeholder type
    */
  def createPlaceholderType(): PlaceholderType =
    id += 1
    PlaceholderType(id)

/** The primitive types */
val PRIMITIVES: Set[PrimitiveType] = Set(
  PRIMITIVE_BYTE,
  PRIMITIVE_SHORT,
  PRIMITIVE_INT,
  PRIMITIVE_LONG,
  PRIMITIVE_CHAR,
  PRIMITIVE_FLOAT,
  PRIMITIVE_DOUBLE,
  PRIMITIVE_BOOLEAN
)

/** The widening relation <<~= */
val WIDENING_RELATION: Set[(Type, Type)] = Set(
  (PRIMITIVE_BYTE, PRIMITIVE_SHORT),
  (PRIMITIVE_BYTE, PRIMITIVE_INT),    // transitive
  (PRIMITIVE_BYTE, PRIMITIVE_LONG),   // transitive
  (PRIMITIVE_BYTE, PRIMITIVE_FLOAT),  // transitive
  (PRIMITIVE_BYTE, PRIMITIVE_DOUBLE), // transitive
  (PRIMITIVE_SHORT, PRIMITIVE_INT),
  (PRIMITIVE_SHORT, PRIMITIVE_LONG),   // transitive
  (PRIMITIVE_SHORT, PRIMITIVE_FLOAT),  // transitive
  (PRIMITIVE_SHORT, PRIMITIVE_DOUBLE), // transitive
  (PRIMITIVE_CHAR, PRIMITIVE_INT),
  (PRIMITIVE_CHAR, PRIMITIVE_LONG),   // transitive
  (PRIMITIVE_CHAR, PRIMITIVE_FLOAT),  // transitive
  (PRIMITIVE_CHAR, PRIMITIVE_DOUBLE), // transitive
  (PRIMITIVE_INT, PRIMITIVE_LONG),
  (PRIMITIVE_INT, PRIMITIVE_FLOAT),  // transitive
  (PRIMITIVE_INT, PRIMITIVE_DOUBLE), // transitive
  (PRIMITIVE_LONG, PRIMITIVE_FLOAT),
  (PRIMITIVE_LONG, PRIMITIVE_DOUBLE), // transitive
  (PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE),
  (PRIMITIVE_BYTE, PRIMITIVE_BYTE),      // reflexive
  (PRIMITIVE_SHORT, PRIMITIVE_SHORT),    // reflexive
  (PRIMITIVE_CHAR, PRIMITIVE_CHAR),      // reflexive
  (PRIMITIVE_INT, PRIMITIVE_INT),        // reflexive
  (PRIMITIVE_LONG, PRIMITIVE_LONG),      // reflexive
  (PRIMITIVE_FLOAT, PRIMITIVE_FLOAT),    // reflexive
  (PRIMITIVE_DOUBLE, PRIMITIVE_DOUBLE),  // reflexive
  (PRIMITIVE_VOID, PRIMITIVE_VOID),      // reflexive
  (PRIMITIVE_BOOLEAN, PRIMITIVE_BOOLEAN) // reflexive
)

val BOX_RELATION: Set[(PrimitiveType, ClassOrInterfaceType)] = Set(
  (PRIMITIVE_CHAR, BOXED_CHAR),
  (PRIMITIVE_BYTE, BOXED_BYTE),
  (PRIMITIVE_SHORT, BOXED_SHORT),
  (PRIMITIVE_INT, BOXED_INT),
  (PRIMITIVE_LONG, BOXED_LONG),
  (PRIMITIVE_FLOAT, BOXED_FLOAT),
  (PRIMITIVE_DOUBLE, BOXED_DOUBLE),
  (PRIMITIVE_BOOLEAN, BOXED_BOOLEAN)
)
val UNBOX_RELATION: Set[(ClassOrInterfaceType, PrimitiveType)] = BOX_RELATION.map { case (x, y) =>
  (y, x)
}

val PRIMITIVES_ASCENDING_NO_VOID = Vector(
  PRIMITIVE_BOOLEAN,
  PRIMITIVE_BYTE,
  PRIMITIVE_SHORT,
  PRIMITIVE_CHAR,
  PRIMITIVE_INT,
  PRIMITIVE_LONG,
  PRIMITIVE_FLOAT,
  PRIMITIVE_DOUBLE
)

val PRIMITIVES_DESCENDING = Vector(
  PRIMITIVE_VOID,
  PRIMITIVE_BOOLEAN,
  PRIMITIVE_DOUBLE,
  PRIMITIVE_FLOAT,
  PRIMITIVE_LONG,
  PRIMITIVE_INT,
  PRIMITIVE_CHAR,
  PRIMITIVE_SHORT,
  PRIMITIVE_BYTE
)
