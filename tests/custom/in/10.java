public class test10 {
    void main() {
        Box<Integer> b = new Box<>();
        Box<String> b2 = new Box<>();
        Box<Dog> b3 = new Box<>();
        Wrap<Integer> o1 = f(b);
        Wrap<String> o1 = f(b2);
        Wrap<Dog> o1 = f(b3);
    }    
}

class Box<T> { }
class Wrap<T> { }
