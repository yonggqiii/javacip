package configuration.assertions

import configuration.declaration.MethodSignature
import configuration.types.*

sealed trait Assertion:
  def replace(oldType: ReplaceableType, newType: Type): Assertion
  def &&(a: Assertion): ConjunctiveAssertion =
    ConjunctiveAssertion(Vector(this, a))
  def ||(a: Assertion): DisjunctiveAssertion =
    DisjunctiveAssertion(Vector(this, a))

case class SubtypeAssertion(left: Type, right: Type) extends Assertion:
  override def toString = s"$left <: $right"
  def replace(oldType: ReplaceableType, newType: Type): SubtypeAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))

case class EquivalenceAssertion(left: Type, right: Type) extends Assertion:
  override def toString = s"$left = $right"
  def replace(oldType: ReplaceableType, newType: Type): EquivalenceAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))

case class ContainmentAssertion(left: Type, right: Type) extends Assertion:
  override def toString = s"$left <= $right"
  def replace(oldType: ReplaceableType, newType: Type): ContainmentAssertion =
    copy(left = left.replace(oldType, newType), right = right.replace(oldType, newType))

case class DisjunctiveAssertion(assertions: Vector[Assertion]) extends Assertion:
  def replace(oldType: ReplaceableType, newType: Type): DisjunctiveAssertion =
    copy(assertions = assertions.map(_.replace(oldType, newType)))
  override def ||(a: Assertion): DisjunctiveAssertion =
    copy(assertions = assertions :+ a)

case class ConjunctiveAssertion(assertions: Vector[Assertion]) extends Assertion:
  def replace(oldType: ReplaceableType, newType: Type): ConjunctiveAssertion =
    copy(assertions = assertions.map(_.replace(oldType, newType)))
  override def &&(a: Assertion): ConjunctiveAssertion =
    copy(assertions = assertions :+ a)

case class IsClassAssertion(identifier: Type) extends Assertion:
  override def toString = s"isClass($identifier)"
  def replace(oldType: ReplaceableType, newType: Type): IsClassAssertion =
    copy(identifier = identifier.replace(oldType, newType))

case class IsInterfaceAssertion(identifier: Type) extends Assertion:
  override def toString = s"isInterface($identifier)"
  def replace(oldType: ReplaceableType, newType: Type): IsInterfaceAssertion =
    copy(identifier = identifier.replace(oldType, newType))

case class HasMethodAssertion(
    source: Type,
    methodName: String,
    args: Vector[Type],
    returnType: Type
) extends Assertion:
  override def toString = s"$source has $returnType $methodName($args)"
  def replace(oldType: ReplaceableType, newType: Type): HasMethodAssertion =
    copy(
      source = source.replace(oldType, newType),
      args = args.map(_.replace(oldType, newType)),
      returnType = returnType.replace(oldType, newType)
    )

case class IsDeclaredAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} ∈ Δ"
  def replace(oldType: ReplaceableType, newType: Type): IsDeclaredAssertion =
    copy(t = t.replace(oldType, newType))

case class IsMissingAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} ∈ Φ₁"
  def replace(oldType: ReplaceableType, newType: Type): IsMissingAssertion =
    copy(t = t.replace(oldType, newType))

case class IsUnknownAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} ∈ Φ₂"
  def replace(oldType: ReplaceableType, newType: Type): IsUnknownAssertion =
    copy(t = t.replace(oldType, newType))

case class IsPrimitiveAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} is primitive"
  def replace(oldType: ReplaceableType, newType: Type): IsPrimitiveAssertion =
    copy(t = t.replace(oldType, newType))

case class IsReferenceAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} is a reference type"
  def replace(oldType: ReplaceableType, newType: Type): IsReferenceAssertion =
    copy(t = t.replace(oldType, newType))

case class IsIntegralAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} is an integral type"
  def replace(oldType: ReplaceableType, newType: Type): IsIntegralAssertion =
    copy(t = t.replace(oldType, newType))

case class IsNumericAssertion(t: Type) extends Assertion:
  override def toString = s"${t.identifier} is an integral type"
  def replace(oldType: ReplaceableType, newType: Type): IsNumericAssertion =
    copy(t = t.replace(oldType, newType))
