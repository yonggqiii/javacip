public class test19 {
    /*
     * A<T> must contain attribute A<T> a; for A<? extends Integer> x, x.a = x.a is ill-typed
     * due to capture conversion
     */
    public static void main() {
        A<Integer> a0 = new A<>();
        a0.a = a0.a;
        a0.a = a0;
        a0 = a0.a;
        A<String> a1 = new A<>();
        a1.a = a1.a;
        a1 = a1.a;
        a1.a = a1;

        A<? extends Integer> a2 = new A<>();
        a2.a = a2.a;
    }
}
