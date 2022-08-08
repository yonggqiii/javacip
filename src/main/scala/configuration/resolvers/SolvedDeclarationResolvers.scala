package configuration.resolvers

import java.lang.reflect.Modifier
import com.github.javaparser.resolution.declarations.*
import configuration.declaration.{FixedDeclaration, Attribute, Modifier as ConfigModifier, Method}
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import scala.util.{Try, Success, Failure}
import scala.collection.mutable.{Map as MutableMap, Set as MutableSet}
import configuration.types.*
import configuration.declaration.PRIVATE

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

  val methods = getMethods(decl, identifier, mmethodTypeParameterBounds) match
    case Success(x) => x
    case Failure(x) => return Failure(x)

  // get constructors
  val constructorDeclarations = decl.getConstructors.asScala
  val mconstructors           = MutableSet[(Vector[TypeParameterName], Vector[Type])]()
  for constructorDeclaration <- constructorDeclarations do
    val typeParamDecls = constructorDeclaration.getTypeParameters.asScala.toVector
    val typeParams =
      typeParamDecls.map(x => TypeParameterName(identifier, x.getContainerId, x.getName))
    val typeParamBounds = typeParams
      .zip(
        typeParamDecls.map(tp =>
          tp.getBounds.asScala.toVector
            .map(x => resolveSolvedType(x.getType))
        )
      )
      .map(x => (x._1.identifier -> x._2))
    val numParams = constructorDeclaration.getNumberOfParams
    val args = (0 until numParams)
      .map(x => resolveSolvedType(constructorDeclaration.getParam(x).getType))
      .toVector
    if mconstructors.contains((typeParams, args)) then
      return Failure(
        Exception(s"${constructorDeclaration.getQualifiedSignature} contains duplicates!")
      )
    mconstructors += ((typeParams, args))
    mmethodTypeParameterBounds ++= typeParamBounds
  val constructors              = mconstructors.toSet
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
    val m        = ConfigModifier(a.accessSpecifier)
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
): Try[Map[String, Map[Vector[Type], Method]]] =
  val methodDeclarations = decl.getDeclaredMethods.asScala.toVector
  // create a mutable map to store the methods
  val mmethods = MutableMap[String, MutableMap[Vector[Type], Method]]()
  // collect each method using a while loop, skipping over non public
  // methods of reflection types
  var i = 0
  while i < methodDeclarations.size do
    val methodDeclaration = methodDeclarations(i)
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
    val accessModifier = ConfigModifier(methodDeclaration.accessSpecifier)
    try
      val isFinal =
        Try(methodDeclaration.toAst.toScala) match
          case Success(Some(x)) =>
            x.getModifiers.asScala.contains(com.github.javaparser.ast.Modifier.finalModifier)
          case _ =>
            val c: java.lang.Class[?] = java.lang.Class.forName(identifier)
            val targetToFind          = methodDeclaration.getQualifiedSignature.replaceAll(" ", "")
            val reflectMethod =
              c.getMethods.filter(x => x.toGenericString.split(" ").contains(targetToFind))(0)
            java.lang.reflect.Modifier.isFinal(reflectMethod.getModifiers)
      // add to table
      if !mmethods.contains(methodName) then mmethods(methodName) = MutableMap()
      val table = mmethods(methodName)
      if table.contains(args) then
        return Failure(Exception(s"$identifier contains duplicate method $methodName"))
      table(args) = Method(
        methodName,
        args,
        returnType,
        typeParamBounds.toMap,
        accessModifier,
        isAbstract,
        isStatic,
        isFinal
      )
      methodTypeParameterBounds ++= typeParamBounds.map(x => (x._1.identifier -> x._2))
    catch case e: Throwable => ()
    finally i += 1

  // case Success(None) =>
  //   return Failure(Exception(s"cannot determine if $methodName in $identifier is final?"))
  // store in table
  //   if table.contains(args) then
  //     return Failure(Exception(s"$identifier contains duplicate method $methodName"))

  // for methodDeclaration <- methodDeclarations do
  //   // name of method
  //   val methodName = methodDeclaration.getName
  //   // add to table
  //   if !mmethods.contains(methodName) then mmethods(methodName) = MutableMap()
  //   val table = mmethods(methodName)
  //   // get type parameters
  //   val typeParamDecls = methodDeclaration.getTypeParameters.asScala.toVector
  //   val typeParams =
  //     typeParamDecls.map(x => TypeParameterName(identifier, x.getContainerId, x.getName))
  //   // get return type
  //   val returnType = resolveSolvedType(methodDeclaration.getReturnType)
  //   // create a map of TypeParameter -> Bounds
  //   val typeParamBounds = typeParams
  //     .zip(
  //       typeParamDecls.map(tp =>
  //         tp.getBounds.asScala.toVector.map(x => resolveSolvedType(x.getType))
  //       )
  //     )
  //   // get formal parameters
  //   val numParams = methodDeclaration.getNumberOfParams
  //   val args = (0 until numParams)
  //     .map(x => resolveSolvedType(methodDeclaration.getParam(x).getType))
  //     .toVector
  //   // get modifiers
  //   val isAbstract     = methodDeclaration.isAbstract
  //   val isStatic       = methodDeclaration.isStatic
  //   val accessModifier = ConfigModifier(methodDeclaration.accessSpecifier)
  //   val isFinal =
  //     if accessModifier == PRIVATE then false
  //     else
  //       Try(methodDeclaration.toAst.toScala) match
  //         case Success(Some(x)) =>
  //           x.getModifiers.asScala.contains(com.github.javaparser.ast.Modifier.finalModifier)
  //         case _ =>
  //           val c: java.lang.Class[?] = java.lang.Class.forName(identifier)
  //           val targetToFind          = methodDeclaration.getQualifiedSignature.replaceAll(" ", "")
  //           println(
  //             "\n" + accessModifier.toString + " " + identifier + " " + methodDeclaration.getQualifiedSignature
  //               .replaceAll(" ", "")
  //           )
  //           val reflectMethod = c.getMethods.filter(x =>
  //             println(x.toGenericString.split(" ").mkString(", "))
  //             x.toGenericString.split(" ").contains(targetToFind)
  //           )(0) //risky
  //           java.lang.reflect.Modifier.isFinal(reflectMethod.getModifiers)
  //   // case Success(None) =>
  //   //   return Failure(Exception(s"cannot determine if $methodName in $identifier is final?"))
  //   // store in table
  //   if table.contains(args) then
  //     return Failure(Exception(s"$identifier contains duplicate method $methodName"))
  //   table(args) = Method(
  //     methodName,
  //     args,
  //     returnType,
  //     typeParamBounds.toMap,
  //     accessModifier,
  //     isAbstract,
  //     isStatic,
  //     isFinal
  //   )
  //   methodTypeParameterBounds ++= typeParamBounds.map(x => (x._1.identifier -> x._2))

  Success(mmethods.map((k, v) => (k, v.toMap)).toMap)
