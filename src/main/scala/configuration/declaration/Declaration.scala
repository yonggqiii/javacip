package configuration.declaration

import configuration.types.*

class FixedDeclaration(
    val identifier: String,
    val typeParameters: Vector[Vector[Type]],
    val isFinal: Boolean,
    val isAbstract: Boolean,
    val isInterface: Boolean,
    val extendedTypes: Vector[Type],
    val implementedTypes: Vector[Type],
    val methodTypeParameterBounds: Map[String, Vector[Type]]
):
  override def toString =
    val numArgs = typeParameters.size
    val finalOrAbstract =
      if isFinal then "final "
      else if isAbstract then "abstract"
      else ""
    val classOrInterface = if isInterface then "interface " else "class "
    val args =
      if numArgs == 0 then ""
      else
        "<" + (0 until numArgs)
          .map(i =>
            TypeParameterIndex(identifier, i).toString +
              (if typeParameters(i).size == 0 then ""
               else " extends " + typeParameters(i).mkString(" & "))
          )
          .mkString(", ") + ">"
    val extendsClause =
      if extendedTypes.size == 0 then ""
      else " extends " + extendedTypes.mkString(", ")
    val implementsClause =
      if implementedTypes.size == 0 then ""
      else " implements " + implementedTypes.mkString(", ")
    s"$finalOrAbstract$classOrInterface$identifier$args$extendsClause$implementsClause\nType parameter bounds:\n$methodTypeParameterBounds"

  def getBounds(typet: Type): Vector[Type] =
    typet match
      case TypeParameterIndex(source, index, subs) =>
        if source != identifier then ??? // TODO
        else typeParameters(index).map(_.addSubstitutionLists(subs))
      case TypeParameterName(source, qualifiedName, subs) =>
        val index = source + "#" + qualifiedName
        if !methodTypeParameterBounds.contains(index) then ??? // TODO
        else methodTypeParameterBounds(index).map(_.addSubstitutionLists(subs))
      case _ => ??? // TODO

  def getErasure(typet: Type, exclusions: Set[Type] = Set()): Type =
    if exclusions.contains(typet) then NormalType("Object", 0)
    typet match
      case _: TypeParameterIndex | _: TypeParameterName =>
        val bounds = getBounds(typet)
        if bounds.isEmpty then NormalType("Object", 0)
        else getErasure(bounds(0), exclusions + typet)
      case _ => typet
