public class Test6 {

    void main() {
        A<? extends C> a = new A<>();
        B<D> b = new B<>();
        a = b;
        C c = new C();
        D d = new D();
        List<? super Integer> i = c.get();
        List<? super String> s = c.get();
    }
}
