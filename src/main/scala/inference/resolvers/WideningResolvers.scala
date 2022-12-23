package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.*
import utils.*

private def resolveWideningAssertion(log: Log, config: Configuration, a: WideningAssertion) =
  val WideningAssertion(left, right) = a
  (left.upwardProjection, right.downwardProjection) match
    case (x: ReferenceOnlyDisjunctiveType, _) =>
      (log.addWarn(s"$x cannot widen to anything"), Nil)
    case (_, x: ReferenceOnlyDisjunctiveType) =>
      (log.addWarn(s"$x be widened from anything"), Nil)
    case (x: BoxesOnlyDisjunctiveType, _) =>
      (log.addWarn(s"$x cannot widen to anything"), Nil)
    case (_, x: BoxesOnlyDisjunctiveType) =>
      (log.addWarn(s"$x be widened from anything"), Nil)
    case (x: DisjunctiveType, _) =>
      expandDisjunctiveTypeToPrimitive(x, log, config asserts a)
    case (_, x: DisjunctiveType) =>
      expandDisjunctiveTypeToPrimitive(x, log, config asserts a)
    case (x: ClassOrInterfaceType, _) =>
      (log.addWarn(s"$x cannot widen to anything"), Nil)
    case (_, x: ClassOrInterfaceType) =>
      (log.addWarn(s"$x be widened from anything"), Nil)
    case (x: Alpha, _) =>
      (log.addWarn(s"$x cannot widen to anything"), Nil)
    case (_, x: Alpha) =>
      (log.addWarn(s"$x be widened from anything"), Nil)
    case (x: TTypeParameter, _) =>
      (log.addWarn(s"$x cannot widen to anything"), Nil)
    case (_, x: TTypeParameter) =>
      (log.addWarn(s"$x be widened from anything"), Nil)
    case (x: TemporaryType, _) =>
      (log.addWarn(s"$x cannot widen to anything"), Nil)
    case (_, x: TemporaryType) =>
      (log.addWarn(s"$x be widened from anything"), Nil)
    case (Bottom, _) =>
      (log.addWarn(s"$Bottom cannot be widened to anything"), Nil)
    case (_, Bottom) =>
      (log.addWarn(s"$Bottom cannot be widened from anything"), Nil)
    case (x: PlaceholderType, _) =>
      addToConstraintStore(x, a, log, config)
    case (_, x: PlaceholderType) =>
      addToConstraintStore(x, a, log, config)
    case (x: ArrayType, _) =>
      (log.addWarn(s"$x cannot widen to anything"), Nil)
    case (_, x: ArrayType) =>
      (log.addWarn(s"$x be widened from anything"), Nil)
    case (x: PrimitiveType, y: PrimitiveType) =>
      (log.addWarn(s"already proven the negation of $a"), Nil)
    case (Wildcard, _)               => ???
    case (_, Wildcard)               => ???
    case (x: ExtendsWildcardType, _) => ???
    case (_, x: ExtendsWildcardType) => ???
    case (x: SuperWildcardType, _)   => ???
    case (_, x: SuperWildcardType)   => ???
// case _ =>
//   (
//     log.addInfo(s"$left $right in <~="),
//     (config asserts ((left, right) in WIDENING_RELATION)) :: Nil
//   )
