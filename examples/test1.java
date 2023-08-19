class B {
  <T extends Number> T methodB(List<T> ls) {
    A a = new A();
    T t = ls.get(0);
    t = a.methodA(t);
    return t;
  }
}
