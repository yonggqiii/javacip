package inference.parameterizers

import scala.collection.mutable.{Map as MutableMap, Set as MutableSet, ArrayBuffer}

import configuration.Configuration
import configuration.assertions.ImplementsMethodAssertion
import configuration.types.*
import configuration.declaration.*
import utils.*

private def checkImplements(
    log: Log,
    config: Configuration,
    x: String
): LogWithEither[List[Configuration], Configuration] =
  val decl = config.getUnderlyingDeclaration(ClassOrInterfaceType(x))
  val baseType =
    if x(0) == 'ξ' then
      TemporaryType(
        java.lang.Integer.parseInt(x.substring(1)),
        (0 until decl.numParams).map(i => TypeParameterIndex(x, i)).toVector
      )
    else
      ClassOrInterfaceType(
        x,
        (0 until decl.numParams).map(i => TypeParameterIndex(x, i)).toVector
      )
  if !decl.isAbstract then
    // concrete class
    // get the inherited methods
    val inheritedMethods = decl.getAllInheritedMethods(config)
    // from inherited methods obtain the abstract methods
    val abstractMethods = inheritedMethods
      .map((k, v) => (k -> v.filter(m => m.isAbstract)))
      .filter((k, v) => !v.isEmpty)
    //println(abstractMethods)
    // from inherited methods and the declaration's own methods get
    // the concrete methods
    val concreteInheritedMethods = (inheritedMethods
      .map((k, v) => (k -> v.filter(m => !m.isAbstract)))
      .filter((k, v) => !v.isEmpty))
    val concreteMethods = MutableMap[String, ArrayBuffer[Method]]()
    for (k, v) <- decl.methods do
      val toAdd = ArrayBuffer(v.filter(m => !m.isInstanceOf[MethodWithContext]): _*)
      if !toAdd.isEmpty then concreteMethods(k) = toAdd
    for (k, v) <- concreteInheritedMethods do
      if !concreteMethods.contains(k) then concreteMethods(k) = ArrayBuffer(v: _*)
      concreteMethods(k).addAll(v)

    // if concreteMethods.contains("addAll") then println(concreteMethods("addAll"))
    // ensure that all abstract methods are overridden
    for (k, v) <- abstractMethods do
      for abstractMethod <- v do
        if !concreteMethods.contains(k) || !concreteMethods(k).exists(m =>
            m.overrides(abstractMethod, config)
          )
        then
          // none provably override it; assert that some alpha :> decl and alpha implements
          // abstract method
          // find the alpha and ensure it does not ever implement/extend the source of the abstract method???
          // val possibilities = ArrayBuffer[Type](baseType)
          // val newAlpha = InferenceVariableFactory.createAlpha(
          //   scala.util.Left(""),
          //   Nil,
          //   false,
          //   (0 until decl.numParams).map(i => TypeParameterIndex(x, i)).toSet
          // )
          val newAssertion = ImplementsMethodAssertion(
            baseType,
            abstractMethod
          )
          // val subtypeAssertion = baseType <:~ newAlpha
          val newConfig = config asserts newAssertion
          //config.addToPsi(newAlpha).asserts(newAssertion)
          // println(s"$x must have $abstractMethod")
          return LogWithLeft(
            log.addInfo(
              s"$x must have access to an overriding implementation of $abstractMethod"
            ),
            // newConfig.copy(phi2 =
            //   newConfig.phi2 + (newAlpha -> InferenceVariableMemberTable(newAlpha))
            newConfig :: Nil
          )
  return LogWithRight(log, config)
