import java.util.*;

public class test2 {
  public static void main(String[] args) {
    // 1
    X<Integer> x;
    Y<? extends Number> xx = x;
    // 2
    X<String> xxx;
    Z<? extends A<? extends Comparable<String>>> z = xxx;
    // 3
    X<? extends Number> xxxx;
    Z<? extends B<Object>> zz = xxxx;
    // 4
    X<Object> a;
    Y<? super D> y = a;
    // 5
    Y<D> afe;
    Z<? extends A<? extends A<Object>>> aasdf = afe;
    // 6
    Y<A<List<Integer>>> g;
    Z<? extends A<? super E<Integer>>> k = g;
    // 7
    E<Integer> t;
    A<? super ArrayList<Integer>> h = t;
    D r2;
    A<Object> r3 = r2;
  }
}
