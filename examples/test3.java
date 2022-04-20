import java.util.List;

class A<T, U, V> extends C<T, U, B> {
  B x;

  T main() {
    C<Integer, String, List<Object>> y;
    x = y;
    // return get(1);
  }
}
