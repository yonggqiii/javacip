package configuration.resolvers

import java.lang.reflect.Modifier
import com.github.javaparser.resolution.declarations.*
import configuration.declaration.FixedDeclaration
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.util.{Try, Success, Failure}
import scala.collection.mutable.{Map as MutableMap}
import configuration.types.*

/** Converts a resolved reference type declaration into a FixedDeclaration
  * @param decl
  *   the declaration to convert
  * @param isAbstract
  *   whether the declaration is abstract
  * @param isFinal
  *   whether the declaration is final
  * @return
  *   a success containing the fixed declaration if successful, a failure otherwise
  */
def convertResolvedReferenceTypeDeclarationToFixedDeclaration(
    decl: ResolvedReferenceTypeDeclaration,
    isAbstract: Boolean,
    isFinal: Boolean
): Try[FixedDeclaration] =
  // basic information
  val identifier  = decl.getQualifiedName
  val isInterface = decl.isInterface

  // get bounds of type parameters tied to the class declaration itself
  val tp = decl.getTypeParameters.asScala
    .map(
      _.getBounds.asScala.toVector
        .map(x => resolveSolvedType(x.getType))
    )
    .toVector

  // get attributes (fail if duplicates are found)
  val attributes = MutableMap[String, Type]()
  for a <- decl.getDeclaredFields.asScala do
    val attrName = a.getName
    val attrType = resolveSolvedType(a.getType)
    if attributes.contains(attrName) then
      return Failure(Exception(s"$identifier contains duplicate attribute $attrName"))
    attributes(attrName) = attrType

  // get methods
  val methodDeclarations = decl.getDeclaredMethods.asScala
  val mmethods = MutableMap[String, MutableMap[(Vector[TypeParameterName], Vector[Type]), Type]]()
  val mmethodTypeParameterBounds = MutableMap[String, Vector[Type]]()
  for methodDeclaration <- methodDeclarations do
    val methodName = methodDeclaration.getName
    if !mmethods.contains(methodName) then mmethods(methodName) = MutableMap()
    val table          = mmethods(methodName)
    val typeParamDecls = methodDeclaration.getTypeParameters.asScala.toVector
    val typeParams =
      typeParamDecls.map(x => TypeParameterName(identifier, x.getContainerId, x.getName))
    val returnType = resolveSolvedType(methodDeclaration.getReturnType)
    val typeParamBounds = typeParams
      .zip(
        typeParamDecls.map(tp =>
          tp.getBounds.asScala.toVector.map(x => resolveSolvedType(x.getType))
        )
      )
      .map(x => (x._1.identifier -> x._2))
    val numParams = methodDeclaration.getNumberOfParams
    val args = (0 until numParams)
      .map(x => resolveSolvedType(methodDeclaration.getParam(x).getType))
      .toVector
    if table.contains((typeParams, args)) then
      return Failure(Exception(s"$identifier contains duplicate method $methodName"))
    table((typeParams, args)) = returnType
    mmethodTypeParameterBounds ++= typeParamBounds
  val methods                   = mmethods.map(x => (x._1, x._2.toMap)).toMap
  val methodTypeParameterBounds = mmethodTypeParameterBounds.toMap

  // get supertypes
  val (extendedTypes, implementedTypes) =
    if isInterface then
      val ifaced = decl.asInstanceOf[ResolvedInterfaceDeclaration]
      (ifaced.getInterfacesExtended.asScala.map(x => resolveSolvedType(x)).toVector, Vector())
    else
      val clsd = decl.asInstanceOf[ResolvedClassDeclaration]
      (
        clsd.getSuperClass.toScala
          .map(x =>
            Vector(resolveSolvedType(x))
              .filter(x => x.identifier != "java.lang.Object")
          )
          .getOrElse(Vector()),
        clsd.getInterfaces.asScala.map(x => resolveSolvedType(x)).toVector
      )
  Success(
    FixedDeclaration(
      identifier,
      tp,
      isFinal,
      isAbstract,
      isInterface,
      extendedTypes,
      implementedTypes,
      methodTypeParameterBounds,
      attributes.toMap,
      methods
    )
  )
