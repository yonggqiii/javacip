public class test18 {
    /*
     * B must be interface so cannot have instance-level attributes
     */
    void main() {
        B b = new A();
        int i = b.a;
    }    
    <T extends A & B> T f(T t) { return t; }
}
