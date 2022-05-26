package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import inference.misc.expandInferenceVariable
import utils.*

def resolveIsReferenceAssertion(log: Log, config: Configuration, a: IsReferenceAssertion) =
  val IsReferenceAssertion(t) = a
  t.substituted match
    case m: InferenceVariable =>
      expandInferenceVariable(m, log, config asserts a)
    case _: PrimitiveType => (log.addWarn(s"$t is not a reference type"), Nil)
