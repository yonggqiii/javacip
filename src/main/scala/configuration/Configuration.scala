package configuration

import configuration.declaration.FixedDeclaration
import configuration.declaration.MissingTypeDeclaration
import configuration.declaration.InferenceVariableMemberTable
import configuration.assertions.*
import configuration.types.*
import utils.*

case class Configuration(
    delta: Map[String, FixedDeclaration],
    phi1: Map[String, MissingTypeDeclaration],
    phi2: Map[Type, InferenceVariableMemberTable],
    omega: List[Assertion]
)
