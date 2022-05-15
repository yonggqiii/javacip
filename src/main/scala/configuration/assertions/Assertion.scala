package configuration.assertions

import configuration.declaration.MethodSignature
import configuration.types.*

sealed trait Assertion:
  def replace(oldType: ReplaceableType, newType: Type): Assertion

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

case class ConjunctiveAssertion(assertions: Vector[Assertion]) extends Assertion:
  def replace(oldType: ReplaceableType, newType: Type): ConjunctiveAssertion =
    copy(assertions = assertions.map(_.replace(oldType, newType)))

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
