interface I<U, V> {

    public <X> Object m(X a, U b, V c);
}

class Test4 {

    void main() {
        A<Dog, String> a = new A<>();
        a = new B<String>();
        I<Integer, Dog> i = new B<Integer>();
    }
}
