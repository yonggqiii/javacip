package configuration

import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver
import com.github.javaparser.resolution.declarations.*
import configuration.declaration.FixedDeclaration
import configuration.declaration.MissingTypeDeclaration
import configuration.declaration.InferenceVariableMemberTable
import configuration.assertions.*
import configuration.types.*
import configuration.resolvers.*
import java.lang.reflect.Modifier
import utils.*
import scala.collection.immutable.Queue
import scala.collection.mutable.{ArrayBuffer, Map as MutableMap}
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
case class Configuration(
    delta: Map[String, FixedDeclaration],
    phi1: Map[String, MissingTypeDeclaration],
    phi2: Map[Type, InferenceVariableMemberTable],
    omega: Queue[Assertion]
):
  def replace(oldType: ReplaceableType, newType: Type): Option[Configuration] =
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

    for (t, ivmt) <- phi2 do
      val newSource            = t.replace(oldType, newType).upwardProjection
      val (newTable, newAssts) = ivmt.replace(oldType, newType)
      newAssertions ++= newAssts
      newSource.substituted match
        case x: SubstitutedReferenceType =>
          getFixedDeclaration(x) match
            case None =>
              val (newT, newA) = newPhi1(x.identifier).merge(newTable, x)
              newAssertions ++= newA
              newPhi1(x.identifier) = newT
            case Some(decl) =>
              if newTable.mustBeClass || !newTable.attributes.isEmpty then
                newAssertions += IsClassAssertion(x)
              if newTable.mustBeInterface then newAssertions += IsInterfaceAssertion(x)
              for (attrName, attrType) <- newTable.attributes do
                val attrContainer = upcastToAttributeContainer(x, attrName)
                attrContainer match
                  // there is no way the attribute can exist
                  case None => return None
                  // attribute belongs to some known type
                  case Some(ac) =>
                    getFixedDeclaration(ac) match
                      // attribute belongs to some declared type
                      case Some(table) =>
                        newAssertions += EquivalenceAssertion(
                          attrType,
                          table.attributes(attrName).addSubstitutionLists(ac.substitutions)
                        )
                      // attribute can be added to a missing type
                      case None =>
                        val mttable = newPhi1(ac.identifier)
                        if mttable.attributes.contains(attrName) then
                          newAssertions += EquivalenceAssertion(
                            attrType,
                            mttable.attributes(attrName).addSubstitutionLists(ac.substitutions)
                          )
                        else
                          val createdAttrType = InferenceVariableFactory.createInferenceVariable(
                            Left(ac.identifier),
                            Nil,
                            true,
                            (0 until mttable.numParams)
                              .map(i => TypeParameterIndex(ac.identifier, i))
                              .toSet,
                            false
                          )
                          newPhi1(ac.identifier) = mttable.addAttribute(attrName, createdAttrType)
                          newAssertions += EquivalenceAssertion(
                            attrType,
                            createdAttrType.addSubstitutionLists(newType.substitutions)
                          )
              for (methodName, methodTable) <- newTable.methods do
                // each row in the methodTable is actually a method definition
                for ((paramTypes, context), returnType) <- methodTable do
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
                    val relevantMethods =
                      decl.methods(methodName).toVector.filter(x => x._1._2.size == paramTypes.size)
                    for ((realTypeParams, realParamTypes), realRt) <- relevantMethods do
                      // create the assertions
                      val tpMap: Map[TTypeParameter, Type] =
                        realTypeParams
                          .map(x => x -> InferenceVariableFactory.createAnyReplaceable())
                          .toMap
                      val conjAssertion = ArrayBuffer[Assertion]()
                      val subs          = (tpMap :: t.substitutions).filter(!_.isEmpty)
                      for i <- (0 until paramTypes.size) do
                        conjAssertion += SubtypeAssertion(
                          paramTypes(i),
                          realParamTypes(i).addSubstitutionLists(subs)
                        )
                      conjAssertion += EquivalenceAssertion(
                        returnType,
                        realRt.addSubstitutionLists(subs)
                      )
                      disjassts += ConjunctiveAssertion(conjAssertion.toVector)
                  for t <- missingMethodContainers do
                    disjassts += HasMethodAssertion(t, methodName, paramTypes, returnType)
                  newAssertions += DisjunctiveAssertion(disjassts.toVector)
        case x: TTypeParameter =>
          val sourceType = x match
            case y: TypeParameterIndex => y.source
            case y: TypeParameterName  => y.sourceType
          val (erasure, allBounds) = getFixedDeclaration(NormalType(sourceType, 0, Nil)) match
            case None =>
              (NormalType("java.lang.Object", 0, Nil), Set(NormalType("java.lang.Object", 0)))
            case Some(d) =>
              (d.getErasure(x), d.getAllBounds(x))
          if newTable.mustBeClass || !newTable.attributes.isEmpty then
            newAssertions += IsClassAssertion(erasure)
          if newTable.mustBeInterface then newAssertions += IsInterfaceAssertion(erasure)
          for (attrName, attrType) <- newTable.attributes do
            val attrContainer = upcastToAttributeContainer(erasure, attrName)
            attrContainer match
              // there is no way the attribute can exist
              case None => return None
              // attribute belongs to some known type
              case Some(ac) =>
                getFixedDeclaration(ac) match
                  // attribute belongs to some declared type
                  case Some(table) =>
                    newAssertions += EquivalenceAssertion(
                      attrType,
                      table.attributes(attrName).addSubstitutionLists(ac.substitutions)
                    )
                  // attribute can be added to a missing type
                  case None =>
                    val mttable = newPhi1(ac.identifier)
                    if mttable.attributes.contains(attrName) then
                      newAssertions += EquivalenceAssertion(
                        attrType,
                        mttable.attributes(attrName).addSubstitutionLists(ac.substitutions)
                      )
                    else
                      val createdAttrType = InferenceVariableFactory.createInferenceVariable(
                        Left(ac.identifier),
                        Nil,
                        true,
                        (0 until mttable.numParams)
                          .map(i => TypeParameterIndex(ac.identifier, i))
                          .toSet,
                        false
                      )
                      newPhi1(ac.identifier) = mttable.addAttribute(attrName, createdAttrType)
                      newAssertions += EquivalenceAssertion(
                        attrType,
                        createdAttrType.addSubstitutionLists(newType.substitutions)
                      )
          for (methodName, methodTable) <- newTable.methods do
            // each row in the methodTable is actually a method definition
            for ((paramTypes, context), returnType) <- methodTable do
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
                val relevantMethods =
                  decl.methods(methodName).toVector.filter(x => x._1._2.size == paramTypes.size)
                for ((realTypeParams, realParamTypes), realRt) <- relevantMethods do
                  // create the assertions
                  val tpMap: Map[TTypeParameter, Type] =
                    realTypeParams
                      .map(x => x -> InferenceVariableFactory.createAnyReplaceable())
                      .toMap
                  val conjAssertion = ArrayBuffer[Assertion]()
                  val subs          = (tpMap :: t.substitutions).filter(!_.isEmpty)
                  for i <- (0 until paramTypes.size) do
                    conjAssertion += SubtypeAssertion(
                      paramTypes(i),
                      realParamTypes(i).addSubstitutionLists(subs)
                    )
                  conjAssertion += EquivalenceAssertion(
                    returnType,
                    realRt.addSubstitutionLists(subs)
                  )
                  disjassts += ConjunctiveAssertion(conjAssertion.toVector)
              for t <- missingMethodContainers do
                disjassts += HasMethodAssertion(t, methodName, paramTypes, returnType)
              newAssertions += DisjunctiveAssertion(disjassts.toVector)
        case x: ReplaceableType =>
          if !newPhi2.contains(x) then newPhi2(newSource) = newTable
          else
            val (nt, na) = newPhi2(x).merge(newTable)
            newPhi2(x) = nt
            newAssertions ++= na
        case _ => ???

    Some(Configuration(delta, newPhi1.toMap, newPhi2.toMap, omega.enqueueAll(newAssertions)))

  def upcastAllToMethodContainer(bounds: Set[Type], methodName: String, arity: Int): Set[Type] =
    bounds.flatMap(upcastOneToMethodContainer(_, methodName, arity))

  def upcastOneToMethodContainer(t: Type, methodName: String, arity: Int): Set[Type] =
    // we can guarantee that the type is a concrete type
    getFixedDeclaration(t) match
      case None => Set(t) // missing type
      case Some(x) =>
        val current =
          if x.methods.contains(methodName) &&
            x.methods(methodName).exists(x => x._1._2.size == arity)
          then Set(t)
          else Set()
        if x.identifier == "java.lang.Object" then current
        else
          val supertypes =
            (x.extendedTypes ++ x.implementedTypes)
              .map(_.addSubstitutionLists(t.substitutions))
          if supertypes.isEmpty then
            current ++ upcastOneToMethodContainer(
              NormalType("java.lang.Object", 0),
              methodName,
              arity
            )
          else current ++ upcastAllToMethodContainer(supertypes.toSet, methodName, arity)
  def isComplete: Boolean = omega.isEmpty
  override def toString =
    "Delta:\n" +
      delta.values.mkString("\n") +
      "\n\nPhi:\n" +
      phi1.values.mkString("\n") + phi2.values.mkString("\n") +
      "\n\nOmega:\n" +
      omega.mkString("\n")

  def upcastToAttributeContainer(typet: Type, attributeName: String): Option[Type] =
    getFixedDeclaration(typet) match
      case Some(x) =>
        if x.attributes.contains(attributeName) then Some(typet)
        else if typet.identifier == "java.lang.Object" then None
        else if x.isInterface || x.extendedTypes.isEmpty then
          upcastToAttributeContainer(NormalType("java.lang.Object", 0, Nil), attributeName)
        else
          upcastToAttributeContainer(
            x.extendedTypes(0).addSubstitutionLists(typet.substitutions),
            attributeName
          )
      // missing
      case None =>
        Some(typet)

  def getFixedDeclaration(t: Type): Option[FixedDeclaration] =
    val boxMap = Map(
      "int"     -> NormalType("java.lang.Integer", 0, Nil),
      "char"    -> NormalType("java.lang.Character", 0, Nil),
      "short"   -> NormalType("java.lang.Short", 0, Nil),
      "byte"    -> NormalType("java.lang.Byte", 0, Nil),
      "long"    -> NormalType("java.lang.Long", 0, Nil),
      "float"   -> NormalType("java.lang.Float", 0, Nil),
      "double"  -> NormalType("java.lang.Double", 0, Nil),
      "boolean" -> NormalType("java.lang.Boolean", 0, Nil)
    )
    if delta.contains(t.identifier) then Some(delta(t.identifier))
    else if boxMap.contains(t.identifier) then getReflectionTypeDeclaration(boxMap(t.identifier))
    else getReflectionTypeDeclaration(t)
  private def getReflectionTypeDeclaration(t: Type): Option[FixedDeclaration] =
    val rts         = ReflectionTypeSolver()
    val declAttempt = rts.tryToSolveType(t.identifier)
    if !declAttempt.isSolved then None
    else
      val rtd         = declAttempt.getCorrespondingDeclaration.asReferenceType
      val identifier  = rtd.getQualifiedName
      val isInterface = rtd.isInterface
      val tp = rtd.getTypeParameters.asScala.toVector
        .map(
          _.getBounds.asScala.toVector
            .map(x => resolveSolvedType(x.getType))
        )
      val attributes =
        rtd.getVisibleFields.asScala.map(x => (x.getName -> resolveSolvedType(x.getType))).toMap
      val methodDeclarations = rtd.getDeclaredMethods.asScala
      val (methods, methodTypeParameterBounds) = methodDeclarations.foldLeft(
        Map[String, Map[(Vector[TypeParameterName], Vector[Type]), Type]]().withDefaultValue(Map()),
        Map[String, Vector[Type]]()
      ) { case ((mtds, mtpbds), mtd) =>
        val name           = mtd.getName
        val typeParamDecls = mtd.getTypeParameters.asScala.toVector
        val typeParams =
          typeParamDecls.map(x => TypeParameterName(identifier, x.getContainerId, x.getName))
        val returnType = resolveSolvedType(mtd.getReturnType)
        val typeParamBounds = typeParams
          .zip(
            typeParamDecls.map(tp =>
              tp.getBounds.asScala.toVector.map(x => resolveSolvedType(x.getType))
            )
          )
          .map(x => (x._1.identifier -> x._2))
        val numParams = mtd.getNumberOfParams
        val args = (0 until numParams).map(x => resolveSolvedType(mtd.getParam(x).getType)).toVector
        (
          mtds.+[Map[(Vector[TypeParameterName], Vector[Type]), Type]](
            name -> (mtds(name) + ((typeParams, args) -> returnType))
          ),
          mtpbds ++ typeParamBounds
        )
      }
      val c: java.lang.Class[?] = java.lang.Class.forName(identifier)
      val modifiers             = c.getModifiers
      val isAbstract            = Modifier.isAbstract(modifiers)
      val isFinal               = Modifier.isFinal(modifiers)
      val (extendedTypes, implementedTypes) =
        if isInterface then
          val ifaced = rtd.asInstanceOf[ResolvedInterfaceDeclaration]
          (ifaced.getInterfacesExtended.asScala.map(x => resolveSolvedType(x)).toVector, Vector())
        else
          val clsd = rtd.asInstanceOf[ResolvedClassDeclaration]
          (
            clsd.getSuperClass.toScala.map(x => Vector(resolveSolvedType(x))).getOrElse(Vector()),
            clsd.getInterfaces.asScala.map(x => resolveSolvedType(x)).toVector
          )

      Some(
        FixedDeclaration(
          identifier,
          tp,
          isFinal,
          isAbstract,
          isInterface,
          extendedTypes,
          implementedTypes,
          methodTypeParameterBounds,
          attributes,
          methods
        )
      )
