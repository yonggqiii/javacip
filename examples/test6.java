class Test extends A {
  public static int stuff(String s) { return 1; }
  public static <T extends Integer> Number a(T t) { return t; }
  public static void main(String[] args) { String s = b.get("hello"); }
}

// class A {
//   static A b;
//   String get(String s) { return s; }
// }