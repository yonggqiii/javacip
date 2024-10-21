public class test25 {
    void main() {
        List<? extends B> ls = new List<>();
        x = ls;
        ls = x;
        // nothing could possibly be passed in to get
        x.get(y);
    }

}

final class List<T> { 
    T get (List<? extends T> ls) { return null; }
}
