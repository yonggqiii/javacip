package inference.resolvers

import scala.collection.mutable.ArrayBuffer

import configuration.Configuration
import configuration.assertions.*
import configuration.declaration.*
import configuration.types.*
import utils.*
import configuration.declaration.PUBLIC

private[inference] def resolveImplementsMethodAssertion(
    log: Log,
    config: Configuration,
    asst: ImplementsMethodAssertion
): (Log, List[Configuration]) =
  val ImplementsMethodAssertion(t, m, canBeAbstract) = asst
  t match
    case x: Alpha =>
      addToConstraintStore(
        x,
        asst,
        log,
        if canBeAbstract then config else (config asserts x.isClass)
      )
    case x: SomeClassOrInterfaceType =>
      if config |- x.isMissing then
        // println(asst)
        // missing type; suppose one of the relevant methods is actually an overriding declaration
        val existingOverrider = generateOverrideAssertion(config, x, m)
        // otherwise add such method to the underlying declaration
        val ud           = config.getUnderlyingDeclaration(x).asInstanceOf[MissingTypeDeclaration]
        val (_, context) = x.expansion
        val methodName   = m.signature.identifier
        val methodFormalParameters     = m.signature.formalParameters
        val methodReturnType           = m.returnType
        val methodTypeParametersBounds = m.typeParameterBounds
        // create the method type parameters
        val newMethodTypeParameterBounds = (0 until methodTypeParametersBounds.size)
          .map(i =>
            (TypeParameterIndex(
              x.identifier,
              i + ud.numParams + ud.methodTypeParameterBounds.size
            ) -> ArrayBuffer[Type]())
          )
          .toVector
        // the substitution for method type parameter mapping
        val tpMap: Map[TTypeParameter, TTypeParameter] =
          newMethodTypeParameterBounds.map(_._1).zip(methodTypeParametersBounds.map(_._1)).toMap
        // get the type parameter choices for disjunctive type creation
        val tpChoices: Set[TTypeParameter] = ((0 until ud.numParams).map(i =>
          TypeParameterIndex(x.identifier, i)
        ) ++ newMethodTypeParameterBounds.map(_._1)).toSet
        // new assertions to generate
        val newAssertions = ArrayBuffer[Assertion]()
        // new types being generated
        val newTypes = ArrayBuffer[Type]()
        // create and assert equality of bounds
        for i <- 0 until methodTypeParametersBounds.size do
          val (_, bounds) = methodTypeParametersBounds(i)
          for b <- bounds do
            val generatedType = InferenceVariableFactory.createDisjunctiveType(
              scala.util.Left(ud.identifier),
              Nil,
              true,
              tpChoices,
              false
            )
            newTypes += generatedType
            val generatedAssertion = (generatedType substitute tpMap substitute context) ~=~ b
            newAssertions += generatedAssertion
            newMethodTypeParameterBounds(i)._2 += generatedType
        // create and assert equality of formal parameters
        val newMethodFormalParameters = (0 until methodFormalParameters.size).map(i =>
          InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
            scala.util.Left(ud.identifier),
            Nil,
            true,
            tpChoices
          )
        )
        newTypes ++= newMethodFormalParameters
        val formalParametersAssertion = newMethodFormalParameters
          .map(x => x substitute tpMap substitute context)
          .zip(methodFormalParameters)
          .map(_ ~=~ _)
        newAssertions ++= formalParametersAssertion
        // create and assert return type substitutability of return type
        val newMethodReturnType = InferenceVariableFactory.createVoidableDisjunctiveType(
          scala.util.Left(ud.identifier),
          Nil,
          true,
          tpChoices
        )
        newTypes += newMethodReturnType
        val substitutedReturnType = newMethodReturnType substitute tpMap substitute context
        newAssertions += (substitutedReturnType ~=~ methodReturnType || substitutedReturnType <:~ methodReturnType)
        val newMethod = new Method(
          MethodSignature(methodName, newMethodFormalParameters.toVector, m.signature.hasVarArgs),
          newMethodReturnType,
          newMethodTypeParameterBounds.map((k, v) => (k -> v.toVector)),
          PUBLIC,
          false,
          false,
          false
        )
        val newUD = ud.asInstanceOf[MissingTypeDeclaration].addFinalizedMethod(newMethod)
        val newConfig = config.copy(
          phi1 = config.phi1 + (ud.identifier -> newUD),
          psi = config.psi ++ newTypes
        ) assertsAllOf newAssertions
        (
          log.addInfo(
            s"either ${x.identifier} already has an overriding method for $m, or a new method is added to ${x.identifier} such that it overrides it:",
            s"new method: $newMethod\nnew assertions: ${newAssertions
              .mkString(", ")}\nnew types: ${newTypes.mkString(", ")}"
          ),
          newConfig :: Nil
          //(config asserts existingOverrider) :: newConfig :: Nil
        )
      else
        // declared type
        (
          log.addInfo(s"one method in ${x.identifier} overrides $m since it is declared"),
          (config asserts generateOverrideAssertion(config, x, m)) :: Nil
        )

private def generateOverrideAssertion(
    config: Configuration,
    x: SomeClassOrInterfaceType,
    m: Method
): DisjunctiveAssertion =
  val ud                         = config.getUnderlyingDeclaration(x)
  val (_, context)               = x.expansion
  val methodName                 = m.signature.identifier
  val methodFormalParameters     = m.signature.formalParameters
  val methodReturnType           = m.returnType
  val methodTypeParametersBounds = m.typeParameterBounds
  val allUDMethods               = ud.getAccessibleMethods(config, PUBLIC)
  if !allUDMethods.contains(methodName) then return DisjunctiveAssertion(Vector())
  val potentialCandidates = allUDMethods(methodName)
    .filter(candidate =>
      !candidate.isInstanceOf[MethodWithContext] &&
        candidate.typeParameterBounds.size == methodTypeParametersBounds.size &&
        candidate.typeParameterBounds.map(_._2.size) == methodTypeParametersBounds.map(_._2.size) &&
        candidate.signature.formalParameters.size == methodFormalParameters.size &&
        m.signature.hasVarArgs == candidate.signature.hasVarArgs &&
        candidate.accessModifier == PUBLIC &&
        !candidate.isAbstract
    )
  if potentialCandidates.isEmpty then return DisjunctiveAssertion(Vector())
  val possibilities = potentialCandidates.map(candidate =>
    val candidateTypeParameters = candidate.typeParameterBounds.map(_._1)
    val methodTypeParameters    = methodTypeParametersBounds.map(_._1)
    val mtpMap                  = candidateTypeParameters.zip(methodTypeParameters).toMap
    val newCandidate            = candidate.reorderTypeParameters(mtpMap).substitute(context)
    ConjunctiveAssertion( // asserting equivalence of bounds
      newCandidate.typeParameterBounds
        .flatMap(_._2)
        .zip(methodTypeParametersBounds.flatMap(_._2))
        .map((a, b) => a ~=~ b)
    ) &&
    ConjunctiveAssertion( // asserting equivalence of formal parameters
      newCandidate.signature.formalParameters.zip(methodFormalParameters).map((a, b) => a ~=~ b)
      // asserting return-type-substitutability
    ) && (newCandidate.returnType ~=~ methodReturnType || newCandidate.returnType <:~ methodReturnType)
  )
  DisjunctiveAssertion(possibilities)
