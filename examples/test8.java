public interface test8 {
    int addAll(B<? extends String> i);
    void get(B<Integer> i);
}

class Main {
    A a;
    test8 b = a;
}
