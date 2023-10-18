// try preventing JavaCIP from overloading the put and get methods!
// java --jar path/to/javacip -n -o out examples/Example1.java

public class Example2 {
    public static void main(String[] args) {
        Box<Object> b1 = new Box<>();
        Box<String> b2 = new Box<>();
        b1.put("hello, world!");
        b2.put("hello, world!");
        Object o = b1.get();
        String s = b2.get();
    }
}
