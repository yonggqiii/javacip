package configuration.assertions

import configuration.declaration.*
import configuration.types.*
import configuration.basetraits.*

/** disjunctive assertions have the lowest priority since they cause branching
  */
given assertionOrdering: Ordering[Assertion] with
  def compare(x: Assertion, y: Assertion): Int =
    val disjunctiveTester: Assertion => Boolean = asst =>
      asst match
        case SubtypeAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] || y.isInstanceOf[DisjunctiveType]
        case EquivalenceAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] && y.isInstanceOf[DisjunctiveType]
        case ContainmentAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] || y.isInstanceOf[DisjunctiveType]
        case x: ConjunctiveAssertion    => false
        case y: DisjunctiveAssertion    => true
        case IsClassAssertion(x)        => x.isInstanceOf[DisjunctiveType]
        case IsInterfaceAssertion(x)    => x.isInstanceOf[DisjunctiveType]
        case x: HasMethodAssertion      => false
        case y: HasConstructorAssertion => false
        case IsDeclaredAssertion(x)     => false
        case IsMissingAssertion(x)      => false
        case IsUnknownAssertion(x)      => false
        case IsPrimitiveAssertion(x)    => x.isInstanceOf[DisjunctiveType]
        case IsIntegralAssertion(x)     => x.isInstanceOf[DisjunctiveType]
        case IsNumericAssertion(x)      => x.isInstanceOf[DisjunctiveType]
        case CompatibilityAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] || y.isInstanceOf[DisjunctiveType]
        case WideningAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] || y.isInstanceOf[DisjunctiveType]
        case ImplementsMethodAssertion(t, m, _) => t.isInstanceOf[DisjunctiveType]
        case _: OverridesAssertion              => false
    val (xdisj, ydisj) =
      (disjunctiveTester(x), disjunctiveTester(y))
    if xdisj && !ydisj then -1
    else if !xdisj && ydisj then 1
    else 0

private trait AssertionOps[+A <: Assertion] extends Replaceable[A], Combineable[A]

/** An assertion in the algorithm */
sealed trait Assertion extends AssertionOps[Assertion]:
  /** Combine a temporary type with another class or interface type
    * @param oldType
    *   the old temporary type to combine
    * @param newType
    *   the other type to combine with
    * @return
    *   the resulting assertion
    */
  def combineTemporaryType(oldType: TemporaryType, newType: SomeClassOrInterfaceType): Assertion

  /** Replaces one type with another type
    * @param oldType
    *   the type to replace
    * @param newType
    *   the type after replacement
    * @return
    *   the resulting assertion
    */
  def replace(oldType: InferenceVariable, newType: Type): Assertion

  /** Asserts that this assertion and another assertion must both be true
    * @param that
    *   the other assertion
    * @return
    *   the resulting ConjunctiveAssertion
    */
  def &&(that: Assertion): ConjunctiveAssertion =
    ConjunctiveAssertion(Vector(this, that))

  /** Asserts that this assertion or another assertion must be true
    * @param that
    *   the other assertion
    * @return
    *   the resulting DisjunctiveAssertion
    */
  def ||(a: Assertion): DisjunctiveAssertion =
    DisjunctiveAssertion(Vector(this, a))

/** An assertion stating that one type must be a subtype of another
  * @param left
  *   the subtype
  * @param right
  *   the supertype
  */
case class SubtypeAssertion(left: Type, right: Type)
    extends Assertion,
      AssertionOps[SubtypeAssertion]:
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): SubtypeAssertion =
    SubtypeAssertion(
      left.combineTemporaryType(oldType, newType),
      right.combineTemporaryType(oldType, newType)
    )
  override def toString = s"${left} <: ${right}"
  def replace(oldType: InferenceVariable, newType: Type): SubtypeAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))

/** An assertion stating that one type must be compatible in an assignment/strict/loose invocation
  * context with another
  * @param target
  *   the target type that the source type must be compatible with
  * @param source
  *   the source type that is compatible with the target type
  */
case class CompatibilityAssertion(target: Type, source: Type)
    extends Assertion,
      AssertionOps[CompatibilityAssertion]:
  override def toString = s"${target} := ${source}"
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): CompatibilityAssertion =
    CompatibilityAssertion(
      target.combineTemporaryType(oldType, newType),
      source.combineTemporaryType(oldType, newType)
    )
  def replace(oldType: InferenceVariable, newType: Type): CompatibilityAssertion =
    copy(target = target.replace(oldType, newType), source = source.replace(oldType, newType))

/** An assertion stating that one type widens to another
  * @param left
  *   the left primitive type
  * @param right
  *   the right primitive type that the left can widen into
  */
case class WideningAssertion(left: Type, right: Type)
    extends Assertion,
      AssertionOps[WideningAssertion]:
  override def toString = s"${left} <<~= ${right}"
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): WideningAssertion =
    WideningAssertion(
      left.combineTemporaryType(oldType, newType),
      right.combineTemporaryType(oldType, newType)
    )
  def replace(oldType: InferenceVariable, newType: Type): WideningAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))

/** An assertion stating that two types are equivalent
  * @param left
  *   a type
  * @param right
  *   the other type
  */
case class EquivalenceAssertion(left: Type, right: Type)
    extends Assertion,
      AssertionOps[EquivalenceAssertion]:
  override def toString = s"${left} = ${right}"
  def replace(oldType: InferenceVariable, newType: Type): EquivalenceAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): EquivalenceAssertion =
    EquivalenceAssertion(
      left.combineTemporaryType(oldType, newType),
      right.combineTemporaryType(oldType, newType)
    )

/** An assertion stating that one type is contained by another type
  * @param left
  *   the type to be contained
  * @param right
  *   the type that contains
  */
case class ContainmentAssertion(left: Type, right: Type)
    extends Assertion,
      AssertionOps[ContainmentAssertion]:
  override def toString = s"${left} <= ${right}"
  def replace(oldType: InferenceVariable, newType: Type): ContainmentAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ContainmentAssertion =
    ContainmentAssertion(
      left.combineTemporaryType(oldType, newType),
      right.combineTemporaryType(oldType, newType)
    )

/** An assertion stating that at least one of the assertions in the disjunction must be true
  * @param assertions
  *   the assertions whom at least one must be true
  */
case class DisjunctiveAssertion(assertions: Vector[Assertion])
    extends Assertion,
      AssertionOps[DisjunctiveAssertion]:
  def replace(oldType: InferenceVariable, newType: Type): DisjunctiveAssertion =
    copy(assertions = assertions.map(_.replace(oldType, newType)))
  override def ||(a: Assertion): DisjunctiveAssertion =
    copy(assertions = assertions :+ a)
  override def toString = "(" + assertions.mkString(" ∨ ") + ")"
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): DisjunctiveAssertion = DisjunctiveAssertion(
    assertions.map(_.combineTemporaryType(oldType, newType))
  )

/** An assertion stating that all of the assertions in the conjunction must be true
  * @param assertions
  *   the assertions all of whom must be true
  */
case class ConjunctiveAssertion(assertions: Vector[Assertion])
    extends Assertion,
      AssertionOps[ConjunctiveAssertion]:
  def replace(oldType: InferenceVariable, newType: Type): ConjunctiveAssertion =
    copy(assertions = assertions.map(_.replace(oldType, newType)))
  override def &&(a: Assertion): ConjunctiveAssertion =
    copy(assertions = assertions :+ a)
  override def toString = "(" + assertions.mkString(" ∧ ") + ")"
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ConjunctiveAssertion = ConjunctiveAssertion(
    assertions.map(_.combineTemporaryType(oldType, newType))
  )

/** An assertion stating that a type must be a class
  * @param t
  *   the type who must be a class
  */
case class IsClassAssertion(t: Type) extends Assertion, AssertionOps[IsClassAssertion]:
  override def toString = s"isClass($t)"
  def replace(oldType: InferenceVariable, newType: Type): IsClassAssertion =
    copy(t = t.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): IsClassAssertion = IsClassAssertion(t.combineTemporaryType(oldType, newType))

/** An assertion stating that a type must be an interface
  * @param t
  *   the type who must be an interface
  */
case class IsInterfaceAssertion(t: Type) extends Assertion, AssertionOps[IsInterfaceAssertion]:
  override def toString = s"isInterface($t)"
  def replace(oldType: InferenceVariable, newType: Type): IsInterfaceAssertion =
    copy(t = t.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): IsInterfaceAssertion = IsInterfaceAssertion(t.combineTemporaryType(oldType, newType))

/** An assertion stating that a type has a constructor
  * @param source
  *   the source (and return type) of the constructor
  * @param args
  *   the arguments to the constructor invocation
  * @param callSiteParameterChoices
  *   the parameter choices at the site of the constructor invocation
  */
case class HasConstructorAssertion(
    source: ClassOrInterfaceType,
    args: Vector[Type],
    callSiteParameterChoices: Set[TTypeParameter]
) extends Assertion,
      AssertionOps[HasConstructorAssertion]:
  override def toString =
    s"${source.identifier} can be constructed with new $source(${args.mkString(", ")}) whose type arguments might be $callSiteParameterChoices"

  def replace(oldType: InferenceVariable, newType: Type): HasConstructorAssertion =
    copy(source = source.replace(oldType, newType), args = args.map(_.replace(oldType, newType)))

  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): HasConstructorAssertion =
    copy(
      source = source.combineTemporaryType(oldType, newType),
      args = args.map(_.combineTemporaryType(oldType, newType)),
      callSiteParameterChoices =
        callSiteParameterChoices.map(_.combineTemporaryType(oldType, newType))
    )

/** An assertion stating that a type must have a method
  * @param source
  *   the type containing the method
  * @param methodName
  *   the name of the method
  * @param args
  *   the type of the arguments to the method
  * @param returnType
  *   the return type of the method
  */
case class HasMethodAssertion(
    source: SomeClassOrInterfaceType,
    methodName: String,
    args: Vector[Type],
    returnType: Type,
    callSiteParameterChoices: Set[TTypeParameter]
) extends Assertion,
      AssertionOps[HasMethodAssertion]:
  override def toString =
    s"$source has $returnType $methodName($args) whose type arguments might be $callSiteParameterChoices"
  def replace(oldType: InferenceVariable, newType: Type): HasMethodAssertion =
    copy(
      source = source.replace(oldType, newType),
      args = args.map(_.replace(oldType, newType)),
      returnType = returnType.replace(oldType, newType),
      callSiteParameterChoices
    )
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): HasMethodAssertion =
    copy(
      source = source.combineTemporaryType(oldType, newType),
      args = args.map(_.combineTemporaryType(oldType, newType)),
      returnType = returnType.combineTemporaryType(oldType, newType),
      callSiteParameterChoices =
        callSiteParameterChoices.map(_.combineTemporaryType(oldType, newType))
    )

/** A predicate checking if a type is declared in the program
  * @param t
  *   the type who must be a declared type
  */
case class IsDeclaredAssertion(t: Type) extends Assertion, AssertionOps[IsDeclaredAssertion]:
  override def toString = s"${t.identifier} ∈ Δ"
  def replace(oldType: InferenceVariable, newType: Type): IsDeclaredAssertion =
    copy(t = t.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): IsDeclaredAssertion = IsDeclaredAssertion(t.combineTemporaryType(oldType, newType))

/** A predicate checking if a type is missing in the program
  * @param t
  *   the type who must be missing
  */
case class IsMissingAssertion(t: Type) extends Assertion, AssertionOps[IsMissingAssertion]:
  override def toString = s"${t.identifier} ∈ Φ₁"
  def replace(oldType: InferenceVariable, newType: Type): IsMissingAssertion =
    copy(t = t.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): IsMissingAssertion = IsMissingAssertion(t.combineTemporaryType(oldType, newType))

/** A predicate checking if a type is unknown
  * @param t
  *   the type who is unknown
  */
case class IsUnknownAssertion(t: Type) extends Assertion, AssertionOps[IsUnknownAssertion]:
  override def toString = s"${t.identifier} ∈ Φ₂"
  def replace(oldType: InferenceVariable, newType: Type): IsUnknownAssertion =
    copy(t = t.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): IsUnknownAssertion = IsUnknownAssertion(t.combineTemporaryType(oldType, newType))

/** An assertion stating that a type is one of the primitive types
  * @param t
  *   the type who must be primitive
  */
case class IsPrimitiveAssertion(t: Type) extends Assertion, AssertionOps[IsPrimitiveAssertion]:
  override def toString = s"${t.identifier} is primitive"
  def replace(oldType: InferenceVariable, newType: Type): IsPrimitiveAssertion =
    copy(t = t.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): IsPrimitiveAssertion = IsPrimitiveAssertion(t.combineTemporaryType(oldType, newType))

/** An assertion stating that a type must be treated as integral (integer or smaller)
  * @param t
  *   the type who must be integral
  */
case class IsIntegralAssertion(t: Type) extends Assertion, AssertionOps[IsIntegralAssertion]:
  override def toString = s"${t.identifier} is an integral type"
  def replace(oldType: InferenceVariable, newType: Type): IsIntegralAssertion =
    copy(t = t.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): IsIntegralAssertion = IsIntegralAssertion(t.combineTemporaryType(oldType, newType))

/** An assertion stating that a type must be treated as numeric (double or smaller)
  * @param t
  *   the type who must be numeric
  */
case class IsNumericAssertion(t: Type) extends Assertion, AssertionOps[IsNumericAssertion]:
  override def toString = s"${t.identifier} is an integral type"
  def replace(oldType: InferenceVariable, newType: Type): IsNumericAssertion =
    copy(t = t.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): IsNumericAssertion = IsNumericAssertion(t.combineTemporaryType(oldType, newType))

/** An assertion stating that a type implements a method
  * @param t
  *   the type to implement the method
  * @param m
  *   the method to implement
  * @param canBeAbstract
  *   whether the method can be abstract
  */
case class ImplementsMethodAssertion(t: Type, m: Method, canBeAbstract: Boolean = false)
    extends Assertion,
      AssertionOps[ImplementsMethodAssertion]:
  override def toString = s"$t implements $m"
  def replace(oldType: InferenceVariable, newType: Type): ImplementsMethodAssertion =
    copy(t = t.replace(oldType, newType), m = m.replace(oldType, newType))
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ImplementsMethodAssertion =
    copy(t = t.combineTemporaryType(oldType, newType), m = m.combineTemporaryType(oldType, newType))

/** An assertion stating that one method overrides another method
  * @param overriding
  *   the method that overrides the other method
  * @param overridden
  *   the method to be overridden
  */
case class OverridesAssertion(overriding: Method, overridden: Method)
    extends Assertion,
      AssertionOps[OverridesAssertion]:
  override def toString = s"$overriding overrides $overridden"
  def replace(oldType: InferenceVariable, newType: Type): OverridesAssertion =
    copy(
      overriding = overriding.replace(oldType, newType),
      overridden = overridden.replace(oldType, newType)
    )
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): OverridesAssertion =
    copy(
      overriding = overriding.combineTemporaryType(oldType, newType),
      overridden = overridden.combineTemporaryType(oldType, newType)
    )
