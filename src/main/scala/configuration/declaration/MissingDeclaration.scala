package configuration.declaration

import configuration.types.*

private type MethodTable = Map[(Vector[Type], List[Map[TTypeParameter, Type]]), Type]

/** A data structure that stores information of missing/unknown types
  */
sealed trait MissingDeclaration:
  /** Gets the type of some attribute in the type
    * @param identifier
    *   the name of the attribute
    * @param context
    *   the type arguments supplied to this type
    * @return
    *   Some(x) where x is the type of the attribute after substituting with the context of this
    *   type, None if the attribute doesn't exist
    */
  def getAttribute(
      identifier: String,
      context: List[Map[TTypeParameter, Type]]
  ): Option[Type]
  def addAttribute(identifier: String, attributeType: Type): MissingDeclaration
  def addMethod(
      identifier: String,
      paramTypes: Vector[Type],
      returnType: Type,
      context: List[Map[TTypeParameter, Type]]
  ): MissingDeclaration
  def getMethodReturnType(
      identifier: String,
      argTypes: Vector[Type],
      context: List[Map[TTypeParameter, Type]]
  ): Option[Type]

class MissingTypeDeclaration(
    val identifier: String,
    val numParams: Int = 0,
    val supertypes: Vector[Type] = Vector(),
    val attributes: Map[String, Type] = Map(),
    val methods: Map[String, MethodTable] = Map().withDefaultValue(Map())
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
      context: List[Map[TTypeParameter, Type]]
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
  def getMethodReturnType(
      identifier: String,
      argTypes: Vector[Type],
      context: List[Map[TTypeParameter, Type]]
  ): Option[Type] =
    val methodTable = methods(identifier)
    if methodTable.contains((argTypes, context)) then Some(methodTable((argTypes, context)))
    else None

  def addMethod(
      identifier: String,
      paramTypes: Vector[Type],
      returnType: Type,
      context: List[Map[TTypeParameter, Type]]
  ): MissingTypeDeclaration =
    MissingTypeDeclaration(
      this.identifier,
      numParams,
      supertypes,
      attributes,
      methods + (identifier -> (methods(identifier) + ((paramTypes, context) -> returnType)))
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
    val methods: Map[String, MethodTable] = Map().withDefaultValue(Map())
) extends MissingDeclaration:
  def getAttribute(
      identifier: String,
      context: List[Map[TTypeParameter, Type]]
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
      paramTypes: Vector[Type],
      returnType: Type,
      context: List[Map[TTypeParameter, Type]]
  ): InferenceVariableMemberTable =
    InferenceVariableMemberTable(
      typet,
      attributes,
      methods + (identifier -> (methods(identifier) + ((paramTypes, context) -> returnType)))
    )

  def getMethodReturnType(
      identifier: String,
      argTypes: Vector[Type],
      context: List[Map[TTypeParameter, Type]]
  ): Option[Type] =
    val methodTable = methods(identifier)
    if methodTable.contains((argTypes, context)) then Some(methodTable((argTypes, context)))
    else None
  override def toString =
    s"type $typet\nattributes:${attributes}\nmethods:$methods"
