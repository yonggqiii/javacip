package sourcebuilder

import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.Modifier
import com.github.javaparser.ast.body.{
  FieldDeclaration,
  MethodDeclaration,
  Parameter,
  ConstructorDeclaration
}
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
import com.github.javaparser.ast.expr.SimpleName

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
    buildType(l, k, v.fix(configuration), configuration, prohibitedNames)
  }

  originalCompilationUnit.getPackageDeclaration.toScala match
    case None => ()
    case Some(x) =>
      res._2.foreach(y => y.setPackageDeclaration(x))
  (res._1, res._2 :+ originalCompilationUnit)

def buildType(
    log: Log,
    identifier: String,
    decl: FixedDeclaration,
    config: Configuration,
    prohibitedNames: Set[String]
): (Log, CompilationUnit) =
  val cu = CompilationUnit()
  // create the declaration
  val classOrInterfaceDeclaration =
    if decl.isClass then cu.addClass(decl.identifier) else cu.addInterface(decl.identifier)
  // if decl.mustBeClass then cu.addClass(decl.identifier)
  // else cu.addInterface(decl.identifier)
  // add comment
  classOrInterfaceDeclaration.setLineComment("added by JavaCIP")
  // set the number of type parameters
  val declTypeParameters = NodeList(
    (0 until decl.numParams)
      .map(numToLetter(_, prohibitedNames))
      .map(ASTTypeParameter(_)): _*
  )
  classOrInterfaceDeclaration.setTypeParameters(declTypeParameters)
  if !decl.extendedTypes.isEmpty then
    classOrInterfaceDeclaration.setExtendedTypes(
      NodeList(
        decl.extendedTypes.map(x =>
          typeToASTType(x, prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
        ): _*
      )
    )
  if !decl.implementedTypes.isEmpty then
    classOrInterfaceDeclaration.setImplementedTypes(
      NodeList(
        decl.implementedTypes.map(x =>
          typeToASTType(x, prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
        ): _*
      )
    )
  // val directSupertypes = decl.getDirectAncestors
  // if decl.mustBeClass then
  //   val extendedTypes    = directSupertypes.filter(config |- _.isClass)
  //   val implementedTypes = directSupertypes.filter(x => !extendedTypes.contains(x))
  //   if !extendedTypes.isEmpty then
  //     if extendedTypes.size > 1 then ???
  //     else
  //       classOrInterfaceDeclaration.setExtendedTypes(
  //         NodeList(
  //           typeToASTType(extendedTypes(0), prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
  //         )
  //       )
  //   if !implementedTypes.isEmpty then
  //     classOrInterfaceDeclaration.setImplementedTypes(
  //       NodeList(
  //         implementedTypes.map(x =>
  //           typeToASTType(x, prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
  //         ): _*
  //       )
  //     )
  // else
  //   classOrInterfaceDeclaration.setExtendedTypes(
  //     NodeList(
  //       decl.supertypes.map(x =>
  //         typeToASTType(x, prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
  //       ): _*
  //     )
  //   )
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
  decl.methods.foreach((identifier, v) =>
    v.foreach(m =>
      val methodTypeParameters = NodeList(
        m.typeParameterBounds
          .map((p, v) =>
            (
              ASTTypeParameter(
                numToLetter(p.asInstanceOf[TypeParameterIndex].index, prohibitedNames)
              ),
              NodeList(
                v.map(t =>
                  typeToASTType(t, prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
                ): _*
              )
            )
          )
          .map((p, v) => p.setTypeBound(v)): _*
      )

      val md = MethodDeclaration()
      md.setModifiers(m.getNodeListModifiers)
      md.setName(SimpleName(identifier))
      if !methodTypeParameters.isEmpty() then md.setTypeParameters(methodTypeParameters)
      // create formal parameters
      val params = (0 until m.signature.formalParameters.size).map(i =>
        val t = m.signature.formalParameters(i)
        Parameter(typeToASTType(t, prohibitedNames), s"arg$i")
      )
      if !params.isEmpty && m.signature.hasVarArgs then params(params.size - 1).setVarArgs(true)
      if !params.isEmpty then md.setParameters(NodeList(params: _*))
      md.setType(typeToASTType(m.returnType, prohibitedNames))
      if decl.isInterface && !m.isStatic then
        md.setAbstract(true)
        md.removeBody()
      else
        val bd = md.getBody().toScala.get // safe
        if m.returnType != PRIMITIVE_VOID then
          if m.returnType == PRIMITIVE_BOOLEAN then bd.addStatement("return false;")
          else if m.returnType == PRIMITIVE_CHAR then bd.addStatement("return 'a';")
          else if m.returnType.isInstanceOf[PrimitiveType] then bd.addStatement("return 0;")
          else bd.addStatement("return null;")
      classOrInterfaceDeclaration.addMember(md)
    )
  )

  // write empty constructor
  val allConstructors =
    if decl.constructors.exists(c =>
        c.signature.formalParameters.size == 0
      ) || decl.isInterface
    then decl.constructors
    else decl.constructors :+ Constructor(decl.identifier, Vector(), Vector(), DEFAULT, false)

  allConstructors.foreach(c =>
    val methodTypeParameters = NodeList(
      c.typeParameterBounds
        .map((p, v) =>
          (
            ASTTypeParameter(
              numToLetter(p.asInstanceOf[TypeParameterIndex].index, prohibitedNames)
            ),
            NodeList(
              v.map(t =>
                typeToASTType(t, prohibitedNames).asInstanceOf[ASTClassOrInterfaceType]
              ): _*
            )
          )
        )
        .map((p, v) => p.setTypeBound(v)): _*
    )

    val cd = ConstructorDeclaration()
    cd.setModifiers(c.getNodeListModifiers)
    cd.setName(SimpleName(c.signature.identifier))
    if !methodTypeParameters.isEmpty() then cd.setTypeParameters(methodTypeParameters)
    // create formal parameters
    val params = (0 until c.signature.formalParameters.size).map(i =>
      val t = c.signature.formalParameters(i)
      Parameter(typeToASTType(t, prohibitedNames), s"arg$i")
    )
    if !params.isEmpty && c.signature.hasVarArgs then params(params.size - 1).setVarArgs(true)
    if !params.isEmpty then cd.setParameters(NodeList(params: _*))
    val bd = cd.getBody()
    bd.addStatement("super();")
    //cd.setType(typeToASTType(c.returnType, prohibitedNames))
    // if decl.isInterface then
    //   md.setAbstract(true)
    //   md.removeBody()
    // else
    //   val bd = md.getBody().toScala.get // safe
    //   if m.returnType != PRIMITIVE_VOID then
    //     if m.returnType == PRIMITIVE_BOOLEAN then bd.addStatement("return false;")
    //     else if m.returnType == PRIMITIVE_CHAR then bd.addStatement("return 'a';")
    //     else if m.returnType.isInstanceOf[PrimitiveType] then bd.addStatement("return 0;")
    //     else bd.addStatement("return null;")
    classOrInterfaceDeclaration.addMember(cd)
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
  case ClassOrInterfaceType(x, args, _) =>
    val res =
      if x.slice(0, 10) == "java.lang." then ASTClassOrInterfaceType(null, x.substring(10))
      else ASTClassOrInterfaceType(null, x)
    if args.size == 0 then res
    else
      // convert args
      val astArgs = args.map(typeToASTType(_, prohibitedNames))
      res.setTypeArguments(NodeList(astArgs: _*))
  case z @ TemporaryType(id, args, _) =>
    val x = z.identifier
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
