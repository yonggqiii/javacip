package configuration.resolvers

import java.lang.reflect.Modifier
import com.github.javaparser.resolution.declarations.*
import configuration.declaration.{FixedDeclaration, Attribute, AccessModifier, Method, Constructor}
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.util.{Try, Success, Failure}
import scala.collection.mutable.{Map as MutableMap, Set as MutableSet}
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

  val attributes = getAttributes(decl, identifier) match
    case Success(x) => x
    case Failure(x) => return Failure(x)

  // collect the type parameter bounds
  val mmethodTypeParameterBounds = MutableMap[String, Vector[Type]]()

  // get the methods
  val methods = getMethods(decl, identifier, mmethodTypeParameterBounds) match
    case Success(x) => x
    case Failure(x) => return Failure(x)

  // get the constructors
  val constructors = getConstructors(decl, identifier, mmethodTypeParameterBounds) match
    case Success(x) => x
    case Failure(x) => return Failure(x)

  val methodTypeParameterBounds = mmethodTypeParameterBounds.toMap

  // get supertypes
  val (extendedTypes, implementedTypes) =
    if isInterface then
      val ifaced = decl.asInstanceOf[ResolvedInterfaceDeclaration]
      (ifaced.getAncestors.asScala.map(x => resolveSolvedType(x)).toVector, Vector())
    else
      val clsd = decl.asInstanceOf[ResolvedClassDeclaration]
      (
        clsd.getSuperClass.toScala
          .map(x =>
            Vector(resolveSolvedType(x))
              .filter(x => x != OBJECT)
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
      methods,
      constructors
    )
  )

def getAttributes(
    decl: ResolvedReferenceTypeDeclaration,
    identifier: String
): Try[Map[String, Attribute]] =
  // get attributes (fail if duplicates are found)
  val attributes = MutableMap[String, Attribute]()
  for a <- decl.getDeclaredFields.asScala do
    val attrName = a.getName
    val attrType = resolveSolvedType(a.getType)
    if attributes.contains(attrName) then
      return Failure(Exception(s"$identifier contains duplicate attribute $attrName"))
    val isStatic = a.isStatic
    val m        = AccessModifier(a.accessSpecifier)
    // dumbass library won't tell me if attr is final
    val isFinal = Try(a.toAst.toScala) match
      case Success(Some(x)) =>
        x.getModifiers.asScala.contains(com.github.javaparser.ast.Modifier.finalModifier)
      case Failure(_) =>
        val c: java.lang.Class[?] = java.lang.Class.forName(identifier)
        val f                     = c.getDeclaredField(attrName)
        java.lang.reflect.Modifier.isFinal(f.getModifiers)
      case Success(None) => return Failure(Exception(s"cannot determine if $attrName is final?"))
    attributes(attrName) = Attribute(attrName, attrType, m, isStatic, isFinal)
  Success(attributes.toMap)

def getMethods(
    decl: ResolvedReferenceTypeDeclaration,
    identifier: String,
    methodTypeParameterBounds: MutableMap[String, Vector[Type]]
): Try[Map[String, Set[Method]]] =
  val methodDeclarations = decl.getDeclaredMethods.asScala.toVector
  // create a mutable map to store the methods
  val mmethods = MutableMap[String, MutableSet[Method]]()
  // collect each method using a while loop, skipping over non public
  // methods of reflection types
  for methodDeclaration <- methodDeclarations do
    // name of method
    val methodName = methodDeclaration.getName
    // get type parameters
    val typeParamDecls = methodDeclaration.getTypeParameters.asScala.toVector
    val typeParams =
      typeParamDecls.map(x => TypeParameterName(identifier, x.getContainerId, x.getName))
    // get return type
    val returnType = resolveSolvedType(methodDeclaration.getReturnType)
    // create a map of TypeParameter -> Bounds
    val typeParamBounds = typeParams
      .zip(
        typeParamDecls.map(tp =>
          tp.getBounds.asScala.toVector.map(x => resolveSolvedType(x.getType))
        )
      )
    // get formal parameters
    val numParams = methodDeclaration.getNumberOfParams
    val args = (0 until numParams)
      .map(x => resolveSolvedType(methodDeclaration.getParam(x).getType))
      .toVector
    // get modifiers
    val isAbstract     = methodDeclaration.isAbstract
    val isStatic       = methodDeclaration.isStatic
    val accessModifier = AccessModifier(methodDeclaration.accessSpecifier)
    val isFinal =
      Try(methodDeclaration.toAst.toScala) match
        case Success(Some(x)) =>
          x.getModifiers.asScala.contains(com.github.javaparser.ast.Modifier.finalModifier)
        case _ =>
          val c: java.lang.Class[?] = java.lang.Class.forName(identifier)
          val ab                    = scala.collection.mutable.ArrayBuffer[String]()
          for i <- (0 until numParams) do
            val p = methodDeclaration.getParam(i).getType
            ab += "<.*>".r.replaceAllIn(p.describe, "")
          val targetToFind =
            raw"\.\.\.".r
              .replaceAllIn(c.getName + "." + methodName + "(" + ab.mkString(", ") + ")", "[]")
          // println(s"\n$targetToFind")
          val reflectMethod =
            c.getDeclaredMethods
              .filter(x =>
                val methodName = x.getName
                val params = x.getGenericParameterTypes
                  .map(_.getTypeName)
                  .map(y => "<.*>".r.replaceAllIn(y, ""))
                  .map(y => "\\$".r.replaceAllIn(y, "."))
                val res = c.getName + "." + methodName + "(" + params.mkString(", ") + ")"
                // println(s"$res: ${res == targetToFind}")
                res == targetToFind
              )(0)
          java.lang.reflect.Modifier.isFinal(reflectMethod.getModifiers)
    val hasVarArgs = methodDeclaration.hasVariadicParameter
    // add to table
    if !mmethods.contains(methodName) then mmethods(methodName) = MutableSet()
    val table = mmethods(methodName)
    table += Method(
      methodName,
      args,
      returnType,
      typeParamBounds.toMap,
      accessModifier,
      isAbstract,
      isStatic,
      isFinal,
      hasVarArgs
    )
    methodTypeParameterBounds ++= typeParamBounds.map(x => (x._1.identifier -> x._2))
  Success(mmethods.map((k, v) => (k, v.toSet)).toMap)

def getConstructors(
    decl: ResolvedReferenceTypeDeclaration,
    identifier: String,
    methodTypeParameterBounds: MutableMap[String, Vector[Type]]
): Try[Set[Constructor]] =
  // get constructors
  val constructorDeclarations = decl.getConstructors.asScala
  val mconstructors           = MutableSet[Constructor]()
  for constructorDeclaration <- constructorDeclarations do
    val typeParamDecls = constructorDeclaration.getTypeParameters.asScala.toVector
    val typeParams =
      typeParamDecls.map(x => TypeParameterName(identifier, x.getContainerId, x.getName))
    val typeParamBounds: Map[TTypeParameter, Vector[Type]] = typeParams
      .zip(
        typeParamDecls.map(tp =>
          tp.getBounds.asScala.toVector
            .map(x => resolveSolvedType(x.getType))
        )
      )
      .toMap
    val numParams = constructorDeclaration.getNumberOfParams
    val args = (0 until numParams)
      .map(x => resolveSolvedType(constructorDeclaration.getParam(x).getType))
      .toVector
    val accessModifier = AccessModifier(constructorDeclaration.accessSpecifier)
    val hasVarArgs     = constructorDeclaration.hasVariadicParameter
    mconstructors += Constructor(identifier, args, typeParamBounds, accessModifier, hasVarArgs)
    methodTypeParameterBounds ++= typeParamBounds.map(x => (x._1.identifier -> x._2))
  Success(mconstructors.toSet)
