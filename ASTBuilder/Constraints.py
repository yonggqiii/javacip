from ASTBuilder.JavaTypes import CompileTimeType, Capture, Type
from typing import List

class BinaryConstraint:
    def __init__(self, lhs: Type, rhs: Type):
        self.lhs: Type = lhs
        self.rhs: Type = lhs
    def __str__(self) -> str:
        return 'Not overridden'
    def clone(self) -> 'BinaryConstraint':
        return BinaryConstraint(self.lhs.clone(), self.rhs.clone())
    def __repr__(self) -> str:
        return 'Not overridden'

    def substitute(self,
            to_be_replaced: Type,
            replaced_by: Type):
        """
        Performs some substitution of types. Usually happens on inference
        variables.

        Parameters:
            to_be_replaced: the type to be replaced
            replaced_by: the type you want to use instead
        """
        if self.lhs == to_be_replaced:
            self.lhs = replaced_by
        else:
            self.lhs.substitute(to_be_replaced, replaced_by)
        if self.rhs == to_be_replaced:
            self.rhs = replaced_by
        else:
            self.rhs.substitute(to_be_replaced, replaced_by)

    def type_erased(self) -> 'BinaryConstraint':
        return BinaryConstraint(self.lhs.type_erased(),
                self.rhs.type_erased())

class SubtypeConstraint(BinaryConstraint):

    def __init__(self, lhs: Type, rhs: Type):
        lhs, rhs = lhs.clone(), rhs.clone()

        if isinstance(lhs, Capture):
            lhs = lhs.upward_projection()
        if isinstance(rhs, Capture):
            rhs = rhs.downward_projection()
        
        # If different dimensions, for now just temporarily take
        # it that a class cannot extend an array.
        if lhs._dims != rhs._dims:
            raise Exception(f'Different number of dimensions: {lhs._dims} and {rhs._dims}')
        self.lhs: CompileTimeType = lhs
        self.rhs: CompileTimeType = rhs

        # Set all the dimensions to 0 because there's literally no point
        self.lhs._dims = self.rhs._dims = 0

    def __str__(self) -> str:
        return f'{self.lhs} <: {self.rhs}'

    def __repr__(self) -> str:
        return f'SubtypeConstraint({repr(self.lhs)}, {repr(self.rhs)})'

    def clone(self):
        return SubtypeConstraint(self.lhs.clone(), self.rhs.clone())

    def substitute(self,
            to_be_replaced: Type,
            replaced_by: Type):
        """
        Performs some substitution of types. Usually happens on inference
        variables.

        Parameters:
            to_be_replaced: the type to be replaced
            replaced_by: the type you want to use instead
        """
        if self.lhs == to_be_replaced:
            self.lhs = replaced_by
        else:
            self.lhs.substitute(to_be_replaced, replaced_by)
        if self.rhs == to_be_replaced:
            self.rhs = replaced_by
        else:
            self.rhs.substitute(to_be_replaced, replaced_by)

    def type_erased(self) -> 'SubtypeConstraint':
        return SubtypeConstraint(self.lhs.type_erased(),
                self.rhs.type_erased())

    def expand(self, dt, mt) -> List[BinaryConstraint]:
        if self.trivially_true(dt, mt):
            return []
        if isinstance(self.lhs, InferenceVariable):
            if self.rhs.is_primitive():
                res = [Substitution(self.lhs, self.rhs)]
                if self.rhs._identifier in CompileTimeType.PRIMITIVE_WIDEN_REVERSE:
                    for i in CompileTimeType.PRIMITIVE_WIDEN_REVERSE[self.rhs._identifier]:
                        res.append(Substitution(self.lhs, CompileTimeType(i)))
                return [DisjunctiveConstraint(*res)]

            if self.rhs._identifier in dt:
                decl = dt[self.rhs._identifier]
            else:
                decl = mt[self.rhs._identifier]
            if decl._is_final:
                raw = CompileTimeType(self.rhs._identifier,
                    [InferenceVariable() for _ in decl._type_parameters],
                    dims = self.rhs._dims)
                res = [Substitution(self.lhs, raw)]
                for i, e in enumerate(raw._captures):
                    res.extend(ContainmentConstraint(e, self.rhs._captures[i]).expand())
                return res
        try:
            up = self.lhs.upcast(dt, mt, self.rhs.type_erased())
            res = []
            for i, e in enumerate(up._captures):
                res += ContainmentConstraint(e, self.rhs._captures[i]).expand()
            final_res = []
            for i in res:
                if isinstance(i, SubtypeConstraint):
                    final_res += i.reduce(dt, mt)
                else:
                    final_res.append(i)
            return [i for i in final_res if not i.trivially_true(dt, mt)]
        except:
            return [self.type_erased()]

    def trivially_true(self, dt, mt):
        if isinstance(self.lhs, InferenceVariable) or\
                isinstance(self.rhs, InferenceVariable):
            return self.lhs == self.rhs

        # rhs Object or lhs null lmao
        if self.rhs._identifier == 'Object' or self.lhs._identifier == 'null':
            return True
       
        # Unbox
        if self.lhs.box()._identifier != self.lhs._identifier:
            return SubtypeConstraint(
                self.lhs.box(),
                self.rhs).trivially_true(dt, mt)

        if self.rhs.box()._identifier != self.rhs._identifier:
            return SubtypeConstraint(
                self.lhs,
                self.rhs.box()).trivially_true(dt, mt)

        # Same thing
        if self.lhs == self.rhs:
            return True

        # Same base, but with generics
        if self.lhs._identifier == self.rhs._identifier:
            lhs_cap = self.lhs._captures
            rhs_cap = self.rhs._captures

            if len(lhs_cap) == 0 or len(rhs_cap) == 0:
                # We are dealing with raw types, no problem.
                return True

            if len(lhs_cap) != len(rhs_cap):
                raise Exception(f'{self.lhs} and {self.rhs} have different'
                        'number of captures!')

            # Compare the captures
            for i in range(len(lhs_cap)):
                l = lhs_cap[i]
                r = rhs_cap[i]
                # Compare upper bounds
                llower, lupper = l.get_bounds()
                rlower, rupper = r.get_bounds()
                upper_const = SubtypeConstraint(lupper, rupper)
                lower_const = SubtypeConstraint(rlower, llower)
                if not upper_const.trivially_true(dt, mt) or \
                        not lower_const.trivially_true(dt, mt):
                    return False
            return True

        # Get supertypes of lhs
        if self.lhs._identifier in dt:
            lhs_decl = dt[self.lhs._identifier].clone()
        elif self.lhs._identifier in mt:
            lhs_decl = mt[self.lhs._identifier].clone()
        else:
            raise Exception(f'{self.lhs} does not have a declaration!')
        if self.lhs._captures:
            lhs_decl.substitute_type_params(self.lhs._captures)
        elif self.lhs_decl._type_parameters:
            # raw type, erase supertypes.
            try:
                lhs_decl._superclass = list(map(
                    lambda i: i.type_erased(),
                    lhs_decl._superclass))
            except:
                pass
            lhs_decl._superinterfaces = list(map(
                lambda i: i.typed_erased(),
                lhs_decl._superinterfaces
            ))
                    
        # Check if superclass is a subtype of rhs
        try:
            superclass = lhs_decl._superclass
            if superclass:
                new_c = SubtypeConstraint(superclass, self.rhs)
                if new_c.trivially_true(dt, mt):
                    return True
        except:
            pass
        
        superinterface = lhs_decl._superinterfaces
        
        for i in superinterface:
            new_c = SubtypeConstraint(i, self.rhs)
            if new_c.trivially_true(dt, mt):
                return True
        
        # More complicated to solve.
        return False
    
    def contains_as_lhs(self, lhs: CompileTimeType) -> bool:
        if self.lhs.type_erased() == lhs.type_erased():
            return True
        if self.lhs._captures:
            return any(SubtypeConstraint(i, self.rhs).contains_as_lhs(lhs)
                    for i in self.lhs._captures)
        return False

    def __eq__(self, o):
        return isinstance(o, SubtypeConstraint) and \
            self.lhs == o.lhs and \
            self.rhs == o.rhs
    
    def __hash__(self):
        return hash(self.lhs) * 17 + hash(self.rhs) * 31

class ContainmentConstraint:

    def __init__(self, lhs: Capture, rhs: Capture):
        self.lhs = lhs
        self.rhs = rhs

    def expand(self) -> List[SubtypeConstraint]:
        lhs_bounds, rhs_bounds = self.lhs.get_bounds(), self.rhs.get_bounds()
        if rhs_bounds[0] == rhs_bounds[1]:
            return [Substitution(lhs_bounds[0], rhs_bounds[0]), 
                    Substitution(lhs_bounds[1], rhs_bounds[1])]
        return [SubtypeConstraint(rhs_bounds[1], lhs_bounds[1]),
                SubtypeConstraint(lhs_bounds[0], rhs_bounds[0])]

class Substitution:
    def __init__(self, a, b):
        self.a = a
        self.b = b

    def __repr__(self):
        return f'Substitution({repr(self.a)}, '\
            f'{repr(self.b)})'

    def contains_as_lhs(self, t):
        return self.a == t or self.b == t

    def clone(self):
        return Substitution(self.a.clone(), self.b.clone())

    def __str__(self):
        return f'{self.a} = {self.b}'

    def substitute(self, to_be_replaced, replaced_by):
        if self.a == to_be_replaced:
            self.a = replaced_by
        else:
            self.a.substitute(to_be_replaced, replaced_by)
        if self.b == to_be_replaced:
            self.b = replaced_by
        else:
            self.b.substitute(to_be_replaced, replaced_by)

    def trivially_true(self, dt, mt) -> bool:
        return self.lhs == self.rhs

class DisjunctiveConstraint:

    def __init__(self, *constraint):
        self.constraints = list(constraint)

    def __str__(self):
        res = '('
        for i in self.constraints:
            res += f'{i} ∨ '
        return res[:-3] + ')'

    def __repr__(self):
        return f'DisjunctiveConstraint(*{self.constraints})'

    def substitute(self, to_be_replaced, replaced_by):
        for i in self.constraints:
            i.substitute(to_be_replaced, replaced_by)

    def clone(self):
        return DisjunctiveConstraint(*[i.clone() for i in self.constraints])

    def trivially_true(self, dt, mt):
        return any(i.trivially_true(dt, mt) for i in self.constraints)

    def contains_as_lhs(self, lhs):
        return any(i.contains_as_lhs(lhs) for i in self.constraints)

class ConjunctiveConstraint:

    def __init__(self, *constraint):
        self.constraints = list(constraint)

    def __str__(self):
        res = '('
        for i in self.constraints:
            res += f'{i} ∧ '
        return res[:-2] + ')'
    
    def substitute(self, to_be_replaced, replaced_by):
        for i in self.constraints:
            i.substitute(to_be_replaced, replaced_by)

    def __repr__(self):
        return f'ConjunctiveConstraint(*{self.constraints})'

    def clone(self):
        return ConjunctiveConstraint(*[i.clone() for i in self.constraints])

    def trivially_true(self, dt, mt):
        return all([i.trivially_true(dt, mt) for i in self.constraints])
    
    def contains_as_lhs(self, lhs):
        return any(i.contains_as_lhs(lhs) for i in self.constraints)
    

class InferenceVariable:
    counter = 1
    def __init__(self, id = None, dims = 0):
        self.id = InferenceVariable.counter if not id else id
        if not id:
            InferenceVariable.counter += 1
        self._identifier = f'τ_{self.id}'
        self._supertypes = []
        self._subtypes = []
        self._dims = dims

    def __str__(self):
        return self._identifier + '[]' * self._dims

    def __repr__(self):
        return f'InferenceVariable({repr(self.id)}, {self._dims})'

    def clone(self):
        return InferenceVariable(self.id)

    def __eq__(self, o):
        return isinstance(o, InferenceVariable) and \
                self.id == o.id

    def substitute(self, to_be_replaced, replaced_by):
        pass

class Intersection:
    def __init__(self, *types: Type):
        self._types: List[Type] = []
        for i in types:
            if isinstance(i, Intersection):
                self._types.extend(i._types)
            else:
                self._types.append(i)

class ExtendsConstraint:
    def __init__(self, lhs, rhs):
        self.lhs: Type = lhs
        self.rhs: Intersection = rhs

    def contains_as_lhs(self, lhs):
        return lhs == self.lhs
