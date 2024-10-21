public class test16 {
    public static void main(String[] args) {
        List<A> ls = new List<>();
        List<B> ls2 = new List<>();
        List<C> ls3 = new List<>();
        ls = f(ls);
        ls2 = f(ls2);
        ls3 = f(ls3);
    }
}
final class List<T> { }