package configuration.declaration

import configuration.Configuration
import configuration.assertions.*
import configuration.basetraits.*
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
    val supertypes: Vector[SomeClassOrInterfaceType] = Vector(),
    val methodTypeParameterBounds: Map[String, Vector[Type]] = Map(),
    val attributes: Map[String, Attribute] = Map(),
    val methods: Map[String, Vector[Method]] = Map().withDefaultValue(Vector()),
    val constructors: Vector[Constructor] = Vector()
) extends Declaration:

  def copy(
      identifier: String = this.identifier,
      typeParameterBounds: Vector[Vector[Type]] = this.typeParameterBounds,
      mustBeClass: Boolean = this.mustBeClass,
      mustBeInterface: Boolean = this.mustBeInterface,
      supertypes: Vector[SomeClassOrInterfaceType] = this.supertypes,
      methodTypeParameterBounds: Map[String, Vector[Type]] = this.methodTypeParameterBounds,
      attributes: Map[String, Attribute] = this.attributes,
      methods: Map[String, Vector[Method]] = this.methods,
      constructors: Vector[Constructor] = this.constructors
  ) = new MissingTypeDeclaration(
    identifier,
    typeParameterBounds,
    mustBeClass,
    mustBeInterface,
    supertypes,
    methodTypeParameterBounds,
    attributes,
    methods,
    constructors
  )

  def fix(config: Configuration): FixedDeclaration =
    if supertypes.filter(x => config |- x.isClass).size > 1 then ???
    val extendedTypes =
      if !mustBeClass then supertypes else supertypes.filter(x => config |- x.isClass)
    val implementedTypes             = supertypes.filter(x => !extendedTypes.contains(x))
    val newMethodTypeParameterBounds = MutableMap[String, Vector[Type]]()
    for (k, v) <- methods do
      for m <- v do
        for tp <- m.typeParameterBounds do
          val typeparam = tp._1.fix
          val bounds    = tp._2.map(_.fix)
          newMethodTypeParameterBounds(typeparam.identifier) = bounds
    for c <- constructors do
      for tp <- c.typeParameterBounds do
        val typeparam = tp._1.fix
        val bounds    = tp._2.map(_.fix)
        newMethodTypeParameterBounds(typeparam.identifier) = bounds
    new FixedDeclaration(
      SomeClassOrInterfaceType(identifier).fix.identifier,
      typeParameterBounds.map(v => v.fix),
      false,
      !mustBeClass,
      !mustBeClass,
      extendedTypes.fix,
      implementedTypes.fix,
      newMethodTypeParameterBounds.toMap,
      attributes >->= (_.fix),
      methods >->= (_.fix),
      constructors.fix
    )

  def addFinalizedMethod(m: Method): MissingTypeDeclaration =
    val mm = if this.isAbstract && !m.isStatic then m.makeAbstract else m
    copy(
      methodTypeParameterBounds =
        methodTypeParameterBounds ++ mm.typeParameterBounds.map((x, y) => (x.toString -> y)),
      methods = methods >+ mm
      // if !methods.contains(mm.signature.identifier) then
      //   methods + (mm.signature.identifier    -> Vector(mm))
      // else methods + (mm.signature.identifier -> (methods(mm.signature.identifier) :+ mm))
    )
  // new MissingTypeDeclaration(
  //   identifier,
  //   typeParameterBounds,
  //   mustBeClass,
  //   mustBeInterface,
  //   supertypes,
  //   methodTypeParameterBounds ++ m.typeParameterBounds.map((x, y) => (x.toString -> y)),
  //   attributes,
  //   if !methods.contains(m.signature.identifier) then
  //     methods + (m.signature.identifier    -> Vector(m))
  //   else methods + (m.signature.identifier -> (methods(m.signature.identifier) :+ m)),
  //   constructors
  // )

  def addFinalizedConstructor(c: Constructor): MissingTypeDeclaration = copy(
    methodTypeParameterBounds =
      methodTypeParameterBounds ++ c.typeParameterBounds.map((x, y) => (x.toString -> y)),
    constructors = constructors :+ c
  )
  // new MissingTypeDeclaration(
  //   identifier,
  //   typeParameterBounds,
  //   mustBeClass,
  //   mustBeInterface,
  //   supertypes,
  //   methodTypeParameterBounds ++ c.typeParameterBounds.map((x, y) => (x.toString -> y)),
  //   attributes,
  //   methods,
  //   constructors :+ c
  // )

  def removeConstructorWithContext(c: ConstructorWithContext): MissingTypeDeclaration =
    new MissingTypeDeclaration(
      identifier,
      typeParameterBounds,
      mustBeClass,
      mustBeInterface,
      supertypes,
      methodTypeParameterBounds,
      attributes,
      methods,
      constructors.filter(other =>
        if !other.isInstanceOf[ConstructorWithContext] then true
        else
          val otherConstructor = other.asInstanceOf[ConstructorWithContext]
          c.context != otherConstructor.context || c.signature != otherConstructor.signature || c.callSiteParameterChoices != otherConstructor.callSiteParameterChoices || c.typeParameterBounds != otherConstructor.typeParameterBounds
      )
    )

  def removeMethodWithContext(m: MethodWithContext): MissingTypeDeclaration =
    if !methods.contains(m.signature.identifier) then this
    else
      new MissingTypeDeclaration(
        identifier,
        typeParameterBounds,
        mustBeClass,
        mustBeInterface,
        supertypes,
        methodTypeParameterBounds,
        attributes,
        methods + (m.signature.identifier -> methods(m.signature.identifier).filter(other =>
          if !other.isInstanceOf[MethodWithContext] then true
          else
            val othermcc = other.asInstanceOf[MethodWithContext]
            othermcc.signature != m.signature || othermcc.returnType != m.returnType ||
            othermcc.context != m.context || othermcc.callSiteParameterChoices != m.callSiteParameterChoices || othermcc.typeParameterBounds != m.typeParameterBounds
        )),
        constructors
      )

  def combineWithTemporaryTypeDeclaration(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType,
      that: MissingTypeDeclaration
  ): Option[(MissingTypeDeclaration, List[Assertion])] =
    if this.numParams != that.numParams || !that.constructors.isEmpty then return None
    val newAssertions = ArrayBuffer[Assertion]()
    val substitution: Map[TTypeParameter, Type] = (0 until this.numParams)
      .map(i =>
        (TypeParameterIndex(that.identifier, i) -> (TypeParameterIndex(this.identifier, i)))
      )
      .toMap
    //val newDecl = that.substitute(substitution)
    if that.mustBeClass && this.mustBeInterface then return None
    if that.mustBeInterface && this.mustBeClass then return None
    val newSupertypes = ArrayBuffer[SomeClassOrInterfaceType]()
    newSupertypes.addAll(this.supertypes)
    for (i <- that.supertypes.map(_.combineTemporaryType(oldType, newType)))
      do
        newSupertypes.find(x => x.identifier == i.identifier) match
          case None    => newSupertypes.append(i)
          case Some(x) => newAssertions += (x ~=~ i)
    val newAttributes = MutableMap[String, Attribute]()
    for (k, v) <- attributes do newAttributes(k) = v
    for (k, v) <- that.attributes.map((k, v) => (k -> v.combineTemporaryType(oldType, newType))) do
      if newAttributes.contains(k) then newAssertions += (newAttributes(k).`type` ~=~ v.`type`)
      else newAttributes(k) = v
    val newMethods = MutableMap[String, ArrayBuffer[Method]]()
    for (k, v) <- methods do
      newMethods(k) = ArrayBuffer()
      newMethods(k).addAll(v)
    for (k, v) <- that.methods do
      if !newMethods.contains(k) then newMethods(k) = ArrayBuffer()
      newMethods(k).addAll(v.map(x => x.combineTemporaryType(oldType, newType)))
    Some(
      (
        new MissingTypeDeclaration(
          identifier,
          typeParameterBounds,
          this.mustBeClass || that.mustBeClass,
          this.mustBeInterface || that.mustBeInterface,
          newSupertypes.toVector,
          methodTypeParameterBounds,
          newAttributes.toMap,
          newMethods.map((k, v) => (k -> v.toVector)).toMap,
          constructors
        ),
        newAssertions.toList
      )
    )
  def erased = new MissingTypeDeclaration(
    identifier,
    Vector(),
    mustBeClass,
    mustBeInterface,
    supertypes.raw,
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
        Vector(),
        m.accessModifier,
        m.isAbstract,
        m.isStatic,
        m.isFinal
      )

  def getConstructorErasure(c: Constructor): Constructor =
    if c.isInstanceOf[ConstructorWithContext] then c
    else new Constructor(c.signature.erased(this), Vector(), c.accessModifier)

  def getLeftmostReferenceTypeBoundOfTypeParameter(t: Type): SomeClassOrInterfaceType = ???
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
  def getDirectAncestors: Vector[SomeClassOrInterfaceType] =
    if identifier == "java.lang.Object" || identifier == "Object" then Vector()
    else
      val a = supertypes
      if a.isEmpty then Vector(OBJECT)
      else a
  //  if it mustn't be an interface then best not to assume that it is
  val isInterface = mustBeInterface
  // if it is not a class then it is abstract
  val isAbstract = !mustBeClass
  // there is never a reason for this type declaration to be false
  val isFinal = false

  val isClass = mustBeClass

  /** The number of parameters of this type */
  val numParams: Int = typeParameterBounds.size

  /** Removes a supertype from this missing type declaration
    * @param t
    *   the type to extend
    */
  def removeSupertype(t: SomeClassOrInterfaceType) =
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
  def greedilyExtends(t: SomeClassOrInterfaceType) =
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
      newType: SomeClassOrInterfaceType
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
        supertypes.map(
          _.replace(oldType, newType).asInstanceOf[SomeClassOrInterfaceType]
        ),
        methodTypeParameterBounds.map((k, v) => (k -> v.map(t => t.replace(oldType, newType)))),
        attributes.map((id, x) => id -> x.replace(oldType, newType)),
        methods.map((id, v) => (id -> v.map(m => m.replace(oldType, newType)))),
        constructors.map(c => c.replace(oldType, newType))
      ),
      Nil
    )

  /** Change the parameters of this declaration
    * @param i
    *   the new number of parameters
    * @return
    *   the resulting declaration with i parameters
    */
  def ofParameters(i: Int) =
    // TODO make sure you can go from raw to generic type of a particular
    // arity, cannot change positive arity.
    if i == 0 && numParams > 0 then this
    else if i == 0 && numParams == 0 then this
    else if i != 0 && numParams == 0 then
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
    else if i != 0 && i == numParams then this
    else ???

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

  def addConstructor(
      paramTypes: Vector[Type],
      typeParameterBounds: Vector[(TTypeParameter, Vector[Type])],
      accessModifier: AccessModifier,
      callSiteParameterChoices: Set[TTypeParameter],
      context: Substitution
  ): MissingTypeDeclaration =
    val newConstructor = new ConstructorWithContext(
      MethodSignature(this.identifier, paramTypes, false),
      typeParameterBounds,
      accessModifier,
      callSiteParameterChoices,
      context
    )

    new MissingTypeDeclaration(
      this.identifier,
      this.typeParameterBounds,
      this.mustBeClass,
      this.mustBeInterface,
      this.supertypes,
      this.methodTypeParameterBounds,
      this.attributes,
      this.methods,
      this.constructors :+ newConstructor
    )

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
      typeParameterBounds: Vector[(TTypeParameter, Vector[Type])],
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
