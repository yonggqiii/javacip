package configuration.declaration

import configuration.types.Type

case class MethodSignature(parameters: Vector[Type], returnType: Type)
