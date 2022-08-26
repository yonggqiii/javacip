package configuration.declaration

import configuration.assertions.*
import configuration.types.*
import utils.*
import scala.collection.mutable.{ArrayBuffer, Map as MutableMap}

class MissingTypeDeclaration(
    val identifier: String,
    val numParams: Int = 0,
    val supertypes: Vector[Type] = Vector(),
    val attributes: Map[String, Attribute] = Map(),
    val methods: Map[String, Set[MethodWithContext]] = Map().withDefaultValue(Set()),
    val constructors: Set[ConstructorWithContext],
    val mustBeClass: Boolean = false,
    val mustBeInterface: Boolean = false
):
  /** Removes a supertype from this missing type declaration
    * @param t
    *   the type to extend
    */
  def removeSupertype(t: NormalType | SubstitutedReferenceType) =
    MissingTypeDeclaration(
      identifier,
      numParams,
      supertypes.filter(x => x.identifier != t.identifier),
      attributes,
      methods,
      constructors,
      mustBeClass,
      mustBeInterface
    )

  /** Make this type extend another type
    * @param t
    *   the type to extend
    */
  def greedilyExtends(t: Type) =
    MissingTypeDeclaration(
      identifier,
      numParams,
      supertypes :+ t,
      attributes,
      methods,
      constructors,
      mustBeClass,
      mustBeInterface
    )

  /** Force this type to be a class */
  def asClass =
    MissingTypeDeclaration(
      identifier,
      numParams,
      supertypes,
      attributes,
      methods,
      constructors,
      true,
      false
    )

  /** Force this type to be an interface */
  def asInterface =
    MissingTypeDeclaration(
      identifier,
      numParams,
      supertypes,
      attributes,
      methods,
      constructors,
      false,
      true
    )

  /** "Absorbs" an inference variable member table into this type declaration, producing assertions
    * whenever necessary
    * @param other
    *   the inference variable member table
    * @param newType
    *   the type which is the result the merger
    * @return
    *   the resulting declaration and assertions
    */
  def merge(
      other: InferenceVariableMemberTable,
      newType: SubstitutedReferenceType
  ): (MissingTypeDeclaration, List[Assertion]) =
    var mttable       = this
    val newAssertions = ArrayBuffer[Assertion]()
    if other.mustBeClass then newAssertions += IsClassAssertion(newType)
    if other.mustBeInterface then newAssertions += IsInterfaceAssertion(newType)
    // handle the attributes
    for (attrName, otherAttr) <- other.attributes do
      if !mttable.attributes.contains(attrName) then
        // create the attribute
        val createdAttrType = InferenceVariableFactory
          .createDisjunctiveType(
            Left(this.identifier),
            Nil,
            true,
            (0 until this.numParams).map(i => TypeParameterIndex(this.identifier, i)).toSet,
            false
          )
        mttable = mttable.addAttribute(
          attrName,
          createdAttrType,
          otherAttr.isStatic,
          otherAttr.accessModifier,
          otherAttr.isFinal
        )
      // create the equivalence
      newAssertions += EquivalenceAssertion(
        otherAttr.`type`,
        mttable
          .attributes(attrName)
          .`type`
          .addSubstitutionLists(newType.substitutions)
      )

    // handle the methods
    for (methodName, otherMethods) <- other.methods do
      val ctx = newType.substitutions
      for otherMethod <- otherMethods do
        mttable
          .methods(methodName)
          .find(x => x.signature == otherMethod.signature && x.context == ctx) match
          case Some(m) =>
            newAssertions += m.returnType ~=~ otherMethod.returnType
          case None =>
            mttable = mttable.addMethod(
              methodName,
              otherMethod.signature.formalParameters,
              otherMethod.returnType,
              otherMethod.typeParameterBounds,
              otherMethod.accessModifier,
              otherMethod.isAbstract,
              otherMethod.isStatic,
              otherMethod.isFinal,
              ctx
            )
    (mttable, newAssertions.toList)

  def replace(
      oldType: InferenceVariable,
      newType: Type
  ): (MissingTypeDeclaration, List[Assertion]) =
    val newAssertions                                          = ArrayBuffer[Assertion]()
    val newMethods: MutableMap[String, Set[MethodWithContext]] = MutableMap()
    for (identifier, methods) <- methods do
      val newMethodSet = ArrayBuffer[MethodWithContext]()
      for method <- methods do
        val replacedMethod = method.replace(oldType, newType)
        newMethodSet.find(x =>
          x.context == replacedMethod.context && x.signature == replacedMethod.signature
        ) match
          case Some(m) =>
            newAssertions += m.returnType ~=~ replacedMethod.returnType
          case None =>
            newMethodSet += replacedMethod
      newMethods(identifier) = newMethodSet.toSet

    (
      MissingTypeDeclaration(
        identifier,
        numParams,
        supertypes.map(_.replace(oldType, newType)),
        attributes.map((id, x) => id -> x.replace(oldType, newType)),
        newMethods.toMap,
        constructors,
        mustBeClass,
        mustBeInterface
      ),
      newAssertions.toList
    )

  // def replaceMissingMethodTable(
  //     mt: MissingMethodTable,
  //     oldType: InferenceVariable,
  //     newType: Type
  // ): (MissingMethodTable, List[Assertion]) =
  //   mt.foldLeft((Map(): MissingMethodTable, List[Assertion]())) {
  //     case ((newMT, assts), ((args, ctx), rt)) =>
  //       val newArgs       = args.map(_.replace(oldType, newType))
  //       val newCtx        = ctx.map(_.map((x, y) => x -> y.replace(oldType, newType)))
  //       val newReturnType = rt.replace(oldType, newType)
  //       if newMT.contains((newArgs, newCtx)) then
  //         val oldReturnType = newMT((newArgs, newCtx))
  //         (newMT, EquivalenceAssertion(oldReturnType, newReturnType) :: assts)
  //       else (newMT + ((newArgs, newCtx) -> newReturnType), assts)
  //   }

  def ofParameters(i: Int) =
    // TODO make sure you can go from raw to generic type of a particular
    // arity, cannot change positive arity.
    MissingTypeDeclaration(
      identifier,
      i,
      supertypes,
      attributes,
      methods,
      constructors,
      mustBeClass,
      mustBeInterface
    )

  // where is this used!?
  // def addSupertype(supertype: Type) =
  //   MissingTypeDeclaration(
  //     identifier,
  //     numParams,
  //     supertypes :+ supertype,
  //     attributes,
  //     methods,
  //     mustBeClass,
  //     mustBeInterface
  //   )

  /** Gets the type of an attribute
    * @param the
    *   attribute's identifier
    * @param the
    *   context in which the attribute is referenced
    * @return
    *   the type of the attribute
    */
  def getAttributeType(
      identifier: String,
      context: List[Map[TTypeParameter, Type]]
  ) =
    if !attributes.contains(identifier) then None
    else Some(attributes(identifier).`type`.addSubstitutionLists(context))

  /** Adds an attribute to this declaration
    * @param identifier
    *   the name of the attribute
    * @param attributeType
    *   the type of the attribute
    * @param isStatic
    *   whether the attribute is static
    * @param accessModifier
    *   the access modifier of the attribute
    * @param isFinal
    *   whether the attribute is final
    * @return
    *   the resulting declaration after adding the attribute
    */
  def addAttribute(
      identifier: String,
      attributeType: Type,
      isStatic: Boolean,
      accessModifier: AccessModifier = PUBLIC,
      isFinal: Boolean = false
  ): MissingTypeDeclaration =
    if attributes.contains(identifier) then ???
    else
      // TODO make sure the attribute isn't overriden
      MissingTypeDeclaration(
        this.identifier,
        numParams,
        supertypes,
        attributes + (identifier -> Attribute(
          identifier,
          attributeType,
          accessModifier,
          isStatic,
          isFinal
        )),
        methods,
        constructors,
        mustBeClass,
        mustBeInterface
      )

  /** Gets the type of a method call expression
    * @param identifier
    *   the method being called
    * @param argTypes
    *   the types passed into the method
    * @param context
    *   the context of which the method was called
    * @return
    *   the type of the expression, or none
    */
  def getMethodReturnType(
      identifier: String,
      argTypes: Vector[Type],
      context: List[Map[TTypeParameter, Type]]
  ): Option[Type] =
    val methodSet = methods(identifier)
    methodSet
      .find(x =>
        x.signature.formalParameters == argTypes &&
          x.context == context
      )
      .map(_.returnType)

  /** Adds a method to this declaration
    * @param identifier
    *   the name of the method
    * @param paramTypes
    *   the types of the arguments to this method
    * @param returnType
    *   the return type of the method
    * @param typeParameterBounds
    *   the bounds of the type parameters to this method
    * @param accessModifier
    *   the access modifier of this method
    * @param isAbstract
    *   whether the method is an abstract method
    * @param isStatic
    *   whether the method is static
    * @param isFinal
    *   whether the method is final
    * @param context
    *   the context of which this method is called
    * @return
    *   the declaration after adding this method
    */
  def addMethod(
      identifier: String,
      paramTypes: Vector[Type],
      returnType: Type,
      typeParameterBounds: Map[TTypeParameter, Vector[Type]],
      accessModifier: AccessModifier,
      isAbstract: Boolean,
      isStatic: Boolean,
      isFinal: Boolean,
      context: List[Map[TTypeParameter, Type]]
  ): MissingTypeDeclaration =
    val newMethod = MethodWithContext(
      MethodSignature(identifier, paramTypes, false),
      returnType,
      typeParameterBounds,
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      context
    )
    MissingTypeDeclaration(
      this.identifier,
      numParams,
      supertypes,
      attributes,
      methods + (identifier -> (methods(identifier) + newMethod)),
      constructors,
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
          x.context == replacedMethod.context && x.signature == replacedMethod.signature
        ) match
          case Some(m) =>
            newAssertions += m.returnType ~=~ replacedMethod.returnType
          case None =>
            newMethodSet += replacedMethod
      newMethods(identifier) = newMethodSet.toSet

    (
      MissingTypeDeclaration(
        identifier,
        numParams,
        supertypes.map(_.replace(oldType, newType)),
        attributes.map((id, x) => id -> x.replace(oldType, newType)),
        newMethods.toMap,
        constructors,
        mustBeClass,
        mustBeInterface
      ),
      newAssertions.toList
    )
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
        mustBeInterface,
        constraintStore.map(_.replace(oldType, newType)),
        exclusions
      ),
      assertions
    )

  def replaceMissingMethodTable(
      mt: MissingMethodTable,
      oldType: InferenceVariable,
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
