public class B<T> extends A<T> {
    /*
     * D extends C but cannot override int get() with String get()
     */
    void main() {
        A<? extends C> a = new A<>();
        B<D> b = new B<>();
        a = b;
        C c = new C();
        D d = new D();
        Integer i = c.get();
        String s = d.get();
    }
}
