package configuration.declaration

import configuration.assertions.*
import configuration.types.*
import utils.*
import scala.collection.mutable.ArrayBuffer

private type MissingMethodTable = Map[(Vector[Type], List[Map[TTypeParameter, Type]]), Type]

class MissingTypeDeclaration(
    val identifier: String,
    val numParams: Int = 0,
    val supertypes: Vector[Type] = Vector(),
    val attributes: Map[String, Type] = Map(),
    val methods: Map[String, MissingMethodTable] = Map().withDefaultValue(Map()),
    val mustBeClass: Boolean = false,
    val mustBeInterface: Boolean = false
):

  def greedilyExtends(t: Type) =
    MissingTypeDeclaration(
      identifier,
      numParams,
      supertypes :+ t,
      attributes,
      methods,
      mustBeClass,
      mustBeInterface
    )

  def asClass =
    MissingTypeDeclaration(identifier, numParams, supertypes, attributes, methods, true, false)

  def asInterface =
    MissingTypeDeclaration(identifier, numParams, supertypes, attributes, methods, false, true)

  def merge(
      other: InferenceVariableMemberTable,
      newType: SubstitutedReferenceType
  ): (MissingTypeDeclaration, List[Assertion]) =
    var mttable       = this
    val newAssertions = ArrayBuffer[Assertion]()
    if other.mustBeClass then newAssertions += IsClassAssertion(newType)
    if other.mustBeInterface then newAssertions += IsInterfaceAssertion(newType)
    // handle the attributes
    for (attrName, attrType) <- other.attributes do
      if mttable.attributes.contains(attrName) then
        // List<t>.x = List<T>.x[T -> t]
        newAssertions += EquivalenceAssertion(
          attrType,
          mttable.attributes(attrName).addSubstitutionLists(newType.substitutions)
        )
      else
        val createdAttrType = InferenceVariableFactory
          .createInferenceVariable(
            Left(this.identifier),
            Nil,
            true,
            (0 until this.numParams).map(i => TypeParameterIndex(this.identifier, i)).toSet,
            false
          )
        mttable = mttable.addAttribute(
          attrName,
          createdAttrType
        )

        newAssertions += EquivalenceAssertion(
          attrType,
          createdAttrType.addSubstitutionLists(newType.substitutions)
        )
    // handle the methods
    for (methodName, mmt) <- other.methods do
      for ((params, context), rt) <- mmt do
        val realContext = newType.substitutions ::: context
        if !mttable.methods(methodName).contains((params, realContext)) then
          // don't need the call site method param choices since
          // the return type should encode for that already
          mttable = mttable.addMethod(methodName, params, rt, realContext)
        else
          newAssertions += EquivalenceAssertion(
            rt,
            mttable.methods(methodName)((params, realContext))
          )
    (mttable, newAssertions.toList)
  def replace(
      oldType: ReplaceableType,
      newType: Type
  ): (MissingTypeDeclaration, List[Assertion]) =
    val (newMethods, assertions) =
      methods.foldLeft(Map(): Map[String, MissingMethodTable], List(): List[Assertion]) {
        case ((newMethods, newAssts), (str, mt)) =>
          val (newMt, assts) = replaceMissingMethodTable(mt, oldType, newType)
          (newMethods + (str -> newMt), assts ::: newAssts)
      }
    (
      MissingTypeDeclaration(
        identifier,
        numParams,
        supertypes.map(_.replace(oldType, newType)),
        attributes.map(_ -> _.replace(oldType, newType)),
        newMethods,
        mustBeClass,
        mustBeInterface
      ),
      assertions
    )

  def replaceMissingMethodTable(
      mt: MissingMethodTable,
      oldType: ReplaceableType,
      newType: Type
  ): (MissingMethodTable, List[Assertion]) =
    mt.foldLeft((Map(): MissingMethodTable, List[Assertion]())) {
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
    MissingTypeDeclaration(
      identifier,
      i,
      supertypes,
      attributes,
      methods,
      mustBeClass,
      mustBeInterface
    )

  def addSupertype(supertype: Type) =
    MissingTypeDeclaration(
      identifier,
      numParams,
      supertypes :+ supertype,
      attributes,
      methods,
      mustBeClass,
      mustBeInterface
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
        methods,
        mustBeClass,
        mustBeInterface
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
      methods + (identifier -> (methods(identifier) + ((paramTypes, context) -> returnType))),
      mustBeClass,
      mustBeInterface
    )

  override def toString =
    val params =
      if numParams > 0 then
        "<" + ((0 until numParams)
          .map((x: Int) => (84 + x).toChar.toString)
          .mkString(", ")) + ">"
      else ""
    val typeName = if mustBeClass then "class" else if mustBeInterface then "interface" else "type"
    s"$typeName $identifier$params:\nsupertypes:${supertypes.map(_.substituted)}\nattributes:${attributes}\nmethods:$methods"

class InferenceVariableMemberTable(
    val typet: Type,
    val attributes: Map[String, Type] = Map(),
    val methods: Map[String, MissingMethodTable] = Map().withDefaultValue(Map()),
    val mustBeClass: Boolean = false,
    val mustBeInterface: Boolean = false
):
  def merge(other: InferenceVariableMemberTable): (InferenceVariableMemberTable, List[Assertion]) =
    val (newAttributes, assertions) = other.attributes.foldLeft(attributes, List[Assertion]()) {
      case ((a, ls), (otherName, otherType)) =>
        if !a.contains(otherName) then (a + (otherName -> otherType), ls)
        else (a, EquivalenceAssertion(otherType, a(otherName)) :: ls)
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
    (
      InferenceVariableMemberTable(typet, newAttributes, newMethods, mustBeClass, mustBeInterface),
      assertions ::: moreAssertions
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
  ): InferenceVariableMemberTable =
    if attributes.contains(identifier) then ???
    else
      // TODO make sure the attribute isn't overriden
      InferenceVariableMemberTable(
        typet,
        attributes + (identifier -> attributeType),
        methods,
        mustBeClass,
        mustBeInterface
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
      methods + (identifier -> (methods(identifier) + ((paramTypes, context) -> returnType))),
      mustBeClass,
      mustBeInterface
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
      methods.foldLeft(Map(): Map[String, MissingMethodTable], List(): List[Assertion]) {
        case ((newMethods, newAssts), (str, mt)) =>
          val (newMt, assts) = replaceMissingMethodTable(mt, oldType, newType)
          (newMethods + (str -> newMt), assts ::: newAssts)
      }
    (
      InferenceVariableMemberTable(
        typet.replace(oldType, newType).upwardProjection,
        attributes.map(_ -> _.replace(oldType, newType)),
        newMethods,
        mustBeClass,
        mustBeInterface
      ),
      assertions
    )

  def replaceMissingMethodTable(
      mt: MissingMethodTable,
      oldType: ReplaceableType,
      newType: Type
  ): (MissingMethodTable, List[Assertion]) =
    mt.foldLeft((Map(): MissingMethodTable, List[Assertion]())) {
      case ((newMT, assts), ((args, ctx), rt)) =>
        val newArgs       = args.map(_.replace(oldType, newType))
        val newCtx        = ctx.map(_.map((x, y) => x -> y.replace(oldType, newType)))
        val newReturnType = rt.replace(oldType, newType)
        if newMT.contains((newArgs, newCtx)) then
          val oldReturnType = newMT((newArgs, newCtx))
          (newMT, EquivalenceAssertion(oldReturnType, newReturnType) :: assts)
        else (newMT + ((newArgs, newCtx) -> newReturnType), assts)
    }
