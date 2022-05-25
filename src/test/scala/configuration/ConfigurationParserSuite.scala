package configuration

import utils.*
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.Assertions.*

class ConfigurationParserSuite extends AnyFunSuite:
  val simpleConfig = AppConfig()
  val emptyLog     = Log(simpleConfig)

  test("Non-existent file should fail with LogWithNone") {
    val filePath = "./.tests/NonExistentFile.java"
    val result   = parseConfiguration(emptyLog, filePath)
    // check that parsing was unsuccessful
    assert(result.opt.isEmpty)
  }

  test("File with syntax errors should fail with a LogWithNone") {
    val filePath = "./tests/FileWithSyntaxErrors.java"
    val result   = parseConfiguration(emptyLog, filePath)
    assert(result.opt.isEmpty)
  }

  test("Simple non-generic class should be successfully parsed") {
    val filePath = "./.tests/SimpleNonGenericClass.java"
    val result   = parseConfiguration(emptyLog, filePath)
    // check that parsing was successful
    assert(result.opt.isDefined)
    val config = result.opt.get
    // check that the parsed class was A
    assert(config.delta.contains("A"))
    val cls = config.delta("A")
    // check the members of the class
    assert(cls.identifier == "A")
    assert(cls.typeParameters.isEmpty)
    assert(!cls.isFinal)
    assert(!cls.isAbstract)
    assert(!cls.isInterface)
    assert(cls.extendedTypes.isEmpty)
    assert(cls.implementedTypes.isEmpty)
    assert(cls.methodTypeParameterBounds.isEmpty)
    assert(cls.attributes.isEmpty)
    assert(cls.methods.isEmpty)

    // check that phi and omega are empty
    assert(config.phi1.isEmpty)
    assert(config.phi2.isEmpty)
    assert(config.omega.isEmpty)
  }

  test("Simple non-generic interface should be successfully parsed") {
    val filePath = "./.tests/SimpleNonGenericInterface.java"
    val result   = parseConfiguration(emptyLog, filePath)
    // check that parsing was successful
    assert(result.opt.isDefined)
    val config = result.opt.get
    // check that the parsed class was A
    assert(config.delta.contains("A"))
    val iface = config.delta("A")
    // check the members of the class
    assert(iface.identifier == "A")
    assert(iface.typeParameters.isEmpty)
    assert(!iface.isFinal)
    assert(iface.isAbstract)
    assert(iface.isInterface)
    assert(iface.extendedTypes.isEmpty)
    assert(iface.implementedTypes.isEmpty)
    assert(iface.methodTypeParameterBounds.isEmpty)
    assert(iface.attributes.isEmpty)
    assert(iface.methods.isEmpty)

    // check that phi and omega are empty
    assert(config.phi1.isEmpty)
    assert(config.phi2.isEmpty)
    assert(config.omega.isEmpty)
  }

  test("Final interface should fail with LogWithNone") {
    val filePath = "./tests/FinalInterface.java"
    val result   = parseConfiguration(emptyLog, filePath)
    assert(result.opt.isEmpty)
  }

  test("Abstract and final class should fail with LogWithNone") {
    val filePath = "./tests/AbstractFinalClass.java"
    val result   = parseConfiguration(emptyLog, filePath)
    assert(result.opt.isEmpty)
  }
