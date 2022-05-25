import java.util.ArrayList;
import java.util.List;
class Whatever {
  void main() {

    D<? extends Integer> d = new D<>();
    B<List<Integer>> b;
    b = d;
    A<String> a;
    B<List<String>> b2;
    b2 = a;
    D<Object> dd;
    A<Object> aa;
    dd = aa;

  }
}

