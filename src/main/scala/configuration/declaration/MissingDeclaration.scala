package configuration.declaration

import configuration.types.*

sealed trait MissingDeclaration:
  def getAttribute(
      identifier: String,
      context: List[Map[TypeParameterIndex, Type]]
  ): Option[Type]
  def addAttribute(identifier: String, attributeType: Type): MissingDeclaration
  def addMethod(
      identifier: String,
      methodSignature: MethodSignature
  ): MissingDeclaration

class MissingTypeDeclaration(
    val identifier: String,
    val numParams: Int = 0,
    val supertypes: Vector[Type] = Vector(),
    val attributes: Map[String, Type] = Map(),
    val methods: Map[String, Vector[MethodSignature]] =
      Map().withDefaultValue(Vector[MethodSignature]())
) extends MissingDeclaration:
  def ofParameters(i: Int) =
    // TODO make sure you can go from raw to generic type of a particular
    // arity, cannot change positive arity.
    MissingTypeDeclaration(identifier, i, supertypes, attributes, methods)

  def addSupertype(supertype: Type) =
    MissingTypeDeclaration(
      identifier,
      numParams,
      supertypes :+ supertype,
      attributes,
      methods
    )

  def getAttribute(
      identifier: String,
      context: List[Map[TypeParameterIndex, Type]]
  ) =
    if !attributes.contains(identifier) then None
    else Some(attributes(identifier).addSubstitutionLists(context))

  def addAttribute(
      identifier: String,
      attributeType: Type
  ): MissingTypeDeclaration =
    if attributes.contains(identifier) then ???
    else
      // TODO make sure the attribute isn't overriden
      MissingTypeDeclaration(
        this.identifier,
        numParams,
        supertypes,
        attributes + (identifier -> attributeType),
        methods
      )

  def addMethod(
      identifier: String,
      methodSignature: MethodSignature
  ): MissingTypeDeclaration =
    MissingTypeDeclaration(
      identifier,
      numParams,
      supertypes,
      attributes,
      methods.map((i, v) =>
        if i != identifier then (i -> v) else (i -> (v :+ methodSignature))
      )
    )
  override def toString =
    val params =
      if numParams > 0 then
        "<" + ((0 until numParams)
          .map((x: Int) => (84 + x).toChar.toString)
          .mkString(", ")) + ">"
      else ""
    s"type $identifier$params:\nsupertypes:${supertypes}\nattributes:${attributes}\nmethods:$methods"

class InferenceVariableMemberTable(
    val typet: Type,
    val attributes: Map[String, Type] = Map(),
    val methods: Map[String, Vector[MethodSignature]] =
      Map().withDefaultValue(Vector[MethodSignature]())
) extends MissingDeclaration:
  def getAttribute(
      identifier: String,
      context: List[Map[TypeParameterIndex, Type]]
  ) =
    if !attributes.contains(identifier) then None
    else Some(attributes(identifier).addSubstitutionLists(context))

  def addAttribute(
      identifier: String,
      attributeType: Type
  ): InferenceVariableMemberTable =
    if attributes.contains(identifier) then ???
    else
      // TODO make sure the attribute isn't overriden
      InferenceVariableMemberTable(
        typet,
        attributes + (identifier -> attributeType),
        methods
      )

  def addMethod(
      identifier: String,
      methodSignature: MethodSignature
  ): InferenceVariableMemberTable =
    InferenceVariableMemberTable(
      typet,
      attributes,
      methods.map((i, v) =>
        if i != identifier then (i -> v) else (i -> (v :+ methodSignature))
      )
    )
  override def toString =
    s"type $typet\nattributes:${attributes}\nmethods:$methods"
