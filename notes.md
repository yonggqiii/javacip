1) Generic attributes

Missing<Integer> m;
Object x = m.attr;
m.attr = 1; Object / int


Missing<String> n;
CharSequence y = n.attr;
n.attr = "Testing";   CharSequence / String

==> Missing<Integer>.attr = [int, Object]
==> Missing<String>.attr = [String, CharSequence]

2) Underlying definition
B<Integer, String> <: A<? extends Integer>
B<? extends String, Object> <: C<? super Double>

==> B<T, U> extends A<? extends Integer> implements C<? super Double>
==> B<T, U> extends A<U> implements C<String>

A <: ArrayList<Integer>
A <: List<? extends Integer>

ArrayList<E> implements List<E>

List<Integer>

A extends ArrayList<Integer> implements List<? extends Integer>

A <: B
A <: C

3) ? extends ? super Integer

List<? extends Integer>

class A<T> { B<? extends T> b; }

class B<T> { T t; }

A<? super Integer>

B<? extends ? super Integer>

? extends ? super Integer t;




