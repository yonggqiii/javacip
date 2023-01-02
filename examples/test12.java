public interface test12 {
    <T extends B<T>> T get(T t);
}

class Main {
    A a;
    test12 x = a;
    String s = a.a;
}
