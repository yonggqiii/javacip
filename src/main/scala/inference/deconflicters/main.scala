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
  val allRawTypes = (config.delta.keys ++ config.phi1.keys).toSet.map(i => ClassOrInterfaceType(i))
  // detect cyclic inheritance
  for x <- allRawTypes do
    val others = allRawTypes - x
    for y <- others do
      if (config |- x <:~ y) && (config |- y <:~ x) then
        return LogWithLeft(log.addWarn(s"cyclic inheritance: $x, $y"), Nil)
  // equality of duplicate supertypes
  for x <- allRawTypes do
    val supertypes = config.getAllKnownSupertypes(x)
    for y <- supertypes do
      val otherSupertypes = supertypes - y
      // equality of duplicate supertypes
      for z <- otherSupertypes do
        if y.identifier == z.identifier then
          if !(y.isSomehowUnknown && z.isSomehowUnknown) then
            if (y.isSomehowUnknown || z.isSomehowUnknown) then newAssertions += (y ~=~ z)
            else if y != z then
              return LogWithLeft(
                log.addWarn(s"$x <: $y and $z!"),
                Nil
              )
      // class/interface assertions
      if (config |- x.isInterface) && !(config |- y.isInterface) && y != OBJECT then
        newAssertions += y.isInterface
    if supertypes.exists(x => (config |- x.isClass) && x != OBJECT) && !(config |- x.isClass) then
      newAssertions += x.isClass
  // stop here
  if !newAssertions.isEmpty then
    return LogWithLeft(
      log,
      (config assertsAllOf newAssertions) :: Nil
    )
  // pointless extensions
  var resConfig = config
  for i <- resConfig.phi1.keys do
    val supertypes = resConfig.phi1(i).supertypes.toSet
    for candidate <- supertypes do
      val decl = resConfig.phi1(i)
      val res  = supertypes - candidate
      if res.exists(t => config |- t <:~ ClassOrInterfaceType(candidate.identifier)) then
        val newDecl = decl.removeSupertype(ClassOrInterfaceType(candidate.identifier))
        // println(newDecl)
        resConfig = resConfig.copy(phi1 =
          resConfig.phi1 + (decl.identifier -> decl.removeSupertype(
            ClassOrInterfaceType(candidate.identifier)
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
  LogWithRight(log.addWarn("deconflict might not be fully implemented!"), resConfig)
