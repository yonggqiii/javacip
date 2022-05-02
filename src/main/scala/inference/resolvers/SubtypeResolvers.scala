package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*

private[inference] def resolveSubtypeAssertion(
    log: Log,
    config: Configuration,
    a: SubtypeAssertion
): (Log, List[Configuration]) =
  val SubtypeAssertion(x, y) = a
  val (sub, sup)             = (x.upwardProjection.substituted, y.downwardProjection.substituted)
  // trivial cases
  if sub == sup || sup == NormalType("java.lang.Object", 0, Nil).substituted || sub == Bottom then
    (log, config :: Nil)
  // subtype is Object
  else if sub == NormalType("java.lang.Object", 0, Nil).substituted then
    val newAssertion =
      EquivalenceAssertion(y.downwardProjection, NormalType("java.lang.Object", 0, Nil))
    (
      log.addInfo(s"replaced $a with $newAssertion"),
      config.copy(omega = config.omega.enqueue(newAssertion)) :: Nil
    )
  // supertype is bottom
  else if sup == Bottom then
    val newAssertion = EquivalenceAssertion(x.upwardProjection, Bottom)
    (
      log.addInfo(s"replaced $a with $newAssertion"),
      config.copy(omega = config.omega.enqueue(newAssertion)) :: Nil
    )
  else
    (sub, sup) match
      case (i: InferenceVariable, _) =>
        // return assertion back to config
        val originalConfig = config.copy(omega = config.omega.enqueue(a))
        i.source match
          case Left(_) =>
            val choices = i._choices
            (
              log.addInfo(s"expanding ${i.identifier} into its choices"),
              choices.map(originalConfig.replace(i.copy(substitutions = Nil), _)).toList
            )
          case Right(_) =>
            (
              log.addInfo(
                "returning $a back to config as insufficient information is available"
              ),
              originalConfig :: Nil
            )

      case (_, i: InferenceVariable) =>
        val originalConfig = config.copy(omega = config.omega.enqueue(a))
        i.source match
          case Left(_) =>
            val choices = i._choices
            (
              log.addInfo(s"expanding ${i.identifier} into its choices"),
              choices.map(originalConfig.replace(i.copy(substitutions = Nil), _)).toList
            )
          case Right(_) =>
            (
              log.addInfo(
                "returning $a back to config as insufficient information is available"
              ),
              originalConfig :: Nil
            )
      case (ArrayType(i), ArrayType(j)) =>
        // array types are covariant
        (log, config.copy(omega = config.omega.enqueue(SubtypeAssertion(i, j))) :: Nil)
      // array type cannot be sub/super types of other types except the trivial ones
      case (ArrayType(_), _) =>
        (log.addError(s"$x can only be a subtype of other array types or Object"), Nil)
      case (_, ArrayType(_)) =>
        (log.addError(s"$y can only be a supertype of other array types or Bottom"), Nil)
      // left type parameter can be reduces to assertions on its bounds
      case (m: TTypeParameter, _) =>
        val source = m match
          case TypeParameterIndex(source, _, _)   => source
          case TypeParameterName(source, _, _, _) => source
        val fixed = config.getFixedDeclaration(NormalType(source, 0, Nil))
        if !fixed.isEmpty then
          val decl  = fixed.get
          val assts = DisjunctiveAssertion(decl.getBounds(m).map(SubtypeAssertion(_, y)))
          (
            log.addInfo(s"expanding assertion on $x to assertions on its bounds"),
            config.copy(omega = config.omega.enqueue(assts)) :: Nil
          )
        else if config.phi1.contains(source) then
          (
            log.addWarn(s"bounds of $x is always Object because it is found in a missing type"),
            config.copy(omega =
              config.omega
                .enqueue(SubtypeAssertion(NormalType("java.lang.Object", 0, Nil), y))
            ) :: Nil
          )
        else (log.addError(s"unable to find the declaration where $sub is declared"), Nil)
      case (_, _: TTypeParameter) =>
        (
          log.addError(
            s"it is not possible for $x <: $y as $y is a type parameter, unless $x itself is a type parameter"
          ),
          Nil
        )
      // case of two substituted reference types where raw is the same
      case (m, n): (SubstitutedReferenceType, SubstitutedReferenceType)
          if m.identifier == n.identifier =>
        if sup.args.size == 0 || sub.args.size == 0 then (log, config :: Nil)
        else if sup.args.size != sup.args.size then
          (
            log.addError(s"$sub and $sup have same raw type but different number of arguments?"),
            Nil
          )
        else
          val newAssertion = ConjunctiveAssertion(
            sub.args.zip(sup.args).map(x => ContainmentAssertion(x._1, x._2))
          )
          (log, config.copy(omega = config.omega.enqueue(newAssertion)) :: Nil)
      // case of subtype being a normal reference type and is declared
      case (m: SubstitutedReferenceType, _) if !config.getFixedDeclaration(m).isEmpty =>
        resolveLeftFixedSubtypeAssertion(log, config, x, y)

private def resolveLeftFixedSubtypeAssertion(
    log: Log,
    config: Configuration,
    subtype: Type,
    supertype: Type
): (Log, List[Configuration]) =
  val newAssertion = config
    .getFixedDeclaration(subtype.upwardProjection.substituted)
    .map(decl =>
      val supertypes =
        val tmp = (decl.extendedTypes ++ decl.implementedTypes).map(x =>
          x.addSubstitutionLists(
            subtype.upwardProjection.substituted.substitutions.filter(x => !x.isEmpty)
          )
        )
        if tmp.isEmpty then Vector(NormalType("java.lang.Object", 0, Nil)) else tmp
      supertypes.map(x => SubtypeAssertion(x, supertype.downwardProjection))
    )
    .map(DisjunctiveAssertion(_))
    .get
  val newConfig = config.copy(omega = config.omega.enqueue(newAssertion))
  (
    log.addInfo(s"replacing ${SubtypeAssertion(subtype, supertype)} with $newAssertion"),
    newConfig :: Nil
  )
