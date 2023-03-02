package configuration.basetraits

import configuration.types.*
import scala.collection.SeqOps

/** Represents an object that is wildcard-capturable
  * @tparam A
  *   the object after wildcard-capture
  */
trait WildcardCapturable[+A]:
  /** Performs a wildcard capture on this object
    * @return
    *   the object after wildcard capture
    */
  def wildcardCaptured: A

/** Represents an object that is capturable via capture conversion
  * @tparam A
  *   the object after capture conversion
  */
trait Capturable[+A]:
  /** Performs capture conversion on this object
    * @return
    *   the resulting object after capture conversion
    */
  def captured: A

/** Represents an object can be converted to its raw equivalent
  * @tparam A
  *   the type of the raw object
  */
trait Raw[+A]:
  /** Returns the equivalent object after removing all type arguments, a.k.a. the raw object.
    * @return
    *   the raw object of this object
    */
  def raw: A

/** Represents an object that can be fixed to normal Java types
  * @tparam A
  *   the type of the fixed object
  */
trait Fixable[+A]:
  /** Fix this object
    * @return
    *   the fixed object
    */
  def fix: A

/** An object that has an upward projection
  * @tparam A
  *   the type of the upward projection
  */
trait UpwardProjectable[+A]:
  /** The upward projection of this object */
  val upwardProjection: A

/** An object that has a downward projection
  * @tparam A
  *   the type of the downward projection
  */
trait DownwardProjectable[+A]:
  /** The downward projection of this object */
  val downwardProjection: A

/** An object that can be substituted
  * @tparam A
  *   the object after substitution
  */
trait Substitutable[+A]:
  /** Add a substitution function to this object
    * @param function
    *   the function that maps type parameters to types
    * @return
    *   the object after substitution
    */
  def substitute(function: Substitution): A

/** An type that has an expansion
  * @tparam A
  *   the type after expansion
  */
trait Expandable[+A]:
  /** Performs an expansion on this type
    * @return
    *   the type after expansion, and its corresponding substitution; such that
    *   A.substitute(Substitution) = this
    */
  def expansion: (A, Substitution)

/** An object that can be replaced
  * @tparam A
  *   the object after replacement
  */
trait Replaceable[+A]:
  /** Replace any occurrences of an old type in this object and all its substitutions/members with a
    * new type
    * @param oldType
    *   the old type to be replaced
    * @param newType
    *   the new type after replacement
    * @return
    *   the resulting object
    */
  def replace(oldType: InferenceVariable, newType: Type): A

/** An object that can be combined
  * @tparam A
  *   the object after combining
  */
trait Combineable[+A]:
  /** Combines all occurrences of an old type in this type and all its substitutions/members with a
    * new type
    * @param oldType
    *   the old type to combine
    * @param newType
    *   the new type after combining
    * @return
    *   the resulting object
    */
  def combineTemporaryType(oldType: TemporaryType, newType: SomeClassOrInterfaceType): A

/** An object whose type parameters can be reordered
  */
trait Reorderable[+A]:
  /** Reorders type parameters by replacing all type parameters given a scheme
    * @param scheme
    *   the scheme to replace all type parameters
    * @return
    *   the resulting object after re-ordering
    */
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): A

extension [A, F[_]](t: SeqOps[Raw[A], F, F[Raw[A]]])
  /** Maps all types in this sequence to their raw types
    * @return
    *   the sequence of the raw types
    */
  def raw: F[A] = t.map(_.raw)

extension [A, F[_]](t: SeqOps[Fixable[A], F, F[Fixable[A]]])
  /** Fixes all types in this sequence
    * @return
    *   the sequence of fixed types
    */
  def fix: F[A] = t.map(_.fix)

extension [A, F[_]](t: SeqOps[WildcardCapturable[A], F, F[WildcardCapturable[A]]])
  /** Performs a wildcard capture on all types in this sequence
    * @return
    *   the resulting sequence
    */
  def wildcardCaptured: F[A] = t.map(_.wildcardCaptured)

extension [A, F[_]](t: SeqOps[Capturable[A], F, F[Capturable[A]]])
  /** Performs capture conversion on all types in this sequence
    * @return
    *   the resulting sequence
    */
  def captured: F[A] = t.map(_.captured)

extension [A, F[_]](t: SeqOps[UpwardProjectable[A], F, F[UpwardProjectable[A]]])
  /** Gets the upward projection on all types in this sequence
    * @return
    *   the resulting sequence
    */
  def upwardProjection: F[A] = t.map(_.upwardProjection)

extension [A, F[_]](t: SeqOps[DownwardProjectable[A], F, F[DownwardProjectable[A]]])
  /** Gets the downward projections of all types in this sequence
    * @return
    *   the resulting sequence
    */
  def downwardProjection: F[A] = t.map(_.downwardProjection)

extension [A, F[_]](t: SeqOps[Substitutable[A], F, F[Substitutable[A]]])
  /** Performs a substitution on all types in this sequence
    * @param function
    *   the substitution function
    * @return
    *   the resulting sequence
    */
  def substitute(function: Substitution): F[A] = t.map(_.substitute(function))

extension [A, F[_]](t: SeqOps[Expandable[A], F, F[Expandable[A]]])
  /** Performs an expansion on all types in this sequence
    * @return
    *   the resulting sequence of expansions
    */
  def expansion: F[(A, Substitution)] = t.map(_.expansion)

extension [A, F[_]](t: SeqOps[Replaceable[A], F, F[Replaceable[A]]])
  /** Performs a replacement on all types in this sequence
    * @param oldType
    *   the type to replace
    * @param newType
    *   the type to replace it with
    * @return
    *   the resulting sequence
    */
  def replace(oldType: InferenceVariable, newType: Type): F[A] = t.map(_.replace(oldType, newType))

extension [A, F[_]](t: SeqOps[Combineable[A], F, F[Combineable[A]]])
  /** Performs a combination on all types in this sequence
    * @param oldType
    *   the temporary type to combine
    * @param newType
    *   the type to combine with
    * @return
    *   the resulting sequence
    */
  def combineTemporaryType(oldType: TemporaryType, newType: SomeClassOrInterfaceType) =
    t.map(_.combineTemporaryType(oldType, newType))

extension [A, F[_]](t: SeqOps[Reorderable[A], F, F[Reorderable[A]]])
  /** Reorders the type parameters of all types in this sequence
    * @param scheme
    *   the scheme to reorder the type parameters by
    * @return
    *   the resulting sequence
    */
  def reorderTypeParameters(scheme: Map[TTypeParameter, TTypeParameter]): F[A] =
    t.map(_.reorderTypeParameters(scheme))
