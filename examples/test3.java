import java.util.List;

class A<T, U, V> extends C<T, U, B<String>> {
  B<Integer> x;

  int main() {
    return x.y;
  }
}
