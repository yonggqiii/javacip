class test4 extends D {
    void main() {
        I i;
        S s;
        x = i;
        x = s;
        i = x.get();
        s = x.get();
    }
}

// interface C<T extends C<T>> {
//     T get();
// }

// class I extends C<I> {
//     I get() {
//         return null;
//     }
// }

// class S extends C<S> {
//     S get() {
//         return null;
//     }
// }
