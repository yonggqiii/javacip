import java.util.*;

public class test2 {
        public static final int mycoolAttr = 1;

        public static void main(String[] args) {
                // 1
                X<Integer> x = new X<>(1);
                Y<? extends Number> xx = x;
                // 2
                X<String> xxx = new X<>("abc");
                Z<? extends A<? extends Comparable<String>>> z = xxx;
                // 3
                X<? extends Number> xxxx = x;
                Z<? extends B<Object>> zz = xxxx;
                // 4
                X<Object> a = new X<>(2);
                Y<? super D> y = a;
                // 5
                Y<D> afe = new Y<>();
                Z<? extends A<? extends A<Object>>> aasdf = afe;
                // 6
                Y<A<List<Integer>>> g = new Y<>();
                Z<? extends A<? super E<Integer>>> k = g;
                // 7
                E<Integer> t = new E<>("abc", 2, "def");
                A<? super ArrayList<Integer>> h = t;
                D r2 = new D(t);
                A<Object> r3 = r2;
        }
}
