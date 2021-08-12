from typing import List
if __name__ is not None and '.' in __name__:
    from .Java9Parser import Java9Parser
    from .Constraints import InferenceVariable
else:
    from Java9Parser import Java9Parser
    from Constraints import InferenceVariable

class Attribute:
    def __init__(self, identifier, type, is_static, is_final):
        self._identifier = identifier
        self._type = type
        self._is_static = is_static
        self._is_final = is_final
    def __str__(self):
        res = 'static ' if self._is_static else ''
        res += 'final ' if self._is_final else ''
        res += f'{str(self._type)} {self._identifier}'
        return res

    def clone(self):
        return Attribute(self._identifier, self._type.clone(),
                self._is_static, self._is_final)

    def substitute_type_params(self, substitutions):
        if self._type._identifier in substitutions:
            self._type = substitutions[self._type._identifier]
        else:
            self._type.substitute_type_params(substitutions)
    def __repr__(self):
        return f'Attribute({repr(self._identifier)}, {repr(self._type)}, ' \
            f'{repr(self._is_static)}, {repr(self._is_final)})'

    def __eq__(self, o):
        return isinstance(o, Attribute) and \
            self._identifier == o._identifier and \
            self._type == o._type and \
            self._is_static == o._is_static and \
            self._is_final == o._is_final

    def substitute(self, to_be_replaced, replaced_by):
        if self._type == to_be_replaced:
            self._type = replaced_by
        else:
            self._type.substitute(to_be_replaced, replaced_by)

class FormalParameter:

    def __init__(self, identifier, type, is_var_arg = False):
        self._identifier = identifier
        self._type = type
        self._is_var_arg = is_var_arg

    def __eq__(self, o):
        return isinstance(o, FormalParameter) and \
                self._identifier == o._identifier and \
                self._type == o._type and \
                self._is_var_arg == o._is_var_arg

    def __str__(self):
        s = '...' if self._is_var_arg else ''
        return f'{str(self._type)}{s} {self._identifier}'

    def clone(self):
        return FormalParameter(self._identifier, self._type.clone(), self._is_var_arg)

    def substitute_type_params(self, substitutions):
        if self._type._identifier in substitutions:
            self._type = substitutions[self._type._identifier]
        else:
            self._type.substitute_type_params(substitutions)

    def __repr__(self):
        return f'FormalParameter({repr(self._identifier)}, {repr(self._type)}' \
                f', {repr(self._is_var_arg)})'

    def substitute(self, to_be_replaced, replaced_by):
        if self._type == to_be_replaced:
            self._type = replaced_by
        else:
            self._type.substitute(to_be_replaced, replaced_by)

class TypeParameter:
    def __init__(self, identifier: str, type_bounds = None):
        self._identifier = identifier
        self._type_bounds = type_bounds if type_bounds else []

    def __eq__(self, o):
        return isinstance(o, TypeParameter) and \
                self._identifier == o._identifier and \
                self._type_bounds == o._type_bounds

    def add_type_bound(self, tp):
        self._type_bounds.append(tp)

    def __str__(self):
        tb = ' & '.join(map(str, self._type_bounds))
        if tb:
            return f'{self._identifier} extends {tb}'
        return self._identifier

    def __repr__(self):
        return f'TypeParameter({repr(self._identifier)}, ' \
            f'{repr(self._type_bounds)})'

    def clone(self):
        return TypeParameter(self._identifier, [i.clone() for i in self._type_bounds])

    def substitute_type_params(self, substitutions):
        for idx in range(len(self._type_bounds)):
            i = self._type_bounds[idx]
            if i._identifier in substitutions:
                self._type_bounds[idx] = substitutions[i._identifier]
            else:
                i.substitute_type_params(substitutions)

    def substitute(self, to_be_replaced, replaced_by):
        for i in self._type_bounds:
            i.substitute(to_be_replaced, replaced_by)

class MethodHeader:
    def __init__(self, identifier, is_static, is_abstract, is_final,
            type_parameters = None, throws = None, formal_params = None,
            result = None):
        self._identifier = identifier
        self._result = result
        self._is_static = is_static
        self._is_abstract = is_abstract
        self._is_final = is_final
        self._type_parameters = [] if not type_parameters else type_parameters
        self._throws = [] if not throws else throws
        self._formal_parameters = [] if not formal_params else formal_params

    def substitute(self, to_be_replaced, replaced_by):
        if self._result == to_be_replaced:
            self._result = replaced_by
        else:
            self._result.substitute(to_be_replaced, replaced_by)
        for i in self._type_parameters:
            i.substitute_type_params(to_be_replaced, replaced_by)
        for i in self._formal_parameters:
            i.substitute_type_params(to_be_replaced, replaced_by)

    def add_type_parameter(self, tp: TypeParameter):
        self._type_parameters.append(tp)

    def add_formal_parameter(self, fp):
        self._formal_parameters.append(fp)

    def __str__(self):
        type_params = '<' + ', '.join(map(str, self._type_parameters)) + '> ' \
                if self._type_parameters \
                else ''
        static = 'static ' if self._is_static else ''
        abstract = 'abstract ' if self._is_abstract else ''
        final = 'final ' if self._is_final else ''
        mods = static + abstract + final
        formal_params = '(' + ', '.join(map(str, self._formal_parameters)) + ')'
        return f'{mods}{type_params}{str(self._result)} {self._identifier}{formal_params}'

    def __repr__(self):
        return f'MethodHeader({repr(self._identifier)}, {repr(self._is_static)}'\
                f', {repr(self._is_abstract)}, {repr(self._is_final)}, ' \
                f'{repr(self._type_parameters)}, {repr(self._throws)}, ' \
                f'{repr(self._formal_parameters)})'

    def clone(self):
        res = MethodHeader(self._identifier, self._is_static, self._is_abstract,
                self._is_final)
        for i in self._type_parameters:
            res.add_type_parameter(i.clone())
        for i in self._formal_parameters:
            res.add_formal_parameter(i.clone())
        return res

    def substitute_type_params(self, substitutions):
        if self._result is not None:
            if self._result._identifier in substitutions:
                self._result = substitutions[self._result._identifier]
            else:
                self._result.substitute_type_params(substitutions)
        # Remove substitutions where type params conflict.
        new_subs = {}
        for k, v in substitutions.items():
            for tp in self._type_parameters:
                if k == tp._identifier: break
            else:
                new_subs[k] = v
        for i in self._formal_parameters:
            i.substitute_type_params(new_subs)
            


class Constructor:
    def __init__(self, type_params = None, throws = None, formal_params = None):
        self._type_parameters = [] if not type_params else type_params
        self._throws = [] if not throws else throws
        self._formal_parameters = [] if not formal_params else formal_params

    def add_type_parameter(self, tp: TypeParameter):
        self._type_parameters.append(tp)

    def add_formal_parameter(self, fp):
        self._formal_parameters.append(fp)
    
    def substitute(self, to_be_replaced, replaced_by):
        for i in self._type_parameters:
            i.substitute_type_params(to_be_replaced, replaced_by)
        for i in self._formal_parameters:
            i.substitute_type_params(to_be_replaced, replaced_by)

    def __str__(self):
        type_params = '<' + ', '.join(map(str, self._type_parameters)) + '> ' \
                if self._type_parameters \
                else ''
        formal_params = '(' + ', '.join(map(str, self._formal_parameters)) + ')'
        return f'{type_params}{{}}{formal_params}'

    def __repr__(self):
        return f'Constructor({repr(self._type_parameters)}, {repr(self._throws)},' \
                f' {repr(self._formal_parameters)})'

    def clone(self):
        res = Constructor()
        for i in self._type_parameters:
            res.add_type_parameter(i.clone())
        for i in self._formal_parameters:
            res.add_formal_parameter(i.clone())
        return res

class Capture:
    SUPER = 'super'
    EXTENDS = 'extends'
    WILDCARD = '?'
    EXACT = 'exact'
    def __init__(self, type = None, base = None):
        self._type = type
        self._base = base
    def set_type(self, type: str, base = None):
        self._type = type
        self._base = base
    def set_base(self, base):
        self._base = base

    def substitute(self, to_be_replaced, replaced_by):
        if self._base:
            if self._base == to_be_replaced:
                self._base = replaced_by
            else:
                self._base.substitute(to_be_replaced, replaced_by)

    def get_bounds(self):
        if self._type == Capture.EXACT:
            lupper = self._base
            llower = self._base
        elif self._type == Capture.WILDCARD:
            lupper = CompileTimeType('Object')
            llower = None
        elif self._type == Capture.EXTENDS:
            lupper = self._base
            llower = None
        else:
            lupper = CompileTimeType('Object')
            llower = self._base
        return llower, lupper
    
    def __str__(self):
        if self._type == Capture.WILDCARD:
            return '?'
        if self._type == Capture.EXACT:
            return str(self._base)
        return f'? {self._type} {str(self._base)}'
    def __repr__(self):
        return f'Capture({repr(self._type)}, {repr(self._base)})'
    def clone(self):
        return Capture(self._type, None if not self._base else self._base.clone())

    def substitute_type_params(self, substitutions):
        for k, v in substitutions.items():
            v = v.clone()
            if isinstance(v, CompileTimeType):
                v = Capture('exact', v)
            if not self._base:
                continue
            if self._base._identifier == k:
                # U substituted with anything
                if self._type == Capture.EXACT:
                    self._base = v._base.clone() if v._base else None
                    self._type = v._type
                    return
                if self._type == Capture.SUPER:
                    if v._type == Capture.SUPER or v._type == Capture.EXACT:
                        # ? super U substituted with ? super X or X
                        self._base = v._base.clone()
                        return
                    # ? super U substituted with ? or ? extends X
                    self._base = None
                    self._type = Capture.WILDCARD
                    return
                if self._type == Capture.EXTENDS:
                    if v._type == Capture.EXTENDS or v._type == Capture.EXACT:
                        # ? extends U substituted with ? extends X or X
                        self._base = v._base.clone()
                        return
                    # ? extends U substituted with ? or ? extends X
                    self._base = None
                    self._type = Capture.WILDCARD
                    return
        self._base.substitute_type_params(substitutions)

    def __eq__(self, o):
        if isinstance(o, Capture):
            return self._type == o._type and \
                    self._base == o._base
        if isinstance(o, CompileTimeType):
            return self._type == Capture.EXACT and \
                    self._base == o
        return False

class TypeVariable:
    def __init__(self, identifier: str):
        self._identifier = identifier

    def __str__(self):
        return self._identifier

    def __repr__(self):
        return f'TypeVariable({repr(self._identifier)})'

    def substitute_type_params(self, substitutions):
        pass

    def substitute(self, to_be_replaced, replaced_by):
        pass

    def clone(self):
        return TypeVariable(self._identifier)

    

class CompileTimeType:
    created_types = []
    PRIMITIVES = {
            'int': 'Integer',
            'short': 'Short',
            'long': 'Long',
            'double': 'Double',
            'float': 'Float',
            'boolean': 'Boolean',
            'char': 'Character',
            'byte': 'Byte',
            'void': 'Void'
            }
    def __init__(self, identifier: str, captures = None, nest = None,
            is_diamond = False, dims = 0):
        self._identifier = identifier
        self._captures = captures if captures else []
        self._nest = nest
        self._is_diamond = is_diamond
        self._dims = dims
        CompileTimeType.created_types.append(self)
       
    def __repr__(self):
        return f'CompileTimeType({repr(self._identifier)}, {repr(self._captures)}'\
                f', {repr(self._nest)}, {repr(self._is_diamond)},' \
                f' {repr(self._dims)})'

    def substitute(self, to_be_replaced, replaced_by):
        for c in self._captures:
            c.substitute(to_be_replaced, replaced_by)
        if self._nest:
            self._nest.substitute(to_be_replaced, replaced_by)

    def add_capture(self, capture: Capture):
        self._captures.append(capture)

    def nest(self, ctt: 'CompileTimeType'):
        if not self._nest:
            self._nest = ctt
        else:
            self._nest.nest(ctt)

    def box(self):
        dimless = self.clone()
        dimless._dims = 0
        if dimless.is_primitive():
            dimless._identifier = CompileTimeType.PRIMITIVES[dimless._identifier]
            dimless._dims = self._dims
            return dimless
        return self

    def unbox(self):
        res = self.clone()
        res._dims = 0
        for unboxed, boxed in CompileTimeType.PRIMITIVES:
            if boxed == res._identifier:
                res._identifier = unboxed
                res._dims = self._dims
                return res
        return self


    def __eq__(self, o):
        if isinstance(o, Capture):
            return o._type == Capture.EXACT and \
                    o._base == self
        if isinstance(o, CompileTimeType):
            # int == Integer
            o = o.box()
            self = self.box()
            return self._identifier == o._identifier and \
                    self._captures == o._captures and \
                    self._nest == o._nest and \
                    self._is_diamond == o._is_diamond and \
                    self._dims == o._dims
        return False

    def get_attribute_type(self, identifier, dt, mt):
        if self._dims > 0:
            if identifier == 'length':
                return CompileTimeType('int')
            raise Exception(f'{self} does not have attribute {identifier}!')
            
        # Get the underlying declaration of the type.
        if self.is_primitive():
            self = CompileTimeType(CompileTimeType.PRIMITIVES[self._identifier])
        if self._identifier in dt:
            try:
                underlying_declaration = dt[self._identifier].clone()
                if self._captures:
                    underlying_declaration.substitute_type_params(self._captures)
                if identifier in underlying_declaration._attributes:
                    return underlying_declaration._attributes[identifier]._type
                # Find in superclass
                if underlying_declaration._superclass:
                    return underlying_declaration._superclass.get_attribute_type(identifier, dt, mt)
                if self._identifier != 'Object':
                    return CompileTimeType('Object').get_attribute_type(identifier, dt, mt)
                raise Exception(f'{self} does not have attribute {identifier}!')
            except:
                raise Exception(f'{self} does not have attribute {identifier}!')
        elif self._identifier in mt:
            underlying_declaration = mt[self._identifier]
            if identifier not in underlying_declaration._attributes:
                new_attr = Attribute(identifier, InferenceVariable(), False, False)
                underlying_declaration.add_attribute(new_attr, 
                        self.clone() if self._captures else None)
            if not self._captures:
                # No context. can return immediately.
                return underlying_declaration._attributes[identifier]._type
            # Find the attribute in the attribute list.
            attr_list = underlying_declaration._attributes[identifier]
            for i in attr_list:
                if i[1] == self:
                    return i[0]._type
            new_attr = Attribute(identifier, InferenceVariable(), False, False)
            underlying_declaration.add_attribute(new_attr, self.clone())
            return new_attr
        else:
            raise Exception(f'{self} not in dt or mt!')
        

    def __str__(self):
        cp = ', '.join(map(str, self._captures))
        res = self._identifier
        if cp:
            res += f'<{cp}>'
        if self._is_diamond:
            res += '<>'
        if self._nest:
            res += '.' + str(self._nest)
        if self._dims:
            res += '[]' * self._dims
        return res

    def is_primitive(self):
        return self._identifier in CompileTimeType.PRIMITIVES and \
                not self._dims

    def clone(self):
        return CompileTimeType(self._identifier, [i.clone() for i in self._captures],
                self._nest.clone() if self._nest else None, self._is_diamond)

    def substitute_type_params(self, substitutions):
        for i in self._captures:
            i.substitute_type_params(substitutions)

class JavaClassDeclaration:
    def __init__(self, identifier: str, type_parameters = None,
            super_class = None, super_interfaces = None, fqn = None,
            is_final = False, is_abstract = False, attributes=None, methods=None,
            constructors = None):
        self._identifier = identifier
        self._type_parameters = type_parameters if type_parameters else []
        self._superclass = super_class
        self._superinterfaces = super_interfaces if super_interfaces else []
        self._fqn = fqn if fqn else identifier
        self._is_final = is_final
        self._is_abstract = is_abstract
        self._attributes = attributes if attributes else {}
        self._methods = methods if methods else {}
        self._constructors = constructors if constructors else []
        self._type = 'class'

    def __repr__(self):
        return f'JavaClassDeclaration({repr(self._identifier)}, '\
                f'{repr(self._type_parameters)}, '\
                f'{repr(self._superclass)}, '\
                f'{repr(self._superinterfaces)}, '\
                f'{repr(self._fqn)}, '\
                f'{repr(self._is_final)}, '\
                f'{repr(self._is_abstract)}, '\
                f'{repr(self._attributes)}, '\
                f'{repr(self._methods)}, '\
                f'{repr(self._constructors)})'

    def add_type_parameter(self, tp: TypeParameter):
        self._type_parameters.append(tp)

    def __str__(self):
        res = 'abstract ' \
                if self._is_abstract \
                else 'final ' \
                if self._is_final \
                else ''
        res += f'class {self._fqn}'
        cp = ', '.join(map(str, self._type_parameters))
        if cp:
            res += f'<{cp}>'

        if self._superclass:
            res += f' extends {str(self._superclass)}'

        si = ', '.join(map(str, self._superinterfaces))
        if si:
            res += f' implements {si}'
        res += ' {'
        for v in self._attributes.values():
            res += f'\n    {str(v)};'
        for i in self._constructors:
            res += f'\n    {str(i).format(self._identifier)} {{ }}'
        for v in self._methods.values():
            for vv in v:
                res += f'\n    {str(vv)}'
                if vv._is_static or self._type == 'class':
                    if vv._result.is_primitive():
                        if vv._result._identifier == 'void':
                            res += ' { }'
                        elif vv._result._identifier == 'int' or \
                                vv._result._identifier == 'long' or \
                                vv._result._identifier == 'short' or \
                                vv._result._identifier == 'byte' or \
                                vv._result._identifier == 'float' or \
                                vv._result._identifier == 'double':
                            res += ' { return 0; }'
                        elif vv._result._identifier == 'char':
                            res += ' { return \'0\'; }'
                        else:
                            res += ' { return false; }'
                    else:
                        res += ' { return null; }'
                else:
                    res += ';'
        return res + '\n}\n'

    def add_constructor(self, constructor: Constructor):
        self._constructors.append(constructor)
    
    def add_attribute(self, attribute):
        self._attributes[attribute._identifier] = attribute

    def add_method(self, method):
        i = method._identifier
        if i not in self._methods:
            self._methods[i] = []
        self._methods[i].append(method)

    def substitute_type_params(self, captures):
        substitutions = {}
        for i in range(len(captures)):
            capture = captures[i]
            type_param = self._type_parameters[i]
            if capture._type == Capture.EXACT:
                ctt = capture._base.clone()
            else:
                ctt = capture.clone()
            substitutions[type_param._identifier] = ctt
        # Perform substitutions on type parameters
        for tp in self._type_parameters:
            tp.substitute_type_params(substitutions)
        if self._superclass:
            self._superclass.substitute_type_params(substitutions)
        for i in self._superinterfaces:
            i.substitute_type_params(substitutions)
        for i in self._attributes.values():
            i.substitute_type_params(substitutions)
        for method in self._methods.values():
            for m in method:
                m.substitute_type_params(substitutions)

    def clone(self):
        return JavaClassDeclaration(self._identifier,
                [i.clone() for i in self._type_parameters],
                self._superclass.clone() if self._superclass else None,
                [i.clone() for i in self._superinterfaces],
                self._fqn, self._is_final, self._is_abstract,
                { k: v.clone() for k, v in self._attributes.items() },
                { k: [i.clone() for i in v] for k, v in self._methods.items() },
                [i.clone() for i in self._constructors])

class MissingJavaClassDeclaration(JavaClassDeclaration):
    def __init__(self, identifier: str, type_parameters = None,
            super_class = None, super_interfaces = None, fqn = None,
            is_final = False, is_abstract = False, attributes=None, methods=None,
            constructors = None):
        super().__init__(identifier, type_parameters, super_class,
                super_interfaces, fqn, is_final, is_abstract, attributes,
                methods, constructors)

    def __repr__(self):
        return f'MissingJavaClassDeclaration({repr(self._identifier)}, '\
                f'{repr(self._type_parameters)}, '\
                f'{repr(self._superclass)}, '\
                f'{repr(self._superinterfaces)}, '\
                f'{repr(self._fqn)}, '\
                f'{repr(self._is_final)}, '\
                f'{repr(self._is_abstract)}, '\
                f'{repr(self._attributes)}, '\
                f'{repr(self._methods)}, '\
                f'{repr(self._constructors)})'

    def substitute(self, to_be_replaced, replaced_by):
        for i in self._type_parameters:
            i.substitute(to_be_replaced, replaced_by)
        if self._superclass:
            if self._superclass == to_be_replaced:
                self._superclass = replaced_by
            else:
                self._superclass.substitute(to_be_replaced, replaced_by)
        for i in range(len(self._superinterfaces)):
            if self._superinterfaces[i] == to_be_replaced:
                self._superinterfaces[i] = replaced_by
            else:
                self._superinterfaces[i].substitute(to_be_replaced, replaced_by)
        for i in self._attributes.values():
            if isinstance(i, list):
                for j in i:
                    j[0].substitute(to_be_replaced, replaced_by)
            else:
                i.substitute(to_be_replaced, replaced_by)
        for i in self._constructors:
            i.substitute(to_be_replaced, replaced_by)
        for i in self._methods:
            i.substitute(to_be_replaced, replaced_by)

    def add_attribute(self, attribute: Attribute, context = None):
        if context is None and self._type_parameters and not attribute._is_static:
            raise Exception('Missing instance-level attribute in a generic type must have a context')
        if context:
            if attribute._identifier not in self._attributes:
                self._attributes[attribute._identifier] = []
            self._attributes[attribute._identifier].append((attribute, context))
        else:
            super().add_attribute(attribute)

    def clone(self):
        return MissingJavaClassDeclaration(self._identifier,
                [i.clone() for i in self._type_parameters],
                self._superclass.clone() if self._superclass else None,
                [i.clone() for i in self._superinterfaces],
                self._fqn, self._is_final, self._is_abstract,
                { k: ([i.clone() for i in v] if isinstance(v, list) else v) for k, v in self._attributes.items() },
                { k: [i.clone() for i in v] for k, v in self._methods.items() },
                [i.clone() for i in self._constructors])


class JavaInterfaceDeclaration:

    def __init__(self, identifier: str, type_parameters = None,
            super_interfaces = None, fqn = None, attributes = None,
            methods = None):
        self._identifier = identifier
        self._type = 'interface'
        self._type_parameters = type_parameters if type_parameters else []
        self._superinterfaces = super_interfaces if super_interfaces else []
        self._fqn = fqn if fqn else identifier
        self._attributes = attributes if attributes else {}
        self._methods = methods if methods else {}

    def __repr__(self):
        return f'JavaInterfaceDeclaration({repr(self._identifier)}, '\
                f'{repr(self._type_parameters)}, '\
                f'{repr(self._superinterfaces)}, '\
                f'{repr(self._fqn)}, '\
                f'{repr(self._attributes)}, '\
                f'{repr(self._methods)})'\

    def substitute_type_params(self, captures):
        substitutions = {}
        for i in range(len(captures)):
            capture = captures[i]
            type_param = self._type_parameters[i]
            if capture._type == Capture.WILDCARD or \
                    capture._type == Capture.SUPER:
                ctt = CompileTimeType('Object')
            else:
                ctt = capture._base.clone()
            substitutions[type_param._identifier] = ctt
        # Perform substitutions on type parameters
        for tp in self._type_parameters:
            tp.substitute_type_params(substitutions)
        for i in self._superinterfaces:
            i.substitute_type_params(substitutions)
        for i in self._attributes.values():
            i.substitute_type_params(substitutions)
        for method in self._methods.values():
            for m in method:
                m.substitute_type_params(substitutions)
    def clone(self):
        return JavaInterfaceDeclaration(self._identifier,
                [i.clone() for i in self._type_parameters],
                [i.clone() for i in self._superinterfaces],
                self._fqn,
                { k: v.clone() for k, v in self._attributes.items() },
                { k: [i.clone() for i in v] for k, v in self._methods.items() })


    def add_type_parameter(self, tp: TypeParameter):
        self._type_parameters.append(tp)

    def add_attribute(self, attribute):
        self._attributes[attribute._identifier] = attribute

    def add_method(self, method):
        i = method._identifier
        if i not in self._methods:
            self._methods[i] = []
        self._methods[i].append(method)


    def __str__(self):
        res = f'interface {self._fqn}'
        cp = ', '.join(map(str, self._type_parameters))
        if cp:
            res += f'<{cp}>'

        si = ', '.join(map(str, self._superinterfaces))
        if si:
            res += f' extends {si}'
        res += ' {'
        for v in self._attributes.values():
            res += f'\n\t{str(v)}'
        res += '\n'
        for v in self._methods.values():
            for vv in v:
                res += f'\n    {str(vv)}'
                if vv._is_static or self._type == 'class':
                    if vv._result.is_primitive():
                        if vv._result._identifier == 'void':
                            res += ' { }'
                        elif vv._result._identifier == 'int' or \
                                vv._result._identifier == 'long' or \
                                vv._result._identifier == 'short' or \
                                vv._result._identifier == 'byte' or \
                                vv._result._identifier == 'float' or \
                                vv._result._identifier == 'double':
                            res += ' { return 0; }'
                        elif vv._result._identifier == 'char':
                            res += ' { return \'0\'; }'
                        else:
                            res += ' { return false; }'
                    else:
                        res += ' { return null; }'
                else:
                    res += ';'
        return res + '\n}\n'
class JDKTypeLoader:
    def load(self, path: str = 'jdk_types.json'):
        from json import loads
        with open(path) as f:
            read = f.read()
            l = loads(read)
        res = {}
        for i in l:
            res[i['identifier']] = self.parse_type(i)
        return res
    def parse_type(self, decl):
        if decl['type'] == 'class':
            res = JavaClassDeclaration(decl['identifier'])
        else:
            res = JavaInterfaceDeclaration(decl['identifier'])
        if 'modifiers' in decl:
            modifiers = decl['modifiers']
            res._is_abstract = 'abstract' in modifiers
            res._is_final = 'final' in modifiers
        if 'type_params' in decl:
            type_params = decl['type_params']
            for i in type_params:
                res.add_type_parameter(self.parse_type_params(i))
        if 'superclass' in decl:
            superclass = decl['superclass']
            res._superclass = self.parse_ctt(superclass)
        if 'superinterfaces' in decl:
            superinterfaces = decl['superinterfaces']
            for i in superinterfaces:
                res._superinterfaces.append(self.parse_ctt(i))
        if 'constructors' in decl:
            constructors = decl['constructors']
            for i in constructors:
                res.add_constructor(self.parse_constructor(i))
        if 'attributes' in decl:
            attributes = decl['attributes']
            for i in attributes:
                res.add_attribute(self.parse_attribute(i))
        if 'methods' in decl:
            methods = decl['methods']
            for i in methods:
                res.add_method(self.parse_method(i))
        return res

    def parse_type_params(self, i):
        if 'identifier' not in i:
            raise Exception(f'Parsing error: {i}')

        identifier = i['identifier']
        res = TypeParameter(identifier)
        if 'type_bounds' in i:
            type_bounds = i['type_bounds']
            for t in type_bounds:
                res.add_type_bound(self.parse_ctt(t))
        return res

    def parse_ctt(self, ctt):
        if 'identifier' not in ctt:
            raise Exception(f'Parsing error: {ctt}')
        identifier = ctt['identifier']
        res = CompileTimeType(identifier)
        if 'captures' in ctt:
            captures = ctt['captures']
            for i in captures:
                res.add_capture(self.parse_capture(i))
        if 'dims' in ctt:
            res._dims = ctt['dims']
        return res

    def parse_capture(self, cap):
        if 'type' not in cap:
            raise Exception(f'Parsing error: {cap}')
        type = cap['type']
        res = Capture()
        res.set_type(type)
        if 'base' in cap:
            base = cap['base']
            res.set_base(self.parse_ctt(base))
        return res

    def parse_constructor(self, cons):
        res = Constructor()
        if 'formal_params' in cons:
            formal_params = cons['formal_params']
            for i in formal_params:
                res.add_formal_parameter(self.parse_formal_params(i))
        if 'type_params' in cons:
            type_params = cons['type_params']
            for i in type_params:
                res.add_type_parameter(self.parse_type_params(i))
        return res

    def parse_method(self, meth):
        if 'identifier' not in meth:
            raise Exception(f'Parse error: {meth}')
        if 'returns' not in meth:
            raise Exception(f'Parse error: {meth}')
        if 'modifiers' in meth:
            modifiers = meth['modifiers']
            is_abstract = 'abstract' in modifiers
            is_final = 'final' in modifiers
            is_static = 'static' in modifiers
        else:
            is_abstract = is_final = is_static = False
        identifier = meth['identifier']
        returns = meth['returns']
        res = MethodHeader(identifier, is_static, is_abstract, is_final)
        res._result = self.parse_ctt(returns)
        if 'type_params' in meth:
            type_params = meth['type_params']
            for i in type_params:
                res.add_type_parameter(self.parse_type_params(i))
        if 'formal_params' in meth:
            formal_params = meth['formal_params']
            for i in formal_params:
                res.add_formal_parameter(self.parse_formal_params(i))
        return res

    def parse_attribute(self, attr):
        if 'identifier' not in attr:
            raise Exception(f'Parse error: {attr}')
        if 'type' not in attr:
            raise Exception(f'Parse error: {attr}')
        if 'modifiers' in attr:
            modifiers = attr['modifiers']
            is_static = 'static' in modifiers
            is_final = 'final' in modifiers
        else:
            is_final = is_static = False
        identifier = attr['identifier']
        type = self.parse_ctt(attr['type'])
        return Attribute(identifier, type, is_static, is_final)

    def parse_formal_params(self, fp):
        if 'identifier' not in fp:
            raise Exception(f'Parse error: {fp}')
        if 'type' not in fp:
            raise Exception(f'Parse error: {fp}')
        identifier = fp['identifier']
        type = self.parse_ctt(fp['type'])
        is_var_arg = 'is_var_arg' in fp
        return FormalParameter(identifier, type, is_var_arg)

