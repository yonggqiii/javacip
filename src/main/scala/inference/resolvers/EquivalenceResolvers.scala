package inference.resolvers
import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandDisjunctiveType
import utils.*

private[inference] def resolveEquivalenceAssertion(
    log: Log,
    config: Configuration,
    asst: EquivalenceAssertion
): (Log, List[Configuration]) =
  val EquivalenceAssertion(x, y) = asst
  if x ⊂ y then (log.addWarn(s"${x} ⊂ ${y} OCCURS"), Nil)
  else if y ⊂ x then (log.addWarn(s"${y} ⊂ ${x} OCCURS"), Nil)
  else
    (x, y) match
      case (Bottom, Bottom)     => (log, config :: Nil)
      case (Wildcard, Wildcard) => (log, config :: Nil)
      case (a: PlaceholderType, b) =>
        (
          log.addInfo(s"replacing $a with $b"),
          (config.replace(a, b) :: Nil).filter(!_.isEmpty).map(_.get)
        )
      case (b, a: PlaceholderType) =>
        (
          log.addInfo(s"replacing $a with $b"),
          (config.replace(a, b) :: Nil).filter(!_.isEmpty).map(_.get)
        )
      case (a: ExtendsWildcardType, b: ExtendsWildcardType) =>
        (log, (config asserts (a.upper ~=~ b.upper)) :: Nil)
      case (a: SuperWildcardType, b: SuperWildcardType) =>
        (log, (config asserts (a.lower ~=~ b.lower)) :: Nil)
      case (a: ExtendsWildcardType, b: SuperWildcardType) =>
        (log, (config asserts (Bottom ~=~ b.lower && a.upper ~=~ OBJECT)) :: Nil)
      case (a: SuperWildcardType, b: ExtendsWildcardType) =>
        (log, (config asserts (a.lower ~=~ Bottom && OBJECT ~=~ b.upper)) :: Nil)
      case (a: ExtendsWildcardType, Wildcard) =>
        (log, (config asserts a.upper ~=~ OBJECT) :: Nil)
      case (a: SuperWildcardType, Wildcard) =>
        (log.addWarn(s"$a != $Wildcard"), Nil)
      case (Wildcard, a: ExtendsWildcardType) =>
        (log, (config asserts a.upper ~=~ OBJECT) :: Nil)
      case (Wildcard, a: SuperWildcardType) =>
        (log.addWarn(s"$a != $Wildcard"), Nil)
      case (a: ArrayType, b: ArrayType) =>
        (log, (config asserts (a.base ~=~ b.base)) :: Nil)
      case (a: PrimitiveType, b: PrimitiveType) =>
        // definitely false
        (log.addWarn(s"$x != $y"), Nil)
      case (a: TTypeParameter, b: TTypeParameter) =>
        // definitely false
        (log.addWarn(s"$x != $y"), Nil)
      case (a: DisjunctiveType, _) =>
        expandDisjunctiveType(a, log, config asserts asst)
      case (_, a: DisjunctiveType) =>
        expandDisjunctiveType(a, log, config asserts asst)
      case (a: Alpha, b: PrimitiveType) =>
        (log.addWarn(s"$a cannot be a primitive type"), Nil)
      case (b: PrimitiveType, a: Alpha) =>
        (log.addWarn(s"$a cannot be a primitive type"), Nil)
      case (x: Alpha, y: SomeClassOrInterfaceType) =>
        concretizeAlphaToReference(log, config asserts asst, x, y)
      case (y: SomeClassOrInterfaceType, x: Alpha) =>
        concretizeAlphaToReference(log, config asserts asst, x, y)
      case (x: ClassOrInterfaceType, y: ClassOrInterfaceType) =>
        resolveReferenceEquivalences(x, y, log, config)
      case (x: Alpha, y: TTypeParameter) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (x: TTypeParameter, y: Alpha) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (x: Alpha, y: Alpha) =>
        val (newLog, newList) = addToConstraintStore(x, asst, log, config)
        if newList.isEmpty then (newLog, newList)
        else
          val c = newList(0)
          addToConstraintStore(y, asst, newLog, c)
      case (x: PrimitiveType, _) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (_, y: PrimitiveType) =>
        (log.addWarn(s"$x != $y"), Nil)

// case _ => ???

// private[inference] def concretizeAlphaToPrimitive(
//     log: Log,
//     config: Configuration,
//     a: Alpha,
//     p: PrimitiveType
// ): (Log, List[Configuration]) =
//   (
//     log.addInfo(s"concretizing $a to $p"),
//     List(config.replace(a.copy(substitutions = Nil), p))
//       .filter(!_.isEmpty)
//       .map(_.get)
//   )

private[inference] def concretizeAlphaToReference(
    log: Log,
    config: Configuration,
    a: Alpha,
    t: SomeClassOrInterfaceType
): (Log, List[Configuration]) = t match
  case s: ClassOrInterfaceType =>
    val newType = a.concretizeToReference(s.identifier, s.numArgs)

    (
      log.addInfo(s"concretizing $a to $newType"),
      List(
        config.replace(a.copy(substitutions = Nil), newType)
      )
        .filter(!_.isEmpty)
        .map(_.get)
    )
  case s: TemporaryType =>
    val newType = a.concretizeToTemporary(s.id, s.numArgs)
    (
      log.addInfo(s"concretizing $a to $newType"),
      List(
        config.replace(a.copy(substitutions = Nil), newType)
      )
        .filter(!_.isEmpty)
        .map(_.get)
    )

private def resolveReferenceEquivalences(
    x: ClassOrInterfaceType,
    y: ClassOrInterfaceType,
    log: Log,
    config: Configuration
) =
  if x.identifier != y.identifier || x.numArgs != y.numArgs then (log.addWarn(s"$x != $y"), Nil)
  else if x.numArgs == 0 then (log, Nil)
  else
    val newAsst = x.args.zip(y.args).map(_ ~=~ _)
    (log, (config assertsAllOf newAsst) :: Nil)
