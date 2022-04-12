package configuration

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.visitor.VoidVisitorAdapter

import scala.jdk.CollectionConverters.*
import scala.jdk.OptionConverters.*

import configuration.declaration.MissingDeclaration

class PhiOmegaBuilderVisitor
    extends VoidVisitorAdapter[(MutablePhi, MutableOmega)]:
  override def visit(
      c: ClassOrInterfaceDeclaration,
      arg: (MutablePhi, MutableOmega)
  ) =
    super.visit(c, arg)
    // Obtain identifier of the class
    val identifier = c.getName.getIdentifier
    // Obtain the type parameters of the class/interface
    val typeParameters = c.getTypeParameters.asScala.toVector
    val typeParameterNames =
      typeParameters.map(_.getName.getIdentifier).toVector
// TODO: handle ArrayAccessExpr ==> make sure that some type is an array
// TODO: handle ArrayCreationExpr ==> just make sure that type exists
// TODO: handle ArrayCreationLevel ==> just make sure that depth of array is correct and type exists
// TODO: handle ArrayInitializerExpr ==> make sure that 1) type exists, and elements of array are subtypes of that type
// TODO: handle ArrayType? ==> make sure that type exists
// TODO: handle AssignExpr ==> make sure that right <: left
// TODO: handle BinaryExpr ==> make sure that both are numbers, or one is a String
// TODO: handle CastExpr ==> make sure that casted type is castable to target type and that type exists
// TODO: handle CatchClause ==> make sure that type exists and is an Exception
// TODO: handle ClassOrInterfaceType ==> make sure that type exists
// TODO: handle ConditionalExpr ==> make sure that type is a boolean
// TODO: handle ConstructorDeclaration ==> not sure
// TODO: handle DoStmt ==> make sure that expression in while is a boolean
// TODO: handle ExplicitConstructorInvocationStmt ==> make sure supertype has the constructor
// TODO: handle FieldAccessExpr ==> make sure type has field
// TODO: handle FieldDeclaration ==> make sure right <: left
// TODO: handle ForeachStmt ==> Make sure right hand side is an iterable over left hand side
// TODO: handle IfStmt ==> make sure expression is a boolean
// TODO: handle InstanceOfExpr ==> make sure type exists
// TODO: handle IntersectionType ==> make sure types exist
// TODO: handle LambdaExpr ==> not sure how to do this yet
// TODO: handle MethodCallExpr ==> make sure type has method
// TODO: handle NameExpr ==> make sure name exists and has a type
// TODO: handle ObjectCreationExpr ==> make sure type exists and is a class
// TODO: handle Parameter ==> make sure type exists
// TODO: handle SwitchStmt ==> make sure type is integral
// TODO: handle SwitchEntryStmt ==> make sure type is integral
// TODO: handle ThrowStmt ==> make sure type exists and is an exception
// TODO: handle TryStmt ==> make sure that one of the methods throws an exception that aims to be caught
// TODO: handle TypeExpr ==> make sure type exists
// TODO: handle TypeParameter ==> not sure
// TODO: handle UnaryExpr ==> make sure it makes sense
// TODO: handle UnionType ==> make sure types exist
// TODO: handle VariableDeclarationExpr ==> make sure right <: left
// TODO: handle VariableDeclarator ==> make sure right <: left
// TODO: handle WhileStmt ==> make sure expression is a boolean
// TODO: handle WildcardType ==> make sure type exists
// val actualTypeParameters = for t <- typeParameters yield
//   val bounds = t.getTypeBound.asScala.toVector
//   bounds.map(convertASTTypeToType(identifier, typeParameterNames, _))
    ???
