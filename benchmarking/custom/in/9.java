public class test9 {
     void main() {
         A<? extends Integer> a = new A<>();
         A<String> aa = new A<>();
         A<?> aaa = new A<>();
         B b = new B();
         Integer i = b.get(a);
         String s = b.get(aa);
         Object o = b.get(aaa);
     }
 }
