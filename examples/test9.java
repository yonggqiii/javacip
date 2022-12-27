import java.util.List;
public class test9 {
    void main() {
        A<? extends Integer> a;
        A<String> aa;
        A<?> aaa;
        B b;
        int i = b.get(a);
        String s = b.get(aa);
        Object o = b.get(aaa);
    }
}
