public class test15 {
    /*
     * Cyclic inheritance on A and C
     */
    void main() {
        List<? extends A> ls = new List<C>();
        C c = new A();
    }
}
