package configuration.declaration

import configuration.types.*
import scala.annotation.tailrec
import scala.collection.mutable.{Set as MutableSet, ArrayBuffer}

class FixedDeclaration(
    val identifier: String,
    val typeParameters: Vector[Vector[Type]],
    val isFinal: Boolean,
    val isAbstract: Boolean,
    val isInterface: Boolean,
    val extendedTypes: Vector[Type],
    val implementedTypes: Vector[Type],
    val methodTypeParameterBounds: Map[String, Vector[Type]],
    val attributes: Map[String, Attribute],
    val methods: Map[String, Set[Method]],
    val constructors: Set[Constructor]
):
  override def toString =
    val ab      = ArrayBuffer[String]()
    val numArgs = typeParameters.size
    if isFinal then ab += "final"
    if isAbstract then ab += "abstract"
    if isInterface then ab += "interface"
    else ab += "class"
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
    ab += identifier + args
    if extendedTypes.size > 0 then
      ab += "extends"
      ab += extendedTypes.map(_.substituted).mkString(", ")
    if implementedTypes.size > 0 then
      ab += "implements"
      ab += implementedTypes.map(_.substituted).mkString(", ")
    val header = ab.mkString(" ")
    val res    = ArrayBuffer[String](header)
    if attributes.size > 0 then
      res += "Attributes:"
      for (k, v) <- attributes do res += s"\t$v"
    if methods.size > 0 then
      res += "Methods:"
      for (k, v) <- methods do for m <- v do res += s"\t$m"
    if constructors.size > 0 then
      res += "Constructors:"
      for c <- constructors do res += s"\t$c"
    res.mkString("\n")

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

  def getRawErasure(typet: Type): Type =
    getErasure(typet).substituted match
      case x: SubstitutedReferenceType => NormalType(x.identifier, 0)
      case x                           => x

  def conflictingMethods =
    val s   = MutableSet[MethodSignature]()
    val res = ArrayBuffer[Method]()
    for table <- methods.values do
      for method <- table do
        val erasedSig = method.signature.erased(this)
        if s.contains(erasedSig) then res += method
        else s += erasedSig
    res.toVector
