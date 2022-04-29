import java.util.ArrayList;

class A<T extends U, U extends Comparable<? super U> & Comparable<T>> extends ArrayList<U>
    implements Comparable<D<E, F, G>> {

  T t;
  B b;
  U u;
  int i = b.d = 2;
  Object o = b;
  // public static void main(String[] args) {
  // A<? extends ArrayList<Integer>, Integer> a = new A<>();
  // System.out.println(a.run());
  // b.e = 1;
  // c.e = 1;
  // }

  B run() {
    b.c = "Hello";
    Number n = b.d;
    b.f.g;
    return b;
  }
}
