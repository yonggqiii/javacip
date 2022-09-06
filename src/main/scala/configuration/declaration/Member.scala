package configuration.declaration

import scala.collection.mutable.ArrayBuffer
import com.github.javaparser.ast.AccessSpecifier
import com.github.javaparser.ast.Modifier
import com.github.javaparser.ast.NodeList

import configuration.types.*
import scala.annotation.constructorOnly

/** An access modifier in a Java program */
sealed trait AccessModifier

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
  override def toString = "public"

/** The package-private (default) access modifier */
case object DEFAULT extends AccessModifier:
  override def toString = ""

/** The protected access modifier */
case object PROTECTED extends AccessModifier:
  override def toString = "protected"

/** The private access modifier */
case object PRIVATE extends AccessModifier:
  override def toString = "private"

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
):
  /** Appends some substitution lists to the type of this attribute
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the resulting attribute after adding the substitution lists to its type
    */
  def addSubstitutionLists(subs: SubstitutionList) =
    copy(`type` = `type`.addSubstitutionLists(subs))

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
  def replace(i: InferenceVariable, t: Type): Attribute =
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
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): Attribute =
    Attribute(
      identifier,
      `type`.reorderTypeParameters(scheme),
      accessModifier,
      isStatic,
      isFinal
    )

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
):
  // make sure the values are passed in to the constructor properly
  if hasVarArgs && formalParameters.size == 0 then
    throw new IllegalArgumentException(s"$identifier cannot have VarArgs but no parameters!")

  /** Reorders the type parameters in this method signature by replacing all the type parameters
    * given a scheme
    * @param scheme
    *   the scheme of reordering type parameters
    * @return
    *   the resulting method signature
    */
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): MethodSignature =
    copy(formalParameters = formalParameters.map(_.reorderTypeParameters(scheme)))

  /** Appends some substitution lists to the types in this method signature
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the resulting method signature after adding the substitution lists to its types
    */
  def addSubstitutionLists(subs: SubstitutionList) =
    copy(formalParameters = formalParameters.map(_.addSubstitutionLists(subs)))

  /** Determines if this signature is callable with n arguments
    * @param n
    *   n
    * @returns
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
  def replace(i: InferenceVariable, t: Type): MethodSignature =
    copy(formalParameters = formalParameters.map(_.replace(i, t)))
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
  def erased(decl: FixedDeclaration) =
    MethodSignature(identifier, formalParameters.map(decl.getRawErasure(_)), hasVarArgs)

/** A method or constructor */
sealed trait MethodLike:
  /** The signature of the method */
  val signature: MethodSignature

  /** The type parameter bounds of the method */
  val typeParameterBounds: Map[TTypeParameter, Vector[TypeBound]]

  /** The access modifier tied to the method */
  val accessModifier: AccessModifier

  /** Determines if this signature is callable with n arguments
    * @param n
    *   n
    * @returns
    *   true if it is callable, false otherwise
    */
  def callableWithNArgs(n: Int): Boolean = signature.callableWithNArgs(n)

  /** Replaces all the occurrences of some inference variable with another type
    * @param i
    *   the inference variable to replace
    * @param t
    *   the type after replacement
    * @return
    *   the resulting method-like from replacement
    */
  def replace(i: InferenceVariable, t: Type): MethodLike

  /** Appends some substitution lists to the types of this method-like
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the resulting method-like after adding the substitution lists to its types
    */
  def addSubstitutionLists(subs: SubstitutionList): MethodLike

  /** Determines if the access level of this method is at least some other access level, based on
    * the following ordering--private, package-private, protected, public
    * @param accessLevel
    * @return
    *   true if the method-like is at least as accessible as the provided access level
    */
  def accessLevelAtLeast(accessLevel: AccessModifier): Boolean =
    val vec = Vector(PRIVATE, DEFAULT, PROTECTED, PUBLIC)
    vec.indexOf(accessLevel) <= vec.indexOf(accessModifier)

  /** Reorders the type parameters of this method-like given a scheme by replacing the type
    * parameters
    * @param scheme
    *   the reordering scheme
    * @return
    *   the resulting method-like
    */
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): MethodLike

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
    val typeParameterBounds: Map[TTypeParameter, Vector[TypeBound]],
    val accessModifier: AccessModifier,
    val isAbstract: Boolean,
    val isStatic: Boolean,
    val isFinal: Boolean
) extends MethodLike:
  // make sure the modifiers make sense
  if isAbstract && (isStatic || isFinal) then
    throw new java.lang.IllegalArgumentException(
      s"$signature cannot be abstract and (static or final) at the same time!"
    )

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

  /** Appends some substitution lists to the types of this method
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the resulting method after adding the substitution lists to its types
    */
  def addSubstitutionLists(subs: SubstitutionList): Method =
    new Method(
      signature.addSubstitutionLists(subs),
      returnType.addSubstitutionLists(subs),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.addSubstitutionLists(subs)))),
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
      typeParameterBounds: Map[TTypeParameter, Vector[TypeBound]],
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
    _typeParameterBounds: Map[TTypeParameter, Vector[TypeBound]],
    _accessModifier: AccessModifier,
    _isAbstract: Boolean,
    _isStatic: Boolean,
    _isFinal: Boolean,
    val context: List[Map[TTypeParameter, Type]]
) extends Method(
      _signature,
      _returnType,
      _typeParameterBounds,
      _accessModifier,
      _isAbstract,
      _isStatic,
      _isFinal
    ):

  /** Appends some substitution lists to the types of this method
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the resulting method after adding the substitution lists to its types
    */
  override def addSubstitutionLists(subs: SubstitutionList): MethodWithContext =
    new MethodWithContext(
      signature.addSubstitutionLists(subs),
      returnType.addSubstitutionLists(subs),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.addSubstitutionLists(subs)))),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      context ::: subs
    )

  override def replace(i: InferenceVariable, t: Type): MethodWithContext =
    new MethodWithContext(
      signature.replace(i, t),
      returnType.replace(i, t),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.replace(i, t)))),
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      context.map(mp => mp.map((tp, tt) => (tp -> tt.replace(i, t))))
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
      context.map(m =>
        m.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
      )
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
    _typeParameterBounds: Map[TTypeParameter, Vector[TypeBound]],
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

  /** Appends some substitution lists to the types of this method
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the resulting method after adding the substitution lists to its types
    */
  override def addSubstitutionLists(subs: SubstitutionList): MethodWithCallSiteParameterChoices =
    new MethodWithCallSiteParameterChoices(
      signature.addSubstitutionLists(subs),
      returnType.addSubstitutionLists(subs),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.addSubstitutionLists(subs)))),
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
    val typeParameterBounds: Map[TTypeParameter, Vector[TypeBound]],
    val accessModifier: AccessModifier
) extends MethodLike:
  /** Appends some substitution lists to the types of this constructor
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the resulting constructor after adding the substitution lists to its types
    */
  def addSubstitutionLists(subs: SubstitutionList): Constructor =
    new Constructor(
      signature.addSubstitutionLists(subs),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.addSubstitutionLists(subs)))),
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
    _typeParameterBounds: Map[TTypeParameter, Vector[TypeBound]],
    _accessModifier: AccessModifier,
    val context: List[Map[TTypeParameter, Type]]
) extends Constructor(_signature, _typeParameterBounds, _accessModifier):

  /** Appends some substitution lists to the types of this constructor
    * @param substitutionList
    *   the lists of substitutions to add
    * @return
    *   the resulting constructor after adding the substitution lists to its types
    */
  override def addSubstitutionLists(subs: SubstitutionList): ConstructorWithContext =
    new ConstructorWithContext(
      signature.addSubstitutionLists(subs),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.addSubstitutionLists(subs)))),
      accessModifier,
      context ::: subs
    )

  override def replace(i: InferenceVariable, t: Type): ConstructorWithContext =
    new ConstructorWithContext(
      signature.replace(i, t),
      typeParameterBounds.map((k, v) => (k -> v.map(x => x.replace(i, t)))),
      accessModifier,
      context.map(mp => mp.map((tp, tt) => (tp -> tt.replace(i, t))))
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
      context.map(m =>
        m.map((k, v) => (k.reorderTypeParameters(scheme) -> v.reorderTypeParameters(scheme)))
      )
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
      typeParameterBounds: Map[TTypeParameter, Vector[TypeBound]],
      accessModifier: AccessModifier,
      hasVarArgs: Boolean
  ): Constructor =
    new Constructor(
      MethodSignature(containingTypeIdentifier, formalParameters, hasVarArgs),
      typeParameterBounds,
      accessModifier
    )
