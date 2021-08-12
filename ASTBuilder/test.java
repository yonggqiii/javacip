class A<T extends B & C> {
    T t;
}
class B { }
interface C { }
class D extends B implements C { }
class E extends D { }
class Main {
    public static void main(String[] args) {
        A<? extends E> a = new A<>();
        a.t = new E();
    }
}
