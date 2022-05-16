import java.util.List;
import java.util.ArrayList;

class A {

  int x;
  double y;

  void main() {
    B<Integer> b;
    C<Integer> c;
    b = c;
    B<List<String>> d;
    C<List<String>> e;
    d = e;
    List<? extends Number> l;
    ArrayList<Integer> al;
    l = al;
  }

  // void main() {
  // B<Integer> x;
  // C<String> y;
  // x = y;
  // return;
  // }

  // <T extends List<U>, U> U main(T y, int z) {
  // return y.get(z);
  // }

}
