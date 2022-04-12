package configuration.resolvers

import com.github.javaparser.resolution.types.ResolvedType
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration
// Java/Scala imports
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

import configuration.types.{
  Bottom,
  ExtendsWildcardType,
  NormalType,
  PrimitiveType,
  SuperWildcardType,
  Type,
  TypeParameterIndex,
  TypeParameterName,
  Wildcard
}

private[configuration] def resolveSolvedType(
    typeToConvert: ResolvedType
): Type =
  if typeToConvert.isArray then ???                  // TODO
  else if typeToConvert.isConstraint then ???        // TODO
  else if typeToConvert.isInferenceVariable then ??? // TODO
  else if typeToConvert.isNull then Bottom
  else if typeToConvert.isPrimitive then
    PrimitiveType(typeToConvert.asPrimitive.describe)
  else if typeToConvert.isReferenceType then
    // get type arguments
    val typet      = typeToConvert.asReferenceType
    val typeParams = typet.typeParametersValues
    // get the identifier
    val identifier = typet.getQualifiedName
    // make type arguments to substitution list
    val subs: List[Map[TypeParameterIndex, Type]] =
      if typeParams.size == 0 then Nil
      else
        ((0 until typeParams.size)
          .foldLeft(Map[TypeParameterIndex, Type]())((m, i) =>
            m + (TypeParameterIndex(identifier, i) -> resolveSolvedType(
              typeParams.asScala(i)
            ))
          )) :: Nil
    // convert to a normaltype
    NormalType(
      identifier,
      typeParams.size,
      subs
    )
  else if typeToConvert.isTypeVariable then
    val typeVar   = typeToConvert.asTypeVariable
    val typeParam = typeVar.asTypeParameter
    if typeParam.declaredOnType then
      val container =
        typeParam.getContainer.asInstanceOf[ResolvedReferenceTypeDeclaration]
      val containerID         = container.getQualifiedName
      val containerTypeParams = container.getTypeParameters.asScala
      TypeParameterIndex(containerID, containerTypeParams.indexOf(typeParam))
    else if typeParam.declaredOnMethod then
      TypeParameterName(typeParam.getContainerId, typeParam.getName)
    else ???                                 // TODO
  else if typeToConvert.isUnionType then ??? // TODO
  else if typeToConvert.isVoid then NormalType("void", 0)
  else if typeToConvert.isWildcard then
    val typet = typeToConvert.asWildcard
    if !typet.isBounded then Wildcard
    else if typet.isExtends then
      ExtendsWildcardType(
        resolveSolvedType(
          typet.getBoundedType()
        )
      )
    else
      SuperWildcardType(
        resolveSolvedType(
          typet.getBoundedType()
        )
      )
  else ??? // TODO make this safe.
