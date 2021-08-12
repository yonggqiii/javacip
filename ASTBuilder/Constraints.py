class SubtypeConstraint:

    def __init__(self, lhs, rhs):
        self.lhs = lhs
        self.rhs = rhs

    def __str__(self):
        return f'{self.lhs} <: {self.rhs}'

    def __repr__(self):
        return f'SubtypeConstraint({repr(self.lhs)}, {repr(self.rhs)})'

    def clone(self):
        return SubtypeConstraint(self.lhs.clone(), self.rhs.clone())

    def substitute(self, to_be_replaced, replaced_by):
        if self.lhs == to_be_replaced:
            self.lhs = replaced_by
        else:
            self.lhs.substitute(to_be_replaced, replaced_by)
        if self.rhs == to_be_replaced:
            self.rhs = replaced_by
        else:
            self.rhs.substitute(to_be_replaced, replaced_by)

    def trivially_true(self, dt, mt):
        from .JavaTypes import CompileTimeType, Capture
        if isinstance(self.lhs, InferenceVariable) or\
                isinstance(self.rhs, InferenceVariable):
            return self.lhs == self.rhs
        # rhs is capture
        if isinstance(self.rhs, Capture):
            # rhs is ? or ? extends
            if self.rhs._type == Capture.WILDCARD or self.rhs._type == Capture.EXTENDS:
                return False
            return SubtypeConstraint(self.lhs.clone(), self.rhs._base.clone()) \
                    .trivially_true(dt, mt)

        # rhs Object lmao
        if self.rhs._identifier == 'Object':
            return True

        # lhs is capture
        if isinstance(self.lhs, Capture):
            # lhs is ? or ? super
            if self.lhs._type == Capture.WILDCARD or self.lhs._type == Capture.SUPER:
                return False
            return SubtypeConstraint(self.lhs._base.clone(), self.rhs.clone()) \
                    .trivially_true(dt, mt)
       
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
            if len(lhs_cap) != len(rhs_cap):
                raise Exception(f'{self.lhs} and {self.rhs} have different'
                        'number of captures!')
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
    
    def contains_as_lhs(self, lhs):
        return self.lhs == lhs
   
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
    def __init__(self, id = None):
        self.id = InferenceVariable.counter if not id else id
        if not id:
            InferenceVariable.counter += 1
        self._identifier = f'τ_{self.id}'
    def __str__(self):
        return self._identifier

    def __repr__(self):
        return f'InferenceVariable({repr(self.id)})'

    def clone(self):
        return InferenceVariable(self.id)

    def __eq__(self, o):
        return isinstance(o, InferenceVariable) and \
                self.id == o.id

    def substitute(self, to_be_replaced, replaced_by):
        pass

