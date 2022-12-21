package configuration.declaration

import configuration.types.*
import configuration.Configuration
import scala.collection.mutable.{Map as MutableMap}

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
  val attributes: Map[String, Attribute]

  /** The methods of this declaration
    */
  val methods: Map[String, Vector[Method]]

  /** The constructors of this declaration
    */
  val constructors: Vector[Constructor]

  /** Gets the direct ancestors of this type
    * @return
    *   the direct ancestors of this type
    */
  def getDirectAncestors: Vector[ClassOrInterfaceType]

  /** The number of type parameters this type receives, i.e. the arity of the type constructor of
    * this type
    */
  val numParams: Int

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

  // /** Gets all inherited methods from all its supertypes, whether they are abstract or otherwise
  //   * @param config
  //   *   the configuration to obtain the declaration of its supertypes from
  //   * @return
  //   *   the inherited methods from all its supertypes
  //   */
  // def getInheritedMethodUsages(config: Configuration): Map[String, Set[Method]] =
  //   val x = getDirectAncestors.map(x =>
  //     config
  //       .getDeclaration(x)
  //       .getAllAccessibleMethodUsagesFromInstance(x, config)
  //       .map((k, v) => k -> v.filter(m => m.accessLevelAtLeast(PROTECTED)))
  //   ) :+ methods
  //   val res = MutableMap[String, Set[Method]]().withDefaultValue(Set())
  //   for m <- x do for (k, v) <- m do res(k) = res(k) ++ v
  //   res.toMap

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

  // /** Gets all the methods (including inherited ones) that are accessible and/or inheritable based
  //   * on a minimum access level
  //   * @param config
  //   *   the configuration to obtain the declaration of its supertypes from
  //   * @param minAccessLevel
  //   *   the minimum access level of the methods to obtain
  //   * @return
  //   *   all methods of this declaration that meet the access level
  //   */
  // def getAccessibleMethods: Map[String, Set[T]] = ???

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

  // /** Get the all method usages (including inherited ones) from an instance of this declaration
  //   * @param t
  //   *   the instance
  //   * @param config
  //   *   the configuration to obtain the declaration of the supertypes from
  //   * @return
  //   *   the method usages of this instance
  //   */
  // def getAllAccessibleMethodUsagesFromInstance(
  //     t: Type,
  //     config: Configuration
  // ): Map[String, Set[Method]] =
  //   getAllAccessibleMethodUsages(config).map((k, v) =>
  //     (k -> v.map(m => m.addSubstitutionLists(t.substitutions)))
  //   )

  // /** Get all the accessible method usages (including inherited ones) from this declaration
  //   * @param the
  //   *   configuration to obtain declarations of the supertypes from
  //   * @return
  //   *   the method usages
  //   */
  // def getAllAccessibleMethodUsages(config: Configuration): Map[String, Set[Method]] =
  //   val x   = getInheritedMethodUsages(config)
  //   val res = MutableMap[String, Set[Method]]().withDefaultValue(Set())
  //   for (k, v) <- x do res(k) = res(k) ++ v
  //   for (k, v) <- methods do res(k) = res(k) ++ v
  //   res.toMap

  def substitute(function: Substitution): Declaration
  def getLeftmostReferenceTypeBoundOfTypeParameter(`type`: Type): ClassOrInterfaceType
  def getAllReferenceTypeBoundsOfTypeParameter(
      `type`: Type,
      exclusions: Set[Type] = Set()
  ): Vector[ClassOrInterfaceType]

  def getErasure(`type`: Type): Type
