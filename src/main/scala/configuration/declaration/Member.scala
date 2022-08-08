package configuration.declaration

import scala.collection.mutable.ArrayBuffer
import com.github.javaparser.ast.AccessSpecifier

import configuration.types.*

sealed trait Modifier
case object Modifier:
  def apply(a: AccessSpecifier) = a match
    case AccessSpecifier.PACKAGE_PRIVATE => DEFAULT
    case AccessSpecifier.PRIVATE         => PRIVATE
    case AccessSpecifier.PROTECTED       => PROTECTED
    case AccessSpecifier.PUBLIC          => PUBLIC

case object PUBLIC extends Modifier:
  override def toString = "public"
case object DEFAULT extends Modifier:
  override def toString = ""
case object PROTECTED extends Modifier:
  override def toString = "protected"
case object PRIVATE extends Modifier:
  override def toString = "private"

final case class Attribute(
    identifier: String,
    `type`: Type,
    accessModifier: Modifier,
    isStatic: Boolean,
    isFinal: Boolean
):
  override def toString =
    val ab = ArrayBuffer[String]()
    ab += accessModifier.toString
    if isStatic then ab += "static"
    if isFinal then ab += "final"
    ab += `type`.toString
    ab += identifier
    ab.filter(_.length > 0).mkString(" ")

final case class MethodSignature(identifier: String, formalParameters: Vector[Type]):
  override def toString =
    identifier + "(" + formalParameters.mkString(", ") + ")"
  def erased(decl: FixedDeclaration) =
    MethodSignature(identifier, formalParameters.map(decl.getRawErasure(_)))

final case class Method(
    signature: MethodSignature,
    returnType: Type,
    typeParameterBounds: Map[TTypeParameter, Vector[Type]],
    accessModifier: Modifier,
    isAbstract: Boolean,
    isStatic: Boolean,
    isFinal: Boolean
):
  if isAbstract && (isStatic || isFinal) then
    throw new java.lang.IllegalArgumentException(
      s"$signature cannot be abstract and (static or final) at the same time!"
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
      accessModifier: Modifier,
      isAbstract: Boolean,
      isStatic: Boolean,
      isFinal: Boolean
  ): Method =
    Method(
      MethodSignature(identifier, formalParameters),
      returnType,
      typeParameterBounds,
      accessModifier,
      isAbstract,
      isStatic,
      isFinal
    )
