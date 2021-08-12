from antlr4 import FileStream, CommonTokenStream
from typing import List
if __name__ is not None and '.' in __name__:
    from .Java9Lexer import Java9Lexer
    from .Java9Parser import Java9Parser
    from .JavaTypes import TypeParameter, CompileTimeType, \
        JavaClassDeclaration,Capture, TypeVariable, JavaInterfaceDeclaration, \
        Attribute, MethodHeader, FormalParameter, MissingJavaClassDeclaration, \
        JDKTypeLoader
    from .Constraints import SubtypeConstraint, DisjunctiveConstraint, InferenceVariable
else:
    from Java9Lexer import Java9Lexer
    from Java9Parser import Java9Parser
    from JavaTypes import TypeParameter, CompileTimeType, \
        JavaClassDeclaration,Capture, TypeVariable, JavaInterfaceDeclaration, \
        Attribute, MethodHeader, FormalParameter, MissingJavaClassDeclaration, \
        JDKTypeLoader
    from Constraints import SubtypeConstraint, DisjunctiveConstraint, InferenceVariable

class TypeBuilder:
    def __init__(self, paths_to_files: List[str]):
        self._compilation_units = []
        # Read files
        for path in paths_to_files:
            input_stream = FileStream(path)
            lexer = Java9Lexer(input_stream)
            stream = CommonTokenStream(lexer)
            parser = Java9Parser(stream)
            tree = parser.compilationUnit()
            self._compilation_units.append(tree)

    def build(self):
        # Set up
        self._declared_types = {}
        self._missing_types = {}
        self._worklist = []
        self._referenced_types = []
        self._contexts = []
        ordinary_compilations = list(map(lambda x: x.ordinaryCompilation(),
            self._compilation_units))
        
        # Walk through entire tree and gather as much information as possible.
        self.handle_ordinary_compilations(ordinary_compilations)

        # Obtain jdk types
        jdk_types = JDKTypeLoader().load()
        for k, v in jdk_types.items():
            if k not in self._declared_types:
                self._declared_types[k] = v
            
        # Settle missing types
        for rtl in self._referenced_types:
            for t in rtl:
                if t._identifier not in self._declared_types and \
                        t._identifier not in self._missing_types:
                    # get number of type params
                    params = [chr((19 + x) % 26 + 65) for x in
                            range(len(t._captures))]
                    self._missing_types[t._identifier] = MissingJavaClassDeclaration(t._identifier, params if params else None)
        '''
        # Get all constraints from contexts
        for ctx in self._contexts:
            if len(ctx) == 2: # attribute decl
                self.get_assertions_from_attribute_declarator(*ctx)
            else:
                self.get_assertions_from_method_declarator(*ctx)
        #TODO: Handle bounded type arguments from referenced types
        '''

        '''
        == TESTING STUB ==

        ==================
        '''
        self.stub_one(self._declared_types, self._missing_types, self._worklist)
        a, b, c = self._declared_types, self._missing_types, self._worklist
        self._declared_types = self._missing_types = self._worklist = self._contexts = None
        return a, b, c


    def stub_one(self, dt, mt, worklist):
        # b.d
        mt['B'].add_attribute(Attribute('d', InferenceVariable(), False, False))
        # Number n = b.d;
        worklist.append(SubtypeConstraint(CompileTimeType('B').get_attribute_type('d',
            dt, mt),
            CompileTimeType('Number')))
        # b.d = 2;
        worklist.append(SubtypeConstraint(CompileTimeType('int'),
            CompileTimeType('B').get_attribute_type('d', dt, mt)))
        # int i = b.d;
        worklist.append(SubtypeConstraint(CompileTimeType('B').get_attribute_type('d',
            dt, mt), CompileTimeType('int')))
        # Object o = b;
        worklist.append(SubtypeConstraint(CompileTimeType('B'),
                CompileTimeType('Object')))

        # A<ArrayList<Integer>, Integer>
        worklist.append(SubtypeConstraint(CompileTimeType('ArrayList',
        [Capture('exact', CompileTimeType('Integer'))]), CompileTimeType('ArrayList',
        [Capture('exact', CompileTimeType('Integer'))])))
        worklist.append(SubtypeConstraint(
            CompileTimeType('Integer'),
            CompileTimeType('Comparable',
                [Capture('super', CompileTimeType('Integer'))])))

        # System.out.println(a.run())
        worklist.append(DisjunctiveConstraint(
                SubtypeConstraint(CompileTimeType('B'), CompileTimeType('boolean')),
                SubtypeConstraint(CompileTimeType('B'), CompileTimeType('char')),
                SubtypeConstraint(CompileTimeType('B'), CompileTimeType('char', dims = 1)),
                SubtypeConstraint(CompileTimeType('B'), CompileTimeType('double')),
                SubtypeConstraint(CompileTimeType('B'), CompileTimeType('float')),
                SubtypeConstraint(CompileTimeType('B'), CompileTimeType('int')),
                SubtypeConstraint(CompileTimeType('B'), CompileTimeType('Object')),
                SubtypeConstraint(CompileTimeType('B'), CompileTimeType('String'))
            ))

        # b.c = "Hello"
        mt['B'].add_attribute(Attribute('c', InferenceVariable(), False, False))
        worklist.append(SubtypeConstraint(CompileTimeType('String'),
            CompileTimeType('B').get_attribute_type('c', dt, mt)))
        
        # return b;
        worklist.append(SubtypeConstraint(CompileTimeType('B'), CompileTimeType('B')))


    def handle_ordinary_compilations(self, 
            ordinary_compilations: List[Java9Parser.OrdinaryCompilationContext]):
        for ordinary_compilation in ordinary_compilations:
            # TODO handle package and import declarations.
            type_declarations = ordinary_compilation.typeDeclaration()
            for type in type_declarations:
                self.handle_type_declarations(type)


    def handle_type_declarations(self, ctx:
            Java9Parser.TypeDeclarationContext):
        if ctx is None:
            return

        cls = ctx.classDeclaration()
        iface = ctx.interfaceDeclaration()

        self.handle_class_declaration(cls)
        self.handle_interface_declaration(iface)

    def handle_class_declaration(self, ctx: Java9Parser.ClassDeclarationContext):
        if ctx is None:
            return
        normal_ctx = ctx.normalClassDeclaration()
        #enum_ctx = ctx.enumDeclaration()
        self.handle_normal_class_declaration(normal_ctx)
        #TODO: Handle enumDeclaration

    def handle_normal_class_declaration(self,
            ctx: Java9Parser.NormalClassDeclarationContext):
        if not ctx:
            return
        class_name = ctx.identifier().getText()
        modifiers = ctx.classModifier()
        is_abstract = is_final = False
        for i in modifiers:
            if i.getText() == 'abstract':
                is_abstract = True
            if i.getText() == 'final':
                is_final = True
        cls_declaration = JavaClassDeclaration(class_name,
                is_abstract=is_abstract,
                is_final=is_final)
        self._declared_types[class_name] = cls_declaration
        type_parameters = ctx.typeParameters()
        superclass = ctx.superclass()
        superinterfaces = ctx.superinterfaces()
        tp = self.handle_type_parameters(type_parameters)
        for i in tp:
            cls_declaration.add_type_parameter(i)
        if superclass:
            superclass = superclass.classType()
            cls_declaration._superclass = self.handle_class_or_interface_type(superclass)

        if superinterfaces:
            superinterfaces = map(lambda x: x.classType(), superinterfaces.interfaceTypeList().interfaceType())
            for i in superinterfaces:
                cls_declaration._superinterfaces.append(self.handle_class_or_interface_type(i))
   
        self.handle_class_body_declarations(cls_declaration, ctx.classBody().classBodyDeclaration())

        # Get all the compiletimetypes
        t = []
        for i in CompileTimeType.created_types:
            if not i.is_primitive():
                # check if i is not a type argument of the class
                if all(i._identifier != ta._identifier for ta in
                        cls_declaration._type_parameters):
                    t.append(i)

        self._referenced_types.append(t)
        CompileTimeType.created_types = []

    def handle_class_body_declarations(self, cls: JavaClassDeclaration, ctx:
            List[Java9Parser.ClassBodyDeclarationContext]):
        for d in ctx:
            if d.classMemberDeclaration():
                d = d.classMemberDeclaration()
                if d.fieldDeclaration():
                    self.handle_field_declaration(cls, d.fieldDeclaration())
                if d.methodDeclaration():
                    self.handle_method_declaration(cls, d.methodDeclaration())
                #TODO: handle nested classes and interfaces
            #TODO: handle instance, static initializers, constructors

    def handle_field_declaration(self, cls, ctx):
        # check if static
        is_static = isinstance(ctx, Java9Parser.ConstantDeclarationContext)
        is_final = isinstance(ctx, Java9Parser.ConstantDeclarationContext)
        if isinstance(ctx, Java9Parser.FieldDeclarationContext):
            mods = ctx.fieldModifier()
        else:
            mods = ctx.constantModifier()
        for i in mods:
            if i.getText() == 'static':
                is_static = True
            if i.getText() == 'final':
                is_final = True

        # get type
        field_type = self.handle_unanntype(ctx.unannType())
        l = ctx.variableDeclaratorList().variableDeclarator()
        for v in l:
            var_id = v.variableDeclaratorId().identifier().getText()
            # TODO: handle arr type
            a = Attribute(var_id, field_type, is_static, is_final)
            cls.add_attribute(a)
            if v.variableInitializer():
                self._contexts.append((cls, ctx))
             
    def handle_method_declaration(self, cls: JavaClassDeclaration, ctx:
            Java9Parser.MethodDeclarationContext):
        is_static = False
        is_abstract = False
        is_final = False
        modifiers = ctx.methodModifier()
        for m in modifiers:
            if m.getText() == 'abstract':
                is_abstract = True
            if m.getText() == 'final':
                is_final = True
            if m.getText() == 'static':
                is_static = True

        method_header = ctx.methodHeader()
        method_obj = MethodHeader(method_header.methodDeclarator().identifier().getText(),
                is_static, is_abstract, is_final)
        self.handle_method_header(method_header, method_obj)
        cls.add_method(method_obj)
        self._contexts.append((cls, method_obj, ctx))

    def get_formal_parameter(self, fp: Java9Parser.FormalParameterContext,
            is_var_arg = False):
        t = self.handle_unanntype(fp.unannType())
        id = fp.variableDeclaratorId().identifier().getText()
        # TODO: also handle array type in id
        return FormalParameter(id, t, is_var_arg)

    def handle_unanntype(self, ctx: Java9Parser.UnannTypeContext):
        if ctx.unannPrimitiveType():
            return CompileTimeType(ctx.unannPrimitiveType().getText())
        else:
            t = ctx.unannReferenceType()
            if t.unannClassOrInterfaceType():
                return self.handle_class_or_interface_type(t.unannClassOrInterfaceType())
            elif t.unannTypeVariable():
                return TypeVariable(t.unannTypeVariable().identifier().getText())
            else:
                t = t.unannArrayType()
                dims = t.dims().getText().count('[]')
                if t.unannPrimitiveType():
                    return CompileTimeType(t.unannPrimitiveType().getText(),
                            dims=dims)
                if t.unannTypeVariable():
                    return CompileTimeType(t.unannTypeVariable().getText(),
                            dims=dims)
                res = \
                    self.handle_class_or_interface_type(t.unannClassOrInterfaceType())
                res._dims = dims
                return res


    def handle_interface_declaration(self, ctx:
            Java9Parser.InterfaceDeclarationContext):
        if ctx is None:
            return
        normal_ctx = ctx.normalInterfaceDeclaration()
        self.handle_normal_interface_declaration(normal_ctx)
        #TODO: Handle annotationTypeDeclaration
        #annotation_ctx = ctx.annotationTypeDeclaration()

    def handle_normal_interface_declaration(self,
            ctx: Java9Parser.NormalClassDeclarationContext):
        if not ctx:
            return
        interface_name = ctx.identifier().getText()
        iface_declaration = JavaInterfaceDeclaration(interface_name)
        self._declared_types[interface_name] = iface_declaration
        type_parameters = ctx.typeParameters()
        superinterfaces = ctx.extendsInterfaces()
        tp = self.handle_type_parameters(type_parameters)
        for i in tp:
            iface_declaration.add_type_parameter(i)
        if superinterfaces:
            superinterfaces = map(lambda x: x.classType(), superinterfaces.interfaceTypeList().interfaceType())
            for i in superinterfaces:
                iface_declaration._superinterfaces.append(self.handle_class_or_interface_type(i))

        self.handle_interface_member_declarations(iface_declaration,
                ctx.interfaceBody().interfaceMemberDeclaration())
        # Get all the compiletimetypes
        t = []
        for i in CompileTimeType.created_types:
            if not i.is_primitive():
                # check if i is not a type argument of the class
                if all(i._identifier != ta._identifier for ta in
                        iface_declaration._type_parameters):
                    t.append(i)

        self._referenced_types.append(t)
        CompileTimeType.created_types = []

    def handle_interface_member_declarations(self, if_dcl, ctx):
        for d in ctx:
            if d.constantDeclaration():
                self.handle_field_declaration(if_dcl, d.constantDeclaration())
            if d.interfaceMethodDeclaration():
                self.handle_interface_method_declaration(if_dcl,
                        d.interfaceMethodDeclaration())
            #TODO: handle nested classes and interfaces


    def handle_method_header(self, method_header, method_obj):
        # Handle result
        result_ctx = method_header.result()
        if result_ctx.getText() == 'void':
            result = CompileTimeType('void')
        else:
            result = self.handle_unanntype(result_ctx.unannType())
        method_obj._result = result
        # Handle type parameters
        if method_header.typeParameters():
            tp = self.handle_type_parameters(method_header.typeParameters())
            for i in tp:
                method_obj.add_type_parameter(i)
        
        # Handle formal parameters
        params = method_header.methodDeclarator().formalParameterList()
        if params:
            # ignore receiver params for now.
            fp = params.formalParameters()
            lfp = params.lastFormalParameter()
            if fp:
                fp = fp.formalParameter()
                for p in fp:
                    method_obj.add_formal_parameter(self.get_formal_parameter(p))
            if lfp.formalParameter():
                method_obj.add_formal_parameter(self.get_formal_parameter(lfp.formalParameter()))
            else:
                method_obj.add_formal_parameter(self.get_formal_parameter(lfp,
                    True))
        #TODO: Handle throws

    def handle_interface_method_declaration(self, if_dcl: JavaClassDeclaration, ctx:
            Java9Parser.InterfaceMethodDeclarationContext):
        is_static = False
        is_abstract = False
        modifiers = ctx.interfaceMethodModifier()
        for m in modifiers:
            if m.getText() == 'abstract':
                is_abstract = True
            if m.getText() == 'static':
                is_static = True

        method_header = ctx.methodHeader()
        method_obj = MethodHeader(method_header.methodDeclarator().identifier().getText(),
                is_static, is_abstract, is_final=False)
        self.handle_method_header(method_header, method_obj)

        if_dcl.add_method(method_obj)
        if ctx.methodBody().getText() != ';':
            self._contexts.append((if_dcl, method_obj, ctx))

    def handle_type_parameters(self, ctx:
            Java9Parser.TypeParametersContext):
        if ctx is None:
            return []
        type_parameters = ctx.typeParameterList().typeParameter()
        return [self.handle_type_parameter(i) for i in type_parameters]

    def handle_type_parameter(self, ctx: Java9Parser.TypeParameterContext):
        identifier = ctx.identifier().getText()
        tp = TypeParameter(identifier)
        type_bound = ctx.typeBound()
        self.handle_type_bound(tp, type_bound)
        return tp

    def handle_type_bound(self, tp: TypeParameter, ctx:
            Java9Parser.TypeBoundContext):
        '''
        typeBound
	        :	'extends' typeVariable
	        |	'extends' classOrInterfaceType additionalBound*
	        ;
        '''
        if not ctx:
            return
        tv = ctx.typeVariable()
        if tv is not None:
            tv = TypeParameter(tv.identifier().getText())
            tp.add_type_bound(tv)
            return
        if ctx.classOrInterfaceType():
            tp.add_type_bound(self.handle_class_or_interface_type(ctx.classOrInterfaceType()))
        ab = ctx.additionalBound()
        if ab:
            ab = [i.interfaceType().classType() for i in ab]
            for i in ab:
                tp.add_type_bound(self.handle_class_or_interface_type(i))

    def handle_class_or_interface_type(self, ctx) -> CompileTimeType:
        if isinstance(ctx, Java9Parser.ClassOrInterfaceTypeContext) or \
                isinstance(ctx, Java9Parser.UnannClassOrInterfaceTypeContext):
            tmp = ctx.getChild(0)
            if isinstance(tmp,
                    Java9Parser.InterfaceType_lfno_classOrInterfaceTypeContext) or \
                    isinstance(tmp,
                            Java9Parser.UnannInterfaceType_lfno_unannClassOrInterfaceTypeContext):
                tmp = tmp.getChild(0)
            res = self.handle_non_nested_type(tmp)
            i = 1
            while True:
                tmp = ctx.getChild(i)
                if not tmp:
                    break
                if isinstance(tmp,
                        Java9Parser.InterfaceType_lf_classOrInterfaceTypeContext) or \
                        isinstance(tmp,
                                Java9Parser.UnannInterfaceType_lf_unannClassOrInterfaceTypeContext):
                    tmp = tmp.getChild(0)
                res.nest(self.handle_non_nested_type(tmp))
                i += 1
        if isinstance(ctx, Java9Parser.ClassTypeContext):
            res = self.handle_non_nested_type(ctx)
            if ctx.classOrInterfaceType():
                r = self.handle_class_or_interface_type(ctx)
                r.nest(res)
                res = r
        return res


    def handle_non_nested_type(self, ctx) -> CompileTimeType:
        id = ctx.identifier().getText()
        res = CompileTimeType(id)
        ta = self.handle_type_arguments(ctx.typeArguments())
        for i in ta:
            res.add_capture(i)
        return res


    def handle_type_arguments(self, ctx: Java9Parser.TypeArgumentsContext):
        if not ctx:
            return []
        l = ctx.typeArgumentList().typeArgument()
        res = []
        for i in l:
            tmp = Capture()
            res.append(tmp)
            if i.wildcard():
                i = i.wildcard()
                if not i.wildcardBounds():
                    tmp.set_type(Capture.WILDCARD)
                    continue
                i = i.wildcardBounds()
                tmp.set_type(i.getChild(0).getText())
            else:
                tmp.set_type(Capture.EXACT)
            i = i.referenceType()
            if i.classOrInterfaceType():
                tmp.set_base(self.handle_class_or_interface_type(i.classOrInterfaceType()))
            if i.typeVariable():
                tmp.set_base(TypeVariable(i.typeVariable().identifier().getText()))
            # TODO: handle array type
        return res

    def get_assertions_from_attribute_declarator(self, enclosing_type, ctx):
        vdl = ctx.variableDeclaratorList().variableDeclarator()
        for vd in vdl:
            vi = vd.variableInitializer()
            if not vi:
                continue
            vi_type = self.get_assertions_from_variable_initializer(enclosing_type, ctx)
            #TODO
        return 1

    def resolve_variable(self, variable, enclosing_type,
            local_variables = None) -> CompileTimeType:
        
        #TODO
        if isinstance(enclosing_type, CompileTimeType):
            ctt = enclosing_type
            if ctt._identifier in self._declared_types:
                enclosing_type = self._declared_types[ctt._identifier]
        # Find the variable in local variables if any.
        if local_variables is not None and variable in local_variables:
            return local_variables[variable]

        # Otherwise, find the variable in the enclosing type's attribute list.
        if variable in enclosing_type._attributes:
            return enclosing_type._attributes[variable]._type

        # Otherwise, find the variable in the enclosing type's superclass
        try:
            superclass_ctt = enclosing_type._superclass
            if not superclass:
                # Check in object
                if enclosing_type._identifier == 'Object':
                    raise Exception
                superclass = self._declared_types['Object']

            # Find the attribute in declared types
            if superclass._identifier in self._declared_types:
                superclass_declaration = self._declared_types[superclass._identifier].clone()
                superclass_declaration.substitute_type_params(superclass._captures)
                return self.resolve_variable(variable, superclass_declaration, None)

            # Otherwise it should be in missing types
            superclass_declaration = self._missing_types[superclass._identifier]
            substituted_decl = superclass_declaration.clone()
            substituted_decl.substitute_type_params(superclass._captures)
            superclass_declaration.substitute_type_params(superclass._captures)
            if variable not in superclass._attributes:
                pass
        except:
            pass


    def get_assertions_from_method_declarator(self, enclosing_type, method_obj,
            ctx):
        return 1

