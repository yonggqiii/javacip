public class test14 {

    public static void main(String[] args) {
        List<A> ls = new List<>();
        List<B> ls2 = new List<>();
        List<C> ls3 = new List<>();
        ls = JavaCIPUnknownScope.f(ls);
        ls3 = JavaCIPUnknownScope.f(ls2);
        ls2 = JavaCIPUnknownScope.f(ls3);
    }
}

final class List<T> {
}
