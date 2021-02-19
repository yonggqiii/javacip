from typing import Dict, Tuple, List, Union, Mapping, Set

TAB_WIDTH = 4
Signature = Tuple[tuple, 'Type']

def indent(n: int = 1) -> str:
    return ' ' * n * TAB_WIDTH


class Attribute:
    '''
    The data structure representing an attribute or local variable
    in a Java class / abstract class / interface.
    '''
    def __init__(self, name: str, type: 'Type') -> None:
        '''
        Creates an attribute.

        Parameters:
            name: The name of the parameter.
            type: The type of the parameter.
        '''
        self._name: str = name
        self._type: 'Type' = type
        self._must_be_static: bool = False

    def get_type(self) -> 'Type':
        '''
        Returns the type of this attribute.
        '''
        return self._type

    def make_static(self):
        '''
        Makes this attribute a a static attribute.
        '''
        self._must_be_static = True

    def build(self) -> str:
        '''
        Build this attribute into a Java attribute.
        '''
        return ('static ' if self._must_be_static else '') + f'{self._type._name} {self._name};'
        
    def __str__(self) -> str:
        '''
        Returns the string representation of the attribute.
        '''
        res = ('Attribute.\n' +
            f'    Type: {self._type}\n' +
            f'    Name: {self._name}\n' +
            f'    Static: {self._must_be_static}')
        return res

    def __repr__(self) -> str:
        return str(self)



class Method:
    class MethodVariant:
        def __init__(self, signature):
            self._signature = signature
            self._local_variables = {}

        def captures_variant(self, variant):
            '''
            This variant captures variant if
                1) All argument types in this variant are contravariant
                   to the argument types in variant.
                2) The return type in this variant is covariant to
                   the return type in this variant.
            '''
            def is_tuple_covariant(a: tuple, b: tuple) -> bool:
                if len(a) != len(b):
                    return False
                for i in range(len(a)):
                    if not a[i].is_covariant_to(b[i]):
                        return False
                return True

            # Trivially true, they are the exact same method.
            if self._signature == variant._signature:
                return True

            # Look to see if can combine variants.
            variant_arg_type, variant_out_type = variant._signature

            return is_tuple_covariant(variant_arg_type, self._signature[0]) and \
                    self._signature[1].is_covariant_to(variant_out_type)


    '''
    The data structure representing a Java method.

    Attributes:
        self.name:     The name of the method
        self.variants: A dictionary containing the different variants
                       of the method.
    '''
    def __init__(self, name: str, enclosing_type: 'Type') -> None:
        '''
        Instantiates a new Method object.
        
        Parameters:
            name: the name of the method.

        Returns:
            None
        '''
        self._name: str = name
        self._enclosing_type = enclosing_type
        self._variants: Dict['Signature', Dict[str, 'Attribute']] = {}
        self._signatures: Dict[tuple, 'Type'] = {}
        self._mappings: Dict['Signature', 'Signature'] = {}

    def add_variant(self, method_signature: 'Signature', is_static: bool) -> None:
        '''
        Adds a new variant to this method. If there exists
        a variant which already captures the input signature,
        nothing is done.

        Parameters:
            method_signature: The signature of this variant.
        '''

        def is_tuple_covariant(a: tuple, b: tuple) -> bool:
            if len(a) != len(b):
                return False
            for i in range(len(a)):
                if not a[i].is_covariant_to(b[i]):
                    return False
            return True
       
        # Nothing to do.
        if method_signature in self._variants:
            return

        # Look to see if can combine variants.
        to_add_arg_type, to_add_out_type = method_signature

        for signature, body in self._variants:
            # Check variance of signatures
            current_arg_type, current_out_type = signature
            # Check variance of arg types
            
            # if input variant captured by existing variant.
            if (is_tuple_covariant(to_add_arg_type, current_arg_type) and
                    current_out_type.is_covariant_to(to_add_out_type)):
                self._mappings[to_add_arg_type] = current_arg_type
                return

            # If existing variant captured by input variant.
            if (is_tuple_covariant(current_arg_type, to_add_arg_type) and
                    to_add_out_type.is_covariant_to(current_out_type)):
                self._variants[method_signature] = body
                del self._variants[signature]
                del self._signatures[current_arg_type]
                self._mappings[to_add_arg_type] = to_add_arg_type
                self._mappings[current_arg_type] = to_add_arg_type
                return

        # Signatures are invariant.
        self._mappings[to_add_arg_type] = to_add_arg_type
        self._signatures[to_add_arg_type] = to_add_out_type
        self._variants[method_signature] = {}

    def get_mapped_signature(self, input_type: tuple) -> tuple:
        while input_type not in self._signatures:
            input_type = self._mappings[input_type]
        return (input_type, self._signatures[input_type])

    def add_local_variable(self, 
            method_signature: 'Signature',
            name: str, 
            type: 'Type') -> None:
        '''
        Adds a local variable to a variant of this method.

        Parameters:
            method_signature: The method signature of the variant.
            name:             The name of the variable.
            type:             The type assigned to the variable.
        '''
        if method_signature[0] not in self._mappings:
            raise Exception('Method signature does not belong in method.')
        method_signature = self.get_mapped_signature(method_signature[0])
        self.make_concrete()
        self._variants[method_signature][name] = Attribute(name, type)

    def get_local_variable(self, method_signature: 'Signature',
            name: str) -> Attribute:
        if method_signature[0] not in self._mappings:
            raise Exception(f'{method_signature} not captured by any variant of {self._name}')
        method_signature = self.get_mapped_signature(method_signature[0])
        variant = self._variants[method_signature]
        if name not in variant:
            raise Exception(f'{name} not a local variable of {self._name} :: {method_signature}')
        return variant[name]

    def get_return_type(self, param_type: tuple) -> 'Type':
        if param_type not in self._signatures:
            raise Exception('Not a valid parameter type.')
        return self._signatures[param_type]

    def is_abstract(self) -> bool:
        '''
        Determines if the method is abstract.
        '''
        return self._is_abstract

    def is_concrete(self) -> bool:
        '''
        Determines if the method is concrete.
        '''
        return not self._is_abstract

    def make_concrete(self) -> None:
        '''
        Make this method into a concrete method.
        '''
        self._is_abstract = False

    def build(self) -> str:
        '''
        Build this method into a Java method.
        '''
        res = ''
        def get_param_string(params: tuple) -> str:
            res = ''
            if params == (INBUILT_VOID,):
                return res
            used_var_names: Dict['str', bool] = {}
            for idx, p in enumerate(params):
                slice = 1
                arg_name = ''
                while len(arg_name) <= len(p._name):
                    arg_name = p._name.lower()[:slice]
                    if arg_name in used_var_names:
                        slice += 1
                        arg_name = p._name.lower()[:slice]
                    else:
                        break
                counter = 0
                while arg_name in used_var_names:
                    counter += 1
                    arg_name = p._name.lower() + str(counter)
                if idx != 0:
                    res += ', '
                res += f'{p._name} {arg_name}'
            return res
            
        for idx, signature in enumerate(self._variants.keys()):
            if idx != 0:
                res += '\n'
            arg_type, out_type = signature[0], signature[1]
            res += indent(1)
            if self.is_abstract():
                res += 'abstract '
            if self._is_constructor and arg_type == (INBUILT_VOID,):
                continue
            if self._is_constructor:
                res += f'{out_type._name}({get_param_string(arg_type)}) {{ }}'
                continue
            res += f'{out_type._name} {self._name}({get_param_string(arg_type)})'
            if self.is_abstract():
                res += ';'
            else:
                res += '{ return; }' if out_type == INBUILT_VOID else '{ return null; }'
        return res

    def __str__(self) -> str:
        return self._name



class Type:
    def __init__(self) -> None:
        '''
        This constructor should not be used, instead
        use the constructors for either DefinedType, ReferencedType or
        UnknownType to create a Type object.
        '''
        raise Exception("<class 'Type'> is abstract and cannot be instantiated.")

    def is_covariant_to(self, type: 'Type') -> bool:
        '''
        Checks if this type is some subtype of another type.

        Parameters:
            `type`: The type to check whether self is some subtype of.

        Returns:
            True if self is covariant to type, False otherwise.
        '''
        raise Exception(f'Type.is_covariant_to is abstract and must be implemented.')

    def to_concrete_class(self) -> None:
        '''
        Make this type a concrete class.

        Post-condition:
            This type will be a concrete class.
        '''
        self._type_name = 'class'

    def to_interface(self) -> None:
        '''
        Makes this type an interface.

        Post-condition:
            this type will be an interface.
        '''
        self._type_name = 'interface'

    def to_abstract_class(self) -> None:
        '''
        Makes this type an abstract class.

        Post-condition:
            this type will be an abstract class.
        '''
        self._type_name = 'abstract class'
        
    def is_abstract(self) -> bool:
        '''
        Checks if the class is some abstract type, i.e. an interface or
        abstract class.

        Returns:
            True if this type is an abstract class or interface, False if it
            is a concrete class.
        '''
        return self._type_name == 'interface' or self._type_name == 'abstract class'

    def is_interface(self) -> bool:
        '''
        Checks if the class is an interface.

        Returns:
            True if this type is an interface, False otherwise.
        '''
        return 'interface' == self._type_name

    def is_concrete_class(self) -> bool:
        '''
        Checks if the class is a concrete class.

        Returns:
            True if this type is a concrete class, False otherwise.
        '''
        return 'class' == self._type_name

    def is_abstract_class(self) -> bool:
        '''
        Checks if the class is an abstract class.

        Returns:
            True if this type is an abstract class, False otherwise.
        '''
        return 'abstract class' == self._type_name

    def is_final(self) -> bool:
        '''
        Checks if this Type is a final Type and cannot be extended.

        Returns:
            True if this Type is final, False otherwise.
        '''
        return False

    def get_supertype_str(self) -> str:
        '''
        Gets the str representation of the direct supertypes of this class.
        '''
        interfaces: List['Type'] = []
        classes: List['Type'] = []

        for i in self._supertypes:
            if i.is_any_class():
                classes.append(i)
            else:
                interfaces.append(i)
        
        res = ''
        if classes:
            res += f'extends {classes[0]}'
            if interfaces:
                res += ' '
        if interfaces:
            res += 'implements '
            for idx, i in enumerate(interfaces):
                if idx > 0:
                    res += ', '
                res += i._name
        return res

    def build(self) -> str:
        '''
        Builds this type into a Java type.
        '''
        self = self._is_really
        res = '' if not self._package_name else f'package {self._package_name};\n'
        res += f'{self._type_name} {self._name} {self.get_supertype_str()} {{\n'
        for attribute in self._attributes.values():
            res += indent(1) + attribute.build() + '\n'
        for method in self._methods.values():
            res += method.build() + '\n'
        return res + '}\n'

    def __str__(self) -> str:
        return self._name 

    def full_name(self) -> str:
        '''
        Returns the full name of this type.

        For example, the Integer class in java.lang would
        be called java.lang.Integer
        '''
        return f'{self._package_name}.{self._name}'

    def __eq__(self, o):
        return isinstance(o, Type) and self.full_name() == o.full_name()

    def __hash__(self):
        return hash(self.full_name())

    def add_method(self, method_name: str) -> Method:
        '''
        Adds a method into this type. Then, returns the
        resulting Method object. Because methods can have
        multiple variants from method overloading, the signature
        will be passed into the Method object instead.

        Parameters:
            `method_name`: The name of the method to add.

        Post-conditions:
            1) If the method already exists in this Type,
               nothing is added.
               Otherwise, a new method is created with the name
               `method_name`.
            2) The method with name `method_name` is returned.
        '''

        '''
        ========== Program logic: ==========
        
        Handle post-condition 1, then post-condition 2.

        ====================================
        '''
        if method_name not in self._methods:
            self._methods[method_name] = Method(method_name, self)
        return self._methods[method_name]

    def add_attribute(self, attribute_name: str, type: 'Type') -> Attribute:
        '''
        Adds an attribute into this type. Then, returns the
        resulting Attribute object.

        Parameters:
            attribute_name: The name of the attribute to add.
            type:           The Type of the attribute.

        Post-conditions:
            1) If the attribute already exists in this Type,
               nothing is added, but if the type of the attribute conflicts
               with the input `type`, an error is thrown.
               Otherwise, a new attribute is created with the name
               `attribute_name` of type `type`.
            2) The attribute object with name `attribute_name` is returned.
        '''

        '''
        ========== Program logic: ==========
        
        Handle post-condition 1, then post-condition 2.

        ====================================
        '''
        if attribute_name in self._attributes:
            if self._attributes[attribute_name].get_type() != type:
                raise Exception(f'Attribute {self._attributes[attribute_name]} already exists in {self._name}')
        else:
            self._attributes[attribute_name] = Attribute(attribute_name, type)
        return self._attributes[attribute_name]

    def get_method(self, method_name: str) -> Method:
        '''
        Gets a method from this type, given its name. 

        Parameters:
            `method_name`: The name of the method to get.

        Pre-conditions:
            `method_name` is the name of an existing method in this DefinedType.

        Post-conditions:
            The method with name `method_name` is returned.
        '''

        '''
        ========== Program logic: ==========
        
        Handle pre-condition, raising an Exception if something is wrong.
        Otherwise, just return the method.

        ====================================
        '''
        if method_name not in self._methods:
            raise Exception(f'{method_name} is not a method in {self._name}')
        return self._methods[method_name]

    def get_attribute(self, attribute_name: str) -> Attribute:
        '''
        Gets an attribute from this type, given its name. 

        Parameters:
            `attribute_name`: The name of the method to get.

        Pre-conditions:
            `attribute_name` is the name of an existing attribute in this Type.

        Post-conditions:
            The attribute with name `attribute_name` is returned.
        '''

        '''
        ========== Program logic: ==========
        
        Handle pre-condition, raising an Exception if something is wrong.
        Otherwise, just return the attribute.

        ====================================
        '''
        if attribute_name not in self._attributes:
            raise Exception(f'{attribute_name} not an attribute of {self._name}')
        return self._attributes[attribute_name]

    def must_be_covariant_to(self, type: Type) -> None:
        '''
        Requires this type to be a subtype of some other type.

        Parameters:
            `type`: The type that self must be a covariant to.

        Pre-condition:
            `type` must not be final.

        Post-condition:
            `self` will be covariant to `type`.
        '''
        raise Exception(f'Type.must_be_covariant_to is abstract and must be implemented.')
 
    def is_real(self):
        return self == self._is_really

class DefinedType(Type):
    '''
    An object representing a type that is defined by the user. For example,
    the program:
    `class Foo {
        A a;
        public static void main(String[] args) { }
    }`
    shows that `Foo` is a `DefinedType`.
    '''
    def __init__(self,
            name: str,
            type_name: 'str' = 'interface',
            package_name: str = '',
            is_final: bool = False) -> None:
        '''
        Creates a new DefinedType.

        Parameters:
            `name`:         The name of the DefinedType.
            `type_name`:    `'class'` | `'abstract class'` | `'interface'`
            `package_name`: The name of the package this type belongs to.
            `is_final`:     Whether the type is final.
        '''

        '''
        Attributes:
        _name           : the name of the DefinedType.
        _type_name      : the type of this type; either class, abstract class
                          or interface.
        _package_name   : the name of the package that this type belongs to.
        _methods        : a dictionary with all the methods in this DefinedType;
                          keys are names and values are the Method objects.
        _attributes     : a dictionary with all the attributes in this DefinedType;
                          keys are attribute names and values are the Attribute objects.
        _extends        : the classes or interfaces that this DefinedType extends directly;
                          if self is an interface, then this is a list of interfaces it
                          can extend, otherwise it is a class. It starts out as None
                          (if it is meant to store a class) or {} if it is meant to store
                          interfaces.
        _implements     : the interfaces that this DefinedType implements directly; if self
                          is an interface, then it cannot implement anything, otherwise
                          it starts out as an empty list.
        _is_final       : whether the DefinedType is final and cannot be extended.
        '''
        self._name: str                           = name
        self._type_name: str                      = type_name
        self._package_name: str                   = package_name
        self._methods: Mapping[str, Method]       = {}
        self._attributes: Mapping[str, Attribute] = {}
        self._extends: Union[Set[Type], Type]     = None                            \
                                                    if 'class' in self._type_name   \
                                                    else set()
        self._implements: Set[Type]               = None                                \
                                                    if 'interface' == self._type_name   \
                                                    else set()
        self._is_final: bool                      = is_final

    def extends(self, type: Type) -> None:
        '''
        Make this type extend another type.
 
        Parameters:
            type: The type this DefinedType will extend.

        Pre-conditions:
            1) `type` must not be final.
            2) if `self` is a class, `self` must not have extended another class.
            3) `self` must be of the same type name as `type`. i.e., if `self` is
               a class, then `type` is also a class.

        Post-conditions:
            1) if `type` is a referenced/unknown type, then `type` will
               be forced to have the same `type_name` as self.
            2) `self` now extends `type`.
        '''

        '''
        ========== Program logic: ==========

        PART A
        Handle pre-condition 1, throwing an exception when something is wrong.

        ------------------------------------
       
        PART B
        Handle pre-condition 2, throwing an exception when something is wrong.
        If `self` already extends `A` but this method is called and `A` is passed
        in again, do nothing.

        At this point, either self is an interface, or self is a class that hasn't
        extended anything. (or trivially, already extends the input type)

        ------------------------------------

        PART C
        Handle pre-condition 3 for when `type` is a DefinedType, throwing
        an exception when there is something wrong.

        At this point, `type` is a DefinedType that has the same type_name as
        self, or `type` is a UnknownType / ReferencedType.

        ------------------------------------

        PART D
        Execute post-condition 1. Simply force `type` to have the same type_name
        as self. Even if `type` is a DefinedType, doing so makes no changes as
        it already has been enforced in pre-condition 3.

        At this point, `self` is ready to extend `type`.

        ------------------------------------

        PART E
        Do the extending.

        ====================================

        '''
        # Handle the trivial case
        if self == type:
            return

        # Part A
        if type.is_final():
            raise Exception(f'{type._name} is final')

        # Part B
        if 'class' in self._type_name and                                   \
                (self._extends != None and self._extends != type):
            raise Exception(f'{self._name} already extends {self._extends}')

        # Part C
        if isinstance(type, DefinedType):
            if self._type_name != type._type_name:
                raise TypeError(f'{self._name} cannot extend {type._name}')
        
        # Part D
        if 'class' in self._type_name:
            type.to_class()
        else:
            type.to_interface()
        
        # Part E
        if self._extends == None:
            self._extends = type
        else:
            self._extends.add(type)

    def implements(self, *interfaces: tuple[Type]) -> None:
        '''
        Make this type implement a bunch of interfaces.

        Parameters:
            interfaces: The `Type`s this DefinedType will implement.

        Pre-conditions:
            1) `self` must be a class.
            2) All types in `interfaces` must be interfaces.

        Post-conditions:
            1) if any object in `interfaces` is a referenced/unknown type,
               then force it to be an interface.
            2) `self` now implements all interfaces in `interfaces`
        '''

        '''
        ========== Program logic: ==========

        PART A
        Handle pre-condition 1, throwing an exception when something is wrong.

        At this point, self is a class.

        ------------------------------------

        PART B
        for i in interfaces do
            | PART B1
            | Enforce pre-condition 2 on i
            |-------------------------------
            | PART B2
            | Handle post-condition 1 on i by forcing the type_name of i
            | to be an interface
            |-------------------------------
            | PART B3
            | Make self implement i

        ====================================

        '''
        # Handle the trivial case
        if self == type:
            return

        # Part A
        if 'class' not in self._type_name:
            raise Exception(f'{self._name} is not a class and therefore cannot implement interfaces')

        # Part B
        for i in interfaces:
            # Part B1
            if isinstance(i, DefinedType) and i._type_name != 'interface':
                raise Exception(f'{self} cannot implement non-interface {i}')
            # Part B2
            i._type_name = 'interface'
            # Part B3
            self._implements.add(i)

    def must_be_covariant_to(self, type: Type) -> None:
        '''
        Requires this type to be a subtype of some other type.
        This method raises an Exception if this DefinedType is not already
        covariant to `type`. To make this DefinedType extend or implement another
        `Type`, use `extends` or `implements` instead.

        Parameters:
            `type`: The type that self must be a covariant to.

        Post-condition:
            `self` will be covariant to `type`.
        '''
        if not self.is_covariant_to(type):
            raise Exception(f'Defined Type {self._name} cannot be covariant to {type}')

    def is_covariant_to(self, type: 'Type') -> bool:
        '''
        Checks if this type is some subtype of another type.

        Parameters:
            type: The type to check whether self is some subtype of.

        Returns:
            True if self is covariant to type, False otherwise.
        '''
        '''
        ========== Program logic: ==========

        If self == type then return True.
        If any of the confirmed supertypes are covariant to type,
        return true.

        ====================================
        '''
        if type == self:
            return True
        if 'class' in self._type_name:
            return self._extends.is_covariant_to(type) or                   \
                    any(t.is_covariant_to(type) for t in self._implements)
        return any(t.is_covariant_to(type) for t in self._extends)

    def is_final(self) -> bool:
        '''
        Checks if this DefinedType is a final Type and cannot be extended.

        Returns:
            True if this DefinedType is final, False otherwise.
        '''
        return self._is_final

class ReferencedType(Type):
    '''
    An object representing a type that was referenced but not defined by the user.
    For example, the program:
    `class Foo {
        A a;
        public static void main(String[] args) { }
    }`
    shows that `A` is a `ReferencedType`.

    ReferencedTypes are interfaces by default, and will never be final.
    '''
    def __init__(self,
            name: str,
            package_name: str = '') -> None:
        '''
        Creates a new ReferencedType.

        Parameters:
            `name`:         The name of the ReferencedType.
            `package_name`: The name of the package this type belongs to.
        '''

        '''
        Attributes:
        _name                : the name of the ReferencedType.
        _type_name           : the type of this type; either class, abstract class
                               or interface. It is an interface by default.
        _package_name        : the name of the package that this type belongs to.
        _methods             : a dictionary containing the methods this ReferencedType
                               must have. The return types are UnknownTypes by default,
                               unless it is a constructor. keys are method names and
                               values are the Method objects.
        _attributes          : a dictionary with all the attributes this ReferencedType
                               must have. The types are UnknownTypes by default. keys
                               are attribute names and values are the Attribute objects.
        _confirmed_supertypes: the types that this ReferencedType must be covariant to.
        '''

        self._name: str                           = name
        self._type_name: str                      = 'interface'
        self._package_name: str                   = package_name
        self._methods: Mapping[str, Method]       = {}
        self._attributes: Mapping[str, Attribute] = {}
        self._confirmed_supertypes: Set[Type]     = set()
        
    def add_attribute(self, attribute_name: str) -> Attribute:
        '''
        Adds an attribute into this type. Then, returns the
        resulting Attribute object.

        Parameters:
            attribute_name: The name of the attribute to add.

        Post-conditions:
            1) If the attribute already exists in this ReferencedType,
               nothing is added.
               Otherwise, a new attribute is created with the name
               `attribute_name` of some new UnknownType.
            2) The attribute object with name `attribute_name` is returned.
        '''

        '''
        ========== Program logic: ==========
        
        Handle post-condition 1, then post-condition 2.

        ====================================
        '''
        if attribute_name not in self._attributes:
            self._attributes[attribute_name] = Attribute(attribute_name, UnknownType())
        return self._attributes[attribute_name]

    def get_method(self, method_name: str) -> Method:
        '''
        Gets a method from this type, given its name. 

        Parameters:
            `method_name`: The name of the method to get.

        Post-conditions:
            The method with name `method_name` is created first if it doesn't exist,
            then is returned.
        '''
        return self.add_method(method_name)

    def get_attribute(self, attribute_name: str) -> Attribute:
        '''
        Gets an attribute from this type, given its name. 

        Parameters:
            `attribute_name`: The name of the attribute to get.

        Post-conditions:
            The method with name `method_name` is created first if it doesn't exist,
            then is returned.
        '''
        return self.add_attribute(attribute_name)

    def must_be_covariant_to(self, type: Type) -> None:
        '''
        Requires this type to be a subtype of some other type.

        Parameters:
            `type`: The type that self must be a covariant to.

        Pre-condition:
            `type` must not be final.

        Post-condition:
            `self` will be covariant to `type`.
        '''
        '''
        ========== Program logic: ==========

        PART A
        Handle pre-condition 1, throwing an exception when something is wrong.

        At this point, type is extendable.

        ------------------------------------

        PART B
        for i in self._confirmed_supertypes do
            | PART B1
            | if i is already covariant to type then do nothing.
            | and return
            |-------------------------------
            | PART B2
            | if type is covariant to i, then replace i with type
            | and return
            |-------------------------------
        
        At this point, all confirmed supertypes are disjoint from
        type.

        ------------------------------------

        PART C
        Make self covariant to type as in post-condition.

        ====================================
        '''
        # Part A
        if type.is_final():
            raise Exception(f'{type._name} is final and thus cannot be extended')
        
        # Part B
        for i in self._confirmed_supertypes:
            # Part B1
            if i.is_covariant_to(type):
                return
            # Part B2
            if type.is_covariant_to(i):
                self._confirmed_supertypes.remove(i)
                self._confirmed_supertypes.add(type)
                return
        # Part C
        self._confirmed_supertypes.add(type)
    
    def is_covariant_to(self, type: 'Type') -> bool:
        '''
        Checks if this type is some subtype of another type.

        Parameters:
            type: The type to check whether self is some subtype of.

        Returns:
            True if self is covariant to type, False otherwise.
        '''
        '''
        ========== Program logic: ==========

        If self == type then return True.
        If any of the confirmed supertypes are covariant to type,
        return true.

        ====================================
        '''
        return self == type or                                                  \
            any(i.is_covariant_to(type) for i in self._confirmed_supertypes)

class UnknownType(Type):
    def __init__(self,
            counter: int,
            package_name: str = '') -> None:
        '''
        Creates a new UnknownType.

        Parameters:
            `counter`:      The counter of the UnknownType.
            `package_name`: The name of the package this type belongs to.
        '''

        '''
        Attributes:
        _name                : the name of the ReferencedType.
        _type_name           : the type of this type; either class, abstract class
                               or interface. It is an interface by default.
        _package_name        : the name of the package that this type belongs to.
        _methods             : a dictionary containing the methods this UnknownType
                               must have. The return types are UnknownTypes by default.
                               keys are method names and values are the Method objects.
        _attributes          : a dictionary with all the attributes this UnknownType
                               must have. The types are UnknownTypes by default. keys
                               are attribute names and values are the Attribute objects.
        _confirmed_supertypes: the types that this UnknownType must be covariant to.
        _is_really           : the exact type that this unknown type can be resolved to.
        '''

        self._name: str                           = f'UnknownType{counter}'
        self._type_name: str                      = 'interface'
        self._package_name: str                   = package_name
        self._methods: Mapping[str, Method]       = {}
        self._attributes: Mapping[str, Attribute] = {}
        self._confirmed_supertypes: Set[Type]     = set()
        self._is_really: Type                     = None
        
    def add_attribute(self, attribute_name: str) -> Attribute:
        '''
        Adds an attribute into this type. Then, returns the
        resulting Attribute object.

        Parameters:
            attribute_name: The name of the attribute to add.
        '''

        '''
        ========== Program logic: ==========

        ====================================
        '''
        if self._is_really:
            if isinstance(self._is_really, DefinedType):
                return self._is_really.get_attribute(attribute_name)
            return self._is_really.add_attribute(attribute_name)
        if attribute_name not in self._attributes:
            self._attributes[attribute_name] = Attribute(attribute_name, UnknownType())
        return self._attributes[attribute_name]

    def add_method(self, method_name: str) -> Method:
        if self._is_really:
            if isinstance(self, DefinedType):
                return self._is_really.get_method(method_name)
            return self._is_really.add_attribute(method_name)
        if method_name not in self._methods:
            self._methods[method_name] = Method(method_name, self)
        return self._methods[method_name]

    def get_method(self, method_name: str) -> Method:
        '''
        Gets a method from this type, given its name. 

        Parameters:
            `method_name`: The name of the method to get.

        Post-conditions:
            The method with name `method_name` is created first if it doesn't exist,
            then is returned.
        '''
        if self._is_really:
            return self._is_really.get_method(method_name)
        return self.add_method(method_name)

    def get_attribute(self, attribute_name: str) -> Attribute:
        '''
        Gets an attribute from this type, given its name. 

        Parameters:
            `attribute_name`: The name of the attribute to get.

        Post-conditions:
            The method with name `method_name` is created first if it doesn't exist,
            then is returned.
        '''
        if self._is_really:
            return self._is_really.get_attribute(attribute_name)
        return self.add_attribute(attribute_name)

    def must_be_covariant_to(self, type: Type) -> None:
        '''
        Requires this type to be a subtype of some other type.

        Parameters:
            `type`: The type that self must be covariant to.

        Post-condition:
            `self` will be covariant to `type`.
        '''
        '''
        ========== Program logic: ==========

        PART A
        Handle pre-condition 1, throwing an exception when something is wrong.

        At this point, type is extendable.

        ------------------------------------

        PART B
        for i in self._confirmed_supertypes do
            | PART B1
            | if i is already covariant to type then do nothing.
            | and return
            |-------------------------------
            | PART B2
            | if type is covariant to i, then replace i with type
            | and return
            |-------------------------------
        
        At this point, all confirmed supertypes are disjoint from
        type.

        ------------------------------------

        PART C
        Make self covariant to type as in post-condition.

        ====================================
        '''
        # Part A
        if type.is_final():
            raise Exception(f'{type._name} is final and thus cannot be extended')
        
        # Part B
        for i in self._confirmed_supertypes:
            # Part B1
            if i.is_covariant_to(type):
                return
            # Part B2
            if type.is_covariant_to(i):
                self._confirmed_supertypes.remove(i)
                self._confirmed_supertypes.add(type)
                return
        # Part C
        self._confirmed_supertypes.add(type)
    
    def is_covariant_to(self, type: 'Type') -> bool:
        '''
        Checks if this UnknownType is some subtype of another type.

        Parameters:
            type: The type to check whether self is some subtype of.

        Returns:
            True if self is covariant to type, False otherwise.
        '''
        '''
        ========== Program logic: ==========

        If self == type then return True.
        If any of the confirmed supertypes are covariant to type,
        return true.

        ====================================
        '''
        if self == type:
            return True
        if any(i.is_covariant_to(type) for i in self._confirmed_supertypes):
            return True
        if self._is_really:
            if self._is_really == type:
                return True
            if self._is_really.is_covariant_to(type):
                return True
        return False

class Database:
    def __init__(self):
        self.database = {}

    def add_and_get_type(self, name: str) -> Type:
        '''
        Adds and gets a type to this database.
        '''
        if name not in self.database:
            self.database[name] = Type(name)
        return self.database[name]

    def add_unknown_type(self) -> UnknownType:
        t = UnknownType()
        self.database[t._name] = t
        return t



INBUILT_VOID = Type('void')
JAVA_LANG_STRING = Type('String', 'java.lang')
JAVA_LANG_STRING.to_concrete_class()
JAVA_LANG_INTEGER = Type('Integer', 'java.lang')
JAVA_LANG_INTEGER.to_concrete_class()
JAVA_LANG_BOOLEAN = Type('Boolean', 'java.lang')
JAVA_LANG_BOOLEAN.to_concrete_class()

sargs = Type('String[]')

d = Database()

'''
class Foo {

    A a;

    void main(String[] args) {
        Foo f = new Foo();
        f.a = new B();
        String s = f.a.doX(1).doY(false);
    }

}
'''

# class Foo
foo = d.add_and_get_type('Foo')
foo.to_concrete_class()

# A a;
foo.add_attribute('a', d.add_and_get_type('A'))

# public static void main(String[] args) { ...
foo.add_method('main', ((sargs,), INBUILT_VOID))
main = foo.get_method('main')

# Foo f = ...
main.add_local_variable(((sargs,), INBUILT_VOID), 'f', d.add_and_get_type('Foo'))

# String s = ...
main.add_local_variable(((sargs,), INBUILT_VOID), 's', JAVA_LANG_STRING)

# Foo f = new Foo();
foo.get_method('Foo').get_return_type((INBUILT_VOID,)).must_be_subtype_of(d.add_and_get_type('Foo'))

# f.a = new B();
d.add_and_get_type('B')
d.add_and_get_type('B').to_concrete_class()
d.add_and_get_type('B').get_method('B').get_return_type((INBUILT_VOID,)).must_be_subtype_of(d.add_and_get_type('A'))

# f.a.doX(1)
foo.get_method('main').get_local_variable(((sargs,), INBUILT_VOID),
        'f').get_type().get_attribute('a').get_type().add_method('doX', ((JAVA_LANG_INTEGER,), d.add_unknown_type()))

foo.get_method('main').get_local_variable(((sargs,), INBUILT_VOID),
        'f').get_type().get_attribute('a').get_type().get_method('doX').get_return_type((JAVA_LANG_INTEGER,)).add_method('doY',
                ((JAVA_LANG_BOOLEAN,), d.add_unknown_type()))
# String s = f.a.doX(1).doY(false)
foo.get_method('main').get_local_variable(((sargs,), INBUILT_VOID),
        'f').get_type().get_attribute('a').get_type().get_method('doX').get_return_type((JAVA_LANG_INTEGER,)).get_method('doY').get_return_type((JAVA_LANG_BOOLEAN,)).must_be_subtype_of(JAVA_LANG_STRING)


'''
class Main {

    public static void main(String[] args) {
        int a = new A().doX(1).doY('Hello').doZ(false);
    }

}
'''

d = Database()
foo = d.add_and_get_type('Foo')
foo.to_concrete_class()
foo.add_method('main', ((sargs,), INBUILT_VOID))
main = foo.get_method('main')
main.add_local_variable(((sargs,), INBUILT_VOID), 'a', JAVA_LANG_INTEGER)
a = d.add_and_get_type('A')
a.to_concrete_class()
a.add_method('doX', ((JAVA_LANG_INTEGER,), d.add_unknown_type()))
doX = a.get_method('doX')
doX.get_return_type((JAVA_LANG_INTEGER,)).add_method('doY', ((JAVA_LANG_STRING,), d.add_unknown_type()))
doY = doX.get_return_type((JAVA_LANG_INTEGER,)).get_method('doY')
doY.get_return_type((JAVA_LANG_STRING,)).add_method('doZ', ((JAVA_LANG_BOOLEAN,), d.add_unknown_type()))
doY.get_return_type((JAVA_LANG_STRING,)).get_method('doZ').get_return_type((JAVA_LANG_BOOLEAN,)).make_subtype_of(JAVA_LANG_INTEGER)

for t in d.database.values():
    print(t.build())
