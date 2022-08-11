package configuration.declaration

import scala.collection.mutable.ArrayBuffer
import com.github.javaparser.ast.AccessSpecifier

import configuration.types.*

sealed trait AccessModifier
case object AccessModifier:
  def apply(a: AccessSpecifier) = a match
    case AccessSpecifier.PACKAGE_PRIVATE => DEFAULT
    case AccessSpecifier.PRIVATE         => PRIVATE
    case AccessSpecifier.PROTECTED       => PROTECTED
    case AccessSpecifier.PUBLIC          => PUBLIC

case object PUBLIC extends AccessModifier:
  override def toString = "public"
case object DEFAULT extends AccessModifier:
  override def toString = ""
case object PROTECTED extends AccessModifier:
  override def toString = "protected"
case object PRIVATE extends AccessModifier:
  override def toString = "private"

final case class Attribute(
    identifier: String,
    `type`: Type,
    accessModifier: AccessModifier,
    isStatic: Boolean,
    isFinal: Boolean
):
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

final case class Method(
    signature: MethodSignature,
    returnType: Type,
    typeParameterBounds: Map[TTypeParameter, Vector[Type]],
    accessModifier: AccessModifier,
    isAbstract: Boolean,
    isStatic: Boolean,
    isFinal: Boolean
) extends MethodLike:
  if isAbstract && (isStatic || isFinal) then
    throw new java.lang.IllegalArgumentException(
      s"$signature cannot be abstract and (static or final) at the same time!"
    )
  def replace(i: InferenceVariable, t: Type): Method =
    copy(
      signature = signature.replace(i, t),
      returnType = returnType.replace(i, t),
      typeParameterBounds = typeParameterBounds.map((k, v) => (k -> v.map(x => x.replace(i, t))))
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
    Method(
      MethodSignature(identifier, formalParameters, hasVarArgs),
      returnType,
      typeParameterBounds,
      accessModifier,
      isAbstract,
      isStatic,
      isFinal
    )

final case class Constructor(
    signature: MethodSignature,
    typeParameterBounds: Map[TTypeParameter, Vector[Type]],
    accessModifier: AccessModifier
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

object Constructor:
  def apply(
      containingTypeIdentifier: String,
      formalParameters: Vector[Type],
      typeParameterBounds: Map[TTypeParameter, Vector[Type]],
      accessModifier: AccessModifier,
      hasVarArgs: Boolean
  ): Constructor =
    Constructor(
      MethodSignature(containingTypeIdentifier, formalParameters, hasVarArgs),
      typeParameterBounds,
      accessModifier
    )
