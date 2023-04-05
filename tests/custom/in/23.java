public class test23 {
    public static void main(String[] args) {
        // A<T>.f() -> T
        A<Integer> a = new A<>();
        A<String> as = new A<>();
        Integer i = a.f();
        String s = as.f();

        // B<T>.f() -> Integer
        B<Integer> bi = new C<>();
        B<String> bs = new C<>();
        i = bi.f();
        i = bs.f();

        // D<T> extends A<T> and B<T> has errors since there are overriding methods
        // with unsubstitutable return types
        D<Integer> d = new D<>();
        D<String> ds = new D<>();
        a = d;
        as = ds;
        bi = d;
        bs = ds;

    }    
}
