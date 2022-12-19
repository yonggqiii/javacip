package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
import utils.*

private def resolveCompatibilityAssertion(
    log: Log,
    config: Configuration,
    a: CompatibilityAssertion
) =
  val CompatibilityAssertion(target, source) = a
  /* either
   * 1) source <: target
   * 2) source <<~= target
   * 3) boxed(source) <: target
   * 4) unboxed(source) <<~= target
   */
  (source, target) match
    case (source, target): (PrimitiveType, PrimitiveType) =>
      (log, (config asserts (target in source.widened.toSet[Type])) :: Nil)
    case (source, target): (ClassOrInterfaceType, ClassOrInterfaceType) =>
      (log, (config asserts (source <:~ target)) :: Nil)
    case (source, target): (PrimitiveType, ClassOrInterfaceType) =>
      (log, (config asserts (source.boxed <:~ target)) :: Nil)
    case (source, target): (ClassOrInterfaceType, PrimitiveType) =>
      (
        log,
        (config asserts DisjunctiveAssertion(target.boxedSources.map(source <:~ _).toVector)) :: Nil
      )
    case (source, target) =>
      // case 1
      val case1Assertion = source <:~ target
      // case 2
      val case2Assertion = source <<~= target
      val B              = InferenceVariableFactory.createBoxesOnlyDisjunctiveType()
      val P              = InferenceVariableFactory.createPrimitivesOnlyDisjunctiveType()
      // case 3
      val case3Assertion = ((source, B) in BOX_RELATION.toSet[(Type, Type)]) && (B <:~ target)
      // case 4
      val case4Assertion =
        (source <:~ B) && ((B, P) in UNBOX_RELATION.toSet[(Type, Type)]) && (P <<~= target)
      (
        log,
        (config asserts DisjunctiveAssertion(
          Vector(case1Assertion, case2Assertion, case3Assertion, case4Assertion)
        )) :: Nil
      )
