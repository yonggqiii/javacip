package configuration.types
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
  (PRIMITIVE_BYTE, PRIMITIVE_BYTE),    // reflexive
  (PRIMITIVE_SHORT, PRIMITIVE_SHORT),  // reflexive
  (PRIMITIVE_CHAR, PRIMITIVE_CHAR),    // reflexive
  (PRIMITIVE_INT, PRIMITIVE_INT),      // reflexive
  (PRIMITIVE_LONG, PRIMITIVE_LONG),    // reflexive
  (PRIMITIVE_FLOAT, PRIMITIVE_FLOAT),  // reflexive
  (PRIMITIVE_DOUBLE, PRIMITIVE_DOUBLE) // reflexive
)

extension (t: (Type, Type))
  def in(set: Set[(Type, Type)]): DisjunctiveAssertion =
    DisjunctiveAssertion(set.toVector map { case (l, r) => (t._1 ~=~ l) && (t._2 ~=~ r) })

// Type aliases
/** Represents a list of subtitutions--maps from type parameters to types */
type SubstitutionList = List[Substitution]
type Substitution = Map[TTypeParameter, Type]

/** Represents some type in the program */
sealed trait Type:
  /** Asserts that some other type is compatible with this type
    * @param that
    *   the other type that is compatible with this type
    * @return
    *   the resulting CompatibilityAssertion
    */
  def ~:=(that: Type) = CompatibilityAssertion(this, that)

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

  /** The identifier of the type
    */
  val identifier: String

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

  /** Asserts that this type is a reference type
    * @return
    *   the resulting IsReferenceAssertion
    */
  def isReference = IsReferenceAssertion(this)

  /** Determines if this type contains any inference variables, alphas or placeholder types */
  def isSomehowUnknown: Boolean

  /** Replace any occurrences of an old type in this type and all its substitutions/members with a
    * new type
    * @param oldType
    *   the old type to be replaced
    * @param newType
    *   the new type after replacement
    * @return
    *   the resulting type
    */
  def replace(oldType: InferenceVariable, newType: Type): Type

  /** Returns the equivalent type after removing all type arguments, a.k.a. the raw type.
    *
    * @return
    *   the raw type of this type
    */
  def raw: Type

  /** Checks if this type strictly occurs in, but is not equal to, another type
    * @param that
    *   the other type
    * @return
    *   true if this strictly occurs in that, false otherwise
    */
  def ⊂(that: Type): Boolean = that match
    case ArrayType(x)                => this ⊆ x
    case x: PrimitiveType            => false
    case Bottom | Wildcard           => false
    case x: ExtendsWildcardType      => this ⊆ x.upper
    case x: SuperWildcardType        => this ⊆ x.lower
    case x: TTypeParameter           => false
    case x: InferenceVariable        => false
    case x: ClassOrInterfaceType => ??? // not possible

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
    // val subsFn: Map[TTypeParameter, Type] => String = subs => subs.mkString(", ")
    // val subsstring = substitutions.map(subs => s"[${subsFn(subs)}]").mkString
    s"$start$argumentList"

  /** Reorders type parameters by replaces all type parameters given a scheme
    * @param scheme
    *   the scheme to replace all type parameters
    * @return
    *   the resulting type after re-ordering
    */
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Type

  /** Asserts that this type is a member of a set of types
    * @param set
    *   the set of types
    * @return
    *   the corresponding disjunction
    */
  def in(set: Set[Type]): DisjunctiveAssertion =
    DisjunctiveAssertion(set.toVector.map(this ~=~ _))

  /** Add a substitution function to this type
    * @param function the function that maps type parameters to types
    * @return the type after substitution
    */
  def substitute(function: Substitution): Type

  def expansion: (Type, Substitution)

/** The primitive types */
sealed trait PrimitiveType extends Type:
  val boxed: ClassOrInterfaceType
  val numArgs                                                                           = 0
  val substitutions                                                                     = Nil
  def addSubstitutionLists(substitutions: SubstitutionList): PrimitiveType              = this
  val upwardProjection: PrimitiveType                                                   = this
  val downwardProjection: PrimitiveType                                                 = this
  def replace(oldType: InferenceVariable, newType: Type): PrimitiveType                 = this
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): PrimitiveType = this
  def substituted                                                                       = this
  val args                                                                              = Vector()
  def isSomehowUnknown                                                                  = false
  def raw: PrimitiveType                                                                = this
  /** the types which this primitive type can widen to */
  def widened: Set[PrimitiveType]
  /** the types which can be assigned into this type */
  def isAssignableBy: Set[Type]
  /** Substitutions on a primitive type do not change it */
  def substitute(function: Substitution): PrimitiveType = this
  /** The expansion of any primitive type is just itself and an empty function */  
  def expansion: (PrimitiveType, Substitution) = (this, Map())

/** An array
  * @param base
  *   the type of the elements of this array
  */
final case class ArrayType(
    base: Type
) extends Type:
  override def toString =
    base.toString + "[]"
  val identifier                    = base.identifier + "[]"
  val numArgs                       = 0
  val upwardProjection: ArrayType   = this
  val downwardProjection: ArrayType = this
  def replace(oldType: InferenceVariable, newType: Type): ArrayType =
    copy(base = base.replace(oldType, newType))
  val args             = Vector()
  def isSomehowUnknown = base.isSomehowUnknown

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

final case class ClassOrInterfaceType(
  identifier: String,
  args: Vector[Type]
) extends Type:
  val numArgs = args.size
  override def toString =
    val a = if args.size == 0 then "" else "<" + args.mkString(", ") + ">"
    s"$identifier$a"
  def isSomehowUnknown = args.exists(_.isSomehowUnknown)
  def raw: ClassOrInterfaceType = copy(args = Vector())
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): ClassOrInterfaceType =
    copy(args = args.map(_.reorderTypeParameters(scheme)))
  val upwardProjection: ClassOrInterfaceType = this
  val downwardProjection: ClassOrInterfaceType = this
  def replace(oldType: InferenceVariable, newType: Type): ClassOrInterfaceType = copy(args = args.map(_.replace(oldType, newType)))
  def substitute(function: Substitution): ClassOrInterfaceType = copy(args = args.map(_.substitute(function)))
  def expansion: (ClassOrInterfaceType, Substitution) = 
    val base = copy(args = (0 until numArgs).map(TypeParameterIndex(identifier, _)).toVector)
    val subs = (0 until numArgs).map[(TTypeParameter, Type)](i => (TypeParameterIndex(identifier, i) -> args(i))).toMap
    (base, subs)

object ClassOrInterfaceType:
  def apply(identifier: String): ClassOrInterfaceType = new ClassOrInterfaceType(identifier, Vector())
// /** Just your regular reference type
//   * @param identifier
//   *   the name of the type
//   * @param numArgs
//   *   the number of arguments of this type
//   * @param substitutions
//   *   the list of substitutions of this type
//   */
// final case class NormalType(
//     identifier: String,
//     numArgs: Int,
//     substitutions: SubstitutionList = Nil
// ) extends ReferenceType:
//   val upwardProjection: NormalType   = this
//   val downwardProjection: NormalType = this
//   def addSubstitutionLists(substitutions: SubstitutionList): NormalType =
//     if numArgs > 0 then
//       copy(substitutions = (this.substitutions ::: substitutions).filter(!_.isEmpty))
//     else this
//   def replace(oldType: InferenceVariable, newType: Type): NormalType =
//     copy(substitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType))))
//   def substituted = SubstitutedReferenceType(identifier, args.map(_.substituted))
//   val args = (0 until numArgs)
//     .map(TypeParameterIndex(identifier, _))
//     .map(_.addSubstitutionLists(substitutions))
//     .toVector
//   def isSomehowUnknown = substituted.isSomehowUnknown
//   def raw: NormalType  = copy(numArgs = 0, substitutions = Nil)
//   def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): NormalType =
//     copy(substitutions =
//       substitutions.map(m =>
//         m.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
//       )
//     )

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
  val identifier = "int"
  val boxed      = BOXED_INT
  def widened    = Set(PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_SHORT, PRIMITIVE_CHAR, PRIMITIVE_BYTE, PRIMITIVE_INT).flatMap(x =>
      Set(x, x.boxed)
    )

/** the `byte` type */
case object PRIMITIVE_BYTE extends PrimitiveType:
  val identifier = "byte"
  val boxed      = BOXED_BYTE
  def widened = Set(
    PRIMITIVE_CHAR,
    PRIMITIVE_SHORT,
    PRIMITIVE_INT,
    PRIMITIVE_LONG,
    PRIMITIVE_FLOAT,
    PRIMITIVE_DOUBLE
  )
  def isAssignableBy =
    Set(PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

/** the `short` type */
case object PRIMITIVE_SHORT extends PrimitiveType:
  val identifier = "short"
  val boxed      = BOXED_SHORT
  def widened    = Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_SHORT, PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

/** the `char` type */
case object PRIMITIVE_CHAR extends PrimitiveType:
  val identifier = "char"
  val boxed      = BOXED_CHAR
  def widened    = Set(PRIMITIVE_INT, PRIMITIVE_LONG, PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_CHAR, PRIMITIVE_BYTE).flatMap(x => Set(x, x.boxed))

/** the `long` type */
case object PRIMITIVE_LONG extends PrimitiveType:
  val identifier = "long"
  val boxed      = BOXED_LONG
  def widened    = Set(PRIMITIVE_FLOAT, PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(PRIMITIVE_LONG, PRIMITIVE_SHORT, PRIMITIVE_INT, PRIMITIVE_CHAR, PRIMITIVE_BYTE).flatMap(x =>
      Set(x, x.boxed)
    )

/** the `float` type */
case object PRIMITIVE_FLOAT extends PrimitiveType:
  val identifier = "float"
  val boxed      = BOXED_FLOAT
  def widened    = Set(PRIMITIVE_DOUBLE)
  def isAssignableBy =
    Set(
      PRIMITIVE_FLOAT,
      PRIMITIVE_LONG,
      PRIMITIVE_SHORT,
      PRIMITIVE_INT,
      PRIMITIVE_CHAR,
      PRIMITIVE_BYTE
    ).flatMap(x => Set(x, x.boxed))

/** the `double` type */
case object PRIMITIVE_DOUBLE extends PrimitiveType:
  val identifier = "double"
  val boxed      = BOXED_DOUBLE
  def widened    = Set()
  override def isAssignableBy =
    Set(
      PRIMITIVE_DOUBLE,
      PRIMITIVE_FLOAT,
      PRIMITIVE_LONG,
      PRIMITIVE_SHORT,
      PRIMITIVE_INT,
      PRIMITIVE_CHAR,
      PRIMITIVE_BYTE
    ).flatMap(x => Set(x, x.boxed))

/** the `boolean` type */
case object PRIMITIVE_BOOLEAN extends PrimitiveType:
  val identifier = "boolean"
  val boxed      = BOXED_BOOLEAN
  def widened    = Set()
  def isAssignableBy =
    Set(PRIMITIVE_BOOLEAN).flatMap(x => Set(x, x.boxed))

/** the `void` type */
case object PRIMITIVE_VOID extends PrimitiveType:
  val identifier = "void"
  val boxed      = BOXED_VOID
  def widened    = Set()
  def isAssignableBy =
    Set(PRIMITIVE_VOID).flatMap(x => Set(x, x.boxed))

/** The bottom type, usually a null */
case object Bottom extends Type:
  val upwardProjection: Bottom.type                                                   = this
  val downwardProjection: Bottom.type                                                 = this
  val identifier                                                                      = "⊥"
  val numArgs                                                                         = 0
  def replace(oldType: InferenceVariable, newType: Type): Bottom.type                 = this
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Bottom.type = this
  val args                                                                            = Vector()
  def isSomehowUnknown                                                                = false
  def raw: Bottom.type                                                                = this
  def expansion: (Bottom.type, Substitution) = (this, Map())
  def substitute(function: Substitution): Bottom.type = this

/** the `?` type */
case object Wildcard extends Type:
  val upwardProjection: ClassOrInterfaceType                                                      = OBJECT
  val downwardProjection: Bottom.type                                                   = Bottom
  val identifier                                                                        = "?"
  val numArgs                                                                           = 0
  def replace(oldType: InferenceVariable, newType: Type): Wildcard.type                 = this
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Wildcard.type = this
  val args                                                                              = Vector()
  def isSomehowUnknown                                                                  = false
  override def toString                                                                 = identifier
  def raw: Wildcard.type                                                                = this
  def substitute(function: Substitution): Wildcard.type = this
  def expansion:(Wildcard.type, Substitution) = (this, Map())

/** the `? extends Foo` type */
final case class ExtendsWildcardType(
    upper: Type
) extends Type:
  val identifier                      = ""
  val numArgs                         = 0
  val upwardProjection                = upper.upwardProjection
  val downwardProjection: Bottom.type = Bottom
  def replace(oldType: InferenceVariable, newType: Type): ExtendsWildcardType =
    copy(upper = upper.replace(oldType, newType))
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): ExtendsWildcardType =
    copy(upper = upper.reorderTypeParameters(scheme))
  val args              = Vector()
  def isSomehowUnknown  = upper.isSomehowUnknown
  override def toString = s"? extends $upper"
  def substitute(function: Substitution): ExtendsWildcardType = copy(upper.substitute(function))
  def expansion: (ExtendsWildcardType, Substitution) = (this, Map())

  /** Returns the type after making the extended type raw
    * @return
    *   the resulting type
    */
  def raw: ExtendsWildcardType = copy(upper.raw)

/** the `? super Foo` type */
final case class SuperWildcardType(
    lower: Type
) extends Type:
  val identifier                      = ""
  val numArgs                         = 0
  val substitutions: SubstitutionList = Nil
  val upwardProjection: ClassOrInterfaceType    = OBJECT
  val downwardProjection =
    lower.downwardProjection
  def replace(oldType: InferenceVariable, newType: Type): SuperWildcardType =
    copy(lower = lower.replace(oldType, newType))
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): SuperWildcardType =
    copy(lower = lower.reorderTypeParameters(scheme))
  val args              = Vector()
  def isSomehowUnknown  = lower.isSomehowUnknown
  override def toString = s"? super ${lower}"
  /** Returns the type after making the extending type raw
    * @return
    *   the resulting type
    */
  def raw: SuperWildcardType = copy(lower.raw)
  def substitute(function: Substitution): SuperWildcardType = copy(lower = lower.substitute(function))
  def expansion: (SuperWildcardType, Substitution) = (this, Map())

/** A type parameter of some sort */
sealed trait TTypeParameter extends Type:
  val upwardProjection: TTypeParameter
  val downwardProjection: TTypeParameter
  def replace(oldType: InferenceVariable, newType: Type): TTypeParameter
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): TTypeParameter =
    if scheme contains this then scheme(this) else this
  /** The type who contains this type parameter */
  def containingTypeIdentifier: String
  def isSomehowUnknown = false
  def raw: this.type = this
  def expansion: (this.type, Substitution) = (this, Map())
  def substitute(function: Substitution): Type = if function contains this then function(this) else this

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
    index: Int,
) extends TTypeParameter:
  def containingTypeIdentifier               = source
  val numArgs                                = 0
  val identifier                             = s"$source#${(84 + index).toChar.toString}"
  val upwardProjection: TypeParameterIndex   = this
  val downwardProjection: TypeParameterIndex = this
  def replace(oldType: InferenceVariable, newType: Type): TypeParameterIndex = this
  val args = Vector()


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
    qualifiedName: String,
) extends TTypeParameter:
  def containingTypeIdentifier = sourceType
  val identifier               = s"$source#$qualifiedName"
  val numArgs                  = 0
  val upwardProjection         = this
  val downwardProjection       = this
  def replace(oldType: InferenceVariable, newType: Type): TypeParameterName = this
    // copy(substitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType))))
  val args = Vector()

/** Some placeholder for inference */
sealed trait InferenceVariable extends Type:
  /** the ID of this type */
  val id: Int
  def isSomehowUnknown = true

  /** The raw type of an inference variable is simply itself
    * @return
    *   itself
    */
  def raw: this.type = this
  val substitutions: SubstitutionList
  def expansion: (this.type, Substitution) = (this, Map())

final case class TemporaryType(
  id: Int,
  args: Vector[Type]
) extends Type:
  val numArgs = args.size
  override def toString =
    val a = if args.size == 0 then "" else "<" + args.mkString(", ") + ">"
    s"ξ$id$a"
  def isSomehowUnknown = args.exists(_.isSomehowUnknown)
  def raw: TemporaryType = copy(args = Vector())
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): TemporaryType =
    copy(args = args.map(_.reorderTypeParameters(scheme)))
  val upwardProjection: TemporaryType = this
  val downwardProjection: TemporaryType = this
  def replace(oldType: InferenceVariable, newType: Type): TemporaryType = copy(args = args.map(_.replace(oldType, newType)))
  def substitute(function: Substitution): TemporaryType = copy(args = args.map(_.substitute(function)))
  def expansion: (TemporaryType, Substitution) = 
    val base = copy(args = (0 until numArgs).map(TypeParameterIndex(identifier, _)).toVector)
    val subs = (0 until numArgs).map[(TTypeParameter, Type)](i => (TypeParameterIndex(identifier, i) -> args(i))).toMap
    (base, subs)

/** Some random placeholder type
  * @param id
  *   the ID of this type
  */
final case class PlaceholderType(id: Int) extends InferenceVariable:
  val substitutions                                                          = Nil
  val numArgs                                                                = 0
  val identifier                                                             = s"τ$id(placeholder)"
  val upwardProjection: PlaceholderType                                      = this
  val downwardProjection: PlaceholderType                                    = this
  def replace(oldType: InferenceVariable, newType: Type): Type =
    if id != oldType.id then this
    else newType
  val args                                                                                = Vector()
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): PlaceholderType = this
  def substitute(function: Substitution): PlaceholderType = this

/** A disjunctive type is a type who could be one of several possible other types
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
final case class DisjunctiveType(
    id: Int,
    source: Either[String, Type],
    substitutions: SubstitutionList = Nil,
    canBeSubsequentlyBounded: Boolean = false,
    parameterChoices: Set[TTypeParameter] = Set(),
    _choices: Vector[Type] = Vector()
) extends InferenceVariable:
  val numArgs = 0
  val identifier =
    val str = _choices.mkString(", ")
    s"τ$id=V{$str}"
  override def toString() =
    val subsFn: Map[TTypeParameter, Type] => String = subs => subs.mkString(", ")
    val subsstring = substitutions.map(subs => s"{${subsFn(subs)}}").mkString
    s"$identifier$subsstring"
  val upwardProjection: DisjunctiveType   = this
  val downwardProjection: DisjunctiveType = this
  def substitute(function: Substitution): DisjunctiveType = 
    copy(substitutions = (this.substitutions ::: (function :: Nil)).filter(!_.isEmpty))

  /** The choices this type can be
    * @return
    *   the choices this type can be
    */
  def choices = _choices.map(x => substitutions.foldLeft(x)((t, s) => t.substitute(s)))
  def replace(oldType: InferenceVariable, newType: Type): Type =
    val newChoices       = _choices.map(_.replace(oldType, newType))
    val newSource        = source.map(_.replace(oldType, newType))
    val newSubstitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    if id != oldType.id then
      copy(source = newSource, substitutions = newSubstitutions, _choices = newChoices)
    else substitutions.foldLeft(newType)((t, s) => t.substitute(s))
  val args        = Vector()
  override def ⊆(that: Type): Boolean = that match
    case x: DisjunctiveType => this.id == x.id
    case _                  => super.⊆(that)
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): DisjunctiveType =
    copy(
      source = source.map(_.reorderTypeParameters(scheme)),
      parameterChoices = parameterChoices.map(_.reorderTypeParameters(scheme)),
      _choices = _choices.map(_.reorderTypeParameters(scheme)),
      substitutions = substitutions.map(m =>
        m.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
      )
    )

final case class DisjunctiveTypeWithPrimitives(id: Int, substitutions: SubstitutionList = Nil, canBeBounded: Boolean = true, parameterChoices: Set[TTypeParameter] = Set(), choices: Set[Type] = Set[Type]() ++ PRIMITIVES) extends InferenceVariable:
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
    parameterChoices: Set[TTypeParameter] = Set()
) extends InferenceVariable:
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
  def addSubstitutionLists(substitutions: SubstitutionList) =
    Alpha(
      id,
      source,
      (this.substitutions ::: substitutions).filter(!_.isEmpty),
      canBeBounded,
      parameterChoices
    )
  def replace(oldType: InferenceVariable, newType: Type): Type =
    val newSource        = source.map(_.replace(oldType, newType))
    val newSubstitutions = substitutions.map(_.map((x, y) => x -> y.replace(oldType, newType)))
    if id != oldType.id then copy(source = newSource, substitutions = newSubstitutions)
    else substitutions.foldLeft(newType)((t, s) => t.substitute(s))
  def substituted = this
  val args        = Vector()
  def substitute(function: Substitution): Alpha = copy(substitutions = (substitutions ::: (function :: Nil)).filter(!_.isEmpty))

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
  def concretizeToReference(identifier: String, numArgs: Int): ClassOrInterfaceType =
    ClassOrInterfaceType(
      identifier,
      (0 until numArgs)
        .map(i =>
            InferenceVariableFactory.createDisjunctiveType(
              source,
              Nil,
              canBeBounded,
              parameterChoices,
              canBeBounded
            )
        )
        .toVector
    )
  override def ⊆(that: Type): Boolean = that match
    case x: Alpha => this.id == x.id
    case _        => super.⊆(that)

// /** A reference type after substitution, i.e. it should have no further substitutions unless it
//   * contains type arguments who are inference variables
//   * @param identifier
//   *   the identifier of this type
//   * @param args
//   *   the type arguments
//   */
// final case class SubstitutedReferenceType(
//     identifier: String,
//     args: Vector[Type]
// ) extends ReferenceType:
//   val numArgs = args.size
//   val substitutions: SubstitutionList =
//     (0 until numArgs).map(i => (TypeParameterIndex(identifier, i) -> args(i))).toMap :: Nil
//   val upwardProjection   = this
//   val downwardProjection = this
//   def addSubstitutionLists(substitutions: SubstitutionList) =
//     NormalType(
//       identifier,
//       numArgs,
//       this.substitutions
//     ).addSubstitutionLists(substitutions)
//   def replace(oldType: InferenceVariable, newType: Type) =
//     copy(args = args.map(_.replace(oldType, newType)))
//   def substituted = this
//   override def toString =
//     val a = if args.size == 0 then "" else "<" + args.mkString(", ") + ">"
//     s"$identifier$a"
//   def isSomehowUnknown              = args.foldLeft(false)(_ || _.isSomehowUnknown)
//   def raw: SubstitutedReferenceType = copy(args = Vector())
//   def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): SubstitutedReferenceType =
//     copy(args = args.map(_.reorderTypeParameters(scheme)))

/** Object that contains helper methods to create inference variables
  */
object InferenceVariableFactory:
  private var id = 0

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
      canBeBounded: Boolean = false
  ) =
    id += 1
    val choices: Vector[Type] =
      if !canBeBounded then
        parameterChoices.toVector :+ createAlpha(
          source,
          Nil,
          canBeSubsequentlyBounded || canBeBounded,
          parameterChoices
        )
      else
        val alpha       = createAlpha(source, Nil, true, parameterChoices)
        val baseChoices = parameterChoices.toVector :+ alpha
        val supers      = baseChoices.map(SuperWildcardType(_))
        val extend      = baseChoices.map(ExtendsWildcardType(_))
        (baseChoices :+ Wildcard) ++ supers ++ extend
    DisjunctiveType(id, source, substitutions, canBeSubsequentlyBounded, parameterChoices, choices)

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
      parameterChoices: Set[TTypeParameter] = Set()
  ) =
    id += 1
    Alpha(id, source, substitutions, canBeBounded, parameterChoices)

  /** Creates a generic placeholder type
    * @return
    *   the resulting placeholder type
    */
  def createPlaceholderType(): PlaceholderType =
    id += 1
    PlaceholderType(id)
