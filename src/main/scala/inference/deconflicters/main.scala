package inference.deconflicters

import scala.collection.mutable.ArrayBuffer

import configuration.Configuration
import configuration.assertions.*
import configuration.types.*
import utils.*

private[inference] def deconflict(
    log: Log,
    config: Configuration
): LogWithEither[List[Configuration], Configuration] =
  val newAssertions: ArrayBuffer[Assertion] = ArrayBuffer()
  val allBaseTypes = (config.delta.keys ++ config.phi1.keys).toSet.map(i =>
    val arity = config.getUnderlyingDeclaration(SomeClassOrInterfaceType(i)).numParams
    SomeClassOrInterfaceType(i, (0 until arity).map(n => TypeParameterIndex(i, n)).toVector)
  )
  // detect cyclic inheritance
  for x <- allBaseTypes do
    val others = allBaseTypes - x
    for y <- others do
      if (config |- x.raw <:~ y.raw) && (config |- y.raw <:~ x.raw) then
        return LogWithLeft(log.addWarn(s"cyclic inheritance: $x, $y"), Nil)
  // equality of duplicate supertypes
  for x <- allBaseTypes do
    val supertypes = config.getAllKnownSupertypes(x)
    for y <- supertypes do
      val otherSupertypes = supertypes - y
      // equality of duplicate supertypes
      for z <- otherSupertypes do
        if y.identifier == z.identifier then if config !|- (y ~=~ z) then newAssertions += (y ~=~ z)
      if (config |- x.isInterface) && !(config |- y.isInterface) && y != OBJECT then
        newAssertions += y.isInterface
    if supertypes.exists(x => (config |- x.isClass) && x != OBJECT) && !(config |- x.isClass) then
      newAssertions += x.isClass
  // stop here
  if !newAssertions.isEmpty then
    return LogWithLeft(
      log.addInfo("adding new assertions after deconflicting..."),
      (config assertsAllOf newAssertions) :: Nil
    )
  // pointless extensions
  var resConfig = config
  for i <- resConfig.phi1.keys do
    val supertypes = resConfig.phi1(i).supertypes.toSet
    for candidate <- supertypes do
      val decl = resConfig.phi1(i)
      val res  = supertypes - candidate
      if res.exists(t => config |- t <:~ SomeClassOrInterfaceType(candidate.identifier)) then
        val newDecl = decl.removeSupertype(SomeClassOrInterfaceType(candidate.identifier))
        resConfig = resConfig.copy(phi1 =
          resConfig.phi1 + (decl.identifier -> decl.removeSupertype(
            SomeClassOrInterfaceType(candidate.identifier)
          ))
        )
  // double class extensions
  for decl <- resConfig.phi1.values do
    if decl.mustBeClass then
      val supertypeClasses = decl.supertypes.filter(x => resConfig |- x.isClass)
      if supertypeClasses.size > 1 then
        val a = supertypeClasses(0)
        val b = supertypeClasses(1)
        return LogWithLeft(
          log.addInfo(s"either $a <: $b or $b <: $a"),
          (resConfig asserts ((a <:~ b) || (b <:~ a))) :: Nil
        )
  LogWithRight(log.addSuccess("deconflict complete!"), resConfig)
