package configuration

import configuration.declaration.FixedDeclaration
import configuration.declaration.MissingTypeDeclaration
import configuration.declaration.InferenceVariableMemberTable
import configuration.assertions.*
import configuration.types.*
import utils.*
import scala.collection.immutable.Queue

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
  override def toString =
    "Delta:\n" +
      delta.values.mkString("\n") +
      "\n\nPhi:\n" +
      phi1.values.mkString("\n") + phi2.values.mkString("\n") +
      "\n\nOmega:\n" +
      omega.mkString("\n")
