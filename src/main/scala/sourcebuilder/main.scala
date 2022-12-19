package sourcebuilder

import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.Modifier
import com.github.javaparser.ast.body.FieldDeclaration
import com.github.javaparser.ast.`type`.{
  Type as ASTType,
  PrimitiveType as ASTPrimitiveType,
  ReferenceType as ASTReferenceType,
  ArrayType as ASTArrayType,
  ClassOrInterfaceType as ASTClassOrInterfaceType,
  TypeParameter as ASTTypeParameter,
  VoidType as ASTVoidType,
  WildcardType as ASTWildcardType
}

import configuration.Configuration
import configuration.declaration.*
import configuration.types.*
import utils.*

val paramNameMemo = ArrayBuffer[String]()

def populateParamNameMemo(prohibitedNames: Set[String]): Unit =
  var curr =
    if paramNameMemo.isEmpty then "A" else incrementOnce(paramNameMemo(paramNameMemo.size - 1))
  while prohibitedNames.contains(curr) do curr = incrementOnce(curr)
  paramNameMemo += curr

def incrementOnce(str: String): String =
  val arr   = (" " + str).toCharArray
  var carry = true
  var i     = arr.size - 1
  while carry do
    if arr(i) != 'Z' then
      if arr(i) == ' ' then arr(i) = 'A'
      else arr(i) = (arr(i) + 1).toChar
      carry = false
    else
      arr(i) = 'A'
      i -= 1
  arr.mkString.strip

def numToLetter(i: Int, prohibitedNames: Set[String]) =
  while paramNameMemo.size <= i do populateParamNameMemo(prohibitedNames)
  paramNameMemo(i)

def buildSource(log: Log, configuration: Configuration): (Log, Vector[CompilationUnit]) =
  // get the names which the parameters cannot be called
  val prohibitedNames = configuration.delta.keys.toSet ++ configuration.phi1.keys.toSet
  // parse the original source code
  val originalCompilationUnit = configuration.cu

  val res = mapWithLog(log, configuration.phi1.toVector) { case (l, (k, v)) =>
    buildType(l, k, v, configuration, prohibitedNames)
  }

  originalCompilationUnit.getPackageDeclaration.toScala match
    case None => ()
    case Some(x) =>
      res._2.foreach(y => y.setPackageDeclaration(x))

  (res._1, res._2 :+ originalCompilationUnit)

def buildType(
    log: Log,
    identifier: String,
    decl: MissingTypeDeclaration,
    config: Configuration,
    prohibitedNames: Set[String]
): (Log, CompilationUnit) =
  val cu = CompilationUnit()
  // create the declaration
  val classOrInterfaceDeclaration =
    if decl.mustBeClass then cu.addClass(decl.identifier)
    else cu.addInterface(decl.identifier)
  // add comment
  classOrInterfaceDeclaration.setLineComment("added by JavaCIP")
  // set the number of type parameters
  val declTypeParameters = NodeList(
    (0 until decl.numParams)
      .map(numToLetter(_, prohibitedNames))
      .map(ASTTypeParameter(_)): _*
  )
  classOrInterfaceDeclaration.setTypeParameters(declTypeParameters)
  val directSupertypes = decl.supertypes
  if decl.mustBeClass then
    val extendedTypes    = directSupertypes.filter(config |- _.isClass)
    val implementedTypes = directSupertypes.filter(x => !extendedTypes.contains(x))
    if !extendedTypes.isEmpty then
      if extendedTypes.size > 1 then ???
      else
        classOrInterfaceDeclaration.setExtendedTypes(
          NodeList(
            typeToASTType(extendedTypes(0), prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
          )
        )
    if !implementedTypes.isEmpty then
      classOrInterfaceDeclaration.setImplementedTypes(
        NodeList(
          implementedTypes.map(x =>
            typeToASTType(x, prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
          ): _*
        )
      )
  else
    classOrInterfaceDeclaration.setExtendedTypes(
      NodeList(
        decl.supertypes.map(x =>
          typeToASTType(x, prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
        ): _*
      )
    )
  // add fields
  decl.attributes.foreach((identifier, attr) =>
    classOrInterfaceDeclaration.addMember(
      FieldDeclaration(
        attr.getNodeListModifiers,
        typeToASTType(attr.`type`, prohibitedNames),
        identifier
      )
    )
  )

  (log, cu)

def typeToASTType(t: Type, prohibitedNames: Set[String]): ASTType = t match
  case x: PrimitiveType =>
    if x == PRIMITIVE_BOOLEAN then ASTPrimitiveType(ASTPrimitiveType.Primitive.BOOLEAN)
    else if x == PRIMITIVE_BYTE then ASTPrimitiveType(ASTPrimitiveType.Primitive.BYTE)
    else if x == PRIMITIVE_CHAR then ASTPrimitiveType(ASTPrimitiveType.Primitive.CHAR)
    else if x == PRIMITIVE_DOUBLE then ASTPrimitiveType(ASTPrimitiveType.Primitive.DOUBLE)
    else if x == PRIMITIVE_FLOAT then ASTPrimitiveType(ASTPrimitiveType.Primitive.FLOAT)
    else if x == PRIMITIVE_INT then ASTPrimitiveType(ASTPrimitiveType.Primitive.INT)
    else if x == PRIMITIVE_LONG then ASTPrimitiveType(ASTPrimitiveType.Primitive.LONG)
    else if x == PRIMITIVE_SHORT then ASTPrimitiveType(ASTPrimitiveType.Primitive.SHORT)
    else if x == PRIMITIVE_VOID then ASTVoidType()
    else ???
  case Wildcard => ASTWildcardType()
  case ExtendsWildcardType(x) =>
    val upperBound = typeToASTType(x, prohibitedNames)
    if upperBound.isInstanceOf[ASTReferenceType] then
      ASTWildcardType().setExtendedType(upperBound.asInstanceOf[ASTReferenceType])
    else ???
  case SuperWildcardType(x) =>
    val lowerBound = typeToASTType(x, prohibitedNames)
    if lowerBound.isInstanceOf[ASTReferenceType] then
      ASTWildcardType().setSuperType(lowerBound.asInstanceOf[ASTReferenceType])
    else ???
  case ArrayType(x)             => ASTArrayType(typeToASTType(x, prohibitedNames))
  case TypeParameterIndex(_, i) => ASTClassOrInterfaceType(null, numToLetter(i, prohibitedNames))
  case ClassOrInterfaceType(x, args) =>
    val res =
      if x.slice(0, 10) == "java.lang." then ASTClassOrInterfaceType(null, x.substring(10))
      else ASTClassOrInterfaceType(null, x)
    if args.size == 0 then res
    else
      // convert args
      val astArgs = args.map(typeToASTType(_, prohibitedNames))
      res.setTypeArguments(NodeList(astArgs: _*))
  case _ =>
    println(t)
    ???
