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
  if sub == sup || sup == OBJECT.substituted || sub == Bottom then (log, config :: Nil)
  // subtype is Object
  else if sub == OBJECT.substituted then
    val newAssertion =
      EquivalenceAssertion(y.downwardProjection, OBJECT)
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
              choices
                .map(originalConfig.replace(i.copy(substitutions = Nil), _))
                .filter(!_.isEmpty)
                .map(_.get)
                .toList
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
              choices
                .map(originalConfig.replace(i.copy(substitutions = Nil), _))
                .filter(!_.isEmpty)
                .map(_.get)
                .toList
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
        (log.addWarn(s"$x can only be a subtype of other array types or Object"), Nil)
      case (_, ArrayType(_)) =>
        (log.addWarn(s"$y can only be a supertype of other array types or Bottom"), Nil)
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
                .enqueue(SubtypeAssertion(OBJECT, y))
            ) :: Nil
          )
        else (log.addWarn(s"unable to find the declaration where $sub is declared"), Nil)
      case (_, _: TTypeParameter) =>
        (
          log.addWarn(
            s"it is not possible for $x <: $y as $y is a type parameter, unless $x itself is a type parameter"
          ),
          Nil
        )
      // case of two substituted reference types where raw is the same
      case (m, n): (SubstitutedReferenceType, SubstitutedReferenceType)
          if m.identifier == n.identifier =>
        if sup.args.size == 0 || sub.args.size == 0 then
          (log.addInfo(s"$m <: $n is trivially true as at least one is raw"), config :: Nil)
        else if sup.args.size != sup.args.size then
          (
            log.addWarn(s"$sub and $sup have same raw type but different number of arguments?"),
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
      // case of subtype being a missing type and supertype is a known type
      case (m: SubstitutedReferenceType, n: SubstitutedReferenceType) =>
        config.phi1(m.identifier).supertypes.find(_.identifier == n.identifier) match
          case Some(x) =>
            if m.numArgs == 0 then (log.addInfo(s"$m <: $n trivially is $m is raw"), config :: Nil)
            else
              (
                log.addInfo(s"$x <: $n"),
                config.copy(omega =
                  config.omega.enqueue(SubtypeAssertion(x.addSubstitutionLists(m.substitutions), n))
                ) :: Nil
              )
          case None =>
            // case of left interface and right class
            val supertypeIsClass =
              val fixedDecl = config.getFixedDeclaration(n)
              if !fixedDecl.isEmpty then
                val decl = fixedDecl.get
                !decl.isInterface
              else config.phi1(m.identifier).mustBeClass
            val subtypeIsInterface = config.phi1(m.identifier).mustBeInterface
            if supertypeIsClass && subtypeIsInterface then
              (log.addWarn(s"interface $m cannot be a subtype of class $n"), Nil)
            else
              val newSupertype = NormalType(
                n.identifier,
                n.numArgs,
                ((0 until n.numArgs)
                  .map(i =>
                    (TypeParameterIndex(n.identifier, i) -> InferenceVariableFactory
                      .createInferenceVariable(
                        Left(m.identifier),
                        Nil,
                        false,
                        (0 until m.numArgs).map(TypeParameterIndex(m.identifier, _)).toSet,
                        false
                      ))
                  )
                  .toMap :: Nil).filter(!_.isEmpty)
              )
              val newPhi1 =
                if supertypeIsClass then
                  config.phi1 + (m.identifier -> config
                    .phi1(m.identifier)
                    .greedilyExtends(newSupertype)
                    .asClass)
                else
                  config.phi1 + (m.identifier -> config
                    .phi1(m.identifier)
                    .greedilyExtends(newSupertype))
              if subtypeIsInterface then
                (
                  log.addInfo(s"interface $m.identifier greedily extends interface $newSupertype"),
                  config.copy(
                    phi1 = newPhi1,
                    omega = config.omega.enqueueAll(Vector(a, IsInterfaceAssertion(n)))
                  ) :: Nil
                )
              else
                (
                  log.addInfo(s"${m.identifier} greedily extends $newSupertype"),
                  config.copy(
                    phi1 = newPhi1,
                    omega = config.omega.enqueue(a)
                  ) :: Nil
                )

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
          if subtype.numArgs == 0 then
            // subtype is raw
            NormalType(x.identifier, 0, Nil)
          else
            x.addSubstitutionLists(
              subtype.upwardProjection.substituted.substitutions.filter(x => !x.isEmpty)
            )
        )
        if tmp.isEmpty then Vector(OBJECT) else tmp
      supertypes.map(x => SubtypeAssertion(x, supertype.downwardProjection))
    )
    .map(DisjunctiveAssertion(_))
    .get
  val newConfig = config.copy(omega = config.omega.enqueue(newAssertion))
  (
    log.addInfo(s"replacing ${SubtypeAssertion(subtype, supertype)} with $newAssertion"),
    newConfig :: Nil
  )
