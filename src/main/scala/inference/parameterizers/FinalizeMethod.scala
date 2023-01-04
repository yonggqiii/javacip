package inference.parameterizers

import configuration.Configuration
import configuration.assertions.*
import configuration.declaration.*
import configuration.types.*
import utils.*

private def finalizeMethod(
    log: Log,
    config: Configuration,
    x: String
): LogWithEither[List[Configuration], Configuration] =
  val decl = config.getUnderlyingDeclaration(SomeClassOrInterfaceType(x))
  // partition all methods to those that are finalized, and those that are not
  val finalizedMethods = decl.getAccessibleMethods(config, PRIVATE)
  val unfinalizedMethods = decl.methods
    .map((k, v) =>
      (k -> v.filter(m => m.isInstanceOf[MethodWithContext]).map(_.asInstanceOf[MethodWithContext]))
    )
    .filter((k, v) => !v.isEmpty)
    .map((k, v) => v)
    .flatMap(v => v)
    .toVector
  if unfinalizedMethods.isEmpty then
    return LogWithRight(log.addInfo(s"$x methods are all finalized"), config)
  val methodToFinalize = unfinalizedMethods(0)
  val newConfig = config.copy(phi1 =
    config.phi1 + (x -> decl
      .asInstanceOf[MissingTypeDeclaration]
      .removeMethodWithContext(methodToFinalize))
  )
  val candidates =
    if !finalizedMethods.contains(methodToFinalize.signature.identifier) then Vector()
    else
      finalizedMethods(methodToFinalize.signature.identifier)
        .filter(m => m.callableWithNArgs(methodToFinalize.signature.formalParameters.size))
        .map(_.asNArgs(methodToFinalize.signature.formalParameters.size))
  val configsFromExistingMethods = candidates
    .map(m => generateCompatibleAssertion(methodToFinalize, m))
    .map(assts => newConfig.assertsAllOf(assts))
  // .map((assts, types) => newConfig.assertsAllOf(assts).addAllToPsi(types))
  val newMethods =
    // (0 to 1)
    //   .map(numTypeParams => generateNewMethod(numTypeParams, methodToFinalize, decl))
    //   .toVector
    (normalizedMethod(methodToFinalize), Vector()) +: (0 to 1)
      .map(numTypeParams => generateNewMethod(numTypeParams, methodToFinalize, decl))
      .toVector
  val configWithAddedMethod = newMethods.map((mtd, types) =>
    (
      newConfig
        .copy(phi1 =
          newConfig.phi1 + (x -> newConfig
            .phi1(x)
            .asInstanceOf[MissingTypeDeclaration]
            .addFinalizedMethod(mtd))
        )
        .addAllToPsi(types),
      mtd
    )
  )
  val configWithAddedAssertionsAndTypes = configWithAddedMethod.map((cfg, mtd) =>
    val res = generateCompatibleAssertion(methodToFinalize, mtd)
    cfg.assertsAllOf(res) //.addAllToPsi(res._2)
  )
  LogWithLeft(
    log.addInfo(
      s"combining $methodToFinalize in $x"
    ),
    (configsFromExistingMethods ++ configWithAddedAssertionsAndTypes).toList
  )

private def normalizedMethod(methodToFinalize: MethodWithContext) =
  // for now, just directly use the method
  new Method(
    methodToFinalize.signature,
    methodToFinalize.returnType,
    methodToFinalize.typeParameterBounds,
    methodToFinalize.accessModifier,
    methodToFinalize.isAbstract,
    methodToFinalize.isStatic,
    methodToFinalize.isFinal
  )

private def generateNewMethod(
    numTypeParams: Int,
    methodToFinalize: MethodWithContext,
    decl: Declaration
): (Method, Vector[Type]) =
  val classTP = (0 until decl.numParams).map(i => TypeParameterIndex(decl.identifier, i))
  val numFPs  = methodToFinalize.signature.formalParameters.size
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
  val newReturnType = InferenceVariableFactory.createVoidableDisjunctiveType(
    scala.util.Left(""),
    Nil,
    true,
    (classTP ++ newTPs).toSet
  )
  (
    new Method(
      MethodSignature(methodToFinalize.signature.identifier, newFPs, false),
      newReturnType,
      newTPBounds,
      methodToFinalize.accessModifier,
      methodToFinalize.isAbstract,
      methodToFinalize.isStatic,
      methodToFinalize.isFinal
    ),
    newFPs :+ newReturnType
  )

private def generateCompatibleAssertion(
    source: MethodWithContext,
    target: Method
): Vector[Assertion] =
  val res = target
    .substitute(source.context)
    .callWith(source.signature.formalParameters, source.callSiteParameterChoices)
  res._1 :+ ((res._2.upwardProjection <:~ source.returnType.upwardProjection) || (res._2.upwardProjection <<~= source.returnType.upwardProjection))
// val methodTypeParameters = target.typeParameterBounds.map(_._1)
// val newIVs = (0 until methodTypeParameters.size).map(i =>
//   InferenceVariableFactory.createDisjunctiveType(
//     scala.util.Left(""),
//     Nil,
//     true,
//     source.callSiteParameterChoices,
//     true
//   )
// )
// val methodTPMap = methodTypeParameters.zip(newIVs).toMap
// val boundsAssertions = newIVs
//   .zip(target.typeParameterBounds.map(_._2))
//   .flatMap((v, b) => b.map(t => v <:~ (t.substitute(methodTPMap).substitute(source.context))))
// val fpAssertions = source.signature.formalParameters
//   .zip(
//     target.signature.formalParameters.map(t =>
//       t substitute methodTPMap substitute (source.context)
//     )
//   )
//   .map(_ =:~ _)
// val returnTypeAssertion =
//   target.returnType.substitute(methodTPMap).substitute(source.context) =:~ source.returnType
// (fpAssertions ++ boundsAssertions :+ returnTypeAssertion, newIVs.toVector)
