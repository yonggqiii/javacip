import java.util.List;

// class A<T, U, V> extends C<T, U, B> {
//   B x;

//   T main() {
//     C<Integer, String, List<Object>> y;
//     x = y;
//     // return get(1);
//   }
// }

class B<T> {
  T y;

  String get(T x) {
    return 1;
  }
}

class A extends C {
  String main(B<Integer> x) {
    return x.get(y);
  }
}
