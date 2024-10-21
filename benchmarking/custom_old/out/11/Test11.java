public class Test11 {

    static void main() {
        A<? super B> i = new A<>();
        A<? extends C> j = new A<>();
        i.t = j.t;
    }

    <T extends D<B> & C> B m(T t) {
        return t.a.b;
    }
}
