from ASTBuilder.Constraints import DisjunctiveConstraint, SubtypeConstraint, \
    Substitution, InferenceVariable, ConjunctiveConstraint
from ASTBuilder.JavaTypes import JavaClassDeclaration, CompileTimeType

def inference(td, mt, w):
    # td: type declarations
    # mt: missing types
    # w: worklist
    # returns: mt, satisfiable
    
    # Handle non-disjunctive assertions first.
    mt_new, w, satisfiable = resolve(td, mt, w)
    print('Resolution resulted in')
    for v in mt_new.values():
        print(v)
        print()

    for i in w:
        print(i)

    print()
    if not satisfiable:
        return {}, False
    mt = mt_new
    # Find a disjunctive assertion, if any.
    for i in range(len(w)):
        if isinstance(w[i], DisjunctiveConstraint):
            w_temp = [a.clone() for a in w]
            disjunction = w_temp.pop(i)
            for ai in disjunction.constraints:
                print(f'From {disjunction}, {ai} was chosen.')
                w_new = w_temp + [ai]
                mt_new, satisfiable = inference(td, mt, w_new)
                if satisfiable:
                    return mt_new, satisfiable
            return {}, False

    return combineMethods(mt_new)

def resolve(td, mt, w):
    # clone mt and w.
    mt = {k:v.clone() for k, v in mt.items()}
    w = [i.clone() for i in w]
    disjunctions = [i for i in w if isinstance(i, DisjunctiveConstraint) and
            not i.trivially_true(td, mt)]
    to_do = [i for i in w if isinstance(i, SubtypeConstraint) and
            not i.trivially_true(td, mt)]


    unprocessed = [] 
    while to_do:
        # Try all the substitutions first
        substitutions = [i for i in to_do if isinstance(i, Substitution)]
        to_do = [i for i in to_do if not isinstance(i, Substitution)]
        while substitutions:
            i = substitutions.pop(0)
            print(f'Performing substitution {i}')
            if i.a == i.b:
                # Nothing to do
                continue
            if isinstance(i.a, InferenceVariable):
                to_be_replaced = i.a
                replaced_by = i.b
            elif isinstance(i.b, InferenceVariable):
                to_be_replaced = i.b
                replaced_by = i.a
            else:
                # Substitution cannot happen. Fail.
                return {}, [], False
            for d in disjunctions:
                d.substitute(to_be_replaced, replaced_by)
            for t in to_do:
                t.substitute(to_be_replaced, replaced_by)
            for m in mt.values():
                m.substitute(to_be_replaced, replaced_by)
            for j in substitutions:
                j.substitute(to_be_replaced, replaced_by)
        
        if not to_do:
            break
        # combining assertions
        target_lhs = to_do[0].lhs
        target = [i for i in to_do if i.lhs == target_lhs]
        to_do = [i for i in to_do if i.lhs != target_lhs]

        # Should not combine until all assertions regarding that type
        # have been selected from disjunctions
        if any(i.contains_as_lhs(target_lhs) for i in disjunctions):
            print(f'Skipping {target_lhs}...')
            unprocessed.extend(target)
            continue

        # Combine assertions in target
        # Remove trivial combinations, i.e. if C <: A and C <: B but
        # we know A <: B, then just remove C <: B.
        to_remove = []
        for i in range(len(target)):
            for j in range(len(target)):
                if i == j:
                    continue
                if target[i].rhs != target[j].rhs and \
                        SubtypeConstraint(target[i].rhs,
                        target[j].rhs).trivially_true(td, mt):
                    print(f'Because '\
                        f'{SubtypeConstraint(target[i].rhs,target[j].rhs)}'\
                        f' is obviously true, {target[j]} will be removed.')
                    to_remove.append(target[j])
        for i in to_remove:
            target.remove(i)

        # At this point, target contains assertions
        # we must combine nontrivially.
        # Combine two at a time.
        if len(target) >= 2:
            unprocessed.extend(target[2:])
            target = target[:2]
            disjunctions.append(combine_two_subtype_constraints(target, td, mt))
            print(f'Combining {target[0]} and {target[1]} results in'
                    f'{unprocessed[-1]}')
        else:
            target = target[0]
            # TODO: Handle the case where lhs or rhs is a capture
            # First try lhs = rhs
            first = Substitution(target.lhs, target.rhs)
            #TODO: Handle the case where lhs is an inference variable. i.e.
            # Check if the substitution is even possible.
            if isinstance(target.lhs, InferenceVariable) or \
                    isinstance(target.rhs, InferenceVariable):
                print(f'Processing {target} results in {first}')
                to_do.append(first)
                continue
            # Then look for supertypes of lhs
            if target.lhs.is_primitive():
                lhs_d = td[CompileTimeType.PRIMITIVES[target.lhs._identifier]]
            elif target.lhs._identifier in td:
                lhs_d = td[target.lhs._identifier].clone()
            else:
                lhs_d = mt[target.lhs._identifier].clone()
            if target.lhs._captures:
                lhs_d.substitute_type_params(target.lhs._captures)
            supertypes = []
            if isinstance(lhs_d, JavaClassDeclaration):
                if lhs_d._superclass:
                    supertypes.append(lhs_d._superclass)
            supertypes.extend(lhs_d._superinterfaces)

            # One of the supertypes must be a subtype of rhs.
            second = DisjunctiveConstraint(
                    *(SubtypeConstraint(i, target.rhs)
                    for i in supertypes))
            print(f'Processing {target} results in {first} or {second}')
            disjunctions.append(DisjunctiveConstraint(first, second))
    return mt, unprocessed + disjunctions, True
def combine_two_subtype_constraints(constraints, td, mt):
    # constraint in the form A <: B and A <: C.
    a = constraints[0].lhs
    b = constraints[0].rhs
    c = constraints[1].rhs
    # First, assume A <: B and B <: C
    first = ConjunctiveConstraint(SubtypeConstraint(a.clone(), b.clone()),
            SubtypeConstraint(b.clone(), c.clone()))

    
    # Next, assume A <: C and C <: B
    second = ConjunctiveConstraint(SubtypeConstraint(a.clone(), c.clone()),
            SubtypeConstraint(c.clone(), b.clone()))

    # Third, fail if b and c are both classes
    if b._identifier in td:
        b_def = td[b._identifier]
    else:
        b_def = mt[b._identifier]
    if c._identifier in td:
        c_def = td[c._identifier]
    else:
        c_def = mt[c._identifier]
    if b_def._type == 'class' and c_def._type == 'class':
        return DisjunctiveConstraint(first, second)

    # Fourth, a extends one supertype t where t is a subtype of
    # b and c
    #TODO
    return DisjunctiveConstraint(first, second)

def combineMethods(mt):
    return mt, True
