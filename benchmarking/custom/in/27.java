public class test27 {
    /*
     * B.a is ArrayList<Integer> but A.m(b.a) is ambiguous
     */
    void main() {
        B b = new B<>();
        b.a = new ArrayList<Integer>();
        ArrayList<Integer> x = b.a;
        int i = A.m(b.a);
    }
}

class A {
    static int m(List<Integer> ls) { return 1; }
    static String m(ArrayList<?> ls) { return "" ; }
}

class List<T> { } 
class ArrayList<T> extends List<T> { }
