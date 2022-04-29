package configuration.declaration

import configuration.assertions.*
import configuration.types.*
import utils.*

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
    val methods: Map[String, MethodTable] = Map().withDefaultValue(Map()),
    val mustBeClass: Boolean = false,
    val mustBeInterface: Boolean = false
) extends MissingDeclaration:

  def merge(other: InferenceVariableMemberTable): (MissingTypeDeclaration, List[Assertion]) =
    ???

  def replace(
      oldType: ReplaceableType,
      newType: Type
  ): (MissingTypeDeclaration, List[Assertion]) =
    val (newMethods, assertions) =
      methods.foldLeft(Map(): Map[String, MethodTable], List(): List[Assertion]) {
        case ((newMethods, newAssts), (str, mt)) =>
          val (newMt, assts) = replaceMethodTable(mt, oldType, newType)
          (newMethods + (str -> newMt), assts ::: newAssts)
      }
    (
      MissingTypeDeclaration(
        identifier,
        numParams,
        supertypes.map(_.replace(oldType, newType)),
        attributes.map(_ -> _.replace(oldType, newType)),
        newMethods
      ),
      assertions
    )

  def replaceMethodTable(
      mt: MethodTable,
      oldType: ReplaceableType,
      newType: Type
  ): (MethodTable, List[Assertion]) =
    mt.foldLeft((Map(): MethodTable, List[Assertion]())) {
      case ((newMT, assts), ((args, ctx), rt)) =>
        val newArgs       = args.map(_.replace(oldType, newType))
        val newCtx        = ctx.map(_.map((x, y) => x -> y.replace(oldType, newType)))
        val newReturnType = rt.replace(oldType, newType)
        if newMT.contains((newArgs, newCtx)) then
          val oldReturnType = newMT((newArgs, newCtx))
          (newMT, EquivalenceAssertion(oldReturnType, newReturnType) :: assts)
        else (newMT + ((newArgs, newCtx) -> newReturnType), assts)
    }

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
    s"type $identifier$params:\nsupertypes:${supertypes.map(_.substituted)}\nattributes:${attributes}\nmethods:$methods"

class InferenceVariableMemberTable(
    val typet: Type,
    val attributes: Map[String, Type] = Map(),
    val methods: Map[String, MethodTable] = Map().withDefaultValue(Map()),
    val mustBeClass: Boolean = false,
    val mustBeInterface: Boolean = false
) extends MissingDeclaration:
  def merge(other: InferenceVariableMemberTable): (InferenceVariableMemberTable, List[Assertion]) =
    val (newAttributes, assertions) = other.attributes.foldLeft(attributes, List[Assertion]()) {
      case ((a, ls), (otherName, otherType)) =>
        if !a.contains(otherName) then (a + (otherName -> otherType), ls)
        else (a, EquivalenceAssertion(a(otherName), otherType) :: ls)
    }
    val (newMethods, moreAssertions) = other.methods.foldLeft(methods, List[Assertion]()) {
      case ((m, ls), (otherName, otherTable)) =>
        if !m.contains(otherName) then (m + (otherName -> otherTable), ls)
        else
          val thisTable = m(otherName)
          val (resultingTable, assts) = otherTable.foldLeft(thisTable, List[Assertion]()) {
            case ((t, ls), ((args, ctx), result)) =>
              if !t.contains((args, ctx)) then (t + ((args, ctx) -> result), ls)
              else
                val thisResult = t((args, ctx))
                (t, EquivalenceAssertion(result, thisResult) :: ls)
          }
          (m + (otherName -> resultingTable), assts ::: ls)
    }
    (InferenceVariableMemberTable(typet, newAttributes, newMethods), assertions ::: moreAssertions)

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

  def replace(
      oldType: ReplaceableType,
      newType: Type
  ): (InferenceVariableMemberTable, List[Assertion]) =
    val (newMethods, assertions) =
      methods.foldLeft(Map(): Map[String, MethodTable], List(): List[Assertion]) {
        case ((newMethods, newAssts), (str, mt)) =>
          val (newMt, assts) = replaceMethodTable(mt, oldType, newType)
          (newMethods + (str -> newMt), assts ::: newAssts)
      }
    (
      InferenceVariableMemberTable(
        typet.replace(oldType, newType).upwardProjection,
        attributes.map(_ -> _.replace(oldType, newType)),
        newMethods
      ),
      assertions
    )

  def replaceMethodTable(
      mt: MethodTable,
      oldType: ReplaceableType,
      newType: Type
  ): (MethodTable, List[Assertion]) =
    mt.foldLeft((Map(): MethodTable, List[Assertion]())) {
      case ((newMT, assts), ((args, ctx), rt)) =>
        val newArgs       = args.map(_.replace(oldType, newType))
        val newCtx        = ctx.map(_.map((x, y) => x -> y.replace(oldType, newType)))
        val newReturnType = rt.replace(oldType, newType)
        if newMT.contains((newArgs, newCtx)) then
          val oldReturnType = newMT((newArgs, newCtx))
          (newMT, EquivalenceAssertion(oldReturnType, newReturnType) :: assts)
        else (newMT + ((newArgs, newCtx) -> newReturnType), assts)
    }
