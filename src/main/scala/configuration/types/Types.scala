package configuration.types

import configuration.basetraits.*
import configuration.assertions.*

import scala.Console.{RED, RESET}
import utils.IllegalOperationException

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

private type SimpleTypeOps[A <: Type] = TypeOps[A, A, A, A, A, A, A, A, A, A, A]

private type BaseTypeOps = SimpleTypeOps[Type]

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

private type PrimitiveTypeOps = SimpleTypeOps[PrimitiveType]

/** The primitive types */
sealed trait PrimitiveType extends Type, PrimitiveTypeOps:
  /** The boxed type of this primitive type */
  val boxed: ClassOrInterfaceType

  /** Primitive types have no type arguments */
  override final val args = Vector()

  /** Primitive types have no type arguments */
  override final val numArgs = 0

  /** The upward projection of any primitive type is itself */
  override final val upwardProjection: PrimitiveType = this

  /** The downward projection of any primitive type is itself */
  override final val downwardProjection: PrimitiveType = this

  /** Primitive types will never be the static type */
  override final val static: Boolean = false

  /** the types which this primitive type can widen to */
  def widened: Set[PrimitiveType]

  /** the types which can be assigned into this type */
  def isAssignableBy: Set[Type] = widenedFrom ++ boxedSources

  /** The types that widen to this type */
  def widenedFrom: Set[PrimitiveType]

  /** The types that, after unboxing conversions, widen to this type */
  def boxedSources: Set[ClassOrInterfaceType] = widenedFrom.map(_.boxed)

  /** There is no static primitive type */
  override final def toStaticType: PrimitiveType = this

  /** Fixing this type just produces itself */
  override final def fix: PrimitiveType = this

  /** Capturing this type just produces itself */
  override final def captured: PrimitiveType = this

  /** Wildcard-capturing this type just produces itself */
  override final def wildcardCaptured: PrimitiveType = this

  /** Attempting to combine a temporary type on this type just produces itself */
  override final def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): PrimitiveType = this

  /** The breadth of a primitive type is 0 */
  override final def breadth = 0

  /** The depth of a primitive type is 0 */
  override final def depth = 0

  /** Attempting to replace inference variables in this primitive type just produces itself */
  override final def replace(oldType: InferenceVariable, newType: Type): PrimitiveType = this

  /** Attempting to reorder the type parameters of this type just produces itself */
  override final def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): PrimitiveType =
    this

  /** All primitive types are known */
  override final def isSomehowUnknown = false

  /** The raw type of a primitive type is itself */
  override final def raw: PrimitiveType = this

  /** Substitutions on a primitive type do not change it */
  override final def substitute(function: Substitution): PrimitiveType = this

  /** The expansion of any primitive type is just itself and an empty function */
  override final def expansion: (PrimitiveType, Substitution) = (this, Map())

  override final def toString = identifier

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
    case x         => throw new IllegalArgumentException(f"$x is not a valid primitive type")

/** the `int` type */
case object PRIMITIVE_INT extends PrimitiveType:
  /** `int` */
  override val identifier = "int"

  /** `java.lang.Integer` */
  override val boxed = BOXED_INT

  /** `int` widens to `int`, `long`, `float` and `double` */
  override def widened = Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)

  /** `byte`, `char`, `short` and `int` widen to `int` */
  override def widenedFrom = Set(PRIMITIVE_SHORT, PRIMITIVE_CHAR, PRIMITIVE_BYTE, PRIMITIVE_INT)

/** the `byte` type */
case object PRIMITIVE_BYTE extends PrimitiveType:
  /** `byte` */
  override val identifier = "byte"

  /** `java.lang.Byte` */
  override val boxed = BOXED_BYTE

  /** `byte` widens to `byte`, `short`, `int`, `long`, `float`, `double` */
  override def widened = Set(
    PRIMITIVE_BYTE,
    PRIMITIVE_SHORT,
    PRIMITIVE_INT,
    PRIMITIVE_LONG,
    PRIMITIVE_FLOAT,
    PRIMITIVE_DOUBLE
  )

  /** Only `byte` widens to `byte` */
  override def widenedFrom = Set(PRIMITIVE_BYTE)

/** the `short` type */
case object PRIMITIVE_SHORT extends PrimitiveType:
  /** `short` */
  override val identifier = "short"

  /** `java.lang.Short` */
  override val boxed = BOXED_SHORT

  /** `short` widens to `short`, `int`, `long`, `float`, `double` */
  override def widened =
    Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE, PRIMITIVE_SHORT)

  /** `short` and `byte` widen to `short` */
  override def widenedFrom = Set(PRIMITIVE_SHORT, PRIMITIVE_BYTE)

/** the `char` type */
case object PRIMITIVE_CHAR extends PrimitiveType:
  /** `char` */
  override val identifier = "char"

  /** `java.lang.Character` */
  override val boxed = BOXED_CHAR

  /** `char` widens to `char`, `int`, `long`, `float`, `double` */
  override def widened =
    Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE, PRIMITIVE_CHAR)

  /** `byte` and `char` widen to `char` */
  override def widenedFrom = Set(PRIMITIVE_CHAR, PRIMITIVE_BYTE)

/** the `long` type */
case object PRIMITIVE_LONG extends PrimitiveType:
  /** `long` */
  override val identifier = "long"

  /** `java.lang.Long` */
  override val boxed = BOXED_LONG

  /** `long` widens to `long`, `float` and `double` */
  override def widened = Set(PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE, PRIMITIVE_LONG)

  /** `byte`, `short`, `char`, `int` and `long` widen to `long` */
  override def widenedFrom =
    Set(PRIMITIVE_LONG, PRIMITIVE_SHORT, PRIMITIVE_INT, PRIMITIVE_CHAR, PRIMITIVE_BYTE)

/** the `float` type */
case object PRIMITIVE_FLOAT extends PrimitiveType:
  /** `float` */
  override val identifier = "float"

  /** `java.lang.Float` */
  override val boxed = BOXED_FLOAT

  /** `float` widens to `float` and `double` */
  override def widened = Set(PRIMITIVE_DOUBLE, PRIMITIVE_FLOAT)

  /** `byte`, `short`, `char`, `int`, `long` and `float` widen to `float` */
  override def widenedFrom = Set(
    PRIMITIVE_FLOAT,
    PRIMITIVE_LONG,
    PRIMITIVE_SHORT,
    PRIMITIVE_INT,
    PRIMITIVE_CHAR,
    PRIMITIVE_BYTE
  )

/** the `double` type */
case object PRIMITIVE_DOUBLE extends PrimitiveType:
  /** `double` */
  override val identifier = "double"

  /** `java.lang.Double` */
  override val boxed = BOXED_DOUBLE

  /** `double` only widens to `double` */
  override def widened = Set(PRIMITIVE_DOUBLE)

  /** all of the numeric types widen to `double` */
  override def widenedFrom = Set(
    PRIMITIVE_DOUBLE,
    PRIMITIVE_FLOAT,
    PRIMITIVE_LONG,
    PRIMITIVE_SHORT,
    PRIMITIVE_INT,
    PRIMITIVE_CHAR,
    PRIMITIVE_BYTE
  )

/** the `boolean` type */
case object PRIMITIVE_BOOLEAN extends PrimitiveType:
  /** `boolean` */
  override val identifier = "boolean"

  /** `java.lang.Boolean` */
  override val boxed = BOXED_BOOLEAN

  /** `boolean` only widens to `boolean` */
  override def widened = Set(PRIMITIVE_BOOLEAN)

  /** `boolean` only widens to `boolean` */
  override def widenedFrom = widened

/** the `void` type */
case object PRIMITIVE_VOID extends PrimitiveType:
  /** `void` */
  override val identifier = "void"

  /** `java.lang.Void` */
  override val boxed = BOXED_VOID

  /** `void` only widens to `void` */
  override def widened = Set(PRIMITIVE_VOID)

  /** `void` only widens to `void` */
  override def widenedFrom = widened

private type SomeClassOrInterfaceTypeOps = TypeOps[
  SomeClassOrInterfaceType,
  ClassOrInterfaceType,
  SomeClassOrInterfaceType,
  SomeClassOrInterfaceType,
  SomeClassOrInterfaceType,
  SomeClassOrInterfaceType,
  SomeClassOrInterfaceType,
  SomeClassOrInterfaceType,
  SomeClassOrInterfaceType,
  SomeClassOrInterfaceType,
  SomeClassOrInterfaceType,
]

/** Represents some class or interface type; could be a temporary type */
sealed trait SomeClassOrInterfaceType extends Type, SomeClassOrInterfaceTypeOps:
  /** Converts this type into the static type (the type of this type)
    * @return
    *   the static type of this type
    */
  def toStaticType: SomeClassOrInterfaceType

  /** The number of type arguments to this type */
  override final val numArgs = args.size

  /** The breadth of a class or interface type is its number of arguments */
  override final def breadth = numArgs

  /** The depth of a class or interface type is the maximum depth of its arguments + 1 (if it has no
    * arguments then its depth is 0)
    */
  override final def depth = if args.size == 0 then 0 else args.map(x => (x.depth + 1)).max

  /** Determines if any of its arguments are unknown types */
  override final def isSomehowUnknown = args.exists(_.isSomehowUnknown)

/** Companion object to [[configuration.types.SomeClassOrInterfaceType]] */
object SomeClassOrInterfaceType:
  /** Creates some class or interface type
    * @param identifier
    *   the name of the type
    * @param args
    *   the type arguments of this type
    * @return
    *   the resulting class or interface type (could be a temporary type)
    */
  def apply(identifier: String, args: Vector[Type] = Vector()): SomeClassOrInterfaceType =
    if identifier.length() == 0 then
      throw new IllegalArgumentException(
        f"cannot create some class or interface type with an empty identifier!"
      )
    // this is a temporary type
    else if identifier.charAt(0) == 'ξ' then
      TemporaryType(Integer.parseInt(identifier.substring(1)), args)
    // just a regular ClassOrInterfaceType
    else ClassOrInterfaceType(identifier, args)

private type ClassOrInterfaceTypeOps = SimpleTypeOps[ClassOrInterfaceType]

/** A class or interface type in a Java program
  * @param identifier
  *   the name of the type
  * @param args
  *   the type arguments to this type
  * @param static
  *   whether this type is the static type
  */
case class ClassOrInterfaceType(
    identifier: String,
    args: Vector[Type],
    static: Boolean = false
) extends SomeClassOrInterfaceType,
      ClassOrInterfaceTypeOps:
  if identifier.size == 0 then
    throw new IllegalArgumentException(
      "cannot create ClassOrInterfaceType with an empty identifier!"
    )
  if identifier.charAt(0) == 'ξ' then
    throw new IllegalArgumentException(
      s"$identifier is not a valid identifier of a ClassOrInterfaceType!"
    )

  /** The upward projection of a class or interface type is itself */
  override val upwardProjection: ClassOrInterfaceType = this

  /** The downward projection of a class or interface type is itself */
  override val downwardProjection: ClassOrInterfaceType = this

  /** Makes this the static type */
  override def toStaticType: ClassOrInterfaceType = copy(static = true)

  /** Fixes the arguments to this class or interface type */
  override def fix: ClassOrInterfaceType = copy(args = args.map(_.fix))

  /** Wildcard-capturing a class or interface type simply produces itself */
  override def wildcardCaptured: ClassOrInterfaceType = this

  /** Capturing a class or interface type performs a wildcard-capture on its arguments */
  override def captured: ClassOrInterfaceType = copy(identifier, args.map(_.wildcardCaptured))

  override def toString =
    val a = if args.size == 0 then "" else "<" + args.mkString(", ") + ">"
    s"$identifier$a${if static then ".type" else ""}"

  /** The raw type of a class or interface type is itself without any of its type arguments */
  override def raw: ClassOrInterfaceType = copy(args = Vector())

  /** Reorders the type parameters of the arguments to this class or interface type
    * @param scheme
    *   the scheme to reorder the type parameters with
    * @return
    *   the resulting class or interface type
    */
  override def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): ClassOrInterfaceType =
    copy(args = args.map(_.reorderTypeParameters(scheme)))

  /** Replaces all occurrences of an inference variable with a new type in this class or interface
    * type
    * @param oldType
    *   the type to replace
    * @param newType
    *   the type to replace it with
    * @return
    *   the resulting class or interface type
    */
  override def replace(oldType: InferenceVariable, newType: Type): ClassOrInterfaceType =
    copy(args = args.map(_.replace(oldType, newType)))

  /** Performs a substitution on this type
    * @param function
    *   the substitution function
    * @return
    *   the resulting type after substitution
    */
  override def substitute(function: Substitution): ClassOrInterfaceType =
    copy(args = args.substitute(function))

  /** Performs an expansion on this type
    * @return
    *   the base type with the substitution function
    */
  override def expansion: (ClassOrInterfaceType, Substitution) =
    val base = copy(args = (0 until numArgs).map(TypeParameterIndex(identifier, _)).toVector)
    val subs = (0 until numArgs)
      .map[(TTypeParameter, Type)](i => (TypeParameterIndex(identifier, i) -> args(i)))
      .toMap
    (base, subs)

  /** Combines all occurrences of a temporary type in the arguments to this type with another class
    * or interface type
    * @param oldType
    *   the temporary type to combine
    * @param newType
    *   the type to combine with
    * @return
    *   the resulting class or interface type
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ClassOrInterfaceType =
    copy(args = args.map(_.combineTemporaryType(oldType, newType)))

/** Companion object to [[configuration.types.ClassOrInterfaceType]] */
object ClassOrInterfaceType:
  /** Creates an empty ClassOrInterfaceType with just the identifier and no type arguments
    * @param identifier
    *   the identifier of this type
    * @return
    *   the resulting ClassOrInterfaceType
    */
  def apply(identifier: String): ClassOrInterfaceType =
    new ClassOrInterfaceType(identifier, Vector())

private type TemporaryTypeOps = TypeOps[
  TemporaryType,
  ClassOrInterfaceType,
  TemporaryType,
  TemporaryType,
  TemporaryType,
  TemporaryType,
  TemporaryType,
  TemporaryType,
  TemporaryType,
  SomeClassOrInterfaceType,
  TemporaryType
]

/** A temporary ClassOrInterfaceType that can be combined with another ClassOrInterfaceType in the
  * future (if necessary)
  * @param id
  *   the ID of this type
  * @param args
  *   the type arguments
  * @param static
  *   the static type
  */
final case class TemporaryType(
    id: Int,
    args: Vector[Type],
    static: Boolean = false
) extends SomeClassOrInterfaceType,
      TemporaryTypeOps:
  /** `ξ<id>` */
  override val identifier = s"ξ$id"

  /** The upward projection of a temporary type is itself */
  override val upwardProjection: TemporaryType = this

  /** The downward projection of a temporary type is itself */
  override val downwardProjection: TemporaryType = this

  /** The static type of a temporary type */
  override def toStaticType: TemporaryType = copy(static = true)

  /** Fixes this type as a [[configuration.types.ClassOrInterfaceType]] of identifier `UNKNOWN_<id>`
    */
  override def fix: ClassOrInterfaceType = ClassOrInterfaceType(s"UNKNOWN_$id", args.map(_.fix))

  /** Performs capture conversion on this type */
  override def captured = copy(args = args.map(_.wildcardCaptured))

  /** Performs a wildcard-capture on this type */
  override def wildcardCaptured: TemporaryType = this

  /** This type without its arguments */
  override def raw: TemporaryType = copy(args = Vector())

  /** Reorders the type parameters in this type via some scheme
    * @param scheme
    *   the scheme to reorder the type parameters with
    * @return
    *   the resulting type after reordering the type parameters
    */
  override def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): TemporaryType =
    copy(args = args.map(_.reorderTypeParameters(scheme)))

  /** Replaces all occurrences of an inference variable in this type with a new type
    * @param oldType
    *   the type to replace
    * @param newType
    *   the type to replace it with
    * @return
    *   the resulting type
    */
  override def replace(oldType: InferenceVariable, newType: Type): TemporaryType =
    copy(args = args.map(_.replace(oldType, newType)))

  /** Performs a substitution on this type
    * @param function
    *   the substitution function
    * @return
    *   the resulting type
    */
  override def substitute(function: Substitution): TemporaryType =
    copy(args = args.map(_.substitute(function)))

  /** Performs an expansion on this type */
  override def expansion: (TemporaryType, Substitution) =
    val base = copy(args = (0 until numArgs).map(TypeParameterIndex(identifier, _)).toVector)
    val subs = (0 until numArgs)
      .map[(TTypeParameter, Type)](i => (TypeParameterIndex(identifier, i) -> args(i)))
      .toMap
    (base, subs)

  /** Combines all instances of a temporary type (could be itself) within this type with another
    * class or interface type
    * @param oldType
    *   the type to combine
    * @param newType
    *   the type to combine it with
    * @return
    *   the resulting type
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): SomeClassOrInterfaceType =
    if oldType.id != id then copy(args = args.map(_.combineTemporaryType(oldType, newType)))
    else
      SomeClassOrInterfaceType(
        newType.identifier,
        args.map(_.combineTemporaryType(oldType, newType))
      )

  override def toString =
    val a = if args.size == 0 then "" else "<" + args.mkString(", ") + ">"
    s"$identifier$a"

private type ArrayTypeOps = SimpleTypeOps[ArrayType]

/** An array
  * @param base
  *   the type of the elements of this array
  */
case class ArrayType(base: Type) extends Type, ArrayTypeOps:
  /** There are no static array types */
  override val static: Boolean = false

  /** the identifier of an array type is its elements identifier, with [] */
  override val identifier = base.identifier + "[]"

  /** Arrays have no type arguments */
  override val numArgs = 0

  /** The upward projection of an array type is itself */
  override val upwardProjection: ArrayType = this

  /** The downward projection of an array type is itself */
  override val downwardProjection: ArrayType = this

  /** Arrays have no type arguments */
  override val args = Vector()

  /** The static type of an array type is itself */
  override def toStaticType: ArrayType = this

  /** To capture an array type is to capture its elements */
  override def captured: ArrayType = copy(base = base.captured)

  /** To wildcard-capture an array type is to wildcard-capture its elements */
  override def wildcardCaptured: ArrayType = copy(base = base.wildcardCaptured)

  /** The breadth of an array type is 0 */
  override def breadth = 0

  /** The depth of an array type is the depth of its elements + 1 */
  override def depth = base.depth + 1

  override def toString = base.toString + "[]"

  /** To fix an array type is to fix its elements */
  override def fix: ArrayType = copy(base = base.fix)

  /** Replaces all occurrences of an inference variable in its element type with a new type
    * @param oldType
    *   the type to replace
    * @param newType
    *   the type to replace it with
    * @return
    *   the resulting array type
    */
  override def replace(oldType: InferenceVariable, newType: Type): ArrayType =
    copy(base = base.replace(oldType, newType))

  /** An array type is unknown if its base is unknown */
  override def isSomehowUnknown = base.isSomehowUnknown

  /** Combines all occurrences of a temporary type in its element type with a new type
    * @param oldType
    *   the type to combine
    * @param newType
    *   the type to combine it with
    * @return
    *   the resulting array type
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ArrayType =
    copy(base = base.combineTemporaryType(oldType, newType))

  /** Returns the type after making its element type raw
    * @return
    *   the resulting raw type
    */
  override def raw: ArrayType = copy(base = base.raw)

  /** Reorders the type parameters in this type using a scheme
    *
    * @param scheme
    *   the scheme to reorder type parameters with
    * @return
    *   the resulting type
    */
  override def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]) =
    copy(base = base.reorderTypeParameters(scheme))

  /** Performs a substitution on this type
    * @param function
    *   the substitution function
    * @return
    *   the resulting type
    */
  override def substitute(function: Substitution): ArrayType =
    copy(base = base.substitute(function))

  /** Performs an expansion on this type */
  override def expansion: (ArrayType, Substitution) =
    val (a, b) = base.expansion
    (ArrayType(a), b)

private type CaptureOps = TypeOps[
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

private type BottomTypeOps = SimpleTypeOps[Bottom.type]

/** The bottom type, usually a null */
case object Bottom extends Type, BottomTypeOps:
  /** `⊥` */
  override val identifier = "⊥"

  /** ⊥ has no arguments */
  override val numArgs = 0

  /** There is no ⊥ static type */
  override val static: Boolean = false

  /** The upward projection of ⊥ is itself */
  override val upwardProjection: Bottom.type = this

  /** The downward projection of ⊥ is itself */
  override val downwardProjection: Bottom.type = this

  /** ⊥ has no arguments */
  override val args = Vector()

  /** There is no ⊥ static type */
  override def toStaticType: Bottom.type = this

  /** Fixing ⊥ gives itself */
  override def fix: Bottom.type = this

  /** Capture conversion on ⊥ just gives itself */
  override def captured: Bottom.type = this

  /** A wildcard-capture on ⊥ just produces itself */
  override def wildcardCaptured: Bottom.type = this

  /** The breadth of ⊥ is 0 */
  override def breadth = 0

  /** The depth of ⊥ is 0 */
  override def depth = 0

  /** A replacement on ⊥ is itself
    * @param oldType
    *   the old type to replace
    * @param newType
    *   the type to replace it with
    * @return
    *   itself
    */
  override def replace(oldType: InferenceVariable, newType: Type): Bottom.type = this

  /** Reordering the type parameters of ⊥ just gives itself
    * @param scheme
    *   the scheme to reorder type parameters with
    * @return
    *   itself
    */
  override def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Bottom.type =
    this

  /** ⊥ is not an unknown type */
  override def isSomehowUnknown = false

  /** The raw type of ⊥ is itself */
  override def raw: Bottom.type = this

  /** An expansion on ⊥ just produces itself and the empty substitution function */
  override def expansion: (Bottom.type, Substitution) = (this, Map())

  /** Performing a substitution on ⊥ just produces itself
    * @param function
    *   the substitution function
    * @return
    *   itself
    */
  override def substitute(function: Substitution): Bottom.type = this

  /** Combining temporary types on ⊥ just produces itself
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
  ): Bottom.type =
    this

private type WildcardTypeOps = TypeOps[
  Wildcard.type,
  Wildcard.type,
  Wildcard.type,
  Capture,
  ClassOrInterfaceType,
  Bottom.type,
  Wildcard.type,
  Wildcard.type,
  Wildcard.type,
  Wildcard.type,
  Wildcard.type
]

/** the `?` type */
case object Wildcard extends Type, WildcardTypeOps:
  /** The upward projection of `?` is `java.lang.Object` */
  override val upwardProjection: ClassOrInterfaceType = OBJECT

  /** The downward projection of `?` is ⊥ */
  override val downwardProjection: Bottom.type = Bottom

  /** `?` */
  override val identifier = "?"

  /** `?` has no type arguments */
  override val numArgs = 0

  /** There is no `?` static type */
  override val static = false

  /** `?` has no type arguments */
  override val args = Vector()

  /** There is no `?` static type */
  override def toStaticType: Wildcard.type = this

  /** Performing capture conversion on `?` gives itself */
  override def captured: Wildcard.type = this

  /** Performing a wildcard-capture on `?` gives a new open type variable ⊥ <: X <:
    * `java.lang.Object`
    */
  override def wildcardCaptured: Capture = InferenceVariableFactory.createCapture(Bottom, OBJECT)

  /** The breadth of `?` is 0 */
  override def breadth = 0

  /** The depth of `?` is 0 */
  override def depth = 0

  /** Performing a replacement on `?` produces itself
    * @param oldType
    *   the inference variable to replace
    * @param newType
    *   the type to replace it with
    * @return
    *   itself
    */
  override def replace(oldType: InferenceVariable, newType: Type): Wildcard.type = this

  /** Reordering the type parameters of `?` simply produces itself
    * @param scheme
    *   the scheme to reorder type parameters
    * @return
    *   itself
    */
  override def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Wildcard.type =
    this

  /** `?` is not unknown */
  override def isSomehowUnknown = false

  /** The raw type of `?` is itself */
  override def raw: Wildcard.type = this

  /** Performing a substitution on `?` produces itself
    * @param function
    *   the substitution function
    * @return
    *   itself
    */
  override def substitute(function: Substitution): Wildcard.type = this

  /** Performing an expansion on `?` produces itself and the empty substitution */
  override def expansion: (Wildcard.type, Substitution) = (this, Map())

  /** Combining temporary types in `?` produces itself
    * @param oldType
    *   the temporary type to combine
    * @param newType
    *   the type to combine it with
    * @return
    *   itself
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): Wildcard.type = this

  /** Fixing `?` produces itself */
  override def fix: Wildcard.type = this

  override def toString = identifier

private type ExtendsWildcardTypeOps = TypeOps[
  ExtendsWildcardType,
  ExtendsWildcardType,
  ExtendsWildcardType,
  Capture,
  Type,
  Bottom.type,
  ExtendsWildcardType,
  ExtendsWildcardType,
  ExtendsWildcardType,
  ExtendsWildcardType,
  ExtendsWildcardType,
]

/** the `? extends Foo` type
  * @param upper
  *   the upper bound of this type, i.e. if this type represents `? extends A<B>` then `upper =
  *   A<B>`
  */
final case class ExtendsWildcardType(
    upper: Type
) extends Type,
      ExtendsWildcardTypeOps:
  /** `? extends <upper>` */
  override val identifier = s"? extends $upper"

  /** `? extends T` has no type arguments */
  override val numArgs = 0

  /** The upward projection of `? extends T` is the upward projection of `T` */
  override val upwardProjection = upper.upwardProjection

  /** The downward projection of `? extends T` is ⊥ */
  override val downwardProjection: Bottom.type = Bottom

  /** There is no static `? extends T` type */
  override val static: Boolean = false

  /** `? extends T` has no type arguments */
  override val args = Vector()

  /** There is no static `? extends T` type */
  override def toStaticType: ExtendsWildcardType = this

  /** Fixing this type simply fixes the upper bound */
  override def fix: ExtendsWildcardType = copy(upper = upper.fix)

  /** Performing capture conversion on `? extends T` gives itself */
  override def captured: ExtendsWildcardType = this

  /** Performing a wildcard-capture on `? extends T` gives a new open type variable `⊥ <: X <:
    * upper`
    */
  override def wildcardCaptured: Capture = InferenceVariableFactory.createCapture(Bottom, upper)

  /** The breadth of a `? extends T` is the breadth of `T` */
  override def breadth = upper.breadth

  /** The depth of `? extends T` is the depth of `T` */
  override def depth = upper.depth

  /** Replaces all occurrences of an inference variable in its upper bound with some other type
    * @param oldType
    *   the type to replace
    * @param newType
    *   the type to replace it with
    * @return
    *   the resulting type
    */
  override def replace(oldType: InferenceVariable, newType: Type): ExtendsWildcardType =
    copy(upper = upper.replace(oldType, newType))

  /** Reorders the type parameters given a scheme
    * @param scheme
    *   the scheme to reorder type parameters
    * @return
    *   the resulting type
    */
  override def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): ExtendsWildcardType =
    copy(upper = upper.reorderTypeParameters(scheme))

  /** This type is unknown if the upper bound is unknown */
  override def isSomehowUnknown = upper.isSomehowUnknown

  /** Performs a substitution on this type
    * @param function
    *   the substitution function
    * @return
    *   the resulting type
    */
  override def substitute(function: Substitution): ExtendsWildcardType =
    copy(upper = upper.substitute(function))

  /** Performs an expansion on this type */
  override def expansion: (ExtendsWildcardType, Substitution) =
    val (a, b) = upper.expansion
    (ExtendsWildcardType(a), b)

  /** Combines all occurrences of an old type in this type with a new type
    * @param oldType
    *   the old type to combine
    * @param newType
    *   the new type after combining
    * @return
    *   the resulting type
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ExtendsWildcardType = copy(upper = upper.combineTemporaryType(oldType, newType))

  /** Returns the type after making the extended type raw
    * @return
    *   the resulting type
    */
  override def raw: ExtendsWildcardType = copy(upper = upper.raw)

  override def toString = identifier

private type SuperWildcardTypeOps = TypeOps[
  SuperWildcardType,
  SuperWildcardType,
  SuperWildcardType,
  Capture,
  ClassOrInterfaceType,
  Type,
  SuperWildcardType,
  SuperWildcardType,
  SuperWildcardType,
  SuperWildcardType,
  SuperWildcardType,
]

/** the `? super Foo` type */
final case class SuperWildcardType(
    lower: Type
) extends Type,
      SuperWildcardTypeOps:
  /** `? super <lower>` */
  override val identifier = s"? super ${lower}"

  /** `? super T` has no type arguments */
  override val numArgs = 0

  /** The upward projection of `? super T` is `java.lang.Object` */
  override val upwardProjection: ClassOrInterfaceType = OBJECT

  /** The downward projection of `? super T` is the downward projection of `T` */
  override val downwardProjection = lower.downwardProjection

  /** There is no `? super T` static type */
  override val static: Boolean = false

  /** `? super T` has no type arguments */
  override val args = Vector()

  /** There is no `? super T` static type */
  override def toStaticType: SuperWildcardType = this

  /** Fixes the lower bound of this type */
  override def fix: SuperWildcardType = copy(lower = lower.fix)

  /** Performing capture conversion on `? super T` produces itself */
  override def captured: SuperWildcardType = this

  /** Performing a wildcard-capture on `? super T` produces a new open type variable `T <: X <:
    * java.lang.Object`
    */
  override def wildcardCaptured: Capture = InferenceVariableFactory.createCapture(lower, OBJECT)

  /** The breadth of `? super T` is the breadth of `T` */
  override def breadth = lower.breadth

  /** The depth of `? super T` is the depth of `T` */
  override def depth = lower.depth

  /** Replace any occurrences of an old type in this type with a new type
    * @param oldType
    *   the old type to be replaced
    * @param newType
    *   the new type after replacement
    * @return
    *   the resulting type
    */
  override def replace(oldType: InferenceVariable, newType: Type): SuperWildcardType =
    copy(lower = lower.replace(oldType, newType))

  /** Reorders type parameters by replacing all type parameters given a scheme
    * @param scheme
    *   the scheme to replace all type parameters
    * @return
    *   the resulting type after re-ordering
    */
  override def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): SuperWildcardType =
    copy(lower = lower.reorderTypeParameters(scheme))

  /** `? super T` is unknown if `T` is unknown */
  override def isSomehowUnknown = lower.isSomehowUnknown

  /** Combines all occurrences of an old type in this type with a new type
    * @param oldType
    *   the old type to combine
    * @param newType
    *   the new type after combining
    * @return
    *   the resulting type
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): SuperWildcardType = copy(lower = lower.combineTemporaryType(oldType, newType))

  /** Returns the type after making the extending type raw
    * @return
    *   the resulting type
    */
  override def raw: SuperWildcardType = copy(lower.raw)

  /** Performs a substitution on this type
    * @param function
    *   the function that maps type parameters to types
    * @return
    *   the type after substitution
    */
  override def substitute(function: Substitution): SuperWildcardType =
    copy(lower = lower.substitute(function))

  /** Performs an expansion on this type
    * @return
    *   the type after expansion, and its corresponding substitution; such that
    *   A.substitute(Substitution) = this
    */
  override def expansion: (SuperWildcardType, Substitution) =
    val (a, b) = lower.expansion
    (SuperWildcardType(a), b)

  override def toString = identifier

private type TypeParameterOps[A <: TTypeParameter] =
  TypeOps[A, A, A, A, A, A, Type, A, A, A, TTypeParameter]

/** A type parameter of some sort */
sealed trait TTypeParameter extends Type, TypeParameterOps[TTypeParameter]:
  /** There is no static type parameter */
  override final val static: Boolean = false

  /** The upward projection of a type parameter is itself */
  override final val upwardProjection: this.type = this

  /** The downward projection of a type parameter is itself */
  override final val downwardProjection: this.type = this

  /** Type parameters have no type arguments */
  override final val args = Vector()

  /** Type parameters have no type arguments */
  override final val numArgs = 0

  /** There is no static type parameter */
  override final def toStaticType: this.type = this

  /** Performing capture conversion on a type parameter produces itself */
  override final def captured: this.type = this

  /** Performing a wildcard-capture on a type parameter produces itself */
  override final def wildcardCaptured: this.type = this

  /** The breadth of a type parameter is 0 */
  override final def breadth = 0

  /** The depth of a type parameter is 0 */
  override final def depth = 0

  /** Performing a replacement on a type parameter produces itself
    * @param oldType
    *   the type to replace
    * @param newType
    *   the type to replace it with
    * @return
    *   itself
    */
  override final def replace(oldType: InferenceVariable, newType: Type): this.type = this

  /** Reorders type parameters by replacing all type parameters given a scheme
    * @param scheme
    *   the scheme to replace all type parameters
    * @return
    *   the resulting object after re-ordering
    */
  override final def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): TTypeParameter =
    if scheme contains this then scheme(this) else this

  /** The type who contains this type parameter */
  def containingTypeIdentifier: String

  /** Type parameters are not unknown */
  override final def isSomehowUnknown = false

  /** The raw type of a type parameter is itself */
  override final def raw: this.type = this

  /** The expansion of a type parameter is itself with the empty substitution */
  override final def expansion: (this.type, Substitution) = (this, Map())

  /** Performs a substitution on this type parameter
    * @param function
    *   the substitution function
    * @return
    *   the resulting type
    */
  override final def substitute(function: Substitution): Type =
    if function contains this then function(this) else this

/** A type parameter by index (something like de Bruijn indexing)
  * @param source
  *   the type containing this parameter
  * @param index
  *   the index of this parameter
  */
final case class TypeParameterIndex(
    source: String,
    index: Int
) extends TTypeParameter,
      TypeParameterOps[TypeParameterIndex]:
  /** `<source>#<T + index>` */
  override val identifier = s"$source#${(84 + index).toChar.toString}"

  /** Fixes this type parameter */
  override def fix: TypeParameterIndex =
    copy(source = SomeClassOrInterfaceType(source).fix.identifier)

  /** The source of the type parameter */
  override def containingTypeIdentifier = source

  /** Combines all occurrences of an old type in the source with a new type
    * @param oldType
    *   the old type to combine
    * @param newType
    *   the new type after combining
    * @return
    *   the resulting type parameter
    */
  override def combineTemporaryType(
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
  */
final case class TypeParameterName(
    sourceType: String,
    source: String,
    qualifiedName: String
) extends TTypeParameter:
  /** `Foo.bar(T,int)#T` */
  override val identifier = s"$source#$qualifiedName"

  /** Fixes this type parameter */
  override def fix: TypeParameterName = this

  /** The source of the type parameter */
  override def containingTypeIdentifier = sourceType

  /** Combines all occurrences of an old type in the source with a new type
    * @param oldType
    *   the old type to combine
    * @param newType
    *   the new type after combining
    * @return
    *   the resulting type parameter
    */
  override def combineTemporaryType(
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
  /** Whether this inference variable has been capture converted */
  val toBeCaptured: Boolean

  /** Whether this inference variable has been wildcard-captured */
  val toBeWildcardCaptured: Boolean

  /** the ID of this type */
  val id: Int

  /** The list of substitutions on this type */
  val substitutions: SubstitutionList

  /** An inference variable is always an unknown type */
  override final def isSomehowUnknown = true

  /** The raw type of an inference variable is simply itself
    * @return
    *   itself
    */
  override final def raw: this.type = this

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

/** Some random placeholder type
  * @param id
  *   the ID of this type
  */
final case class PlaceholderType(
    id: Int,
    toBeCaptured: Boolean = false,
    toBeWildcardCaptured: Boolean = false
) extends InferenceVariable:
  val static: Boolean                   = false
  def toStaticType: PlaceholderType     = this
  def fix: Nothing                      = throw IllegalOperationException(s"$this cannot be fixed!")
  val substitutions                     = Nil
  val numArgs                           = 0
  val identifier                        = s"δ$id"
  val upwardProjection: PlaceholderType = this
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
