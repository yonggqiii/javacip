public class test24 {
    static void main() {
        List<Cat> ls = new List<>();
        List<Dog> ls2 = new List<>();
        List<Duck> ls3 = new List<>();
        // same erasures, different inputs. requires no overloading
        // for significant speedup
        Integer i = f(ls);
        String s = f(ls2);
        Cat c = f(ls3);
    }
}
class List<T> { }
