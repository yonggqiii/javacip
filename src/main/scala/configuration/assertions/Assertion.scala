package configuration.assertions

import configuration.types.*

sealed trait Assertion

case class SubtypeAssertion(left: Type, right: Type) extends Assertion

case class EquivalenceAssertion(left: Type, right: Type) extends Assertion

case class ContainmentAssertion(left: Type, right: Type) extends Assertion

case class DisjunctiveAssertion(assertions: Vector[Assertion]) extends Assertion

case class ConjunctiveAssertion(assertions: Vector[Assertion]) extends Assertion

case class IsClassAssertion(identifier: Type) extends Assertion

case class IsInterfaceAssertion(identifier: Type) extends Assertion
