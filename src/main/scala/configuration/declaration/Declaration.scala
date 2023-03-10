package configuration.declaration

import configuration.types.*
import configuration.Configuration
import scala.collection.mutable.{Map as MutableMap, ArrayBuffer}

type AttributesList = Map[String, Attribute]

type ConstructorList = Vector[Constructor]

type MethodList = Map[String, Vector[Method]]

extension [A, B](m: Map[A, B])
  /** Maps the values of this map
    * @tparam C
    *   the result of applying the function
    * @param f
    *   the function to apply to the values of this map
    * @return
    *   the resulting map
    */
  def >->=[C](f: B => C): Map[A, C] = m.map((k, v) => (k -> f(v)))

extension (m: AttributesList)
  /** Adds one attribute to this attributes list
    * @param a
    *   the attribute to add
    * @return
    *   the resulting attributes list
    */
  def >+(a: Attribute): AttributesList = m + (a.identifier -> a)

extension (m: MethodList)
  /** Maps each method in this method list
    * @param f
    *   the function to map the method
    * @return
    *   the resulting method list
    */
  def mmap(f: Method => Method) = m.map((k, v) => (k -> v.map(f)))

  /** Adds one method to this methods list
    * @param mtd
    *   the method to add
    * @return
    *   the resulting methods list
    */
  def >+(mtd: Method): MethodList =
    val id = mtd.signature.identifier
    if !m.contains(id) then m + (id -> Vector(mtd))
    else m + (id                    -> (m(id) :+ mtd))

/** A declaration of a type in the program.
  */
trait Declaration:
  /** The name of the type
    */
  val identifier: String

  /** The bounds of the type parameters of this type
    */
  val typeParameterBounds: Vector[Vector[Type]]

  /** Whether the type is final
    */
  val isFinal: Boolean

  /** Whether the type is abstract
    */
  val isAbstract: Boolean

  /** Whether the type is an interface
    */
  val isInterface: Boolean

  /** Whether the type is a class */
  val isClass: Boolean

  /** The bounds on any of the method type parameters
    */
  val methodTypeParameterBounds: Map[String, Vector[Type]]

  /** The attributes of this declaration
    */
  val attributes: AttributesList

  /** The methods of this declaration
    */
  val methods: MethodList

  /** The constructors of this declaration
    */
  val constructors: Vector[Constructor]

  /** Gets the direct ancestors of this type
    * @return
    *   the direct ancestors of this type
    */
  def getDirectAncestors: Vector[SomeClassOrInterfaceType]

  /** The number of type parameters this type receives, i.e. the arity of the type constructor of
    * this type
    */
  val numParams: Int

  /** The declaration after erasure
    * @return
    *   the erased declaration
    */
  def erased: Declaration

  /** Gets all inherited attributes from all its supertypes
    * @param config
    *   the configuration to obtain the declaration of its supertypes from
    * @return
    *   the inherited attributes from all its supertypes
    */
  def getInheritedAttributes(config: Configuration): Map[String, Attribute] =
    // get all the attributes from the supertypes
    val x: Vector[Map[String, Attribute]] = getDirectAncestors
      .filter(x => config !|- x.isInterface)
      .map(x => config.getSubstitutedDeclaration(x).getAccessibleAttributes(config, PROTECTED))
    // check if the attributes from the supertypes is "overridden" in this declaration
    val y = x.map(m => m.filter((id, a) => !this.attributes.contains(id)))
    // return the attributes
    // there should be only one direct ancestor to inherit from
    if y.isEmpty then Map() else y(0)

  /** Get all the methods that are inherited from the supertypes. Only methods that are finalized
    * and nonstatic Methods will be produced
    * @param config
    *   the configuration of the algorithm
    * @return
    *   the inherited methods
    */
  def getAllInheritedMethods(config: Configuration): Map[String, Vector[Method]] =
    val supertypeDeclarations = getDirectAncestors.map(x => config.getSubstitutedDeclaration(x))
    val methods = supertypeDeclarations.map(x => x.getAccessibleMethods(config, PROTECTED))
    methods
      .foldLeft(Map[String, Vector[Method]]())((om, nm) =>
        nm.foldLeft(om)((m, sm) =>
          if !m.contains(sm._1) then m + sm
          else m + (sm._1 -> (m(sm._1) ++ sm._2))
        )
      )
      .map((k, v) => (k -> v.filter(!_.isStatic)))

  /** Get all methods it has access to, including the original input erasures
    * @param config
    *   the configuration of the algorithm
    * @return
    *   the methods and their original erasures
    */
  def getAllMethodsWithErasures(
      config: Configuration
  ): Map[String, Vector[(Method, MethodSignature)]] =
    val res = MutableMap[String, ArrayBuffer[(Method, MethodSignature)]](
      getAllInheritedMethodsWithErasures(config).map((k, v) => (k -> ArrayBuffer(v: _*))).toSeq: _*
    )
    for
      (id, v) <- methods
        .map((k, v) => (k -> v.filter(m => !m.isInstanceOf[MethodWithContext])))
        .filter((k, v) => !v.isEmpty)
    do
      if !res.contains(id) then res(id) = ArrayBuffer()
      for method <- v do res(id) += ((method, method.signature.erased(this)))
    res.map((k, v) => (k -> v.toVector)).toMap

  /** Get all methods this declaration inherits, along with the original input erasures
    * @param config
    *   the configuration of the algorithm
    * @return
    *   the inherited methods and their original erasures
    */
  def getAllInheritedMethodsWithErasures(
      config: Configuration
  ): Map[String, Vector[(Method, MethodSignature)]] =
    val res = MutableMap[String, ArrayBuffer[(Method, MethodSignature)]]()
    for s <- getDirectAncestors do
      val ud           = config.getUnderlyingDeclaration(s)
      val (_, context) = s.expansion
      for (id, v) <- ud.getAllMethodsWithErasures(config) do
        if !res.contains(id) then res(id) = ArrayBuffer()
        for (method, erasure) <- v do
          if !method.isStatic then res(id) += ((method.substitute(context), erasure))
    res.map((k, v) => (k -> v.toVector)).toMap

  /** Get all methods that are accessible to this method including inherited ones
    * @param config
    *   the configuration of the algorithm
    * @param accessLevel
    *   the access level of the method
    * @return
    *   all the accessible methods of this declaration
    */
  def getAccessibleMethods(
      config: Configuration,
      accessLevel: AccessModifier
  ): Map[String, Vector[Method]] =
    val superMethods = getAllInheritedMethods(config)
    val res = MutableMap(
      methods
        .map((k, v) => (k -> v.filter(m => !m.isInstanceOf[MethodWithContext])))
        .filter((k, v) => !v.isEmpty)
        .toSeq
        .map((k, v) => (k -> ArrayBuffer(v: _*))): _*
    )
    // val tempMethods  = MutableMap(methods.toSeq.map((k, v) => (k -> ArrayBuffer(v: _*))): _*)
    for (id, vm) <- superMethods do
      for method <- vm do
        // nothing to override, just add
        if !methods.contains(id) then
          if !res.contains(id) then res(id) = ArrayBuffer()
          res(id) += method
        else
        // no overriding method
        if methods(id).forall(myMethod => !myMethod.overrides(method, config)) then
          if !res.contains(id) then res(id) = ArrayBuffer()
          res(id) += method
    res.map((k, v) => (k -> v.toVector)).toMap

  /** Get the erasure of a method
    * @param m
    *   the method
    * @return
    *   the erasure of the method
    */
  def getMethodErasure(m: Method): Method

  /** Gets all the attributes (including inherited ones) that are accessible and/or inheritable
    * based on a minimum access level
    * @param config
    *   the configuration to obtain the declaration of its supertypes from
    * @param minAccessLevel
    *   the minimum access level of the attributes to obtain
    * @return
    *   all attributes of this declaration that meet the access level
    */
  def getAccessibleAttributes(
      config: Configuration,
      accessLevel: AccessModifier
  ): Map[String, Attribute] =
    val allAttributes = this.attributes ++ getInheritedAttributes(config)
    allAttributes.filter((k, v) => accessLevel <= v.accessModifier)

  /** Gets an attribute from one of its instances
    * @param t
    *   the type of the instance
    * @param identifier
    *   the identifier of the attribute
    * @return
    *   an optional attribute if it is found and `t` is a valid instance
    */
  def getAttributeFromInstance(
      t: Type,
      identifier: String
  ) =
    if !attributes.contains(identifier) then None
    else if t.identifier != this.identifier then None
    else Some(attributes(identifier).substitute(t.expansion._2))

  /** Substitutes the declaration
    * @param function
    *   the substitution function
    * @return
    *   the resulting declaration
    */
  def substitute(function: Substitution): Declaration

  /** Gets the leftmost reference type bound of a type parameter
    * @param type
    *   the type whose bound is to be obtained
    * @return
    *   the leftmost reference type bound
    */
  def getLeftmostReferenceTypeBoundOfTypeParameter(`type`: Type): SomeClassOrInterfaceType

  /** Get all the reference type bounds of a type parameter
    * @param type
    *   the type whose bounds is to be obtained
    * @param exclusions
    *   the excluded types (for recursion)
    * @return
    *   the resulting reference type bounds
    */
  def getAllReferenceTypeBoundsOfTypeParameter(
      `type`: Type,
      exclusions: Set[Type] = Set()
  ): Vector[SomeClassOrInterfaceType]

  def getErasure(`type`: Type): Type
