class Test {
  void main() {
    A a = new A();
    B<? extends C> b1 = new B<>();
    B<? extends D> b2 = new B<>();
    C c = a.id((C) a.extract(b1));
    D d = a.id((D) a.extract(b2));
  }
}
class B<T> { }
class C { }
class D { }
