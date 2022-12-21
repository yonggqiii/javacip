package configuration.declaration

import configuration.assertions.*
import configuration.types.*
import utils.*
import scala.collection.mutable.{ArrayBuffer, Map as MutableMap}

/** The declaration of a missing type
  * @param identifier
  *   the name of the type
  * @param typeParameterBounds
  *   the bounds of each type parameter
  * @param supertypes
  *   the supertypes of this type
  * @param attributes
  *   the attributes of this type
  * @param methods
  *   the methods of this type
  * @param constructors
  *   the constructors of this type
  * @param mustBeClass
  *   whether this type must be a class
  * @param mustBeInterface
  *   whether this type must be an interface
  */
class MissingTypeDeclaration(
    val identifier: String,
    val typeParameterBounds: Vector[Vector[Type]] = Vector(),
    val mustBeClass: Boolean = false,
    val mustBeInterface: Boolean = false,
    val supertypes: Vector[ClassOrInterfaceType] = Vector(),
    val methodTypeParameterBounds: Map[String, Vector[ClassOrInterfaceType]] = Map(),
    val attributes: Map[String, Attribute] = Map(),
    val methods: Map[String, Vector[Method]] = Map().withDefaultValue(Vector()),
    val constructors: Vector[Constructor] = Vector()
) extends Declaration:
  def erased = new MissingTypeDeclaration(
    identifier,
    Vector(),
    mustBeClass,
    mustBeInterface,
    supertypes.map(_.raw),
    Map(),
    attributes.map((s, a) => (s -> getAttributeErasure(a))),
    methods.map((s, v) => (s -> v.map(m => getMethodErasure(m)))),
    constructors.map(getConstructorErasure(_))
  )

  def getErasure(`type`: Type): Type = `type`.upwardProjection match
    case x: TTypeParameter =>
      if x.containingTypeIdentifier != this.identifier then ???
      OBJECT
    case x: PrimitiveType        => x
    case ArrayType(base)         => ArrayType(getErasure(base))
    case x: ClassOrInterfaceType => x.raw
    case x                       => x

  def getAttributeErasure(a: Attribute): Attribute =
    Attribute(
      a.identifier,
      getErasure(a.`type`),
      a.accessModifier,
      a.isStatic,
      a.isFinal
    )
  def getMethodErasure(m: Method): Method =
    if m.isInstanceOf[MethodWithContext] then m
    else
      new Method(
        m.signature.erased(this),
        getErasure(m.returnType),
        Map(),
        m.accessModifier,
        m.isAbstract,
        m.isStatic,
        m.isFinal
      )

  def getConstructorErasure(c: Constructor): Constructor =
    if c.isInstanceOf[ConstructorWithContext] then c
    else new Constructor(c.signature.erased(this), Map(), c.accessModifier)

  def getLeftmostReferenceTypeBoundOfTypeParameter(t: Type): ClassOrInterfaceType = ???
  def substitute(function: Substitution): MissingTypeDeclaration =
    new MissingTypeDeclaration(
      identifier,
      typeParameterBounds.map(v => v.map(t => t.substitute(function))),
      mustBeClass,
      mustBeInterface,
      supertypes.map(_.substitute(function)),
      methodTypeParameterBounds.map((s, v) => (s -> v.map(t => t.substitute(function)))),
      attributes.map((s, a) => (s -> a.substitute(function))),
      methods.map((id, v) => (id -> v.map(m => m.substitute(function)))),
      constructors.map(_.substitute(function))
    )
  def getDirectAncestors: Vector[ClassOrInterfaceType] = supertypes
  //  if it mustn't be an interface then best not to assume that it is
  val isInterface = mustBeInterface
  // if it musn't be an interface then there is no reason for it to be abstract
  val isAbstract = mustBeInterface
  // there is never a reason for this type declaration to be false
  val isFinal = false

  val isClass = mustBeClass

  /** The number of parameters of this type */
  val numParams: Int = typeParameterBounds.size

  /** Removes a supertype from this missing type declaration
    * @param t
    *   the type to extend
    */
  def removeSupertype(t: ClassOrInterfaceType) =
    MissingTypeDeclaration(
      identifier,
      typeParameterBounds,
      mustBeClass,
      mustBeInterface,
      supertypes.filter(x => x.identifier != t.identifier),
      methodTypeParameterBounds,
      attributes,
      methods,
      constructors
    )

  /** Make this type extend another type
    * @param t
    *   the type to extend
    */
  def greedilyExtends(t: ClassOrInterfaceType) =
    MissingTypeDeclaration(
      identifier,
      typeParameterBounds,
      mustBeClass,
      mustBeInterface,
      supertypes :+ t,
      methodTypeParameterBounds,
      attributes,
      methods,
      constructors
    )

  /** Force this type to be a class */
  def asClass =
    MissingTypeDeclaration(
      identifier,
      typeParameterBounds,
      true,
      false,
      supertypes,
      methodTypeParameterBounds,
      attributes,
      methods,
      constructors
    )

  /** Force this type to be an interface */
  def asInterface =
    MissingTypeDeclaration(
      identifier,
      typeParameterBounds,
      false,
      true,
      supertypes,
      methodTypeParameterBounds,
      attributes,
      methods,
      constructors
    )

  /** "Absorbs" an inference variable member table into this type declaration, producing assertions
    * whenever necessary
    * @param other
    *   the inference variable member table
    * @param newType
    *   the type which is the result of the merger
    * @return
    *   the resulting declaration and assertions
    */
  def merge(
      other: InferenceVariableMemberTable,
      newType: ClassOrInterfaceType
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
          .createDisjunctiveTypeWithPrimitives(
            Left(this.identifier),
            Nil,
            true,
            (0 until this.numParams).map(i => TypeParameterIndex(this.identifier, i)).toSet
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
          .substitute(newType.expansion._2)
      )

    // handle the methods
    for (methodName, otherMethods) <- other.methods do
      val ctx = newType.expansion._2
      for otherMethod <- otherMethods do
        if mttable.methods.contains(methodName) then
          mttable
            .methods(methodName)
            .find(x =>
              x.signature == otherMethod.signature && x.isInstanceOf[MethodWithContext] &&
                x.asInstanceOf[MethodWithContext].context == ctx &&
                x.asInstanceOf[MethodWithContext]
                  .callSiteParameterChoices == otherMethod.callSiteParameterChoices
            ) match
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
                otherMethod.callSiteParameterChoices,
                ctx
              )
        else
          mttable = mttable.addMethod(
            methodName,
            otherMethod.signature.formalParameters,
            otherMethod.returnType,
            otherMethod.typeParameterBounds,
            otherMethod.accessModifier,
            otherMethod.isAbstract,
            otherMethod.isStatic,
            otherMethod.isFinal,
            otherMethod.callSiteParameterChoices,
            ctx
          )

    (mttable, newAssertions.toList)

  /** Replace all occurrences of an inference variable in this declaration with another type
    * @param oldType
    *   the type to replace
    * @param the
    *   type after replacement
    * @return
    *   the resulting declaration after replacement
    */
  def replace(
      oldType: InferenceVariable,
      newType: Type
  ): (MissingTypeDeclaration, List[Assertion]) =
    // simply do a naive replacement
    (
      MissingTypeDeclaration(
        identifier,
        typeParameterBounds.map(v => v.map(t => t.replace(oldType, newType))),
        mustBeClass,
        mustBeInterface,
        supertypes.map(_.replace(oldType, newType)),
        methodTypeParameterBounds.map((k, v) => (k -> v.map(t => t.replace(oldType, newType)))),
        attributes.map((id, x) => id -> x.replace(oldType, newType)),
        methods.map((id, v) => (id -> v.map(m => m.replace(oldType, newType)))),
        constructors.map(c => c.replace(oldType, newType))
      ),
      Nil
    )
  // val newAssertions = ArrayBuffer[Assertion]()
  // val newMethods    = MutableMap[String, Vector[Method]]()
  // for (identifier, mmethods) <- methods do
  //   val newMethodSet = ArrayBuffer[MethodWithContext]()
  //   for method <- mmethods do
  //     val replacedMethod = method.replace(oldType, newType)
  //     newMethodSet.find(x =>
  //       x.context == replacedMethod.context && x.signature == replacedMethod.signature
  //     ) match
  //       case Some(m) =>
  //         newAssertions += m.returnType ~=~ replacedMethod.returnType
  //       case None =>
  //         newMethodSet += replacedMethod
  //   newMethods(identifier) = newMethodSet.toSet
  // (
  //   MissingTypeDeclaration(
  //     identifier,
  //     typeParameterBounds.map(v => v.map(t => t.replace(oldType, newType))),
  //     mustBeClass,
  //     mustBeInterface,
  //     supertypes.map(_.replace(oldType, newType)),
  //     methodTypeParameterBounds.map((k, v) => (k -> v.map(t => t.replace(oldType, newType)))),
  //     attributes.map((id, x) => id -> x.replace(oldType, newType)),
  //     newMethods.toMap,
  //     constructors.map(c => c.replace(oldType, newType))
  //   ),
  //   newAssertions.toList
  // )

  /** Change the parameters of this declaration
    * @param i
    *   the new number of parameters
    * @return
    *   the resulting declaration with i parameters
    */
  def ofParameters(i: Int) =
    // TODO make sure you can go from raw to generic type of a particular
    // arity, cannot change positive arity.
    MissingTypeDeclaration(
      identifier,
      (0 until i).map(x => Vector()).toVector,
      mustBeClass,
      mustBeInterface,
      supertypes,
      methodTypeParameterBounds,
      attributes,
      methods,
      constructors
    )

  /** Gets the type of an attribute
    * @param identifier
    *   the attribute's identifier
    * @param function
    *   the context in which the attribute is referenced
    * @return
    *   the type of the attribute
    */
  def getAttributeType(
      identifier: String,
      function: Substitution
  ) =
    if !attributes.contains(identifier) then None
    else Some(attributes(identifier).substitute(function).`type`)

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
        typeParameterBounds,
        mustBeClass,
        mustBeInterface,
        supertypes,
        methodTypeParameterBounds,
        attributes + (identifier -> Attribute(
          identifier,
          attributeType,
          accessModifier,
          isStatic,
          isFinal
        )),
        methods,
        constructors
      )

  /** Gets the type of a method call expression
    * @param identifier
    *   the method being called
    * @param argTypes
    *   the types passed into the method
    * @param paramChoices
    *   the parameter choices attached to the method
    * @param context
    *   the context of which the method was called
    * @return
    *   the type of the expression, or none
    */
  def getMethodReturnType(
      identifier: String,
      argTypes: Vector[Type],
      paramChoices: Set[TTypeParameter],
      context: Substitution
  ): Option[Type] =
    if !methods.contains(identifier) then return None
    val methodSet = methods(identifier)
    methodSet
      .find(x =>
        x.signature.formalParameters == argTypes &&
          (!x.isInstanceOf[MethodWithContext] || (x
            .asInstanceOf[MethodWithContext]
            .context == context && x
            .asInstanceOf[MethodWithContext]
            .callSiteParameterChoices == paramChoices))
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
      callSiteParameterChoices: Set[TTypeParameter],
      context: Substitution
  ): MissingTypeDeclaration =
    val newMethod = MethodWithContext(
      MethodSignature(identifier, paramTypes, false),
      returnType,
      typeParameterBounds,
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      callSiteParameterChoices,
      context
    )

    val oldMethods =
      if methods.contains(identifier) then methods(identifier) else Vector[Method]()
    val newMethods = oldMethods :+ newMethod

    MissingTypeDeclaration(
      this.identifier,
      this.typeParameterBounds,
      mustBeClass,
      mustBeInterface,
      supertypes,
      methodTypeParameterBounds,
      attributes,
      methods + (identifier -> newMethods),
      constructors
    )

  override def toString =
    val ab = ArrayBuffer[String]()
    if mustBeInterface then ab += "interface"
    else if mustBeClass then ab += "class"
    else ab += "type"
    val args =
      if numParams == 0 then ""
      else
        "<" + (0 until numParams)
          .map(i =>
            TypeParameterIndex(identifier, i).toString +
              (if typeParameterBounds(i).size == 0 then ""
               else " extends " + typeParameterBounds(i).mkString(" & "))
          )
          .mkString(", ") + ">"
    ab += identifier + args
    if supertypes.size > 0 then
      ab += "inherits"
      ab += supertypes.mkString(", ")
    val header = ab.mkString(" ")
    val res    = ArrayBuffer[String](header)
    if attributes.size > 0 then
      res += "Attributes:"
      for (k, v) <- attributes do res += s"\t$v"
    if methods.size > 0 then
      res += "Methods:"
      for (k, v) <- methods do for m <- v do res += s"\t$m"
    if constructors.size > 0 then
      res += "Constructors:"
      for c <- constructors do res += s"\t$c"
    res.mkString("\n")

  def getAllReferenceTypeBoundsOfTypeParameter(
      `type`: Type,
      exclusions: Set[Type] = Set()
  ): Vector[ClassOrInterfaceType] = ???
