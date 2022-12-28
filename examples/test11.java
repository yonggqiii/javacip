import java.util.*;

class Main {
    <T> T get (List<? super T> ls1, List<? super T> ls2) { return null; }
    void main() {
        List<A> ls;
        List<B> ls2;
        A o = get(ls, ls2).x;
    }
}

