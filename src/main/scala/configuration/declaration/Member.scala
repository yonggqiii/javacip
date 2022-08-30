package configuration.declaration

import scala.collection.mutable.ArrayBuffer
import com.github.javaparser.ast.AccessSpecifier
import com.github.javaparser.ast.Modifier
import com.github.javaparser.ast.NodeList

import configuration.types.*

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

final case class MethodSignature(
    identifier: String,
    formalParameters: Vector[Type],
    hasVarArgs: Boolean
):
  if hasVarArgs && formalParameters.size == 0 then
    throw new IllegalArgumentException(s"$identifier cannot have VarArgs but no parameters!")
  def callableWithNArgs(n: Int) =
    formalParameters.size == n || (formalParameters.size < n && hasVarArgs)
  def replace(i: InferenceVariable, t: Type): MethodSignature =
    copy(formalParameters = formalParameters.map(_.replace(i, t)))
  override def toString =
    identifier +
      "(" +
      formalParameters.mkString(", ") +
      (if hasVarArgs then "..." else "") +
      ")"
  def erased(decl: FixedDeclaration) =
    MethodSignature(identifier, formalParameters.map(decl.getRawErasure(_)), hasVarArgs)

sealed trait MethodLike:
  val signature: MethodSignature
  val typeParameterBounds: Map[TTypeParameter, Vector[Type]]
  val accessModifier: AccessModifier
  def callableWithNArgs(n: Int): Boolean = signature.callableWithNArgs(n)

class Method(
    val signature: MethodSignature,
    val returnType: Type,
    val typeParameterBounds: Map[TTypeParameter, Vector[Type]],
    val accessModifier: AccessModifier,
    val isAbstract: Boolean,
    val isStatic: Boolean,
    val isFinal: Boolean
) extends MethodLike:
  if isAbstract && (isStatic || isFinal) then
    throw new java.lang.IllegalArgumentException(
      s"$signature cannot be abstract and (static or final) at the same time!"
    )
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

object Method:
  def apply(
      identifier: String,
      formalParameters: Vector[Type],
      returnType: Type,
      typeParameterBounds: Map[TTypeParameter, Vector[Type]],
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

class MethodWithContext(
    _signature: MethodSignature,
    _returnType: Type,
    _typeParameterBounds: Map[TTypeParameter, Vector[Type]],
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

class MethodWithCallSiteParameterChoices(
    _signature: MethodSignature,
    _returnType: Type,
    _typeParameterBounds: Map[TTypeParameter, Vector[Type]],
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

class Constructor(
    val signature: MethodSignature,
    val typeParameterBounds: Map[TTypeParameter, Vector[Type]],
    val accessModifier: AccessModifier
) extends MethodLike:
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

class ConstructorWithContext(
    _signature: MethodSignature,
    _typeParameterBounds: Map[TTypeParameter, Vector[Type]],
    _accessModifier: AccessModifier,
    val context: List[Map[TTypeParameter, Type]]
) extends Constructor(_signature, _typeParameterBounds, _accessModifier)

object Constructor:
  def apply(
      containingTypeIdentifier: String,
      formalParameters: Vector[Type],
      typeParameterBounds: Map[TTypeParameter, Vector[Type]],
      accessModifier: AccessModifier,
      hasVarArgs: Boolean
  ): Constructor =
    new Constructor(
      MethodSignature(containingTypeIdentifier, formalParameters, hasVarArgs),
      typeParameterBounds,
      accessModifier
    )
