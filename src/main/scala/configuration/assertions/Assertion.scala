package configuration.assertions

import configuration.declaration.MethodSignature
import configuration.types.*

sealed trait Assertion

case class SubtypeAssertion(left: Type, right: Type) extends Assertion:
  override def toString = s"$left <: $right"

case class EquivalenceAssertion(left: Type, right: Type) extends Assertion:
  override def toString = s"$left = $right"

case class ContainmentAssertion(left: Type, right: Type) extends Assertion:
  override def toString = s"$left <= $right"

case class DisjunctiveAssertion(assertions: Vector[Assertion]) extends Assertion

case class ConjunctiveAssertion(assertions: Vector[Assertion]) extends Assertion

case class IsClassAssertion(identifier: Type) extends Assertion:
  override def toString = s"isClass($identifier)"

case class IsInterfaceAssertion(identifier: Type) extends Assertion:
  override def toString = s"isInterface($identifier)"

case class HasMethodAssertion(
    source: Type,
    methodName: String,
    args: Vector[Type],
    returnType: Type
) extends Assertion:
  override def toString = s"$source has $returnType $methodName($args)"
