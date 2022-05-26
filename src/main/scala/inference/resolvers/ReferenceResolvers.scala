package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*

def resolveIsReferenceAssertion(log: Log, config: Configuration, a: IsReferenceAssertion) =
  val IsReferenceAssertion(t) = a
  t.substituted match
    case m: InferenceVariable =>
      val originalConfig = config asserts a
      m.source match
        case Left(_) =>
          val choices = m._choices
          (
            log.addInfo(s"expanding ${m.identifier} into its choices"),
            choices
              .map(originalConfig.replace(m.copy(substitutions = Nil), _))
              .filter(!_.isEmpty)
              .map(_.get)
              .toList
          )
        case Right(_) =>
          (
            log.addInfo(
              s"returning $a back to config as insufficient information is available"
            ),
            originalConfig :: Nil
          )
    case _: PrimitiveType => (log.addWarn(s"$t is not a reference type"), Nil)
