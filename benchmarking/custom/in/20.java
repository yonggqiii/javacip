public class test20 {
    /*
     * A<T> must have attribute a of type T, but Double cannot widen to int
     */
    static void main() {
        A<? super Integer> a = new A<>();
        Object o = a.a;
        A<String> x = new A<>();
        String s = x.a;
    
        A<Double> a1 = new A<>();
        a1.a = (double) 2.03;
        int i = a1.a;
    }    
}
