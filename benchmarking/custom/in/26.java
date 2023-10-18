public class test26 {
    void main() {
        List<Integer> ls = new List<>();
        List<String> ls2 = new List<>();
        Comparable<? extends Comparable<?>> c = ls.get();
        c = ls2.get();
        Integer i = ls.get();
        String s = ls2.get();
        List<? super String> ls3 = new List<>();
        // error
        s = ls3.get();
    }
}
