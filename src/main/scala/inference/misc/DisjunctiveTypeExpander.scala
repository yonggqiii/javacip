package inference.misc

import configuration.Configuration
import configuration.types.*
import configuration.assertions.*
import utils.*

private[inference] def expandDisjunctiveTypeGreedily(
    i: DisjunctiveType,
    target: Type,
    log: Log,
    config: Configuration
) =
  val greedyChoices = target match
    case x: Alpha =>
      i.choices.filter(x => !x.isInstanceOf[PrimitiveType])
    case x: ArrayType =>
      if i.isInstanceOf[PrimitivesOnlyDisjunctiveType] || i
          .isInstanceOf[BoxesOnlyDisjunctiveType]
      then Vector[Type]()
      else
        Vector(
          ArrayType(
            InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
              scala.util.Left(""),
              Nil,
              i.canBeSubsequentlyBounded,
              i.parameterChoices,
              i.canBeUnknown
            )
          )
        )
    case x: SomeClassOrInterfaceType =>
      i.choices.filter(x => !x.isInstanceOf[PrimitiveType])
    case x: PrimitiveType =>
      if i.isInstanceOf[VoidableDisjunctiveType] || i
          .isInstanceOf[DisjunctiveTypeWithPrimitives] || i
          .isInstanceOf[PrimitivesOnlyDisjunctiveType]
      then Vector(x)
      else Vector()
    case Bottom            => Vector()
    case x: TTypeParameter => i.choices.filter(x => x.isInstanceOf[TTypeParameter])
    case _                 => Vector()
  val allChoices =
    if i.isInstanceOf[PrimitivesOnlyDisjunctiveType] || i
        .isInstanceOf[BoxesOnlyDisjunctiveType]
    then i.choices
    else
      i.choices :+ ArrayType(
        InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
          scala.util.Left(""),
          Nil,
          i.canBeSubsequentlyBounded,
          i.parameterChoices,
          i.canBeUnknown
        )
      )
  val remainingChoices = allChoices.filter(x => !greedyChoices.contains(x))
  val choices          = greedyChoices ++ remainingChoices
  (
    log.addInfo(s"expanding ${i.identifier} into its choices", choices.toString),
    choices
      .map(config.replace(i, _))
      .filter(!_.isEmpty)
      .map(_.get)
      .toList
  )

private[inference] def expandDisjunctiveType(
    i: DisjunctiveType,
    log: Log,
    config: Configuration
) =
  val choices =
    if i.isInstanceOf[PrimitivesOnlyDisjunctiveType] || i
        .isInstanceOf[BoxesOnlyDisjunctiveType]
    then i.choices
    else
      i.choices :+ ArrayType(
        InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
          scala.util.Left(""),
          Nil,
          i.canBeSubsequentlyBounded,
          i.parameterChoices,
          i.canBeUnknown
        )
      )
  //println("aasdf")
  //choices.map(config.replace(i, _)).filter(_.isDefined).map(_.get).foreach(println)
  (
    log.addInfo(s"expanding ${i.identifier} into its choices", choices.toString),
    choices
      .map(config.replace(i, _))
      .filter(!_.isEmpty)
      .map(_.get)
      .toList
  )

private[inference] def expandDisjunctiveTypeToPrimitive(
    i: DisjunctiveType,
    log: Log,
    config: Configuration
) =
  val choices = i.choices.filter(x =>
    x.isInstanceOf[PrimitiveType] || x.isInstanceOf[PrimitivesOnlyDisjunctiveType] || x
      .isInstanceOf[DisjunctiveTypeWithPrimitives] || x.isInstanceOf[VoidableDisjunctiveType]
  )
  (
    log.addInfo(s"expanding ${i.identifier} into its primtive choices", choices.toString),
    choices
      .map(config.replace(i, _))
      .filter(!_.isEmpty)
      .map(_.get)
      .toList
  )

private[inference] def expandDisjunctiveTypeToReference(
    i: DisjunctiveType,
    log: Log,
    config: Configuration
) =
  val choices =
    if i.isInstanceOf[PrimitivesOnlyDisjunctiveType] || i
        .isInstanceOf[BoxesOnlyDisjunctiveType]
    then
      i.choices.filter(x =>
        !x.isInstanceOf[PrimitiveType] && !x.isInstanceOf[PrimitivesOnlyDisjunctiveType]
      )
    else
      i.choices.filter(x =>
        !x.isInstanceOf[PrimitiveType] && !x.isInstanceOf[PrimitivesOnlyDisjunctiveType]
      ) :+ ArrayType(
        InferenceVariableFactory.createDisjunctiveTypeWithPrimitives(
          scala.util.Left(""),
          Nil,
          i.canBeSubsequentlyBounded,
          i.parameterChoices,
          i.canBeUnknown
        )
      )
  (
    log.addInfo(s"expanding ${i.identifier} into its reference choices", choices.toString),
    choices
      .map(config.replace(i, _))
      .filter(!_.isEmpty)
      .map(_.get)
      .toList
  )
// i.source match
//   case Left(_) =>
//     val choices = i.choices
//     (
//       log.addInfo(s"expanding ${i.identifier} into its choices", choices.toString),
//       choices
//         .map(config.replace(i, _))
//         .filter(!_.isEmpty)
//         .map(_.get)
//         .toList
//     )
//   case Right(x) =>
//     (log.addError(s"$x\n$i"), Nil)

private[inference] def returnToConfig(a: Assertion, log: Log, config: Configuration) =
  (
    log.addInfo(
      s"returning $a to omega since it involves some replaceable type whose source is not known"
    ),
    (config asserts a) :: Nil
  )
