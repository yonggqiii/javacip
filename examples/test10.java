import java.util.*;
class B<T> {
    List<T> t;
    void main() {
        B<? super Integer> b;
        b.t = b.t;
    }
}
