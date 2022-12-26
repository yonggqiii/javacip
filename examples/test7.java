interface test7<T> {
    T get(int i);
    void addAll(test7<? extends T> ls);
}

class Main {
    void main() {
        test7<Integer> ls;
        A a;
        byte bb = a.a;
        ls = a;
        int i = a.get(1);
        a.addAll(ls);
        // test7<?> shit = a.fuck(a.a);
    }
}

// class A implements test7<Integer> {
//     // public Integer get(int i) { return 1; }
//     public void addAll(test7<? extends Integer> ls) { }
// }