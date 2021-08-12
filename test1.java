import java.util.ArrayList;

class A<T extends ArrayList<U>, U extends Comparable<? super U>> {

    T t;
    B b;
    U u;
    int i = b.d = 2;
    Object o = b;
    public static void main(String[] args) {
        A<? extends ArrayList<Integer>, Integer> a = new A<>();
        System.out.println(a.run());
    }

    B run() {
        b.c = "Hello";
        Number n = b.d;
        return b;
    }

}

