import java.util.ArrayList;
import java.util.List;
class A<T> extends D<T> {
  void main() {
    D<Integer> d;
    B<List<Integer>> b;
    b = d;
    A<String> a;
    B<List<String>> b2;
    b2 = a;
  }
}
