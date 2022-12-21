package configuration.assertions

import configuration.declaration.MethodSignature
import configuration.types.*

/** assertions to be delayed by Replaceable types are given the lowest priority, followed by
  * disjunctive assertions, and finally all others have the highest priority
  */
given assertionOrdering: Ordering[Assertion] with
  def compare(x: Assertion, y: Assertion): Int =
    // val disjunctiveTester: Assertion => Boolean = x =>
    //   x match
    //     case _: DisjunctiveAssertion => true
    //     case _                       => false
    val disjunctiveTester: Assertion => Boolean = asst =>
      asst match
        case SubtypeAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] || y.isInstanceOf[DisjunctiveType]
        case EquivalenceAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] && y.isInstanceOf[DisjunctiveType]
        case ContainmentAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] || y.isInstanceOf[DisjunctiveType]
        case x: ConjunctiveAssertion => false
        case y: DisjunctiveAssertion => true
        case IsClassAssertion(x)     => x.isInstanceOf[DisjunctiveType]
        case IsInterfaceAssertion(x) => x.isInstanceOf[DisjunctiveType]
        case x: HasMethodAssertion   => false
        case IsDeclaredAssertion(x)  => false
        case IsMissingAssertion(x)   => false
        case IsUnknownAssertion(x)   => false
        case IsPrimitiveAssertion(x) => x.isInstanceOf[DisjunctiveType]
        case IsReferenceAssertion(x) => x.isInstanceOf[DisjunctiveType]
        case IsIntegralAssertion(x)  => x.isInstanceOf[DisjunctiveType]
        case IsNumericAssertion(x)   => x.isInstanceOf[DisjunctiveType]
        case CompatibilityAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] || y.isInstanceOf[DisjunctiveType]
        case WideningAssertion(x, y) =>
          x.isInstanceOf[DisjunctiveType] || y.isInstanceOf[DisjunctiveType]
    val (xdisj, ydisj) =
      (disjunctiveTester(x), disjunctiveTester(y))
    if xdisj && !ydisj then -1
    else if !xdisj && ydisj then 1
    else 0

/** An assertion in the algorithm */
sealed trait Assertion:
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
case class SubtypeAssertion(left: Type, right: Type) extends Assertion:
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
case class CompatibilityAssertion(target: Type, source: Type) extends Assertion:
  override def toString = s"${target} := ${source}"
  def replace(oldType: InferenceVariable, newType: Type): CompatibilityAssertion =
    copy(target = target.replace(oldType, newType), source = source.replace(oldType, newType))

/** An assertion stating that one type widens to another
  * @param left
  *   the left primitive type
  * @param right
  *   the right primitive type that the left can widen into
  */
case class WideningAssertion(left: Type, right: Type) extends Assertion:
  override def toString = s"${left} <<~= ${right}"
  def replace(oldType: InferenceVariable, newType: Type): WideningAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))

/** An assertion stating that two types are equivalent
  * @param left
  *   a type
  * @param right
  *   the other type
  */
case class EquivalenceAssertion(left: Type, right: Type) extends Assertion:
  override def toString = s"${left} = ${right}"
  def replace(oldType: InferenceVariable, newType: Type): EquivalenceAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))

/** An assertion stating that one type is contained by another type
  * @param left
  *   the type to be contained
  * @param right
  *   the type that contains
  */
case class ContainmentAssertion(left: Type, right: Type) extends Assertion:
  override def toString = s"${left} <= ${right}"
  def replace(oldType: InferenceVariable, newType: Type): ContainmentAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))

/** An assertion stating that at least one of the assertions in the disjunction must be true
  * @param assertions
  *   the assertions whom at least one must be true
  */
case class DisjunctiveAssertion(assertions: Vector[Assertion]) extends Assertion:
  def replace(oldType: InferenceVariable, newType: Type): DisjunctiveAssertion =
    copy(assertions = assertions.map(_.replace(oldType, newType)))
  override def ||(a: Assertion): DisjunctiveAssertion =
    copy(assertions = assertions :+ a)
  override def toString = "(" + assertions.mkString(" ∨ ") + ")"

/** An assertion stating that all of the assertions in the conjunction must be true
  * @param assertions
  *   the assertions all of whom must be true
  */
case class ConjunctiveAssertion(assertions: Vector[Assertion]) extends Assertion:
  def replace(oldType: InferenceVariable, newType: Type): ConjunctiveAssertion =
    copy(assertions = assertions.map(_.replace(oldType, newType)))
  override def &&(a: Assertion): ConjunctiveAssertion =
    copy(assertions = assertions :+ a)
  override def toString = "(" + assertions.mkString(" ∧ ") + ")"

/** An assertion stating that a type must be a class
  * @param t
  *   the type who must be a class
  */
case class IsClassAssertion(t: Type) extends Assertion:
  override def toString = s"isClass($t)"
  def replace(oldType: InferenceVariable, newType: Type): IsClassAssertion =
    copy(t = t.replace(oldType, newType))

/** An assertion stating that a type must be an interface
  * @param t
  *   the type who must be an interface
  */
case class IsInterfaceAssertion(t: Type) extends Assertion:
  override def toString = s"isInterface($t)"
  def replace(oldType: InferenceVariable, newType: Type): IsInterfaceAssertion =
    copy(t = t.replace(oldType, newType))

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
    source: ClassOrInterfaceType,
    methodName: String,
    args: Vector[Type],
    returnType: Type,
    callSiteParameterChoices: Set[TTypeParameter]
) extends Assertion:
  override def toString =
    s"$source has $returnType $methodName($args) whose type arguments might be $callSiteParameterChoices"
  def replace(oldType: InferenceVariable, newType: Type): HasMethodAssertion =
    copy(
      source = source.replace(oldType, newType),
      args = args.map(_.replace(oldType, newType)),
      returnType = returnType.replace(oldType, newType),
      callSiteParameterChoices
    )

/** A predicate checking if a type is declared in the program
  * @param t
  *   the type who must be a declared type
  */
case class IsDeclaredAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} ∈ Δ"
  def replace(oldType: InferenceVariable, newType: Type): IsDeclaredAssertion =
    copy(t = t.replace(oldType, newType))

/** A predicate checking if a type is missing in the program
  * @param t
  *   the type who must be missing
  */
case class IsMissingAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} ∈ Φ₁"
  def replace(oldType: InferenceVariable, newType: Type): IsMissingAssertion =
    copy(t = t.replace(oldType, newType))

/** A predicate checking if a type is unknown
  * @param t
  *   the type who is unknown
  */
case class IsUnknownAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} ∈ Φ₂"
  def replace(oldType: InferenceVariable, newType: Type): IsUnknownAssertion =
    copy(t = t.replace(oldType, newType))

/** An assertion stating that a type is one of the primitive types
  * @param t
  *   the type who must be primitive
  */
case class IsPrimitiveAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} is primitive"
  def replace(oldType: InferenceVariable, newType: Type): IsPrimitiveAssertion =
    copy(t = t.replace(oldType, newType))

/** An assertion stating that a type is not a primitive type
  * @param t
  *   the type who must not be primitive
  */
case class IsReferenceAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} is a reference type"
  def replace(oldType: InferenceVariable, newType: Type): IsReferenceAssertion =
    copy(t = t.replace(oldType, newType))

/** An assertion stating that a type must be treated as integral (integer or smaller)
  * @param t
  *   the type who must be integral
  */
case class IsIntegralAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} is an integral type"
  def replace(oldType: InferenceVariable, newType: Type): IsIntegralAssertion =
    copy(t = t.replace(oldType, newType))

/** An assertion stating that a type must be treated as numeric (double or smaller)
  * @param t
  *   the type who must be numeric
  */
case class IsNumericAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} is an integral type"
  def replace(oldType: InferenceVariable, newType: Type): IsNumericAssertion =
    copy(t = t.replace(oldType, newType))
