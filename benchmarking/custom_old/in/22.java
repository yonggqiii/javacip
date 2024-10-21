public class test22 {
    public static void main(String[] args) {
        // B<T> -> A<X<T>>
        A<X<Cat>> axc = new A<>();
        A<X<Dog>> axd = new A<>();
        B<Cat> bc = new B<>();
        B<Dog> bd = new B<>();
        axc = bc;
        axd = bd;

        // C<T> -> B<Y<T>>
        B<Y<Cat>> byc = new B<>();
        B<Y<Dog>> byd = new B<>();
        C<Cat> cc = new C<>();
        C<Dog> cd = new C<>();
        byc = cc;
        byd = cd;

        // A<T>.f() -> List<T>
        List<X<Cat>> lxc = axc.f();
        List<X<Dog>> lxd = axd.f();

        // C<T>.f() -> List<Y<X<T>> causes error
        List<Y<X<Cat>>> ls = cc.f();
    }
}

final class List<T> {}
