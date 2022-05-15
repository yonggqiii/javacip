import java.util.List;
import java.util.ArrayList;

class A {

  <T extends U & D, U> U main(List<T> x, int y) {
    B a;
    D d;
    a = d;
    List<? extends Number> i;
    ArrayList<Integer> j;
    i = j;
    return x.get(y);
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
