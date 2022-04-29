import java.util.List;

class A {
  void main() {
    B<Integer> x;
    C<String> y;
    x = y;
    return;
  }

  // <T> T get(List<T> x) {
  // return x.get(0);
  // }

  <T extends List<U>, U> U get(T y) {
    return y.get(0);
  }

}
