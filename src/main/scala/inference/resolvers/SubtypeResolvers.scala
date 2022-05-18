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
  if sub == OBJECT.substituted || sup == Bottom then
    (log.addWarn(s"$x <: $y can never be true"), Nil)
  else
    (sub, sup) match
      case (i: InferenceVariable, _) =>
        // return assertion back to config
        val originalConfig = config asserts a
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
                s"returning $a back to config as insufficient information is available"
              ),
              originalConfig :: Nil
            )
      case (ArrayType(i), ArrayType(j)) =>
        // array types are covariant
        (
          log,
          (config asserts ((i <:~ j && i.isReference && j.isReference) ||
            (i.isPrimitive && i ~=~ j))) :: Nil
        )
      // array type cannot be sub/super types of other types except the trivial ones
      case (ArrayType(_), _) =>
        (log.addWarn(s"$x can only be a subtype of other array types or Object"), Nil)
      case (_, ArrayType(_)) =>
        (log.addWarn(s"$y can only be a supertype of other array types or Bottom"), Nil)
      // left type parameter can be reduces to assertions on its bounds
      case (m: TTypeParameter, n: TTypeParameter) =>
        // already proven that m </: n
        (log.addWarn(s"$n is not one of the bounds of $m"), Nil)
      case (m: TTypeParameter, _) =>
        val source = m.containingTypeIdentifier
        val fixed  = config.getFixedDeclaration(NormalType(source, 0, Nil))
        if !fixed.isEmpty then
          val decl  = fixed.get
          val assts = DisjunctiveAssertion(decl.getAllBounds(m).map(_ <:~ y).toVector)
          (
            log.addInfo(s"expanding assertion on $x to assertions on its bounds"),
            (config asserts assts) :: Nil
          )
        else if config.phi1.contains(source) then
          (
            log.addWarn(s"bounds of $x is always Object because it is found in a missing type"),
            Nil
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
          val newAssertions =
            sub.args.zip(sup.args).map(x => x._1 <=~ x._2)
          (log, (config assertsAllOf newAssertions) :: Nil)
      // case of subtype being a missing type and supertype is a known type
      case (m: SubstitutedReferenceType, n: SubstitutedReferenceType) =>
        config upcast (m, n) match
          case Some(newType) => (log, (config asserts (newType <:~ n)) :: Nil)
          case None =>
            if config |- m.isMissing then
              // make greedily extend
              val supIsClass     = config |- n.isClass
              val subIsInterface = config |- m.isInterface
              if supIsClass && subIsInterface then
                (log.addWarn(s"interface $m cannot be a subtype of class $n"), Nil)
              else if (config |- n.isDeclared) && config.getFixedDeclaration(n).get.isFinal then
                (log.addWarn(s"$m cannot extend $n because $n is declared final"), Nil)
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
                val newPhi1 = config.phi1 + (m.identifier -> config
                  .phi1(m.identifier)
                  .greedilyExtends(newSupertype))
                val newAssts =
                  if supIsClass then Vector(a, m.isClass)
                  else if subIsInterface then Vector(a, n.isInterface)
                  else Vector(a)
                (
                  log.addInfo(s"$m greedily extends $newSupertype"),
                  config.copy(phi1 = newPhi1).assertsAllOf(newAssts) :: Nil
                )
            else
              val missingAncestors = config upcastToMissingAncestors m
              if missingAncestors.isEmpty then
                (log.addWarn(s"$m <: $n cannot be true because $m is fully declared"), Nil)
              else
                val newAsst = DisjunctiveAssertion(missingAncestors.map(_ <:~ n))
                (log, (config asserts newAsst) :: Nil)
      case (m: PrimitiveType, n: PrimitiveType) =>
        (log.addWarn(s"$m does not widen to $n"), Nil)
      // case of subtype being a normal reference type and is declared
      case (m: SubstitutedReferenceType, _) if !config.getFixedDeclaration(m).isEmpty =>
        ???
