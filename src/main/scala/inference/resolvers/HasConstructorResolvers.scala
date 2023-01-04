package inference.resolvers

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*
import configuration.declaration.PUBLIC

private[inference] def resolveHasConstructorAssertion(
    log: Log,
    config: Configuration,
    asst: HasConstructorAssertion
): (Log, List[Configuration]) =
  val HasConstructorAssertion(t, args, paramChoices) = asst
  val decl                                           = config.getMissingDeclaration(t)
  val (_, context)                                   = t.expansion
  decl match
    case None =>
      (
        log.addError(
          s"has constructor assertion on declared type $t but has no constructor of $args"
        ),
        Nil
      )
    case Some(d) =>
      val newDecl = d.addConstructor(args, Vector(), PUBLIC, paramChoices, context)
      (
        log.addInfo(s"added method new constructor or args $args to $t"),
        config.copy(phi1 = config.phi1 + (t.identifier -> newDecl)) :: Nil
      )
