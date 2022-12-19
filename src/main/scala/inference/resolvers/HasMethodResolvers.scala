package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*
import configuration.declaration.PUBLIC

private[inference] def resolveHasMethodAssertion(
    log: Log,
    config: Configuration,
    asst: HasMethodAssertion
): (Log, List[Configuration]) =
  val HasMethodAssertion(t, methodName, args, returnType, paramChoices) = asst
  val decl         = config.getMissingDeclaration(t)
  val (_, context) = t.expansion
  decl match
    case None =>
      (log.addError(s"has method assertion on declared type $t but there is no $methodName"), Nil)
    case Some(d) =>
      if d.methods.contains(methodName) then
        val originalMethod = d.methods(methodName).find(m => m.signature.formalParameters == args)
        originalMethod match
          case Some(x) => (log, (config asserts (returnType ~=~ x.returnType)) :: Nil)
          case None =>
            val newDecl =
              d.addMethod(
                methodName,
                args,
                returnType,
                Map(),
                PUBLIC,
                false,
                false,
                false,
                paramChoices,
                context
              )
            (
              log.addInfo(s"added method $methodName to $t"),
              config.copy(phi1 = config.phi1 + (t.identifier -> newDecl)) :: Nil
            )
      else
        val newDecl =
          d.addMethod(
            methodName,
            args,
            returnType,
            Map(),
            PUBLIC,
            false,
            false,
            false,
            paramChoices,
            context
          )
        (
          log.addInfo(s"added method $methodName to $t"),
          config.copy(phi1 = config.phi1 + (t.identifier -> newDecl)) :: Nil
        )
