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
  def getDirectAncestors: Vector[SomeClassOrInterfaceType]

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

  def substitute(function: Substitution): Declaration
  def getLeftmostReferenceTypeBoundOfTypeParameter(`type`: Type): SomeClassOrInterfaceType
  def getAllReferenceTypeBoundsOfTypeParameter(
      `type`: Type,
      exclusions: Set[Type] = Set()
  ): Vector[SomeClassOrInterfaceType]

  def getErasure(`type`: Type): Type
