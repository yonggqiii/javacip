public class test7 {
    void main() {
        A a;
        B<Integer> b = a;
        Lol<Integer> n = a;
    }
}

public interface Lol<T> {
    void get(T t);
}

public class B<T> {
    void get(T t) {}
}