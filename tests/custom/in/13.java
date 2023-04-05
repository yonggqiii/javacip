public class test13 {
    /*
     * Nothing can extend Cat or Dog
     */
    void main() {
        A a = new A();
        List<? extends Cat> c = a.f();
        List<? extends Dog> d = a.f();
    }
}

final class Cat { }
final class Dog { }
