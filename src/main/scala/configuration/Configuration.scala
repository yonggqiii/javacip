package configuration

import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver
import com.github.javaparser.resolution.declarations.*
import configuration.declaration.{
  Declaration,
  FixedDeclaration,
  MissingTypeDeclaration,
  InferenceVariableMemberTable,
  MethodWithContext
}
import configuration.assertions.*
import configuration.assertions.given
import configuration.types.*
import configuration.resolvers.*
import java.lang.reflect.Modifier
import utils.*
import scala.collection.immutable.Queue
import scala.collection.mutable.{
  ArrayBuffer,
  Map as MutableMap,
  Queue as MutableQueue,
  PriorityQueue,
  Set as MutableSet
}
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
import com.github.javaparser.ast.CompilationUnit

/** Represents the configuration of the algorithm.
  * @param delta
  *   the table of declarations found in the program (does not include reflection types)
  * @param phi1
  *   the table of missing types referenced in the program
  * @param phi2
  *   the table of inference variables generated in the program
  * @param omega
  *   the queue of assertions to be resolved
  * @param psi
  *   the types that are generated
  */
case class Configuration(
    delta: Map[String, FixedDeclaration],
    phi1: Map[String, MissingTypeDeclaration],
    phi2: Map[Type, InferenceVariableMemberTable],
    omega: PriorityQueue[Assertion],
    psi: Set[Type],
    theta: Set[Invocation],
    cu: CompilationUnit,
    _cache: MutableMap[String, FixedDeclaration] = MutableMap(),
    constraintStore: Map[String, Set[Assertion]] = Map(),
    exclusions: Map[String, Set[String]] = Map(),
    // left bounds are definitely subtypes
    // right bounds may be compatibility or subtype assertions
    javaIVBounds: Map[JavaInferenceVariable, (Vector[Type], Vector[(String, Type)])] = Map()
):
  def fix: Configuration =
    if !phi2.isEmpty then ???
    if !omega.isEmpty then ???
    if !constraintStore.isEmpty then ???
    if !javaIVBounds.isEmpty then ???
    val newDelta = MutableMap[String, FixedDeclaration]()
    for (k, v) <- delta do newDelta(k) = v
    for (k, v) <- phi1 do
      val decl = v.fix(this)
      newDelta(decl.identifier) = decl
    Configuration(
      newDelta.toMap,
      Map(),
      Map(),
      PriorityQueue(),
      psi.map(_.fix),
      theta.map(_.fix),
      cu,
      _cache,
      Map(),
      Map(),
      Map()
    )
  def addCompatibileTargetToJavaInferenceVariable(
      jv: JavaInferenceVariable,
      t: Type
  ): Configuration =
    copy(javaIVBounds =
      if !javaIVBounds.contains(jv) then javaIVBounds + (jv -> (Vector(), Vector(("=:", t))))
      else
        val (left, right) = javaIVBounds(jv)
        javaIVBounds + (jv -> (left, right :+ (("=:", t))))
    )
  def addSupertypeToJavaInferenceVariable(jv: JavaInferenceVariable, t: Type): Configuration =
    copy(javaIVBounds =
      if !javaIVBounds.contains(jv) then javaIVBounds + (jv -> (Vector(), Vector(("<:", t))))
      else
        val (left, right) = javaIVBounds(jv)
        javaIVBounds + (jv -> (left, right :+ (("<:", t))))
    )

  def addSubtypeToJavaInferenceVariable(jv: JavaInferenceVariable, t: Type): Configuration =
    copy(javaIVBounds =
      if !javaIVBounds.contains(jv) then javaIVBounds + (jv -> (Vector(t), Vector()))
      else
        val (left, right) = javaIVBounds(jv)
        javaIVBounds + (jv -> (left :+ t, right))
    )

  def liftOneJavaInferenceVariable(): Option[Configuration] =
    // just get one inference variable
    if javaIVBounds.isEmpty then None
    val jv            = javaIVBounds.keySet.toSeq(0)
    val (left, right) = javaIVBounds(jv)
    if left.size == 1 then
      val target = left(0)
      this.replace(jv, target)
    else if left.isEmpty && right.size == 1 && right(0)._1 == "<:" then
      this.replace(jv, right(0)._2)
    else if left.isEmpty && right.isEmpty then this.replace(jv, OBJECT)
    else if left.isEmpty then
      // cannot be some temporary type
      val newType = InferenceVariableFactory.createDisjunctiveType(
        scala.util.Left(""),
        Nil,
        jv.canBeSubsequentlyBounded,
        jv.paramChoices,
        false,
        false
      )
      this.addToPsi(newType).replace(jv, newType)
    else
      // can be some temporary type since Java will be able to find the lub accordingly
      val newType = InferenceVariableFactory.createDisjunctiveType(
        scala.util.Left(""),
        Nil,
        jv.canBeSubsequentlyBounded,
        jv.paramChoices,
        false
      )
      this.addToPsi(newType).replace(jv, newType)
  // val newIVBounds: Map[JavaInferenceVariable, (Vector[Type], Vector[(String, Type)])] =
  //   javaIVBounds.filter((k, v) => k != jv)
  // val newAssertions = ArrayBuffer[Assertion]()
  // for subtype <- left do
  //   // assume iv is equal to subtype
  //   for (kind, supertype) <- right do
  //     if kind == "=:" then newAssertions += (subtype =:~ supertype.replace(jv, subtype))
  //     else newAssertions += (subtype <:~ supertype.replace(jv, subtype))
  // // lift the attributes and methods
  // if phi2.contains(jv) then
  //   val table = phi2(jv)

  // copy(javaIVBounds = newIVBounds).assertsAllOf(newAssertions)

  def addExclusion(t: TemporaryType, toExclude: SomeClassOrInterfaceType): Configuration =
    toExclude match
      case _: ClassOrInterfaceType =>
        if !exclusions.contains(t.identifier) then
          copy(exclusions = exclusions + (t.identifier -> Set(toExclude.identifier)))
        else
          copy(exclusions =
            exclusions + (t.identifier -> (exclusions(t.identifier) + toExclude.identifier))
          )
      case m: TemporaryType =>
        if !exclusions.contains(t.identifier) then
          copy(exclusions = exclusions + (t.identifier -> Set(toExclude.identifier)))
        else if !exclusions(t.identifier).contains(toExclude.identifier) then
          copy(exclusions =
            exclusions + (t.identifier -> (exclusions(t.identifier) + toExclude.identifier))
          ).addExclusion(m, t)
        else this
  def addExclusions(t: TemporaryType, toExclude: Set[SomeClassOrInterfaceType]): Configuration =
    if toExclude.isEmpty then this
    else
      val other = toExclude.head
      this.addExclusion(t, other).addExclusions(t, toExclude - other)

  // if !exclusions.contains(t.identifier) then
  //   copy(exclusions = exclusions + (t.identifier -> toExclude.map(_.identifier)))
  // else
  //   copy(exclusions =
  //     exclusions + (t.identifier -> (exclusions(t.identifier).union(toExclude.map(_.identifier))))
  //   )
  def combineTemporaryType(
      t: TemporaryType,
      other: SomeClassOrInterfaceType
  ): Option[Configuration] =
    if exclusions.contains(t.identifier) && exclusions(t.identifier).contains(other.identifier) then
      return None
    val tempDecl = phi1(t.identifier)
    if tempDecl.numParams != getArity(other) then return None
    // PriorityQueue(newOmega.map(_.replace(oldType, newType)): _*
    val newOmega = ArrayBuffer[Assertion]()
    newOmega.addAll(omega.map(_.combineTemporaryType(t, other)))
    val newPsi = psi.map(_.combineTemporaryType(t, other))
    val newConstraintStore = constraintStore
      .filter((k, v) => k != t.identifier)
      .map((k, v) => (k -> v.map(_.combineTemporaryType(t, other))))
    if constraintStore.contains(t.identifier) then
      newOmega.addAll(constraintStore(t.identifier).map(_.combineTemporaryType(t, other)))
    // get the new exclusions
    val newExclusions =
      // get the exclusions of the temporary type
      val tempExclusions =
        if exclusions.contains(t.identifier) then exclusions(t.identifier) else Set()
      // get the old exclusions that do not include the temporary type, and combine any
      // occurrences of the temporary type
      val e = exclusions
        .filter((k, v) => k != t.identifier)
        .map((k, v) => (k -> v.map(ex => if ex == t.identifier then other.identifier else ex)))
      // add the temporary type's exclusions back into the old exclusions
      if e.contains(other.identifier) then
        e + (other.identifier    -> e(other.identifier).union(tempExclusions))
      else e + (other.identifier -> tempExclusions)
    if this |- other.isMissing then
      // just combine the two MissingTypeDeclarations
      val otherDecl = phi1(other.identifier)
      val oo        = otherDecl.combineWithTemporaryTypeDeclaration(t, other, tempDecl)
      if oo.isEmpty then return None
      val (newDecl, assertions) = oo.get
      newOmega.addAll(assertions)
      val newPhi1 = phi1 + (other.identifier -> newDecl)
      return Some(
        Configuration(
          delta,
          newPhi1,
          phi2.map((k, v) => k.combineTemporaryType(t, other) -> v.combineTemporaryType(t, other)),
          PriorityQueue(newOmega.toSeq: _*),
          newPsi,
          theta.map(_.combineTemporaryType(t, other)),
          cu,
          _cache,
          newConstraintStore,
          newExclusions,
          javaIVBounds.map((k, v) =>
            (k -> (v._1.map(s => s.combineTemporaryType(t, other)) -> v._2.map((a, s) =>
              (a, s.combineTemporaryType(t, other))
            )))
          )
        )
      )
    // here, other is a declared type
    // do isClass and isInterface checks
    if tempDecl.mustBeClass then newOmega += other.isClass
    if tempDecl.mustBeInterface then newOmega += other.isInterface
    val x = SomeClassOrInterfaceType(
      other.identifier,
      (0 until getUnderlyingDeclaration(other).numParams)
        .map(i => TypeParameterIndex(other.identifier, i))
        .toVector
    )
    val newPhi1 = MutableMap[String, MissingTypeDeclaration]()
    // collect the supertypes
    val tempSupertypes = this.getAllKnownSupertypes(
      TemporaryType(
        t.id,
        (0 until tempDecl.numParams).map(i => TypeParameterIndex(t.identifier, i)).toVector
      )
    )
    newOmega ++= tempSupertypes.map(x <:~ _)
    for (s, mtd) <- phi1 do newPhi1(s) = mtd
    // collect attributes
    for
      (attrName, attribute) <- tempDecl.attributes.map((k, v) =>
        (k -> v.combineTemporaryType(t, other))
      )
    do
      val attrType      = attribute.`type`
      val attrContainer = upcastToAttributeContainer(x, attrName)
      attrContainer match
        case None =>
          return None // no way attribute can exist
        case Some(ac) =>
          // attribute belongs to some declared type
          if this |- ac.isDeclared then
            // assert that the types are equal
            newOmega += attrType ~=~ getSubstitutedDeclaration(ac)
              .attributes(attrName)
              .`type`
          else
            // attribute belongs to some missing type
            // if attribute doesn't exist, add a new one first
            if !newPhi1(ac.identifier).attributes.contains(attrName) then
              val createdAttrType =
                InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
                  Left(ac.identifier),
                  Nil,
                  true,
                  (0 until newPhi1(ac.identifier).numParams)
                    .map(i => TypeParameterIndex(ac.identifier, i))
                    .toSet
                )
              // update table in phi
              newPhi1(ac.identifier) = newPhi1(ac.identifier).addAttribute(
                attrName,
                createdAttrType,
                attribute.isStatic,
                attribute.accessModifier,
                attribute.isFinal
              )
            // get expansion
            val (_, substitution) = ac.expansion
            // assert
            newOmega += newPhi1(ac.identifier)
              .attributes(attrName)
              .`type`
              .substitute(substitution) ~=~ attrType
    // collect the methods
    for (methodName, methodSet) <- tempDecl.methods do
      // each row in the methodTable is actually a method definition
      for method <- methodSet.map(_.combineTemporaryType(t, other)) do
        val paramTypes = method.signature.formalParameters
        val returnType = method.returnType
        val context    = method.asInstanceOf[MethodWithContext].context
        val callSiteParameterChoices =
          method.asInstanceOf[MethodWithContext].callSiteParameterChoices
        // find the types that may actually contain the methods
        val methodContainers =
          upcastAllToMethodContainer(Set(x), methodName, paramTypes.size)
        if methodContainers.isEmpty then return None
        // distinguish between declared and missing types
        val (fixedMethodContainers, missingMethodContainers) =
          methodContainers.partition(x => this |- x.isDeclared)
        // the disjunctive assertion to create
        val disjassts = ArrayBuffer[Assertion]()
        // handle the fixed declarations
        for t <- fixedMethodContainers do
          // get all relevant methods
          val decl = getSubstitutedDeclaration(t) // definitely ok
          val relevantMethods = decl
            .methods(methodName)
            .filter(m => m.callableWithNArgs(paramTypes.size))
          for rmethod <- relevantMethods do
            val relevantMethod = rmethod.asNArgs(paramTypes.size).substitute(context)
            val res            = relevantMethod.callWith(paramTypes, callSiteParameterChoices)
            disjassts += ConjunctiveAssertion(res._1 :+ (res._2 =:~ returnType))
        for t <- missingMethodContainers do
          disjassts += HasMethodAssertion(
            t.substitute(context),
            methodName,
            paramTypes,
            returnType,
            callSiteParameterChoices
          )
        newOmega += DisjunctiveAssertion(disjassts.toVector)
    Some(
      Configuration(
        delta,
        newPhi1.toMap,
        phi2.map((k, v) => k.combineTemporaryType(t, other) -> v.combineTemporaryType(t, other)),
        PriorityQueue(newOmega.toSeq: _*),
        newPsi,
        theta.map(_.combineTemporaryType(t, other)),
        cu,
        _cache,
        newConstraintStore,
        newExclusions,
        javaIVBounds.map((k, v) =>
          (k -> (v._1.map(s => s.combineTemporaryType(t, other)) -> v._2.map((a, s) =>
            (a, s.combineTemporaryType(t, other))
          )))
        )
      )
    )

  val maxBreadth =
    val breadthOfTypes = if psi.isEmpty then 0 else psi.map(_.breadth).max
    // val breadthOfMethods =
    //   if phi1.isEmpty then 0
    //   else phi1.values.flatMap(_.methods.flatMap(x => x._2)).map(_.typeParameterBounds.size).max
    //breadthOfTypes.max(breadthOfMethods)
    breadthOfTypes
  val maxDepth = if psi.isEmpty then 0 else psi.map(_.depth).max

  val heuristicValue =
    val b                = maxBreadth
    val d                = maxDepth
    val methodTracker    = MutableMap[(String, String, Int), Int]()
    var methodMaxBreadth = 0
    for d <- phi1.values do
      for (k, v) <- d.methods do
        for m <- v do
          if !m.isInstanceOf[MethodWithContext] then
            val numParams = m.signature.formalParameters.size
            if !methodTracker.contains((d.identifier, k, numParams)) then
              methodTracker((d.identifier, k, numParams)) = 1
            else methodTracker((d.identifier, k, numParams)) += 1
            methodMaxBreadth = methodMaxBreadth.max(m.typeParameterBounds.size)
    val overloadValue = if methodTracker.isEmpty then 1 else methodTracker.values.max
    (((b + 1) * (d + 1)) + ((overloadValue - 1) * 5) + methodMaxBreadth * 2)

  def addToPsi(`type`: Type) =
    copy(psi = psi + `type`)

  def addAllToPsi(types: IterableOnce[Type]) =
    copy(psi = psi ++ types)

  /** Add an assertion to the configuration
    * @param a
    *   the assertion to add
    * @return
    *   the resulting config
    */
  def asserts(a: Assertion): Configuration =
    val newOmega = omega.clone
    newOmega.enqueue(a)
    copy(omega = newOmega)

  /** Adds a bunch of assertions to the configuration
    * @param a
    *   the assertions to add
    * @return
    *   the resulting config
    */
  def assertsAllOf(a: Iterable[Assertion]) =
    val newOmega = omega.clone
    newOmega.addAll(a)
    copy(omega = newOmega)

  def getArity(t: Type): Int = t match
    // the arity of a type constructor is the number of parameters the base type
    // in the underlying declaration
    case x: SomeClassOrInterfaceType => getUnderlyingDeclaration(x).numParams
    // for all other types it is 0
    case _ => 0

  /** Pops an assertion from the configuration
    * @return
    *   the assertion and the new config excluding the assertion
    */
  def pop(): (Assertion, Configuration) =
    val newOmega = omega.clone
    val a        = newOmega.dequeue
    (a, copy(omega = newOmega))

  /** Upcasts some type to all of its missing supertypes. If the type itself is missing, then it is
    * returned
    * @param t
    *   the type to upcast
    * @return
    *   all the missing supertypes
    */
  def upcastToMissingAncestors(t: SomeClassOrInterfaceType): Vector[SomeClassOrInterfaceType] =
    if this |- t.isMissing then Vector(t)
    else getSubstitutedDeclaration(t).getDirectAncestors.flatMap(x => upcastToMissingAncestors(x))

  /** Attempts to prove an assertion. Returns true if it cannot be proven (or the negation can be
    * proven), false otherwise.
    * @param a
    *   the assertion to prove
    * @return
    *   true if it cannot be proven or its negation can be proven, false otherwise
    */
  def !|-(a: Assertion): Boolean = !(this |- a)

  /** Attempts to prove an assertion. Returns true if it can be proven, false if it either proves to
    * be false or does not have enough information to prove it
    * @param a
    *   the assertion to prove
    * @return
    *   true if it can be proven, false otherwise
    */
  def |-(a: Assertion): Boolean = a match
    case SubtypeAssertion(sub, sup) =>
      proveSubtype(sub.upwardProjection, sup.downwardProjection)
    case EquivalenceAssertion(a, b) => proveEquivalence(a, b)
    case ContainmentAssertion(sub, sup) =>
      sup match
        case ExtendsWildcardType(upper) => this |- sub <:~ upper
        case SuperWildcardType(lower)   => this |- lower <:~ sub
        case Wildcard                   => true
        case _                          => this |- (sub <:~ sup && sup <:~ sub)
    case IsClassAssertion(a) =>
      val t  = a.upwardProjection
      val id = t.identifier
      t match
        case x: SomeClassOrInterfaceType =>
          // get info from the underlying declaration
          getFixedDeclaration(x) match
            case Some(decl) => !decl.isInterface
            case None       => phi1(id).mustBeClass
        // get info from the table
        case x: InferenceVariable => getInferenceVariableMemberTable(x).mustBeClass
        // the leftmost bound of a type parameter must be a class
        case x: TTypeParameter =>
          val id         = x.containingTypeIdentifier
          val pseudotype = SomeClassOrInterfaceType(id)
          this |- getUnderlyingDeclaration(pseudotype)
            .getLeftmostReferenceTypeBoundOfTypeParameter(x)
            .isClass
        // we consider arrays as classes since it has attributes
        case x: ArrayType => true
        // everything else is not a class
        case _ => false
    case IsInterfaceAssertion(a) =>
      val t  = a.upwardProjection
      val id = t.identifier
      t match
        case x: SomeClassOrInterfaceType =>
          // get info from the underlying declaration
          getFixedDeclaration(x) match
            case Some(decl) => decl.isInterface
            case None       => phi1(id).mustBeInterface
        // get info from the table
        case x: InferenceVariable => getInferenceVariableMemberTable(x).mustBeInterface
        // the leftmost bound of a type parameter must be an interface
        case x: TTypeParameter =>
          val id         = x.containingTypeIdentifier
          val pseudotype = SomeClassOrInterfaceType(id)
          this |- getUnderlyingDeclaration(pseudotype)
            .getLeftmostReferenceTypeBoundOfTypeParameter(x)
            .isInterface
        // everything else is not an interface
        case _ => false
    case IsDeclaredAssertion(a) =>
      val t = a.upwardProjection
      a match
        case x: ClassOrInterfaceType => getFixedDeclaration(x).isDefined
        case x: ArrayType            => true
        case x: PrimitiveType        => true
        case x: InferenceVariable    => false
        case _                       => ???
    case IsMissingAssertion(a) => phi1.contains(a.upwardProjection.identifier)
    case DisjunctiveAssertion(v) =>
      if v.isEmpty then false
      else (this |- v.head) || (this |- DisjunctiveAssertion(v.tail))
    case ConjunctiveAssertion(v) =>
      if v.isEmpty then true
      else (this |- v.head) && (this |- ConjunctiveAssertion(v.tail))
    case IsPrimitiveAssertion(t) => t.isInstanceOf[PrimitiveType]
    //case IsReferenceAssertion(t) => ???
    case IsIntegralAssertion(t) =>
      t == PRIMITIVE_BYTE ||
        t == PRIMITIVE_CHAR ||
        t == PRIMITIVE_INT ||
        t == PRIMITIVE_SHORT ||
        t == PRIMITIVE_LONG ||
        t == BOXED_BYTE ||
        t == BOXED_CHAR ||
        t == BOXED_INT ||
        t == BOXED_SHORT ||
        t == BOXED_LONG
    case IsNumericAssertion(t) =>
      t == PRIMITIVE_BYTE ||
        t == PRIMITIVE_CHAR ||
        t == PRIMITIVE_DOUBLE ||
        t == PRIMITIVE_FLOAT ||
        t == PRIMITIVE_INT ||
        t == PRIMITIVE_LONG ||
        t == PRIMITIVE_SHORT ||
        t == BOXED_BYTE ||
        t == BOXED_CHAR ||
        t == BOXED_DOUBLE ||
        t == BOXED_FLOAT ||
        t == BOXED_INT ||
        t == BOXED_LONG ||
        t == BOXED_SHORT
    case x: HasMethodAssertion => false
    case WideningAssertion(l, r) =>
      (l, r) match
        case (ll: PrimitiveType, rr: PrimitiveType) => ll.widened.contains(rr)
        case _                                      => false
    case x: CompatibilityAssertion    => false
    case x: ImplementsMethodAssertion => false
    case OverridesAssertion(x, y)     => x.overrides(y, this)
    case x =>
      println(x)
      ???
  private def proveEquivalence(a: Type, b: Type): Boolean =
    (a, b) match
      case (x: Capture, y: Capture) =>
        x == y
      case (x: Capture, _) => false
      case (_, x: Capture) => false
      case (x @ (_: Alpha | _: PlaceholderType), _) =>
        constraintStore.contains(x.identifier) && constraintStore(x.identifier).contains(a ~=~ b)
      case (_, x @ (_: Alpha | _: PlaceholderType)) =>
        constraintStore.contains(x.identifier) && constraintStore(x.identifier).contains(a ~=~ b)
      case (x: SomeClassOrInterfaceType, y: SomeClassOrInterfaceType) =>
        x.identifier == y.identifier && x.numArgs == y.numArgs && x.args.zip(y.args).forall {
          case (x, y) => this |- (x ~=~ y)
        }
      case (x: PrimitiveType, y: PrimitiveType) =>
        x == y
      case (ArrayType(x), ArrayType(y)) =>
        this |- (x ~=~ y)
      case (ExtendsWildcardType(x), ExtendsWildcardType(y)) =>
        this |- (x ~=~ y)
      case (SuperWildcardType(x), SuperWildcardType(y)) =>
        this |- (x ~=~ y)
      case _ => a == b

  private def proveSubtype(left: Type, right: Type): Boolean =
    val (sub, sup) = (left.upwardProjection, right.downwardProjection)
    if sup == OBJECT then true
    else if sub == Bottom then true
    //else if sub.isSomehowUnknown || sup.isSomehowUnknown then false
    else
      (sub, sup) match
        case (x @ (_: Alpha | _: PlaceholderType), _) =>
          constraintStore.contains(x.identifier) && constraintStore(x.identifier).contains(
            sub <:~ sup
          )
        case (_, x @ (_: Alpha | _: PlaceholderType)) =>
          constraintStore.contains(x.identifier) && constraintStore(x.identifier).contains(
            sub <:~ sup
          )
        case (x: TTypeParameter, y: TTypeParameter) =>
          if x == y then true
          else
            val source = x.containingTypeIdentifier
            getFixedDeclaration(SomeClassOrInterfaceType(source)) match
              case None => false
              case Some(decl) =>
                val bounds = decl.getBoundsAsTypeParameters(x)
                bounds.contains(y)
        case (x: TTypeParameter, y: SomeClassOrInterfaceType) =>
          val source = x.containingTypeIdentifier
          getFixedDeclaration(SomeClassOrInterfaceType(source)) match
            case None => false
            case Some(decl) =>
              val bounds = decl.getAllReferenceTypeBoundsOfTypeParameter(x)
              this |- DisjunctiveAssertion(bounds.map(x => x <:~ y))
        case (x: SomeClassOrInterfaceType, y: SomeClassOrInterfaceType) =>
          upcast(x, y) match
            case None => false
            case Some(s) =>
              if s.numArgs == 0 || y.numArgs == 0 then true
              else
                val x = ConjunctiveAssertion(s.args.zip(y.args).map(x => x._1 <=~ x._2))
                this |- ConjunctiveAssertion(s.args.zip(y.args).map(x => x._1 <=~ x._2))
        case (ArrayType(x), ArrayType(y)) =>
          this |- ((x <:~ y) || (x.isPrimitive && (x ~=~ y)))
        case (ArrayType(x), y: SomeClassOrInterfaceType) =>
          this |- ((y ~=~ OBJECT) || (((x in PRIMITIVES
            .toSet[Type]) || (x ~=~ OBJECT)) && (y ~=~ ClassOrInterfaceType(
            "java.lang.Cloneable"
          ) || y ~=~ ClassOrInterfaceType("java.io.Serializable"))))
        case _ => false

  /** Replaces one type by another type everywhere in the configuration
    * @param oldType
    *   the type to be replaced
    * @param newType
    *   the type that will replace the oldType
    * @return
    *   an optional configuration where the types are replaced. it will be None if the replacement
    *   cannot be done
    */
  def replace(oldType: InferenceVariable, newType: Type): Option[Configuration] =
    val newAssertions = ArrayBuffer[Assertion]()
    var newJVBounds   = javaIVBounds

    //copy(javaIVBounds = newIVBounds).assertsAllOf(newAssertions)
    /* There are two things that can happen during a replacement:
     * 1) an inference variable is replaced by one of its choices, and/or
     * 2) some alpha is concretized
     * the result of which is a type whose upward projection is
     * a) a class or interface type of some arguments
     * b) a type parameter
     * c) a primitive type
     * d) an array type
     * e) some other inference variable
     */
    val newPhi1 = MutableMap[String, MissingTypeDeclaration]()

    //if oldType.id == 11 then println(s"yo? $oldType $newType")
    for (s, mtd) <- phi1 do
      val (newMtd, a) = mtd.replace(oldType, newType)
      newPhi1 += (s -> newMtd)
      newAssertions ++= a

    val newPhi2 = MutableMap[Type, InferenceVariableMemberTable]()

    val newConstraintStore = MutableMap[String, MutableSet[Assertion]]()

    val newTypes = ArrayBuffer[Type]()
    for tt <- psi do
      val x = tt.replace(oldType, newType)
      newTypes += x
      x match
        case ExtendsWildcardType(upper)  => newTypes += upper
        case SuperWildcardType(lower)    => newTypes += lower
        case ArrayType(base)             => newTypes += base
        case y: SomeClassOrInterfaceType => newTypes ++= y.args
        case _                           => ()

    // populate the new constraint store
    for (id, set) <- constraintStore do
      if id != oldType.identifier then
        val newSet = MutableSet[Assertion]()
        newConstraintStore(id) = newSet
        newSet.addAll(constraintStore(id).map(_.replace(oldType, newType)))
      else
        val liftedConstraints = constraintStore(id).map(_.replace(oldType, newType))
        newAssertions ++= liftedConstraints

    // here, suppose a java inference variable is to be replaced
    if oldType.isInstanceOf[JavaInferenceVariable] then
      val jv = oldType.asInstanceOf[JavaInferenceVariable]
      // lift the constraints
      if javaIVBounds.contains(jv) then
        val (left, right) = javaIVBounds(jv)
        // remove bounds from JVbounds
        newJVBounds = newJVBounds.filter((k, v) => k != jv)
        for subtype           <- left do newAssertions += subtype.replace(jv, newType) <:~ newType
        for (kind, supertype) <- right do
          if kind == "=:" then newAssertions += (newType =:~ supertype.replace(jv, newType))
          else newAssertions += (newType <:~ supertype.replace(jv, newType))

    for (t, ivmt) <- phi2 do
      val newSource            = t.replace(oldType, newType).upwardProjection
      val (newTable, newAssts) = ivmt.replace(oldType, newType)
      newAssertions ++= newAssts
      newSource match
        case x: SomeClassOrInterfaceType =>
          if this |- x.isMissing then
            // x is a missing type
            // merge
            val (newT, newA) = newPhi1(x.identifier).merge(newTable, x)
            // add new assertions
            newAssertions ++= newA
            // update table
            newPhi1(x.identifier) = newT
          else
            // x is a declared type
            // do isClass and isInterface checks
            if newTable.mustBeClass then newAssertions += x.isClass
            if newTable.mustBeInterface then newAssertions += x.isInterface
            // collect attributes
            for (attrName, attribute) <- newTable.attributes do
              val attrType      = attribute.`type`
              val attrContainer = upcastToAttributeContainer(x, attrName)
              attrContainer match
                case None =>
                  return None // no way attribute can exist
                case Some(ac) =>
                  // attribute belongs to some declared type
                  if this |- ac.isDeclared then
                    // assert that the types are equal
                    newAssertions += attrType ~=~ getSubstitutedDeclaration(ac)
                      .attributes(attrName)
                      .`type`
                  else
                    // attribute belongs to some missing type
                    // if attribute doesn't exist, add a new one first
                    if !newPhi1(ac.identifier).attributes.contains(attrName) then
                      val createdAttrType =
                        InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
                          Left(ac.identifier),
                          Nil,
                          true,
                          (0 until newPhi1(ac.identifier).numParams)
                            .map(i => TypeParameterIndex(ac.identifier, i))
                            .toSet
                        )
                      newTypes += createdAttrType
                      // update table in phi
                      newPhi1(ac.identifier) = newPhi1(ac.identifier).addAttribute(
                        attrName,
                        createdAttrType,
                        attribute.isStatic,
                        attribute.accessModifier,
                        attribute.isFinal
                      )
                    // get expansion
                    val (_, substitution) = ac.expansion
                    // assert
                    newAssertions += newPhi1(ac.identifier)
                      .attributes(attrName)
                      .`type`
                      .substitute(substitution) ~=~ attrType
            // collect the methods
            for (methodName, methodSet) <- newTable.methods do
              // each row in the methodTable is actually a method definition
              for method <- methodSet do
                val paramTypes = method.signature.formalParameters
                val returnType = method.returnType
                // find the types that may actually contain the methods
                val methodContainers =
                  upcastAllToMethodContainer(Set(x), methodName, paramTypes.size)
                if methodContainers.isEmpty then return None
                // distinguish between declared and missing types
                val (fixedMethodContainers, missingMethodContainers) =
                  methodContainers.partition(x => this |- x.isDeclared)
                // the disjunctive assertion to create
                val disjassts = ArrayBuffer[Assertion]()
                // handle the fixed declarations
                for t <- fixedMethodContainers do
                  // get all relevant methods
                  val decl = getSubstitutedDeclaration(t) // definitely ok
                  val relevantMethods = decl
                    .methods(methodName)
                    .filter(m => m.callableWithNArgs(paramTypes.size))
                  for rmethod <- relevantMethods do
                    val relevantMethod = rmethod.asNArgs(paramTypes.size)
                    val res = relevantMethod.callWith(paramTypes, method.callSiteParameterChoices)
                    disjassts += ConjunctiveAssertion(res._1 :+ (res._2 =:~ returnType))
                for t <- missingMethodContainers do
                  disjassts += HasMethodAssertion(
                    t,
                    methodName,
                    paramTypes,
                    returnType,
                    method.callSiteParameterChoices
                  )
                newAssertions += DisjunctiveAssertion(disjassts.toVector)
        case x: TTypeParameter =>
          val sourceType = x.containingTypeIdentifier
          val (erasure, allBounds): (SomeClassOrInterfaceType, Set[SomeClassOrInterfaceType]) =
            getFixedDeclaration(
              SomeClassOrInterfaceType(sourceType)
            ) match
              case None =>
                (OBJECT, Set(OBJECT))
              case Some(d) =>
                (
                  d.getLeftmostReferenceTypeBoundOfTypeParameter(x),
                  d.getAllReferenceTypeBoundsOfTypeParameter(x)
                )
          if newTable.mustBeClass then newAssertions += erasure.isClass
          if newTable.mustBeInterface then newAssertions += erasure.isInterface
          // collect attributes
          for (attrName, attribute) <- newTable.attributes do
            val attrType      = attribute.`type`
            val attrContainer = upcastToAttributeContainer(erasure, attrName)
            attrContainer match
              case None => return None // there is no way the attribute can exist
              // attribute belongs to some known type
              case Some(ac) =>
                // attribute belongs to some declared type
                if this |- ac.isDeclared then
                  // assert that the types are equal
                  newAssertions += attrType ~=~ getSubstitutedDeclaration(ac)
                    .attributes(attrName)
                    .`type`
                else
                  // attribute belongs to some missing type
                  // if attribute doesn't exist, add a new one first
                  if !newPhi1(ac.identifier).attributes.contains(attrName) then
                    val createdAttrType =
                      InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
                        Left(ac.identifier),
                        Nil,
                        true,
                        (0 until newPhi1(ac.identifier).numParams)
                          .map(i => TypeParameterIndex(ac.identifier, i))
                          .toSet
                      )
                    newTypes += createdAttrType
                    // update table in phi
                    newPhi1(ac.identifier) = newPhi1(ac.identifier).addAttribute(
                      attrName,
                      createdAttrType,
                      attribute.isStatic,
                      attribute.accessModifier,
                      attribute.isFinal
                    )
                  // get expansion
                  val (_, substitution) = ac.expansion
                  // assert
                  newAssertions += newPhi1(ac.identifier)
                    .attributes(attrName)
                    .`type`
                    .substitute(substitution) ~=~ attrType

          // collect the methods
          for (methodName, methodSet) <- newTable.methods do
            // each row in the methodTable is actually a method definition
            for method <- methodSet do
              val paramTypes = method.signature.formalParameters
              val returnType = method.returnType
              // find the types that may actually contain the methods
              val methodContainers =
                upcastAllToMethodContainer(allBounds, methodName, paramTypes.size)
              if methodContainers.isEmpty then return None
              // distinguish between declared and missing types
              val (fixedMethodContainers, missingMethodContainers) =
                methodContainers.partition(x => this |- x.isDeclared)
              // the disjunctive assertion to create
              val disjassts = ArrayBuffer[Assertion]()
              // handle the fixed declarations
              for t <- fixedMethodContainers do
                // get all relevant methods
                val decl = getSubstitutedDeclaration(t) // definitely ok
                val relevantMethods = decl
                  .methods(methodName)
                  .filter(m => m.callableWithNArgs(paramTypes.size))
                for rmethod <- relevantMethods do
                  val relevantMethod = rmethod.asNArgs(paramTypes.size)
                  val res = relevantMethod.callWith(paramTypes, method.callSiteParameterChoices)
                  disjassts += ConjunctiveAssertion(res._1 :+ (res._2 =:~ returnType))
              for t <- missingMethodContainers do
                disjassts += HasMethodAssertion(
                  t,
                  methodName,
                  paramTypes,
                  returnType,
                  method.callSiteParameterChoices
                )
              newAssertions += DisjunctiveAssertion(disjassts.toVector)

        case x: InferenceVariable =>
          if !newPhi2.contains(x) then newPhi2(newSource) = newTable
          else
            val (nt, na) = newPhi2(x).merge(newTable)
            newPhi2(x) = nt
            newAssertions ++= na
        case p: PrimitiveType =>
          // primitive types cannot have any members, nor can they be asserted
          // to be a class or interface
          if !newTable.attributes.isEmpty || !newTable.methods.isEmpty || newTable.mustBeClass || newTable.mustBeInterface then
            return None
        case x: ArrayType =>
          if !newTable.methods.isEmpty then return None
          if newTable.attributes.exists((k, v) => k != "length") then return None
          if newTable.mustBeInterface then return None
          if newTable.attributes.contains("length") then
            newAssertions += newTable.attributes("length").`type` ~=~ PRIMITIVE_INT
        case x =>
          println(x)
          ???
    val newOmega = omega.toVector ++ newAssertions
    Some(
      Configuration(
        delta,
        newPhi1.toMap,
        newPhi2.toMap,
        PriorityQueue(newOmega.map(_.replace(oldType, newType)): _*),
        newTypes.toSet[Type],
        theta.map(_.replace(oldType, newType)),
        cu,
        _cache,
        newConstraintStore.map((s, v) => (s -> v.toSet)).toMap,
        exclusions,
        newJVBounds.map((k, v) =>
          (k -> (v._1.map(s => s.replace(oldType, newType)) -> v._2.map((a, s) =>
            (a, s.replace(oldType, newType))
          )))
        )
      )
    )

  def addToConstraintStore(alphaOrDelta: Alpha | PlaceholderType, asst: Assertion): Configuration =
    val id = alphaOrDelta.identifier
    if !constraintStore.contains(id) then
      copy(constraintStore = constraintStore + (id -> Set(asst)))
    else copy(constraintStore = constraintStore + (id -> (constraintStore(id) + asst)))

  /** Upcasts a bunch of types into its supertypes that can potentially contain the method with some
    * arity
    * @param bounds
    *   the types to upcast
    * @param methodName
    *   the name of the method
    * @param arity
    *   the arity of the method
    * @return
    *   the ancestors who contain said method
    */
  def upcastAllToMethodContainer(
      bounds: Set[SomeClassOrInterfaceType],
      methodName: String,
      arity: Int
  ): Set[SomeClassOrInterfaceType] =
    bounds.flatMap(upcastOneToMethodContainer(_, methodName, arity))

  /** Upcasts a type into its supertypes that can potentially contain the method with some arity
    * @param t
    *   the type to upcast
    * @param methodName
    *   the name of the method
    * @param arity
    *   the arity of the method
    * @return
    *   the ancestors who contain said method
    */
  def upcastOneToMethodContainer(
      t: SomeClassOrInterfaceType,
      methodName: String,
      arity: Int
  ): Set[SomeClassOrInterfaceType] =
    if this |- t.isMissing then return Set(t)
    // safe because it must be declared
    val decl = getSubstitutedDeclaration(t).asInstanceOf[FixedDeclaration]
    val current =
      if decl.methods.contains(methodName) &&
        decl.methods(methodName).exists(x => x.callableWithNArgs(arity))
      then Set(t)
      else Set()
    // don't waste time if we're already at Object
    if t == OBJECT then return current
    // if t is not abstract then only look at the superclass since they must implement
    // interface methods anyway
    val supertypes =
      if !decl.isAbstract then
        if decl.extendedTypes.isEmpty then Set(OBJECT) else decl.extendedTypes
      else decl.getDirectAncestors
    current ++ upcastAllToMethodContainer(supertypes.toSet, methodName, arity)

  /** Determines if the configuration is complete and ready to be built
    */
  def isComplete: Boolean =
    val tester: Assertion => Boolean = asst =>
      asst match
        case SubtypeAssertion(x, y) =>
          x.isInstanceOf[PlaceholderType] || y.isInstanceOf[PlaceholderType]
        case EquivalenceAssertion(x, y) =>
          x.isInstanceOf[PlaceholderType] && y.isInstanceOf[PlaceholderType]
        case ContainmentAssertion(x, y) =>
          x.isInstanceOf[PlaceholderType] || y.isInstanceOf[PlaceholderType]
        case x: ConjunctiveAssertion => false
        case y: DisjunctiveAssertion => false
        case IsClassAssertion(x)     => x.isInstanceOf[PlaceholderType]
        case IsInterfaceAssertion(x) => x.isInstanceOf[PlaceholderType]
        case x: HasMethodAssertion   => false
        case IsDeclaredAssertion(x)  => false
        case IsMissingAssertion(x)   => false
        case IsUnknownAssertion(x)   => false
        case IsPrimitiveAssertion(x) => false
        case IsIntegralAssertion(x)  => false
        case IsNumericAssertion(x)   => false
        case CompatibilityAssertion(a, b) =>
          a.isInstanceOf[PlaceholderType] || b.isInstanceOf[PlaceholderType]
        case WideningAssertion(left, right) =>
          left.isInstanceOf[PlaceholderType] || right.isInstanceOf[PlaceholderType]
    omega.isEmpty || omega.forall(tester)

  override def toString =
    "Delta:\n" +
      delta.values.mkString("\n") + (if phi1.isEmpty && phi2.isEmpty then ""
                                     else
                                       ("\n\nPhi:" + (if phi1.isEmpty then ""
                                                      else
                                                        ("\n" + phi1.values.mkString("\n")
                                                      )) + (if phi2.isEmpty then ""
                                                            else
                                                              ("\n" + phi2.values.mkString("\n")
                                                            ))
                                     )) +
      // "\n\nPhi:\n" +
      // phi1.values.mkString("\n") + "\n" + phi2.values.mkString("\n") +
      (if omega.isEmpty then ""
       else
         "\n\nOmega:\n" +
           omega.mkString("\n")
      ) +
      (if psi.isEmpty then "" else s"\n\nPsi: [${psi.mkString(", ")}]") +
      (if theta.isEmpty then "" else "\n\nTheta: " + theta.mkString(", ")) +
      (if constraintStore.isEmpty then ""
       else
         "\n\nConstraint store:\n" + constraintStore
           .map((id, s) => s"$id: [${s.mkString(", ")}]")
           .mkString("\n")
      ) +
      (if exclusions.isEmpty then ""
       else
         "\n\nExclusions:\n" + exclusions
           .map((id, s) => s"$id: [${s.mkString(", ")}]")
           .mkString("\n")
      ) +
      (if javaIVBounds.isEmpty then ""
       else
         "\n\nInference Variable Bounds:\n" + javaIVBounds
           .map((id, s) =>
             s"[${s._1.mkString(", ")}] <: $id [${s._2.map((a, b) => a + " " + b.toString).mkString(", ")}]"
           )
           .mkString("\n")
      )

  /** Upcasts a type into the first ancestor that contains an attribute of attributeName
    * @param typet
    *   the type to upcast
    * @param attributeName
    *   the name of the attribute
    * @return
    *   an optional ancestor who contains said attribute
    */
  def upcastToAttributeContainer(
      t: SomeClassOrInterfaceType,
      attributeName: String
  ): Option[SomeClassOrInterfaceType] =
    if this |- t.isMissing then return Some(t)
    // safe because we know t must be declared
    val decl = getSubstitutedDeclaration(t).asInstanceOf[FixedDeclaration]
    // easy
    if decl.attributes.contains(attributeName) then return Some(t)
    // it means Object doesn't contain said attribute; fail
    if t == OBJECT then return None
    // otherwise, look for superclasses
    val supertype =
      // can only inherit from Object in this case
      if decl.isAbstract || decl.extendedTypes.isEmpty then OBJECT
      // get the superclass; there should only be one
      else decl.extendedTypes(0)
    upcastToAttributeContainer(supertype, attributeName)

  /** Gets the declaration of some type t
    * @param t
    *   the type whose declaration is to be obtained
    * @return
    *   the declaration of the type
    */
  def getUnderlyingDeclaration(t: SomeClassOrInterfaceType): Declaration =
    getFixedDeclaration(t) match
      case Some(x) => x
      case _       => phi1(t.identifier)

  def getInferenceVariableMemberTable(t: InferenceVariable): InferenceVariableMemberTable = phi2(t)

  def getSubstitutedDeclaration(t: SomeClassOrInterfaceType): Declaration =
    val ud = getUnderlyingDeclaration(t)
    // case of t being a raw type
    if t.numArgs < ud.numParams && t.numArgs == 0 then ud.erased
    else
      if t.numArgs != ud.numParams then
        throw RuntimeException(
          s"$t and its underlying declaration have different number of parameters!"
        )
      val (_, substitution) = t.captured.expansion
      ud.substitute(substitution)

  /** Obtains the declaration of some declared type
    * @param t
    *   the type whose declaration is to be obtained
    * @return
    *   an optional declaration; it returns None as long as the type is missing or unknown
    */
  def getFixedDeclaration(t: SomeClassOrInterfaceType): Option[FixedDeclaration] = t match
    case x: ClassOrInterfaceType =>
      if this |- t.isMissing then None
      else if delta.contains(t.identifier) then Some(delta(t.identifier))
      else getReflectionTypeDeclaration(x)
    case x: TemporaryType => None

  private def getReflectionTypeDeclaration(t: ClassOrInterfaceType): Option[FixedDeclaration] =
    val rts         = ReflectionTypeSolver()
    val declAttempt = rts.tryToSolveType(t.identifier)
    if !declAttempt.isSolved then None
    else
      val rtd        = declAttempt.getCorrespondingDeclaration.asReferenceType
      val identifier = rtd.getQualifiedName
      if _cache.contains(identifier) then Some(_cache(identifier))
      else
        val c: java.lang.Class[?] = java.lang.Class.forName(identifier)
        val modifiers             = c.getModifiers
        val isAbstract            = Modifier.isAbstract(modifiers)
        val isFinal               = Modifier.isFinal(modifiers)
        val res =
          convertResolvedReferenceTypeDeclarationToFixedDeclaration(rtd, isAbstract, isFinal).get
        _cache(identifier) = res
        Some(res)

  /** Upcasts a type into an instance of another type
    * @param t
    *   the type to upcast
    * @param target
    *   the type to upcast it into
    * @return
    *   the resulting type; it returns None if the upcast fails
    */
  def upcast(t: SomeClassOrInterfaceType, target: SomeClassOrInterfaceType): Option[Type] =
    if t.identifier == target.identifier then return Some(t)
    val decl       = getSubstitutedDeclaration(t)
    val supertypes = decl.getDirectAncestors
    supertypes.foldLeft(None: Option[Type])((o, i) =>
      o match
        case None          => upcast(i, target)
        case x: Some[Type] => x
    )

  def getAllKnownSupertypes(t: SomeClassOrInterfaceType): Set[SomeClassOrInterfaceType] =
    getSubstitutedDeclaration(t).getDirectAncestors.toSet.flatMap(x => getAllKnownSupertypes(x) + x)

  def isFullyDeclared(t: SomeClassOrInterfaceType): Boolean =
    getFixedDeclaration(t) match
      case None => false
      case Some(decl) =>
        val a = decl.getDirectAncestors
        a.forall(x => isFullyDeclared(SomeClassOrInterfaceType(x.identifier)))

  def getMissingDeclaration(t: SomeClassOrInterfaceType): Option[MissingTypeDeclaration] =
    if phi1.contains(t.identifier) then Some(phi1(t.identifier))
    else None

object Configuration:
  def createEmptyConfiguration() =
    Configuration(Map(), Map(), Map(), PriorityQueue(), Set(), Set(), null)
