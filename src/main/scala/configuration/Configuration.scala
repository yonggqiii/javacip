package configuration

import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver
import com.github.javaparser.resolution.declarations.*
import configuration.declaration.{
  Declaration,
  FixedDeclaration,
  MissingTypeDeclaration,
  InferenceVariableMemberTable
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
  PriorityQueue
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
  */
case class Configuration(
    delta: Map[String, FixedDeclaration],
    phi1: Map[String, MissingTypeDeclaration],
    phi2: Map[Type, InferenceVariableMemberTable],
    omega: PriorityQueue[Assertion],
    cu: CompilationUnit,
    _cache: MutableMap[String, FixedDeclaration] = MutableMap()
):
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

  def getArity(t: ReferenceType): Int =
    getDeclaration(t).numParams

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
  def upcastToMissingAncestors(t: Type): Vector[Type] =
    if this |- t.substituted.isMissing || t.isSomehowUnknown then Vector(t)
    else
      getFixedDeclaration(t.substituted).get.getDirectAncestors
        .map(x => x.addSubstitutionLists(t.substitutions))
        .flatMap(x => upcastToMissingAncestors(x))

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
      proveSubtype(sub.substituted.upwardProjection, sup.substituted.downwardProjection)
    case EquivalenceAssertion(a, b) => a.substituted == b.substituted
    case ContainmentAssertion(sub, sup) =>
      if sup.substituted.upwardProjection == sup.substituted.downwardProjection then
        this |- (sub ~=~ sup)
      else
        (this |- (sub.upwardProjection <:~ sup.upwardProjection)) &&
        (this |- (sub.downwardProjection ~:> sup.downwardProjection))
    case IsClassAssertion(a) =>
      val t  = a.substituted.upwardProjection
      val id = t.identifier
      getFixedDeclaration(t) match
        case Some(decl) => !decl.isInterface
        case None =>
          if phi1.contains(id) then phi1(id).mustBeClass
          else if phi2.contains(t) then phi2(t).mustBeClass
          else false
    case IsInterfaceAssertion(a) =>
      val t  = a.substituted.upwardProjection
      val id = t.identifier
      getFixedDeclaration(t) match
        case Some(decl) => decl.isInterface
        case None =>
          if phi1.contains(id) then phi1(id).mustBeInterface
          else if phi2.contains(t) then phi2(t).mustBeInterface
          else false
    case IsDeclaredAssertion(a) =>
      getFixedDeclaration(
        a.substituted.upwardProjection
      ).isDefined
    case IsMissingAssertion(a) => phi1.contains(a.substituted.upwardProjection.identifier)
    case DisjunctiveAssertion(v) =>
      if v.isEmpty then false
      else (this |- v.head) || (this |- DisjunctiveAssertion(v.tail))
    case ConjunctiveAssertion(v) =>
      if v.isEmpty then true
      else (this |- v.head) && (this |- ConjunctiveAssertion(v.tail))
    case IsPrimitiveAssertion(t) =>
      t.substituted match
        case _: PrimitiveType => true
        case _                => false
    case IsReferenceAssertion(t) =>
      t.substituted match
        case _: PrimitiveType => false
        case _                => true
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
    case x =>
      println(x)
      ???
  private def proveSubtype(sub: Type, sup: Type): Boolean =
    if sup == OBJECT.substituted then true
    else if sub == Bottom.substituted then true
    else if sub.isSomehowUnknown || sup.isSomehowUnknown then false
    else
      (sub, sup) match
        case (x: TTypeParameter, y: TTypeParameter) =>
          val source = x.containingTypeIdentifier
          getFixedDeclaration(NormalType(source, 0)) match
            case None => false
            case Some(decl) =>
              val bounds = decl.getBoundsAsTypeParameters(x)
              bounds.contains(y)
        case (x: TTypeParameter, y: SubstitutedReferenceType) =>
          val source = x.containingTypeIdentifier
          getFixedDeclaration(NormalType(source, 0)) match
            case None => false
            case Some(decl) =>
              val bounds = decl.getAllBounds(x)
              this |- DisjunctiveAssertion(bounds.toVector.map(x => x <:~ y))
        case (x: SubstitutedReferenceType, y: SubstitutedReferenceType) =>
          upcast(x, y) match
            case None => false
            case Some(s) =>
              if s.numArgs == 0 || y.numArgs == 0 then true
              else this |- ConjunctiveAssertion(s.args.zip(y.args).map(x => x._1 <=~ x._2))
        case (x: PrimitiveType, y: PrimitiveType)            => x.widened.contains(y)
        case (x: PrimitiveType, y: SubstitutedReferenceType) => this |- (x.boxed <:~ y)
        case (x: SubstitutedReferenceType, y: PrimitiveType) =>
          y.isAssignableBy.contains(x)
        case (x: ArrayType, y: ArrayType) =>
          this |- ((x.base.isReference && y.base.isReference && x.base <:~ y.base) || (x.base.isPrimitive && x.base ~=~ y.base))
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
    /* There are two things that can happen during a replacement:
     * 1) an inference variable is replaced by one of its choices, and/or
     * 2) some alpha is concretized
     * the result of which is a type whose upward projection is
     * a) a substituted reference type of some arguments
     * b) some other inference variable or alpha
     */
    val newPhi1       = MutableMap[String, MissingTypeDeclaration]()
    val newAssertions = ArrayBuffer[Assertion]()

    for (s, mtd) <- phi1 do
      val (newMtd, a) = mtd.replace(oldType, newType)
      newPhi1 += (s -> newMtd)
      newAssertions ++= a

    val newPhi2 = MutableMap[Type, InferenceVariableMemberTable]()

    val alphaTable = MutableMap[Alpha, NormalType]()

    for (t, ivmt) <- phi2 do
      val newSource            = t.replace(oldType, newType).substituted.upwardProjection
      val (newTable, newAssts) = ivmt.replace(oldType, newType)
      newAssertions ++= newAssts
      newSource.substituted match
        case x: SubstitutedReferenceType =>
          newAssertions ++= newTable.constraintStore
          getFixedDeclaration(x) match
            case None =>
              val (newT, newA) = newPhi1(x.identifier).merge(newTable, x)
              newAssertions ++= newA
              newPhi1(x.identifier) = newT
            case Some(decl) =>
              if newTable.mustBeClass then newAssertions += x.isClass
              if newTable.mustBeInterface then newAssertions += x.isInterface
              for (attrName, attribute) <- newTable.attributes do
                val attrType      = attribute.`type`
                val attrContainer = upcastToAttributeContainer(x, attrName)
                attrContainer match
                  // there is no way the attribute can exist
                  case None => return None
                  // attribute belongs to some known type
                  case Some(ac) =>
                    getFixedDeclaration(ac) match
                      // attribute belongs to some declared type
                      case Some(table) =>
                        newAssertions += attrType ~=~ table
                          .attributes(attrName)
                          .`type`
                          .addSubstitutionLists(ac.substitutions)
                      // attribute can be added to a missing type
                      case None =>
                        val mttable = newPhi1(ac.identifier)
                        if mttable.attributes.contains(attrName) then
                          newAssertions += attrType ~=~ mttable
                            .attributes(attrName)
                            .`type`
                            .addSubstitutionLists(ac.substitutions)
                        else
                          val createdAttrType = InferenceVariableFactory.createDisjunctiveType(
                            Left(ac.identifier),
                            Nil,
                            true,
                            (0 until mttable.numParams)
                              .map(i => TypeParameterIndex(ac.identifier, i))
                              .toSet,
                            false
                          )
                          newPhi1(ac.identifier) = mttable.addAttribute(
                            attrName,
                            createdAttrType,
                            attribute.isStatic,
                            attribute.accessModifier,
                            attribute.isFinal
                          )
                          newAssertions += attrType ~=~ createdAttrType.addSubstitutionLists(
                            newType.substitutions
                          )

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
                    methodContainers.partition(x => getFixedDeclaration(x).isDefined)
                  // the disjunctive assertion to create
                  val disjassts = ArrayBuffer[Assertion]()
                  for t <- fixedMethodContainers do
                    // get all relevant methods
                    val decl = getFixedDeclaration(t).get // definitely ok
                    val relevantMethods = decl
                      .methods(methodName)
                      .filter(m => m.callableWithNArgs(paramTypes.size))
                    for relevantMethod <- relevantMethods do
                      val (realTypeParams, realParamTypes, realRt) = (
                        relevantMethod.typeParameterBounds.keys,
                        relevantMethod.signature.formalParameters,
                        relevantMethod.returnType
                      )
                      // create the assertions
                      val tpMap: Map[TTypeParameter, Type] =
                        realTypeParams
                          .map(x =>
                            x -> InferenceVariableFactory.createDisjunctiveType(
                              scala.util.Left(decl.identifier),
                              Nil,
                              true,
                              method.callSiteParameterChoices,
                              true
                            )
                          )
                          .toMap
                      val conjAssertion = ArrayBuffer[Assertion]()
                      val subs          = (tpMap :: t.substitutions).filter(!_.isEmpty)
                      for i <- (0 until paramTypes.size) do
                        conjAssertion += paramTypes(i) <:~ realParamTypes(i)
                          .addSubstitutionLists(subs)
                      conjAssertion += returnType ~=~ realRt.addSubstitutionLists(subs)
                      disjassts += ConjunctiveAssertion(conjAssertion.toVector)
                  for t <- missingMethodContainers do
                    disjassts += HasMethodAssertion(t, methodName, paramTypes, returnType)
                  newAssertions += DisjunctiveAssertion(disjassts.toVector)

        case x: TTypeParameter =>
          newAssertions ++= newTable.constraintStore
          val sourceType = x match
            case y: TypeParameterIndex => y.source
            case y: TypeParameterName  => y.sourceType
          val (erasure, allBounds): (ReferenceType, Set[ReferenceType]) = getFixedDeclaration(
            NormalType(sourceType, 0, Nil)
          ) match
            case None =>
              (OBJECT, Set(OBJECT))
            case Some(d) =>
              (d.getErasure(x), d.getAllBounds(x))
          if newTable.mustBeClass then newAssertions += erasure.isClass
          if newTable.mustBeInterface then newAssertions += erasure.isInterface
          for (attrName, attribute) <- newTable.attributes do
            val attrType      = attribute.`type`
            val attrContainer = upcastToAttributeContainer(erasure, attrName)
            attrContainer match
              // there is no way the attribute can exist
              case None => return None
              // attribute belongs to some known type
              case Some(ac) =>
                getFixedDeclaration(ac) match
                  // attribute belongs to some declared type
                  case Some(table) =>
                    newAssertions += attrType ~=~ table
                      .attributes(attrName)
                      .`type`
                      .addSubstitutionLists(ac.substitutions)
                  // attribute can be added to a missing type
                  case None =>
                    val mttable = newPhi1(ac.identifier)
                    if mttable.attributes.contains(attrName) then
                      newAssertions += attrType ~=~ mttable
                        .attributes(attrName)
                        .`type`
                        .addSubstitutionLists(ac.substitutions)
                    else
                      val createdAttrType = InferenceVariableFactory.createDisjunctiveType(
                        Left(ac.identifier),
                        Nil,
                        true,
                        (0 until mttable.numParams)
                          .map(i => TypeParameterIndex(ac.identifier, i))
                          .toSet,
                        false
                      )
                      newPhi1(ac.identifier) = mttable.addAttribute(
                        attrName,
                        createdAttrType,
                        attribute.isStatic,
                        attribute.accessModifier,
                        attribute.isFinal
                      )
                      newAssertions += attrType ~=~ createdAttrType.addSubstitutionLists(
                        newType.substitutions
                      )
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
                methodContainers.partition(x => getFixedDeclaration(x).isDefined)
              // the disjunctive assertion to create
              val disjassts = ArrayBuffer[Assertion]()
              for t <- fixedMethodContainers do
                // get all relevant methods
                val decl = getFixedDeclaration(t).get // definitely ok
                val relevantMethods = decl
                  .methods(methodName)
                  .filter(m => m.callableWithNArgs(paramTypes.size))
                for relevantMethod <- relevantMethods do
                  val (realTypeParams, realParamTypes, realRt) = (
                    relevantMethod.typeParameterBounds.keys,
                    relevantMethod.signature.formalParameters,
                    relevantMethod.returnType
                  )
                  // create the assertions
                  val tpMap: Map[TTypeParameter, Type] =
                    realTypeParams
                      .map(x =>
                        x -> InferenceVariableFactory.createDisjunctiveType(
                          scala.util.Left(decl.identifier),
                          Nil,
                          true,
                          method.callSiteParameterChoices,
                          true
                        )
                      )
                      .toMap
                  val conjAssertion = ArrayBuffer[Assertion]()
                  val subs          = (tpMap :: t.substitutions).filter(!_.isEmpty)
                  for i <- (0 until paramTypes.size) do
                    conjAssertion += paramTypes(i) <:~ realParamTypes(i)
                      .addSubstitutionLists(subs)
                  conjAssertion += returnType ~=~ realRt.addSubstitutionLists(subs)
                  disjassts += ConjunctiveAssertion(conjAssertion.toVector)
              for t <- missingMethodContainers do
                disjassts += HasMethodAssertion(t, methodName, paramTypes, returnType)
              newAssertions += DisjunctiveAssertion(disjassts.toVector)

        case x: InferenceVariable =>
          if !newPhi2.contains(x) then newPhi2(newSource) = newTable
          else
            val (nt, na) = newPhi2(x).merge(newTable)
            newPhi2(x) = nt
            newAssertions ++= na
        case p: PrimitiveType =>
          val x = p.boxed.substituted.asInstanceOf[SubstitutedReferenceType]
          newAssertions ++= newTable.constraintStore
          getFixedDeclaration(x) match
            case None =>
              val (newT, newA) = newPhi1(x.identifier).merge(newTable, x)
              newAssertions ++= newA
              newPhi1(x.identifier) = newT
            case Some(decl) =>
              if newTable.mustBeClass then newAssertions += x.isClass
              if newTable.mustBeInterface then newAssertions += x.isInterface
              for (attrName, attribute) <- newTable.attributes do
                val attrType      = attribute.`type`
                val attrContainer = upcastToAttributeContainer(x, attrName)
                attrContainer match
                  // there is no way the attribute can exist
                  case None => return None
                  // attribute belongs to some known type
                  case Some(ac) =>
                    getFixedDeclaration(ac) match
                      // attribute belongs to some declared type
                      case Some(table) =>
                        newAssertions += attrType ~=~ table
                          .attributes(attrName)
                          .`type`
                          .addSubstitutionLists(ac.substitutions)
                      // attribute can be added to a missing type
                      case None =>
                        val mttable = newPhi1(ac.identifier)
                        if mttable.attributes.contains(attrName) then
                          newAssertions += attrType ~=~ mttable
                            .attributes(attrName)
                            .`type`
                            .addSubstitutionLists(ac.substitutions)
                        else
                          val createdAttrType = InferenceVariableFactory.createDisjunctiveType(
                            Left(ac.identifier),
                            Nil,
                            true,
                            (0 until mttable.numParams)
                              .map(i => TypeParameterIndex(ac.identifier, i))
                              .toSet,
                            false
                          )
                          newPhi1(ac.identifier) = mttable.addAttribute(
                            attrName,
                            createdAttrType,
                            attribute.isStatic,
                            attribute.accessModifier,
                            attribute.isFinal
                          )
                          newAssertions += attrType ~=~ createdAttrType.addSubstitutionLists(
                            newType.substitutions
                          )

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
                    methodContainers.partition(x => getFixedDeclaration(x).isDefined)
                  // the disjunctive assertion to create
                  val disjassts = ArrayBuffer[Assertion]()
                  for t <- fixedMethodContainers do
                    // get all relevant methods
                    val decl = getFixedDeclaration(t).get // definitely ok
                    val relevantMethods = decl
                      .methods(methodName)
                      .filter(m => m.callableWithNArgs(paramTypes.size))
                    for relevantMethod <- relevantMethods do
                      val (realTypeParams, realParamTypes, realRt) = (
                        relevantMethod.typeParameterBounds.keys,
                        relevantMethod.signature.formalParameters,
                        relevantMethod.returnType
                      )
                      // create the assertions
                      val tpMap: Map[TTypeParameter, Type] =
                        realTypeParams
                          .map(x =>
                            x -> InferenceVariableFactory.createDisjunctiveType(
                              scala.util.Left(decl.identifier),
                              Nil,
                              true,
                              method.callSiteParameterChoices,
                              true
                            )
                          )
                          .toMap
                      val conjAssertion = ArrayBuffer[Assertion]()
                      val subs          = (tpMap :: t.substitutions).filter(!_.isEmpty)
                      for i <- (0 until paramTypes.size) do
                        conjAssertion += paramTypes(i) <:~ realParamTypes(i)
                          .addSubstitutionLists(subs)
                      conjAssertion += returnType ~=~ realRt.addSubstitutionLists(subs)
                      disjassts += ConjunctiveAssertion(conjAssertion.toVector)
                  for t <- missingMethodContainers do
                    disjassts += HasMethodAssertion(t, methodName, paramTypes, returnType)
                  newAssertions += DisjunctiveAssertion(disjassts.toVector)

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
        cu
      )
    )

  /** Upcasts a bunch of types into its supertypes that contains the method with some arity
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
      bounds: Set[ReferenceType],
      methodName: String,
      arity: Int
  ): Set[ReferenceType] =
    bounds.flatMap(upcastOneToMethodContainer(_, methodName, arity))

  /** Upcasts a type into its supertypes that contains the method with some arity
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
      t: ReferenceType,
      methodName: String,
      arity: Int
  ): Set[ReferenceType] =
    // we can guarantee that the type is a concrete type
    getFixedDeclaration(t) match
      case None => Set(t) // missing type
      case Some(x) =>
        val current =
          if x.methods.contains(methodName) &&
            x.methods(methodName).exists(x => x.callableWithNArgs(arity))
          then Set(t)
          else Set()
        if x.identifier == "java.lang.Object" then current
        else
          val supertypes =
            (x.extendedTypes ++ x.implementedTypes)
              .map(_.addSubstitutionLists(t.substitutions))
          if supertypes.isEmpty then
            current ++ upcastOneToMethodContainer(
              OBJECT,
              methodName,
              arity
            )
          else current ++ upcastAllToMethodContainer(supertypes.toSet, methodName, arity)

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
        case x: ConjunctiveAssertion        => false
        case y: DisjunctiveAssertion        => false
        case IsClassAssertion(x)            => x.isInstanceOf[PlaceholderType]
        case IsInterfaceAssertion(x)        => x.isInstanceOf[PlaceholderType]
        case HasMethodAssertion(_, _, _, _) => false
        case IsDeclaredAssertion(x)         => false
        case IsMissingAssertion(x)          => false
        case IsUnknownAssertion(x)          => false
        case IsPrimitiveAssertion(x)        => false
        case IsReferenceAssertion(x)        => x.isInstanceOf[PlaceholderType]
        case IsIntegralAssertion(x)         => false
        case IsNumericAssertion(x)          => false
    omega.isEmpty || omega.forall(tester)

  override def toString =
    "Delta:\n" +
      delta.values.mkString("\n") +
      "\n\nPhi:\n" +
      phi1.values.mkString("\n") + "\n" + phi2.values.mkString("\n") +
      "\n\nOmega:\n" +
      omega.mkString("\n")

  /** Upcasts a type into the first ancestor that contains an attribute of attributeName
    * @param typet
    *   the type to upcast
    * @param attributeName
    *   the name of the attribute
    * @return
    *   an optional ancestor who contains said attribute
    */
  def upcastToAttributeContainer(typet: Type, attributeName: String): Option[Type] =
    getFixedDeclaration(typet.substituted) match
      case Some(x) =>
        if x.attributes.contains(attributeName) then Some(typet)
        else if typet.substituted == OBJECT.substituted then None
        else if x.isInterface || x.extendedTypes.isEmpty then
          upcastToAttributeContainer(OBJECT, attributeName)
        else
          upcastToAttributeContainer(
            x.extendedTypes(0).addSubstitutionLists(typet.substitutions),
            attributeName
          )
      // missing
      case None =>
        Some(typet)

  /** Gets the declaration of some type t
    * @param t
    *   the type whose declaration is to be obtained
    * @return
    *   the declaration of the type
    */
  def getDeclaration(t: ReferenceType): Declaration[?, ?] =
    getFixedDeclaration(t) match
      case Some(x) => x
      case None    => phi1(t.identifier)

  /** Obtains the declaration of some declared type
    * @param t
    *   the type whose declaration is to be obtained
    * @return
    *   an optional declaration; it returns None as long as the type is missing or unknown
    */
  def getFixedDeclaration(t: Type): Option[FixedDeclaration] = t.substituted match
    case x: PrimitiveType => getReflectionTypeDeclaration(x.boxed)
    case x: SubstitutedReferenceType =>
      if this |- x.isMissing then None
      else if delta.contains(x.identifier) then Some(delta(x.identifier))
      else getReflectionTypeDeclaration(x)
    case _ => None

  private def getReflectionTypeDeclaration(t: Type): Option[FixedDeclaration] =
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
  def upcast(t: Type, target: Type): Option[Type] = (t.substituted, target.substituted) match
    case (x, y): (SubstitutedReferenceType, SubstitutedReferenceType) =>
      if x.identifier == y.identifier then Some(t)
      else
        val (supertypes, isRaw) = getFixedDeclaration(t) match
          case Some(decl) =>
            (
              (decl.extendedTypes ++ decl.implementedTypes),
              decl.numParams > 0 && x.numArgs == 0
            )
          case None =>
            (phi1(x.identifier).supertypes, phi1(x.identifier).numParams > 0 && x.numArgs == 0)
        (if supertypes.isEmpty && x != OBJECT.substituted then Vector(OBJECT) else supertypes)
          .map(i =>
            if isRaw then NormalType(i.identifier, 0)
            else i.addSubstitutionLists(x.substitutions)
          )
          .foldLeft(None: Option[Type])((o, i) =>
            o match
              case None    => upcast(i, target)
              case Some(_) => o
          )
    case _ => None

  def addExclusionToAlpha(id: Int, exclusion: String) =
    val newPhi2 = MutableMap[Type, InferenceVariableMemberTable]()
    for (t, ivmt) <- phi2 do
      t match
        case x: Alpha if x.id == id =>
          val newTable = ivmt.addExclusion(exclusion)
          newPhi2(x) = newTable
        case _ =>
          newPhi2(t) = ivmt
    copy(phi2 = newPhi2.toMap)

  def excludes(id: Int, exclusion: String) =
    phi2
      .filter((x, y) => x.isInstanceOf[Alpha] && x.asInstanceOf[Alpha].id == id)
      .map((x, y) => y.exclusions.contains(exclusion))
      .foldLeft(false)(_ || _)

  def getAllKnownSupertypes(t: ReferenceType): Set[NormalType] =
    val allSupertypes = getDeclaration(t).getDirectAncestors.map(
      _.addSubstitutionLists(t.substitutions).asInstanceOf[NormalType]
    )
    allSupertypes.toSet.flatMap(x => getAllKnownSupertypes(x)) ++ allSupertypes + OBJECT

  def getDirectSupertypes(t: ReferenceType): Set[NormalType] =
    val allSupertypes = (getFixedDeclaration(t) match
      case Some(decl) => decl.getDirectAncestors
      case None =>
        phi1(t.identifier).supertypes
    ).map(_.addSubstitutionLists(t.substitutions).asInstanceOf[NormalType])
    if allSupertypes.isEmpty then Set(OBJECT) else allSupertypes.toSet

  def isFullyDeclared(t: ReferenceType): Boolean =
    getFixedDeclaration(t) match
      case None => false
      case Some(decl) =>
        val a = decl.getDirectAncestors
        a.forall(x => isFullyDeclared(NormalType(x.identifier, 0)))

  def getMissingDeclaration(t: ReferenceType): Option[MissingTypeDeclaration] =
    if phi1.contains(t.identifier) then Some(phi1(t.identifier))
    else None
