package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveTypeGreedily
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
  (source.captured.upwardProjection, target.downwardProjection) match
    // case (s, t) if t == OBJECT =>
    //   (log, config :: Nil)
    case (s: PrimitiveType, t: PrimitiveType) =>
      (log, (config asserts (s <<~= t)) :: Nil)
    case (PRIMITIVE_VOID, s) =>
      (log.addInfo(s"$s must be void"), (config asserts (s ~=~ PRIMITIVE_VOID)) :: Nil)
    case (s, PRIMITIVE_VOID) =>
      (log.addInfo(s"$s must be void"), (config asserts (s ~=~ PRIMITIVE_VOID)) :: Nil)
    case (s: TemporaryType, t: PrimitiveType) =>
      (
        log,
        (config asserts DisjunctiveAssertion(
          t.widenedFrom.map(_.boxed).map(s <:~ _).toVector
        )) :: Nil
      )
    case (
          s @ (_: SomeClassOrInterfaceType | _: Alpha),
          t @ (_: SomeClassOrInterfaceType | _: Alpha)
        ) =>
      (log, (config asserts (s <:~ t)) :: Nil)
    case (s: Alpha, t: PrimitiveType) =>
      (
        log,
        (config asserts DisjunctiveAssertion(
          t.widenedFrom.map(_.boxed).map(s <:~ _).toVector
        )) :: Nil
      )
    // val B = InferenceVariableFactory.createBoxesOnlyDisjunctiveType()
    // val P = InferenceVariableFactory.createPrimitivesOnlyDisjunctiveType()
    // val asst =
    //   (s <:~ B) && ((B, P) in UNBOX_RELATION.toSet[(Type, Type)]) && (P <<~= t)
    // (log, (config.addToPsi(B).addToPsi(P) asserts asst) :: Nil)
    case (
          s: PrimitiveType,
          t @ (_: SomeClassOrInterfaceType | _: Alpha | _: JavaInferenceVariable)
        ) =>
      (log, (config asserts (s.boxed <:~ t)) :: Nil)
    case (s: JavaInferenceVariable, t: PrimitiveType) =>
      (log, config.addCompatibileTargetToJavaInferenceVariable(s, t) :: Nil)
    case (s @ (_: Alpha | _: PlaceholderType), t @ (_: Alpha | _: PlaceholderType)) =>
      val (l, c) = addToConstraintStore(s, a, log, config)
      addToConstraintStore(t, a, l, c.head)
    case (s @ (_: Alpha | _: PlaceholderType), _) =>
      addToConstraintStore(s, a, log, config)
    case (_, s @ (_: Alpha | _: PlaceholderType)) =>
      addToConstraintStore(s, a, log, config)
    case (s: ClassOrInterfaceType, t: PrimitiveType) =>
      if t.boxedSources.contains(s) then (log, config :: Nil)
      else (log.addWarn(s"$s does not unbox to something that widens to $t"), Nil)
    case (s: TTypeParameter, t: ClassOrInterfaceType) =>
      (log, (config asserts s <:~ t) :: Nil)
    case (Bottom, t) =>
      (log, (config asserts (Bottom <:~ t)) :: Nil)
    case (s, Bottom) =>
      (log.addWarn(s"$Bottom := $s will always be false"), Nil)
    case (s: SomeClassOrInterfaceType, t: JavaInferenceVariable) =>
      (log, config.asserts(s <:~ t) :: Nil)
    case (s: ArrayType, t) =>
      (log, (config asserts (s <:~ t)) :: Nil)
    case (s, t: ArrayType) =>
      (log, (config asserts (s <:~ t)) :: Nil)
    case (s: DisjunctiveType, t) =>
      expandDisjunctiveTypeGreedily(s, t, log, config asserts a)
    case (s, t: DisjunctiveType) =>
      expandDisjunctiveTypeGreedily(t, s, log, config asserts a)
    case (s, t) =>
      // case 1
      val case1Assertion = s <:~ t
      // case 2
      val case2Assertion = s <<~= t
      val B              = InferenceVariableFactory.createBoxesOnlyDisjunctiveType()
      val P              = InferenceVariableFactory.createPrimitivesOnlyDisjunctiveType()
      // case 3
      val case3Assertion = ((s, B) in BOX_RELATION.toSet[(Type, Type)]) && (B <:~ t)
      // case 4
      val case4Assertion =
        (s ~=~ B) && ((B, P) in UNBOX_RELATION.toSet[(Type, Type)]) && (P <<~= t)
      println(s"case: $a, $s, $t")
      (
        log,
        (config asserts case1Assertion) ::
          (config asserts case2Assertion) ::
          (config.addToPsi(B) asserts case3Assertion) ::
          (config.addToPsi(B).addToPsi(P) asserts case4Assertion) ::
          Nil
      )
