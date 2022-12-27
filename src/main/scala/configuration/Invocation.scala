package configuration
import configuration.types.*

case class Invocation(
    source: Type,
    methodName: String,
    formalParameters: Vector[Type],
    returnType: Type,
    parameterChoices: Set[TTypeParameter]
):
  override def toString(): String =
    s"$source can be called with $returnType $methodName(${formalParameters.mkString(", ")}) whose inference variables could contain $parameterChoices"
  def replace(oldType: InferenceVariable, newType: Type): Invocation =
    copy(
      source = source.replace(oldType, newType),
      formalParameters = formalParameters.map(_.replace(oldType, newType)),
      returnType = returnType.replace(oldType, newType)
    )
  def combineTemporaryType(oldType: TemporaryType, newType: SomeClassOrInterfaceType): Invocation =
    copy(
      source = source.combineTemporaryType(oldType, newType),
      formalParameters = formalParameters.map(_.combineTemporaryType(oldType, newType)),
      returnType = returnType.combineTemporaryType(oldType, newType)
    )
