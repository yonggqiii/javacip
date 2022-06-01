import java.util.ArrayList;

class test1 {
  void main() {
    A a;
    D<Integer> d;
    E<Cat> e;
    a.x = d;
    a.x = e;
    B b;
    D<String> d2;
    E<Dog> e2;
    b.x = d2;
    b.x = e2;
    // int i = a.x.get();
    // String s = b.x.get();
  }
}
