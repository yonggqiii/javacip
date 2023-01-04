package inference.parameterizers

import configuration.Configuration
import configuration.assertions.*
import configuration.declaration.*
import configuration.types.*
import utils.*

private def finalizeConstructor(
    log: Log,
    config: Configuration,
    x: String
): LogWithEither[List[Configuration], Configuration] =
  val decl = config.getUnderlyingDeclaration(SomeClassOrInterfaceType(x))
  // partition all methods to those that are finalized, and those that are not
  val (finalizedConstructors, unfinalizedConstructors) =
    val (x, y) = decl.constructors.partition(c => !c.isInstanceOf[ConstructorWithContext])
    (x, y.map(_.asInstanceOf[ConstructorWithContext]))
  if unfinalizedConstructors.isEmpty then
    return LogWithRight(log.addInfo(s"$x constructors are all finalized"), config)
  val constructorToFinalize = unfinalizedConstructors(0)
  val newConfig = config.copy(phi1 =
    config.phi1 + (x -> decl
      .asInstanceOf[MissingTypeDeclaration]
      .removeConstructorWithContext(constructorToFinalize))
  )
  val candidates =
    finalizedConstructors
      .filter(c => c.callableWithNArgs(constructorToFinalize.signature.formalParameters.size))
      .map(_.asNArgs(constructorToFinalize.signature.formalParameters.size))
  val configsFromExistingConstructors = candidates
    .map(m => generateConstructorCompatibleAssertion(constructorToFinalize, m))
    .map(assts => newConfig.assertsAllOf(assts))
  // .map((assts, types) => newConfig.assertsAllOf(assts).addAllToPsi(types))
  val newConstructors =
    // (0 to 1)
    //   .map(numTypeParams => generateNewMethod(numTypeParams, methodToFinalize, decl))
    //   .toVector
    (normalizedConstructor(constructorToFinalize), Vector()) +: (0 to 0)
      .map(numTypeParams => generateNewConstructor(numTypeParams, constructorToFinalize, decl))
      .toVector
  val configWithAddedConstructor = newConstructors.map((c, types) =>
    (
      newConfig
        .copy(phi1 =
          newConfig.phi1 + (x -> newConfig
            .phi1(x)
            .asInstanceOf[MissingTypeDeclaration]
            .addFinalizedConstructor(c))
        )
        .addAllToPsi(types),
      c
    )
  )
  val configWithAddedAssertionsAndTypes = configWithAddedConstructor.map((cfg, c) =>
    val res = generateConstructorCompatibleAssertion(constructorToFinalize, c)
    cfg.assertsAllOf(res) //.addAllToPsi(res._2)
  )
  LogWithLeft(
    log.addInfo(
      s"combining $constructorToFinalize in $x"
    ),
    (configsFromExistingConstructors ++ configWithAddedAssertionsAndTypes).toList
  )

private def normalizedConstructor(methodToFinalize: ConstructorWithContext) =
  // for now, just directly use the constructor
  new Constructor(
    methodToFinalize.signature,
    methodToFinalize.typeParameterBounds,
    methodToFinalize.accessModifier
  )

private def generateNewConstructor(
    numTypeParams: Int,
    constructorToFinalize: ConstructorWithContext,
    decl: Declaration
): (Constructor, Vector[Type]) =
  val classTP = (0 until decl.numParams).map(i => TypeParameterIndex(decl.identifier, i))
  val numFPs  = constructorToFinalize.signature.formalParameters.size
  val newTPs =
    (0 until numTypeParams).map(i =>
      TypeParameterIndex(
        decl.identifier,
        i + (decl.numParams + decl.methodTypeParameterBounds.size)
      )
    )
  val newTPBounds: Vector[(TTypeParameter, Vector[Type])] =
    newTPs.map(x => (x, Vector())).toVector
  val newFPs = (0 until numFPs)
    .map(i =>
      InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
        scala.util.Left(""),
        Nil,
        true,
        (classTP ++ newTPs).toSet
      )
    )
    .toVector
  (
    new Constructor(
      MethodSignature(constructorToFinalize.signature.identifier, newFPs, false),
      newTPBounds,
      constructorToFinalize.accessModifier
    ),
    newFPs
  )

private def generateConstructorCompatibleAssertion(
    source: ConstructorWithContext,
    target: Constructor
): Vector[Assertion] =
  target
    .substitute(source.context)
    .callWith(source.signature.formalParameters, source.callSiteParameterChoices)
