package configuration.declaration

import scala.collection.mutable.ArrayBuffer
import com.github.javaparser.ast.AccessSpecifier
import com.github.javaparser.ast.Modifier
import com.github.javaparser.ast.NodeList
import configuration.Configuration
import configuration.types.*
import configuration.assertions.*
import configuration.basetraits.*

/** An access modifier in a Java program */
sealed trait AccessModifier:
  def <(that: AccessModifier): Boolean
  def <=(that: AccessModifier): Boolean = this < that || this == that

/** Companion class to [[configuration.declaration.AccessModifier]] */
case object AccessModifier:
  /** Gives the correct AccessModifier given a JavaParser AccessSpecifier
    * @param a
    *   the JavaParser access specifier
    * @return
    *   the corresponding AccessModifier
    */
  def apply(a: AccessSpecifier) = a match
    case AccessSpecifier.PACKAGE_PRIVATE => DEFAULT
    case AccessSpecifier.PRIVATE         => PRIVATE
    case AccessSpecifier.PROTECTED       => PROTECTED
    case AccessSpecifier.PUBLIC          => PUBLIC

/** The public access modifier */
case object PUBLIC extends AccessModifier:
  override def toString                = "public"
  def <(that: AccessModifier): Boolean = false

/** The package-private (default) access modifier */
case object DEFAULT extends AccessModifier:
  override def toString                = ""
  def <(that: AccessModifier): Boolean = that == PROTECTED || that == PUBLIC

/** The protected access modifier */
case object PROTECTED extends AccessModifier:
  override def toString                = "protected"
  def <(that: AccessModifier): Boolean = that == PUBLIC

/** The private access modifier */
case object PRIVATE extends AccessModifier:
  override def toString                = "private"
  def <(that: AccessModifier): Boolean = that == DEFAULT || that == PROTECTED || that == PUBLIC

private trait MemberOps[+A <: MemberOps[A]]
    extends Replaceable[A],
      Combineable[A],
      Substitutable[A],
      Reorderable[A],
      Fixable[A]

/** An attribute in a class/interface
  * @param identifier
  *   the name of the attribute
  * @param type
  *   the type of the attribute
  * @param accessModifier
  *   the access modifier to the attribute
  * @param isStatic
  *   whether the attribute is static
  * @param isFinal
  *   whether the attribute is final
  */
final case class Attribute(
    identifier: String,
    `type`: Type,
    accessModifier: AccessModifier,
    isStatic: Boolean,
    isFinal: Boolean
) extends MemberOps[Attribute]:
  /** Fix this attribute
    * @return
    *   the fixed version of the attribute
    */
  override def fix: Attribute =
    copy(`type` = `type`.fix)

  /** Combines any temporary type in this attribute with a new type
    * @param oldType
    *   the old temporary type to combine
    * @param newType
    *   the result of combining the temporary type
    * @return
    *   the resulting attribute
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): Attribute =
    copy(`type` = `type`.combineTemporaryType(oldType, newType))

  /** Perform a substitution on this attribute
    * @param function
    *   the substitution function
    * @return
    *   the resulting attribute
    */
  override def substitute(function: Substitution): Attribute =
    copy(`type` = `type`.substitute(function))

  /** Get all the modifiers of the attribute as a `NodeList[Modifier]`
    * @return
    *   a [[com.github.javaparser.ast.NodeList]] of all the modifiers
    */
  def getNodeListModifiers: NodeList[Modifier] =
    val res: NodeList[Modifier] = NodeList()
    accessModifier match
      case DEFAULT   => ()
      case PUBLIC    => res.add(Modifier.publicModifier())
      case PROTECTED => res.add(Modifier.protectedModifier())
      case PRIVATE   => res.add(Modifier.privateModifier())
    if isStatic then res.add(Modifier.staticModifier())
    if isFinal then res.add(Modifier.finalModifier())
    res

  /** Replaces the type of this attribute with another type
    * @param i
    *   the type to replace
    * @param t
    *   the type after replacement
    * @return
    *   the attribute object after replacement
    */
  override def replace(i: InferenceVariable, t: Type): Attribute =
    copy(`type` = `type`.replace(i, t))

  override def toString =
    val ab = ArrayBuffer[String]()
    ab += accessModifier.toString
    if isStatic then ab += "static"
    if isFinal then ab += "final"
    ab += `type`.toString
    ab += identifier
    ab.filter(_.length > 0).mkString(" ")

  /** Reorders the type parameters in this attribute by replacing all the type parameters given a
    * scheme
    * @param scheme
    *   the scheme of reordering type parameters
    * @return
    *   the resulting attribute
    */
  override def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Attribute =
    copy(`type` = `type`.reorderTypeParameters(scheme))

/** The signature of a method
  * @param identifier
  *   the name of the method
  * @param formalParameters
  *   the formal parameters of the method
  * @param hasVarArgs
  *   whether the last parameter is a VarArg
  */
final case class MethodSignature(
    identifier: String,
    formalParameters: Vector[Type],
    hasVarArgs: Boolean
) extends MemberOps[MethodSignature]:
  // make sure the values are passed in to the constructor properly
  if hasVarArgs && formalParameters.size == 0 then
    throw new IllegalArgumentException(s"$identifier cannot have VarArgs but no parameters!")

  /** Fix this method signature
    * @return
    *   the resulting method signature
    */
  override def fix: MethodSignature = copy(formalParameters = formalParameters.fix)

  /** Combines any temporary types in this signature with another type
    * @param oldType
    *   the temporary type to combine
    * @param newType
    *   the resulting type after combining types
    * @return
    *   the resulting method signature
    */
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): MethodSignature =
    copy(formalParameters = formalParameters.combineTemporaryType(oldType, newType))

  /** Transform this method signature into one with n parameters
    * @param n
    *   the number of parameters to transform this method signature into
    * @return
    *   the resulting method signature
    */
  def asNArgs(n: Int): MethodSignature =
    if !callableWithNArgs(n) then throw new IllegalArgumentException(s"$this cannot have $n args!")
    if formalParameters.size == n then this
    else
      val ab: ArrayBuffer[Type] = ArrayBuffer()
      ab.addAll(formalParameters)
      while ab.size != n do ab.addOne(formalParameters(formalParameters.size - 1))
      MethodSignature(identifier, ab.toVector, hasVarArgs)

  /** Reorders the type parameters in this method signature by replacing all the type parameters
    * given a scheme
    * @param scheme
    *   the scheme of reordering type parameters
    * @return
    *   the resulting method signature
    */
  override def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): MethodSignature =
    copy(formalParameters = formalParameters.reorderTypeParameters(scheme))

  /** Perform a substitution on the types in this method signature
    * @param function
    *   the substitution function
    * @return
    *   the resulting method signature
    */
  override def substitute(function: Substitution): MethodSignature =
    copy(formalParameters = formalParameters.substitute(function))

  /** Determines if this signature is callable with n arguments
    * @param n
    *   n
    * @return
    *   true if it is callable, false otherwise
    */
  def callableWithNArgs(n: Int) =
    formalParameters.size == n || (formalParameters.size < n && hasVarArgs)

  /** Replaces all the occurrences of some inference variable with another type
    * @param i
    *   the inference variable to replace
    * @param t
    *   the type after replacement
    * @return
    *   the resulting method signature from replacement
    */
  override def replace(i: InferenceVariable, t: Type): MethodSignature =
    copy(formalParameters = formalParameters.replace(i, t))

  override def toString =
    identifier +
      "(" +
      formalParameters.mkString(", ") +
      (if hasVarArgs then "..." else "") +
      ")"

  /** Erase the signature into its raw types
    * @param decl
    *   the declaration to obtain any type bounds from
    * @return
    *   the resulting method signature
    */
  def erased(decl: Declaration) =
    if hasVarArgs then
      val newParams: ArrayBuffer[Type] = ArrayBuffer(formalParameters: _*)
      newParams(newParams.size - 1) = ArrayType(newParams(newParams.size - 1))
      MethodSignature(identifier, newParams.toVector.map(decl.getErasure(_)), false)
    else MethodSignature(identifier, formalParameters.map(decl.getErasure(_)), false)

/** A method or constructor */
sealed trait MethodLike extends MemberOps[MethodLike]:
  def fix: MethodLike

  /** The signature of the method */
  val signature: MethodSignature

  /** The type parameter bounds of the method */
  val typeParameterBounds: Vector[(TTypeParameter, Vector[Type])]

  /** The access modifier tied to the method */
  val accessModifier: AccessModifier

  /** Determines if this signature is callable with n arguments
    * @param n
    *   n
    * @returns
    *   true if it is callable, false otherwise
    */
  def callableWithNArgs(n: Int): Boolean = signature.callableWithNArgs(n)

  // /** Replaces all the occurrences of some inference variable with another type
  //   * @param i
  //   *   the inference variable to replace
  //   * @param t
  //   *   the type after replacement
  //   * @return
  //   *   the resulting method-like from replacement
  //   */
  // def replace(i: InferenceVariable, t: Type): MethodLike

  // /** Combines any occurrence of a temporary type in this MethodLike with another type
  //   * @param oldType
  //   *   the temporary type to combine
  //   * @param newType
  //   *   the resulting type after combining the temporary type
  //   * @return
  //   *   the resulting MethodLike
  //   */
  // def combineTemporaryType(oldType: TemporaryType, newType: SomeClassOrInterfaceType): MethodLike

  // /** Performs a substitution on the types in this MethodLike
  //   * @param function
  //   *   the substitution function
  //   * @return
  //   *   the resulting MethodLike
  //   */
  // def substitute(function: Substitution): MethodLike

  /** Determines if the access level of this method is at least some other access level, based on
    * the following ordering--private, package-private, protected, public
    * @param accessLevel
    * @return
    *   true if the method-like is at least as accessible as the provided access level
    */
  def accessLevelAtLeast(accessLevel: AccessModifier): Boolean =
    val vec = Vector(PRIVATE, DEFAULT, PROTECTED, PUBLIC)
    vec.indexOf(accessLevel) <= vec.indexOf(accessModifier)

  // /** Reorders the type parameters of this method-like given a scheme by replacing the type
  //   * parameters
  //   * @param scheme
  //   *   the reordering scheme
  //   * @return
  //   *   the resulting method-like
  //   */
  // def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): MethodLike

  /** Transforms this MethodLike into one that has n parameters
    * @param n
    *   the number of parameters
    * @return
    *   the resulting MethodLike
    */
  def asNArgs(n: Int): MethodLike

/** A method
  * @param signature
  *   the method's signature
  * @param returnType
  *   the method's return type
  * @param typeParameterBounds
  *   the bounds of the type parameters of the methods, if any
  * @param accessModifier
  *   the access modifier tied to the method
  * @param isAbstract
  *   whether the method is abstract
  * @param isStatic
  *   whether the method is static
  * @param isFinal
  *   whether the method is final
  */
class Method(
    val signature: MethodSignature,
    val returnType: Type,
    val typeParameterBounds: Vector[(TTypeParameter, Vector[Type])],
    val accessModifier: AccessModifier,
    val isAbstract: Boolean,
    val isStatic: Boolean,
    val isFinal: Boolean
) extends MethodLike,
      MemberOps[Method]:
  def copy(
      signature: MethodSignature = this.signature,
      returnType: Type = this.returnType,
      typeParameterBounds: Vector[(TTypeParameter, Vector[Type])] = this.typeParameterBounds,
      accessModifier: AccessModifier = this.accessModifier,
      isAbstract: Boolean = this.isAbstract,
      isStatic: Boolean = this.isStatic,
      isFinal: Boolean = this.isFinal
  ): Method = new Method(
    signature,
    returnType,
    typeParameterBounds,
    accessModifier,
    isAbstract,
    isStatic,
    isFinal
  )

  def isUnreasonable: Boolean =
    // check for unused type parameters
    val fp        = signature.formalParameters
    val allBounds = typeParameterBounds.flatMap(_._2)
    if typeParameterBounds
        .map(_._1)
        .exists(t =>
          !(fp.exists(p => t ⊆ p)) && !(allBounds.exists(b => t ⊆ b) && !(t ⊆ returnType))
        )
    then return true
    // check if return type is standalone
    if !returnType.isInstanceOf[TTypeParameter] then return false
    if !typeParameterBounds.map(_._1).contains(returnType) then return false
    !(fp ++ allBounds).exists(x => returnType ⊆ x)

  def fix: Method =
    copy(
      signature = signature.fix,
      returnType = returnType.fix,
      typeParameterBounds = typeParameterBounds.map((k, v) => (k.fix -> (v.fix)))
    )

  def callWith(
      args: Vector[Type],
      callSiteParameterChoices: Set[TTypeParameter]
  ): (Vector[Assertion], Type) =
    if !this.callableWithNArgs(args.size) then ???
    val tps = typeParameterBounds.map(_._1)
    val tpMap = tps
      .map(x =>
        (x -> InferenceVariableFactory.createJavaInferenceVariable(callSiteParameterChoices, true))
      )
      .toMap
    val subtpBounds =
      typeParameterBounds.map((k, v) => (k.substitute(tpMap) -> v.substitute(tpMap)))
    val boundsAssts  = subtpBounds.flatMap((k, v) => v.map(b => k <:~ b))
    val formalParams = signature.asNArgs(args.size).formalParameters.substitute(tpMap)
    val paramAssts   = args.zip(formalParams).map((l, r) => l =:~ r)
    (boundsAssts ++ paramAssts, returnType.substitute(tpMap))

  def getNodeListModifiers: NodeList[Modifier] =
    val res: NodeList[Modifier] = NodeList()
    accessModifier match
      case DEFAULT   => ()
      case PUBLIC    => res.add(Modifier.publicModifier())
      case PROTECTED => res.add(Modifier.protectedModifier())
      case PRIVATE   => res.add(Modifier.privateModifier())
    if isStatic then res.add(Modifier.staticModifier())
    if isFinal then res.add(Modifier.finalModifier())
    res
  // make sure the modifiers make sense
  if isAbstract && (isStatic || isFinal) then
    throw new java.lang.IllegalArgumentException(
      s"$signature cannot be abstract and (static or final) at the same time!"
    )

  def overrides(m: Method, config: Configuration): Boolean =
    if m.typeParameterBounds.size != typeParameterBounds.size then return false
    // if accessModifier < m.accessModifier then return false
    val reordering = m.typeParameterBounds.map(_._1).zip(typeParameterBounds.map(_._1)).toMap
    val reorderedM = m.reorderTypeParameters(reordering)
    return (typeParameterBounds == reorderedM.typeParameterBounds && reorderedM.signature == signature && (config |- ((returnType <:~ reorderedM.returnType) || (returnType ~=~ reorderedM.returnType))))

  def combineTemporaryType(oldType: TemporaryType, newType: SomeClassOrInterfaceType): Method =
    new Method(
      signature.combineTemporaryType(oldType, newType),
      returnType = returnType.combineTemporaryType(oldType, newType),
      typeParameterBounds.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.map(t =>
          t.combineTemporaryType(oldType, newType)
        ))
      ),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal
    )

  def asNArgs(n: Int): Method = copy(signature = signature.asNArgs(n))

  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Method =
    new Method(
      signature.reorderTypeParameters(scheme),
      returnType.reorderTypeParameters(scheme),
      typeParameterBounds.map((k, v) =>
        (k.reorderTypeParameters(scheme) -> v.map(t => t.reorderTypeParameters(scheme)))
      ),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal
    )

  def substitute(function: Substitution): Method =
    new Method(
      signature.substitute(function),
      returnType.substitute(function),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.substitute(function)))),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal
    )

  /** Replaces all the occurrences of some inference variable with another type
    * @param i
    *   the inference variable to replace
    * @param t
    *   the type after replacement
    * @return
    *   the resulting method from replacement
    */
  def replace(i: InferenceVariable, t: Type): Method =
    new Method(
      signature.replace(i, t),
      returnType.replace(i, t),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.replace(i, t)))),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal
    )

  override def toString =
    val ab = ArrayBuffer[String]()
    ab += accessModifier.toString
    if isStatic then ab += "static"
    if isFinal then ab += "final"
    if isAbstract then ab += "abstract"
    val tpString =
      if typeParameterBounds.isEmpty then ""
      else
        "<" + typeParameterBounds
          .map((k, v) =>
            if v.isEmpty then k.toString
            else k.toString + " extends " + v.mkString(" & ")
          )
          .mkString(", ") + ">"
    ab += tpString
    ab += returnType.toString
    ab += signature.toString
    ab.filter(_.length > 0).mkString(" ")

/** Companion object to [[configuration.declaration.Method]] */
object Method:
  /** Alternative constructor for [[configuration.declaration.Method]] objects
    * @param identifier
    *   the name of the method
    * @param formalParameters
    *   the formal parameters of the method
    * @param returnType
    *   the method's return type
    * @param typeParameterBounds
    *   the bounds of the type parameters of the methods, if any
    * @param accessModifier
    *   the access modifier tied to the method
    * @param isAbstract
    *   whether the method is abstract
    * @param isStatic
    *   whether the method is static
    * @param isFinal
    *   whether the method is final
    * @param hasVarArgs
    *   whether the method has vas variadic arguments
    * @return
    *   the new method
    */
  def apply(
      identifier: String,
      formalParameters: Vector[Type],
      returnType: Type,
      typeParameterBounds: Vector[(TTypeParameter, Vector[Type])],
      accessModifier: AccessModifier,
      isAbstract: Boolean,
      isStatic: Boolean,
      isFinal: Boolean,
      hasVarArgs: Boolean
  ): Method =
    new Method(
      MethodSignature(identifier, formalParameters, hasVarArgs),
      returnType,
      typeParameterBounds,
      accessModifier,
      isAbstract,
      isStatic,
      isFinal
    )

/** A method with some context
  * @param _signature
  *   the signature of the method
  * @param _returnType
  *   the return type of the method
  * @param _typeParameterBounds
  *   the bounds of the type parameters
  * @param _accessModifier
  *   the access modifier of the method
  * @param _isStatic
  *   whether the method is static
  * @param _isFinal
  *   whether the method is final
  */
class MethodWithContext(
    _signature: MethodSignature,
    _returnType: Type,
    _typeParameterBounds: Vector[(TTypeParameter, Vector[Type])],
    _accessModifier: AccessModifier,
    _isAbstract: Boolean,
    _isStatic: Boolean,
    _isFinal: Boolean,
    _callSiteParameterChoices: Set[TTypeParameter],
    val context: Substitution
) extends MethodWithCallSiteParameterChoices(
      _signature,
      _returnType,
      _typeParameterBounds,
      _accessModifier,
      _isAbstract,
      _isStatic,
      _isFinal,
      _callSiteParameterChoices
    ):

  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): MethodWithContext =
    new MethodWithContext(
      signature.combineTemporaryType(oldType, newType),
      returnType.combineTemporaryType(oldType, newType),
      typeParameterBounds.map { case (k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.map(t =>
          t.combineTemporaryType(oldType, newType)
        ))
      },
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      callSiteParameterChoices.map(_.combineTemporaryType(oldType, newType)),
      context.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.combineTemporaryType(oldType, newType))
      )
    )

  override def asNArgs(n: Int): MethodWithContext =
    new MethodWithContext(
      signature.asNArgs(n),
      returnType,
      typeParameterBounds,
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      callSiteParameterChoices,
      context
    )

  override def toString =
    super.toString + " with context " + "{" + context
      .map((k, v) => s"$k -> $v")
      .mkString(", ") + "}"

  override def replace(i: InferenceVariable, t: Type): MethodWithContext =
    new MethodWithContext(
      signature.replace(i, t),
      returnType.replace(i, t),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.replace(i, t)))),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      callSiteParameterChoices,
      context.map((tp, tt) => (tp -> tt.replace(i, t)))
    )

  override def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): MethodWithContext =
    new MethodWithContext(
      signature.reorderTypeParameters(scheme),
      returnType.reorderTypeParameters(scheme),
      typeParameterBounds.map((k, v) =>
        (k.reorderTypeParameters(scheme) -> v.map(t => t.reorderTypeParameters(scheme)))
      ),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      callSiteParameterChoices.map(_.reorderTypeParameters(scheme)),
      context.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
    )

/** A method containing the parameter choices at the site of the call
  * @param _signature
  *   the signature of the method
  * @param _returnType
  *   the return type of the method
  * @param _typeParameterBounds
  *   the bounds of the type parameters attached to the method
  * @param _accessModifier
  *   the access modifier of the method
  * @param _isAbstract
  *   whether the method is abstract
  * @param _isFinal
  *   whether the method is final
  * @param callSiteParameterChoices
  *   the parameter choices available at the call site
  */
class MethodWithCallSiteParameterChoices(
    _signature: MethodSignature,
    _returnType: Type,
    _typeParameterBounds: Vector[(TTypeParameter, Vector[Type])],
    _accessModifier: AccessModifier,
    _isAbstract: Boolean,
    _isStatic: Boolean,
    _isFinal: Boolean,
    val callSiteParameterChoices: Set[TTypeParameter]
) extends Method(
      _signature,
      _returnType,
      _typeParameterBounds,
      _accessModifier,
      _isAbstract,
      _isStatic,
      _isFinal
    ):
  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): MethodWithCallSiteParameterChoices =
    new MethodWithCallSiteParameterChoices(
      signature.combineTemporaryType(oldType, newType),
      returnType.combineTemporaryType(oldType, newType),
      typeParameterBounds.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.map(t =>
          t.combineTemporaryType(oldType, newType)
        ))
      ),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      callSiteParameterChoices.map(_.combineTemporaryType(oldType, newType))
    )

  override def asNArgs(n: Int): MethodWithCallSiteParameterChoices =
    new MethodWithCallSiteParameterChoices(
      signature.asNArgs(n),
      returnType,
      typeParameterBounds,
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      callSiteParameterChoices
    )

  override def replace(i: InferenceVariable, t: Type): MethodWithCallSiteParameterChoices =
    new MethodWithCallSiteParameterChoices(
      signature.replace(i, t),
      returnType.replace(i, t),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.replace(i, t)))),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      callSiteParameterChoices
    )

  override def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): MethodWithCallSiteParameterChoices =
    new MethodWithCallSiteParameterChoices(
      signature.reorderTypeParameters(scheme),
      returnType.reorderTypeParameters(scheme),
      typeParameterBounds.map((k, v) =>
        (k.reorderTypeParameters(scheme) -> v.map(t => t.reorderTypeParameters(scheme)))
      ),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      callSiteParameterChoices.map(_.reorderTypeParameters(scheme))
    )

/** A constructor of a type
  * @param signature
  *   the signature of the constructor
  * @param typeParameterBounds
  *   the bounds of the type parameters attached to the constructor
  * @param accessModifier
  *   the access modifier attached to the constructor
  */
class Constructor(
    val signature: MethodSignature,
    val typeParameterBounds: Vector[(TTypeParameter, Vector[Type])],
    val accessModifier: AccessModifier
) extends MethodLike:

  def getNodeListModifiers: NodeList[Modifier] =
    val res: NodeList[Modifier] = NodeList()
    accessModifier match
      case DEFAULT   => ()
      case PUBLIC    => res.add(Modifier.publicModifier())
      case PROTECTED => res.add(Modifier.protectedModifier())
      case PRIVATE   => res.add(Modifier.privateModifier())
    res

  def callWith(
      args: Vector[Type],
      callSiteParameterChoices: Set[TTypeParameter]
  ): Vector[Assertion] =
    if !this.callableWithNArgs(args.size) then ???
    val tps = typeParameterBounds.map(_._1)
    val tpMap = tps
      .map(x =>
        (x -> InferenceVariableFactory.createJavaInferenceVariable(callSiteParameterChoices, true))
      )
      .toMap
    val subtpBounds =
      typeParameterBounds.map((k, v) => (k.substitute(tpMap) -> v.map(x => x.substitute(tpMap))))
    val boundsAssts  = subtpBounds.flatMap((k, v) => v.map(b => k <:~ b))
    val formalParams = signature.asNArgs(args.size).formalParameters.map(_.substitute(tpMap))
    val paramAssts   = args.zip(formalParams).map((l, r) => l =:~ r)
    boundsAssts ++ paramAssts
  def fix: Constructor =
    new Constructor(
      signature.fix,
      typeParameterBounds.map((k, v) => (k.fix -> v.map(t => t.fix))),
      accessModifier
    )
  def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): Constructor =
    new Constructor(
      signature.combineTemporaryType(oldType, newType),
      typeParameterBounds.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.map(t =>
          t.combineTemporaryType(oldType, newType)
        ))
      ),
      accessModifier
    )

  def asNArgs(n: Int): Constructor =
    new Constructor(
      signature.asNArgs(n),
      typeParameterBounds,
      accessModifier
    )

  def substitute(function: Substitution): Constructor =
    new Constructor(
      signature.substitute(function),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.substitute(function)))),
      accessModifier
    )

  /** Replaces all the occurrences of some inference variable with another type
    * @param i
    *   the inference variable to replace
    * @param t
    *   the type after replacement
    * @return
    *   the resulting method from replacement
    */
  def replace(i: InferenceVariable, t: Type): Constructor =
    new Constructor(
      signature.replace(i, t),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.replace(i, t)))),
      accessModifier
    )

  override def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): Constructor =
    new Constructor(
      signature.reorderTypeParameters(scheme),
      typeParameterBounds.map((k, v) =>
        (k.reorderTypeParameters(scheme) -> v.map(t => t.reorderTypeParameters(scheme)))
      ),
      accessModifier
    )

  override def toString =
    val ab = ArrayBuffer[String]()
    ab += accessModifier.toString
    val tpString =
      if typeParameterBounds.isEmpty then ""
      else
        "<" + typeParameterBounds
          .map((k, v) =>
            if v.isEmpty then k.toString
            else k.toString + " extends " + v.mkString(" & ")
          )
          .mkString(", ") + ">"
    ab += tpString
    ab += signature.toString
    ab.filter(_.length > 0).mkString(" ")

/** A constructor with context
  * @param _signature
  *   the signature of the constructor
  * @param _typeParameterBounds
  *   the bounds of the type parameters attached to the constructor
  * @param _accessModifier
  *   the access modifier of the constructor
  * @param context
  *   the context of the constructor
  */
class ConstructorWithContext(
    _signature: MethodSignature,
    _typeParameterBounds: Vector[(TTypeParameter, Vector[Type])],
    _accessModifier: AccessModifier,
    val callSiteParameterChoices: Set[TTypeParameter],
    val context: Substitution
) extends Constructor(_signature, _typeParameterBounds, _accessModifier):

  override def toString: String =
    s"${super.toString()} with context {${context.map((k, v) => s"$k -> $v").mkString(", ")}}"

  override def combineTemporaryType(
      oldType: TemporaryType,
      newType: SomeClassOrInterfaceType
  ): ConstructorWithContext =
    new ConstructorWithContext(
      signature.combineTemporaryType(oldType, newType),
      typeParameterBounds.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.map(t =>
          t.combineTemporaryType(oldType, newType)
        ))
      ),
      accessModifier,
      callSiteParameterChoices.map(_.combineTemporaryType(oldType, newType)),
      context.map((k, v) =>
        (k.combineTemporaryType(oldType, newType) -> v.combineTemporaryType(oldType, newType))
      )
    )

  override def asNArgs(n: Int): ConstructorWithContext =
    new ConstructorWithContext(
      signature.asNArgs(n),
      typeParameterBounds,
      accessModifier,
      callSiteParameterChoices,
      context
    )

  override def replace(i: InferenceVariable, t: Type): ConstructorWithContext =
    new ConstructorWithContext(
      signature.replace(i, t),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.replace(i, t)))),
      accessModifier,
      callSiteParameterChoices,
      context.map((tp, tt) => (tp -> tt.replace(i, t)))
    )

  override def reorderTypeParameters(
      scheme: Map[TTypeParameter, TTypeParameter]
  ): ConstructorWithContext =
    new ConstructorWithContext(
      signature.reorderTypeParameters(scheme),
      typeParameterBounds.map((k, v) =>
        (k.reorderTypeParameters(scheme) -> v.map(t => t.reorderTypeParameters(scheme)))
      ),
      accessModifier,
      callSiteParameterChoices.map(x => x.reorderTypeParameters(scheme)),
      context.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
    )

/** Companion object to [[configuration.declaration.Constructor]] */
object Constructor:
  /** Creates a new constructor
    * @param containingTypeIdentifier
    *   the name of the type containing this constructor
    * @param formalParameters
    *   the formal parameters of the constructor
    * @param typeParameterBounds
    *   the bounds of the type parameters attached to the constructor
    * @param hasVarArgs
    *   whether the constructor has variadic arguments
    * @return
    *   the resulting constructor
    */
  def apply(
      containingTypeIdentifier: String,
      formalParameters: Vector[Type],
      typeParameterBounds: Vector[(TTypeParameter, Vector[Type])],
      accessModifier: AccessModifier,
      hasVarArgs: Boolean
  ): Constructor =
    new Constructor(
      MethodSignature(containingTypeIdentifier, formalParameters, hasVarArgs),
      typeParameterBounds,
      accessModifier
    )
