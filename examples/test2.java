import java.util.*;

public class test2 {
    public static void main(String[] args) {
        A<? super Integer> b = new A<>();
        B<? super ArrayList<Number>> a = b;
        B<? extends Collection<String>> c = new A<String>();
        A<? super C> d = new A<>();
        B<List<? super C>> e = d;
        B<?> f = new A<String>();
    }
}
