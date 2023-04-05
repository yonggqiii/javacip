public class test14 {
    public static void main(String[] args) {
        List<A> ls = new List<>();
        List<B> ls2 = new List<>();
        List<C> ls3 = new List<>();
        ls = f(ls);
        ls3 = f(ls2);
        ls2 = f(ls3);
    }
}
final class List<T> { }