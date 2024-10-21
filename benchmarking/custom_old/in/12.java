public class A extends C {
    void main() {
        B b = new B();
        System.out.println(this.m(b.a));
    }
    int m(String s) { return 1; }
}
