package configuration.declaration

import configuration.types.*
import configuration.Configuration
import scala.collection.mutable.{Map as MutableMap}

/** A declaration of a type in the program.
  */
trait Declaration[T <: Method, U <: Constructor]:
  /** The name of the type
    */
  val identifier: String

  /** The bounds of the type parameters of this type
    */
  val typeParameterBounds: Vector[Vector[TypeBound]]

  /** Whether the type is final
    */
  val isFinal: Boolean

  /** Whether the type is abstract
    */
  val isAbstract: Boolean

  /** Whether the type is an interface
    */
  val isInterface: Boolean

  /** The bounds on any of the method type parameters
    */
  val methodTypeParameterBounds: Map[String, Vector[TypeBound]]

  /** The attributes of this declaration
    */
  val attributes: Map[String, Attribute]

  /** The methods of this declaration
    */
  val methods: Map[String, Set[T]]

  /** The constructors of this declaration
    */
  val constructors: Set[U]

  /** Gets the direct ancestors of this type
    * @return
    *   the direct ancestors of this type
    */
  def getDirectAncestors: Vector[ReferenceType]

  /** The number of type parameters this type receives, i.e. the arity of the type constructor of
    * this type
    */
  val numParams: Int

  /** Gets all inherited attributes from all its supertypes
    * @param config
    *   the configuration to obtain the declaration of its supertypes from
    * @return
    *   the inherited attributes from all its supertypes
    */
  def getInheritedAttributes(config: Configuration): Map[String, Attribute] =
    val x = getDirectAncestors
      .filter(x => config !|- x.isInterface)
      .map(config.getDeclaration(_).getAccessibleAttributes(config, PROTECTED))
    ???

  /** Gets all inherited methods from all its supertypes, whether they are abstract or otherwise
    * @param config
    *   the configuration to obtain the declaration of its supertypes from
    * @return
    *   the inherited methods from all its supertypes
    */
  def getInheritedMethodUsages(config: Configuration): Map[String, Set[Method]] =
    val x = getDirectAncestors.map(x =>
      config
        .getDeclaration(x)
        .getMethodUsagesFromInstance(x)
        .map((k, v) => k -> v.filter(m => m.accessLevelAtLeast(PROTECTED)))
    ) :+ methods
    val res = MutableMap[String, Set[Method]]().withDefaultValue(Set())
    for m <- x do for (k, v) <- m do res(k) = res(k) ++ v
    res.toMap

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
  ): Map[String, Attribute] = ???

  /** Gets all the methods (including inherited ones) that are accessible and/or inheritable based
    * on a minimum access level
    * @param config
    *   the configuration to obtain the declaration of its supertypes from
    * @param minAccessLevel
    *   the minimum access level of the methods to obtain
    * @return
    *   all methods of this declaration that meet the access level
    */
  def getAccessibleMethods: Map[String, Set[T]] = ???

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
    else Some(attributes(identifier).addSubstitutionLists(t.substitutions))

  def getMethodUsagesFromInstance(t: Type): Map[String, Set[Method]] =
    methods.map((k, v) => (k -> v.map(m => m.addSubstitutionLists(t.substitutions))))
