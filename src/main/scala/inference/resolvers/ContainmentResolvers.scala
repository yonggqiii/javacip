package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
import utils.*

private def resolveContainmentAssertion(log: Log, config: Configuration, a: ContainmentAssertion) =
  val ContainmentAssertion(ys, zs) = a
  (ys, zs) match
    case (yy: DisjunctiveType, _) =>
      expandDisjunctiveType(yy, log, config asserts a)
    case (_, yy: DisjunctiveType) =>
      expandDisjunctiveType(yy, log, config asserts a)
    case (_, Wildcard) => (log, config :: Nil)
    case (_, ExtendsWildcardType(upper)) =>
      val newAsst = ys <:~ upper
      (log.addInfo(s"$a reduces to $newAsst"), (config asserts newAsst) :: Nil)
    case (_, SuperWildcardType(lower)) =>
      val newAsst = lower <:~ ys
      (log.addInfo(s"$a reduces to $newAsst"), (config asserts newAsst) :: Nil)
    case (
          x @ (_: SomeClassOrInterfaceType | _: Capture | _: Alpha | _: ArrayType | Bottom |
          _: TTypeParameter),
          y: JavaInferenceVariable
        ) =>
      (
        log.addInfo(s"replacing $y with $x"),
        (config.replace(y, x) :: Nil).filter(_.isDefined).map(_.get)
      )
    case (
          y: JavaInferenceVariable,
          x @ (_: SomeClassOrInterfaceType | _: Capture | _: Alpha | _: ArrayType | Bottom |
          _: TTypeParameter)
        ) =>
      (
        log.addInfo(s"replacing $y with $x"),
        (config.replace(y, x) :: Nil).filter(_.isDefined).map(_.get)
      )
    case _ =>
      val newAsst = ys <:~ zs && zs <:~ ys
      (
        log.addInfo(s"$a reduces to $newAsst"),
        (config asserts newAsst) :: Nil
      )
