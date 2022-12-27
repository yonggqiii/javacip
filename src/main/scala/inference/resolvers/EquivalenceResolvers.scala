package inference.resolvers
import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.*
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
      case (Bottom, _)          => (log.addWarn(s"$x != $y"), Nil)
      case (_, Bottom)          => (log.addWarn(s"$x != $y"), Nil)
      case (Wildcard, Wildcard) => (log, config :: Nil)
      case (x: Capture, _)      => (log.addWarn(s"$x != $y!"), Nil)
      case (_, x: Capture)      => (log.addWarn(s"$x != $y!"), Nil)
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
        (log.addWarn(s"$a != $Wildcard"), Nil)
      case (a: SuperWildcardType, b: ExtendsWildcardType) =>
        (log.addWarn(s"$a != $Wildcard"), Nil)
      case (a: ExtendsWildcardType, Wildcard) =>
        (log, (config asserts a.upper ~=~ OBJECT) :: Nil)
      case (a: SuperWildcardType, Wildcard) =>
        (log.addWarn(s"$a != $Wildcard"), Nil)
      case (Wildcard, a: ExtendsWildcardType) =>
        (log, (config asserts a.upper ~=~ OBJECT) :: Nil)
      case (Wildcard, a: SuperWildcardType) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (a: ArrayType, b: ArrayType) =>
        (log, (config asserts (a.base ~=~ b.base)) :: Nil)
      case (a: PrimitiveType, b: PrimitiveType) =>
        // definitely false
        (log.addWarn(s"$x != $y"), Nil)
      case (PRIMITIVE_VOID, b: VoidableDisjunctiveType) =>
        (
          log.addInfo(s"$b must be void"),
          (config :: Nil).map(x => x.replace(b, PRIMITIVE_VOID)).filter(_.isDefined).map(_.get)
        )
      case (b: VoidableDisjunctiveType, PRIMITIVE_VOID) =>
        (
          log.addInfo(s"$b must be void"),
          (config :: Nil).map(x => x.replace(b, PRIMITIVE_VOID)).filter(_.isDefined).map(_.get)
        )
      case (PRIMITIVE_VOID, _) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (_, PRIMITIVE_VOID) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (a: TTypeParameter, b: TTypeParameter) =>
        // definitely false
        (log.addWarn(s"$x != $y"), Nil)
      case (
            a @ (_: BoxesOnlyDisjunctiveType | _: ReferenceOnlyDisjunctiveType),
            _: PrimitiveType
          ) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (
            _: PrimitiveType,
            a @ (_: BoxesOnlyDisjunctiveType | _: ReferenceOnlyDisjunctiveType)
          ) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (a: DisjunctiveType, b: PrimitiveType) =>
        expandDisjunctiveTypeToPrimitive(a, log, config asserts asst)
      case (b: PrimitiveType, a: DisjunctiveType) =>
        expandDisjunctiveTypeToPrimitive(a, log, config asserts asst)
      case (a: PrimitivesOnlyDisjunctiveType, b: SomeClassOrInterfaceType) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (b: SomeClassOrInterfaceType, a: PrimitivesOnlyDisjunctiveType) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (b: SomeClassOrInterfaceType, a: DisjunctiveType) =>
        expandDisjunctiveTypeToReference(a, log, config asserts asst)
      case (a: DisjunctiveType, b: SomeClassOrInterfaceType) =>
        expandDisjunctiveTypeToReference(a, log, config asserts asst)
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
      case (x: TemporaryType, y: SomeClassOrInterfaceType) =>
        if x.identifier == y.identifier then resolveReferenceEquivalences(x, y, log, config)
        else
          config.asserts(asst).combineTemporaryType(x, y) match
            case Some(x) =>
              (log.addInfo(s"$x was combined with $y"), x :: Nil)
            case None =>
              (log.addWarn(s"$x cannot be combined with $y"), Nil)
      case (y: SomeClassOrInterfaceType, x: TemporaryType) =>
        if x.identifier == y.identifier then resolveReferenceEquivalences(x, y, log, config)
        else
          config.asserts(asst).combineTemporaryType(x, y) match
            case Some(x) =>
              (log.addInfo(s"$x was combined with $y"), x :: Nil)
            case None =>
              (log.addWarn(s"$x cannot be combined with $y"), Nil)

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
      case (Wildcard | _: ExtendsWildcardType | _: SuperWildcardType, _) =>
        (log.addWarn(s"$x != $y"), Nil)
      case (_, Wildcard | _: ExtendsWildcardType | _: SuperWildcardType) =>
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
    x: SomeClassOrInterfaceType,
    y: SomeClassOrInterfaceType,
    log: Log,
    config: Configuration
) =
  if x.identifier != y.identifier || x.numArgs != y.numArgs then (log.addWarn(s"$x != $y"), Nil)
  else if x.numArgs == 0 then (log.addWarn(s"$x != $y"), Nil)
  else
    val newAsst = x.args.zip(y.args).map(_ ~=~ _)
    (log, (config assertsAllOf newAsst) :: Nil)
