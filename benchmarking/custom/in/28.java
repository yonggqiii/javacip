public class test28 {
    // C cannot extends both B and D
    void main() {
        A<? extends B> ab = new A<C>();
        A<? extends D> ad = new A<C>();
        B b = new B();
        D d = new D();
    }
}

class B { }
class D { }
