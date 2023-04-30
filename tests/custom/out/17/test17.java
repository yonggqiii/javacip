public class test17 {

    void main() {
        List<? extends Integer> t = new List<Integer>();
        List<String> ls = new List<String>();
        A<? extends Number> a = new A<Integer>();
        A<String> b = new A<String>();
        Number n = a.m(t);
        String s = a.m(ls);
    }
}
