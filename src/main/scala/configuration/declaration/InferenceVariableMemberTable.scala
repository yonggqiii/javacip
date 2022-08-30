package configuration.declaration

import configuration.assertions.*
import configuration.types.*
import utils.*
import scala.collection.mutable.{ArrayBuffer, Map as MutableMap}

class InferenceVariableMemberTable(
    val typet: Type,
    val attributes: Map[String, Attribute] = Map(),
    val methods: Map[String, Set[MethodWithCallSiteParameterChoices]] =
      Map().withDefaultValue(Set()),
    val mustBeClass: Boolean = false,
    val mustBeInterface: Boolean = false,
    val constraintStore: Vector[Assertion] = Vector(),
    val exclusions: Set[String] = Set()
):
  def addConstraint(asst: Assertion) =
    InferenceVariableMemberTable(
      typet,
      attributes,
      methods,
      mustBeClass,
      mustBeInterface,
      constraintStore :+ asst,
      exclusions
    )

  def addExclusion(identifier: String) =
    InferenceVariableMemberTable(
      typet,
      attributes,
      methods,
      mustBeClass,
      mustBeInterface,
      constraintStore,
      exclusions + identifier
    )

  def merge(other: InferenceVariableMemberTable): (InferenceVariableMemberTable, List[Assertion]) =
    val newAssertions = ArrayBuffer[Assertion]()
    val newAttributes = MutableMap[String, Attribute]()
    val newMethods =
      MutableMap[String, Set[MethodWithCallSiteParameterChoices]]().withDefaultValue(Set())
    if other.mustBeClass then newAssertions += typet.isClass
    if other.mustBeInterface then newAssertions += typet.isInterface
    // add all current attributes
    for (id, attr) <- attributes do newAttributes(id) = attr
    // handle attributes
    for (attrName, otherAttr) <- other.attributes do
      if !newAttributes.contains(attrName) then newAttributes(attrName) = otherAttr
      else newAssertions += newAttributes(attrName).`type` ~=~ otherAttr.`type`

    // add all methods, can be unsophisticated I guess?
    for (id, s) <- methods do newMethods(id) = newMethods(id).union(s)
    for (id, s) <- other.methods do newMethods(id) = newMethods(id).union(s)

    (
      InferenceVariableMemberTable(
        typet,
        newAttributes.toMap,
        newMethods.toMap,
        mustBeClass,
        mustBeInterface,
        constraintStore ++ other.constraintStore,
        exclusions ++ other.exclusions
      ),
      newAssertions.toList
    )

  def getAttributeType(
      identifier: String,
      context: List[Map[TTypeParameter, Type]]
  ) =
    if !attributes.contains(identifier) then None
    else Some(attributes(identifier).`type`.addSubstitutionLists(context))

  def addAttribute(
      identifier: String,
      attributeType: Type,
      isStatic: Boolean = false,
      accessModifier: AccessModifier = PUBLIC,
      isFinal: Boolean = false
  ): InferenceVariableMemberTable =
    if attributes.contains(identifier) then ???
    else
      // TODO make sure the attribute isn't overriden
      InferenceVariableMemberTable(
        typet,
        attributes + (identifier -> Attribute(
          identifier,
          attributeType,
          accessModifier,
          isStatic,
          isFinal
        )),
        methods,
        mustBeClass,
        mustBeInterface,
        constraintStore,
        exclusions
      )

  def addMethod(
      identifier: String,
      paramTypes: Vector[Type],
      returnType: Type,
      typeParameterBounds: Map[TTypeParameter, Vector[Type]],
      accessModifier: AccessModifier,
      isAbstract: Boolean,
      isStatic: Boolean,
      isFinal: Boolean,
      callSiteParameterChoices: Set[TTypeParameter]
  ): InferenceVariableMemberTable =
    InferenceVariableMemberTable(
      typet,
      attributes,
      methods + (identifier -> (methods(identifier) +
        MethodWithCallSiteParameterChoices(
          MethodSignature(identifier, paramTypes, false),
          returnType,
          typeParameterBounds,
          accessModifier,
          isAbstract,
          isStatic,
          isFinal,
          callSiteParameterChoices
        ))),
      mustBeClass,
      mustBeInterface,
      constraintStore,
      exclusions
    )

  def getMethodReturnType(
      identifier: String,
      argTypes: Vector[Type]
  ): Option[Type] =
    methods(identifier)
      .find(x => x.signature.formalParameters == argTypes)
      .map(_.returnType)

  override def toString =
    s"type $typet\nattributes:${attributes}\nmethods:$methods\nconstraints:$constraintStore"

  def replace(
      oldType: InferenceVariable,
      newType: Type
  ): (InferenceVariableMemberTable, List[Assertion]) =
    val newAssertions = ArrayBuffer[Assertion]()
    val newMethods    = MutableMap[String, Set[MethodWithCallSiteParameterChoices]]()
    for (identifier, methods) <- methods do
      val newMethodSet = ArrayBuffer[MethodWithCallSiteParameterChoices]()
      for method <- methods do
        val replacedMethod = method.replace(oldType, newType)
        newMethodSet.find(x =>
          x.callSiteParameterChoices == replacedMethod.callSiteParameterChoices && x.signature == replacedMethod.signature
        ) match
          case Some(m) =>
            newAssertions += m.returnType ~=~ replacedMethod.returnType
          case None =>
            newMethodSet += replacedMethod
      newMethods(identifier) = newMethodSet.toSet

    (
      InferenceVariableMemberTable(
        typet.replace(oldType, newType).upwardProjection,
        attributes.map(_ -> _.replace(oldType, newType)),
        newMethods.toMap,
        mustBeClass,
        mustBeInterface,
        constraintStore.map(_.replace(oldType, newType)),
        exclusions
      ),
      newAssertions.toList
    )
