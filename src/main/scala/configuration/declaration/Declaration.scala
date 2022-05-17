package configuration.declaration

import configuration.types.*
import scala.annotation.tailrec

private type MethodTable = Map[(Vector[TypeParameterName], Vector[Type]), Type]

class FixedDeclaration(
    val identifier: String,
    val typeParameters: Vector[Vector[Type]],
    val isFinal: Boolean,
    val isAbstract: Boolean,
    val isInterface: Boolean,
    val extendedTypes: Vector[Type],
    val implementedTypes: Vector[Type],
    val methodTypeParameterBounds: Map[String, Vector[Type]],
    val attributes: Map[String, Type],
    val methods: Map[String, MethodTable]
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
               else " extends " + typeParameters(i).map(_.substituted).mkString(" & "))
          )
          .mkString(", ") + ">"
    val extendsClause =
      if extendedTypes.size == 0 then ""
      else " extends " + extendedTypes.map(_.substituted).mkString(", ")
    val implementsClause =
      if implementedTypes.size == 0 then ""
      else " implements " + implementedTypes.map(_.substituted).mkString(", ")
    val attrString = attributes.map(x => s"${x._2} ${x._1}").mkString("\n")
    s"$finalOrAbstract$classOrInterface$identifier$args$extendsClause$implementsClause\nType parameter bounds:\n$methodTypeParameterBounds\nAttributes:\n$attrString\nMethods:\n$methods"

  def getDirectAncestors =
    if identifier == "java.lang.Object" || identifier == "Object" then Vector()
    else
      val a = extendedTypes ++ implementedTypes
      if a.isEmpty then Vector(OBJECT)
      else a

  def getAllBounds(t: Type, exclusions: Set[Type] = Set()): Set[Type] =
    if exclusions.contains(t) then Set(OBJECT)
    else
      t match
        case _: TTypeParameter =>
          val bounds = getBounds(t)
          if bounds.isEmpty then Set(OBJECT)
          else bounds.flatMap(getAllBounds(_, exclusions + t)).toSet
        case _ => Set(t)

  def getBoundsAsTypeParameters(t: Type, exclusions: Set[Type] = Set()): Set[Type] =
    if exclusions.contains(t) then Set()
    else
      val b = getBounds(t).filter(x =>
        x match
          case y: TTypeParameter => true
          case _                 => false
      )
      b.toSet.flatMap(x => getBoundsAsTypeParameters(x, exclusions + t) + x)

  def getBounds(typet: Type): Vector[Type] =
    typet match
      case TypeParameterIndex(source, index, subs) =>
        if source != identifier then ??? // TODO
        else typeParameters(index).map(_.addSubstitutionLists(subs))
      case TypeParameterName(sourceType, source, qualifiedName, subs) =>
        if sourceType != identifier then ??? // TODO
        else
          val index = source + "#" + qualifiedName
          if !methodTypeParameterBounds.contains(index) then ??? // TODO
          else methodTypeParameterBounds(index).map(_.addSubstitutionLists(subs))
      case _ => ??? // TODO

  def getErasure(typet: Type, exclusions: Set[Type] = Set()): Type =
    if exclusions.contains(typet) then OBJECT
    else
      typet match
        case _: TypeParameterIndex | _: TypeParameterName =>
          val bounds = getBounds(typet)
          if bounds.isEmpty then OBJECT
          else getErasure(bounds(0), exclusions + typet)
        case _ => typet

  def conflictingMethods =
    methods
      .map((name, table) =>
        val erasedTable = table.toVector
          .map(x => (x._1._2, x._2))
          .map(x => (x._1.map(param => getErasure(param).identifier), x._2))
        val conflictingSignatures = erasedTable
          .foldLeft(Map[Vector[String], Type](), Vector[Vector[String]]()) {
            case ((m, v), (p, t)) =>
              if !m.contains(p) then (m + (p -> t), v)
              else (m, v :+ p)
          }
          ._2
          .toSet
        (
          name,
          table.filter((x, y) =>
            val paramTypes = x._2.map(param => getErasure(param).identifier)
            conflictingSignatures.contains(paramTypes)
          )
        )
      )
      .filter((x, y) => y.size > 0)
