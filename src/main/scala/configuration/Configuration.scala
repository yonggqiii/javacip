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
import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*
case class Configuration(
    delta: Map[String, FixedDeclaration],
    phi1: Map[String, MissingTypeDeclaration],
    phi2: Map[Type, InferenceVariableMemberTable],
    omega: Queue[Assertion]
):
  def replace(oldType: ReplaceableType, newType: Type): Configuration =
    // do replacements in Phi1
    val (tempPhi1, assts) =
      phi1.foldLeft((Map[String, MissingTypeDeclaration](), List[Assertion]())) {
        case ((newPhi1, newAssts), (str, oldMtd)) =>
          val (newMtd, a) = oldMtd.replace(oldType, newType)
          (newPhi1 + (str -> newMtd), a ::: newAssts)
      }

    // do replacements in Phi2
    val (tempPhi2, moreassts) =
      phi2.foldLeft(Map[Type, InferenceVariableMemberTable](), List[Assertion]()) {
        case ((newPhi2, assts), (source, table)) =>
          val newSource          = source.replace(oldType, newType).upwardProjection
          val (ntable, newassts) = table.replace(oldType, newType)
          if !newPhi2.contains(newSource) then (newPhi2 + (newSource -> ntable), assts ::: newassts)
          else
            val otherTable               = newPhi2(newSource)
            val (mergedTable, moreAssts) = otherTable.merge(ntable)
            (newPhi2 + (newSource -> mergedTable), assts ::: newassts ::: moreAssts)
      }

    val (newPhi2, toCombineWithPhi1) = tempPhi2.partition((x, _) =>
      x match
        case _: InferenceVariable | _: Alpha => true
        case _                               => false
    )

    ???

  def isComplete: Boolean = omega.isEmpty
  override def toString =
    "Delta:\n" +
      delta.values.mkString("\n") +
      "\n\nPhi:\n" +
      phi1.values.mkString("\n") + phi2.values.mkString("\n") +
      "\n\nOmega:\n" +
      omega.mkString("\n")
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
        val typeParams     = typeParamDecls.map(x => TypeParameterName(x.getContainerId, x.getName))
        val returnType     = resolveSolvedType(mtd.getReturnType)
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
