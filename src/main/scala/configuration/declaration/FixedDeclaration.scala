package configuration.declaration

import configuration.types.*
import configuration.Configuration
import scala.annotation.tailrec
import scala.collection.mutable.{Set as MutableSet, ArrayBuffer}

/** An immutable declaration of a type that is either part of the incomplete program or from the JDK
  *
  * @param identifier
  *   the name of the type
  * @param typeParameters
  *   the bounds of the type parameters of this type
  * @param isFinal
  *   whether this type is final
  * @param isAbstract
  *   whether this type is abstract
  * @param isInterface
  *   whether this type is an interface; if it is not, then it is a class
  * @param extendedTypes
  *   any type that this type extends
  * @param implementedTypes
  *   any type that this type implements
  * @param methodTypeParameterBounds
  *   any method type parameters and its corresponding bounds
  * @param attributes
  *   the attributes of this type
  * @param methods
  *   the methods of this type
  * @param constructors
  *   the constructors of this type
  */
class FixedDeclaration(
    val identifier: String,
    val typeParameterBounds: Vector[Vector[Type]],
    val isFinal: Boolean,
    val isAbstract: Boolean,
    val isInterface: Boolean,
    val extendedTypes: Vector[Type],
    val implementedTypes: Vector[Type],
    val methodTypeParameterBounds: Map[String, Vector[Type]],
    val attributes: Map[String, Attribute],
    val methods: Map[String, Set[Method]],
    val constructors: Set[Constructor]
) extends Declaration[Method, Constructor]:
  val numParams = typeParameterBounds.size

  override def toString =
    val ab      = ArrayBuffer[String]()
    val numArgs = typeParameterBounds.size
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
              (if typeParameterBounds(i).size == 0 then ""
               else " extends " + typeParameterBounds(i).mkString(" & "))
          )
          .mkString(", ") + ">"
    ab += identifier + args
    if extendedTypes.size > 0 then
      ab += "extends"
      ab += extendedTypes.mkString(", ")
    if implementedTypes.size > 0 then
      ab += "implements"
      ab += implementedTypes.mkString(", ")
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

  /** Gets the direct ancestors of this type. If this type doesn't extend any type and isn't
    * [[java.lang.Object]], then [[java.lang.Object]] will be included as its direct ancestor.
    *
    * @return
    *   the direct ancestors of this type
    */
  def getDirectAncestors =
    if identifier == "java.lang.Object" || identifier == "Object" then Vector()
    else
      val a = extendedTypes ++ implementedTypes
      if a.isEmpty then Vector(OBJECT)
      else a

  /** Gets all the type bounds of this type
    * @param t
    *   the type whose bounds is to be obtained
    * @param exclusions
    *   the types who may already be included in the bounds check
    * @return
    *   the set of all bounds of this type
    */
  def getAllBounds(t: Type, exclusions: Set[Type] = Set()): Set[Type] =
    if exclusions.contains(t) then Set(OBJECT)
    else
      t match
        case _: TTypeParameter =>
          val bounds = getBounds(t)
          if bounds.isEmpty then Set(OBJECT)
          else bounds.flatMap(getAllBounds(_, exclusions + t)).toSet
        case x: Type => Set(x)

  def getBoundsAsTypeParameters(t: Type, exclusions: Set[Type] = Set()): Set[Type] =
    if exclusions.contains(t) then Set()
    else
      val b = getBounds(t).filter(x =>
        x match
          case y: TTypeParameter => true
          case _                 => false
      )
      b.toSet.flatMap(x => getBoundsAsTypeParameters(x, exclusions + t) + x)

  /** Get the type bounds of this type
    * @param typet
    * @return
    *   the bounds of this type
    */
  def getBounds(typet: Type): Vector[Type] =
    typet match
      case TypeParameterIndex(source, index) =>
        if source != identifier then ??? // TODO
        else typeParameterBounds(index)
      case TypeParameterName(sourceType, source, qualifiedName) =>
        if sourceType != identifier then ??? // TODO
        else
          val index = source + "#" + qualifiedName
          if !methodTypeParameterBounds.contains(index) then ??? // TODO
          else methodTypeParameterBounds(index)
      case _ => ??? // TODO

  def getAllReferenceTypeBoundsOfTypeParameter(`type`: Type, exclusions: Set[Type] = Set()): Vector[Type] =
    if exclusions contains `type` then Vector()
    else `type` match
      case x: TTypeParameter => 
        val bounds = getBounds(`type`)
        if bounds.isEmpty then Vector(OBJECT) else
          bounds.flatMap(x => getAllReferenceTypeBoundsOfTypeParameter(x, exclusions + `type`))
      case x: ClassOrInterfaceType => Vector(x)    
      case _ => ???

  def getLeftmostReferenceTypeBoundOfTypeParameter(`type`: Type): Type =
    getAllReferenceTypeBoundsOfTypeParameter(`type`)(0)

  /** Gets the erasure of a type which is the erasure leftmost bound, where the erasure of a
    * non-type parameter is itself (not its raw type)
    * @param typet
    *   the type to obtain its erasure
    * @param exclusions
    *   the set of types who are considered in the erasure check
    * @return
    *   the erasure of the type
    */
  def getErasure(`type`: Type): Type =
    `type` match
      case x: TTypeParameter => getErasure(getLeftmostReferenceTypeBoundOfTypeParameter(x))
      case x: PrimitiveType => x
      case ArrayType(base) => ArrayType(getErasure(base))
      case Bottom => Bottom
      case ExtendsWildcardType(upper) => getErasure(upper)
      case x: ClassOrInterfaceType => x.raw
      case SuperWildcardType(lower) => OBJECT
      case Wildcard => OBJECT
      case x => x

  // def getErasure(typet: Type, exclusions: Set[Type] = Set()): Type =
  //   if exclusions.contains(typet) then OBJECT
  //   else
  //     typet match
  //       case _: TTypeParameter =>
  //         val bounds = getBounds(typet)
  //         if bounds.isEmpty then OBJECT
  //         else getErasure(bounds(0), exclusions + typet)
  //       case x: Type => x

  // /** Get the raw erasure of a type, which is the raw erasure of the leftmost bound, where the
  //   * erasure of a non-type parameter is its raw type
  //   * @param typet
  //   *   the type to obtain its raw erasure
  //   * @return
  //   *   the raw erasure of the type
  //   */
  // def getRawErasure(typet: Type): Type =
  //   typet match
  //     case _: Type | _: TTypeParameter =>
  //       getErasure(typet).substituted.raw
  //     case x: PrimitiveType => x
  //     case ArrayType(base)  => ArrayType(getRawErasure(base))
  //     case x                => ???

  /** Obtains the vector of methods that have conflicting signatures
    * @return
    *   the vector of methods who have conflicting signatures
    */
  def conflictingMethods =
    val s   = MutableSet[MethodSignature]()
    val res = ArrayBuffer[Method]()
    for table <- methods.values do
      for method <- table do
        val erasedSig = method.signature.erased(this)
        if s.contains(erasedSig) then res += method
        else s += erasedSig
    res.toVector
