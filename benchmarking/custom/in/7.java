interface test7<T> {
    T get(int i);
    void addAll(Object ls);
}

class Main {
    void main() {
        Object ls = "";
        test7<? extends Integer> x;
        A a = new A();
        byte bb = a.a;
        x = a;
        int i = a.get(1);
        a.addAll(ls);
    }
}