public class test30 {

    static void main() {
        List<Cat> ls = new List<>();
        List<Dog> ls2 = new List<>();
        List<Duck> ls3 = new List<>();
        Box<? super SubCat> i = JavaCIPUnknownScope.f(ls);
        Box<? extends SuperDog> ss = JavaCIPUnknownScope.f(ls2);
        Box<Duck> c = JavaCIPUnknownScope.f(ls3);
        Cat cat = new Cat();
        Dog dog = new Dog();
        Duck duck = new Duck();
    }
}

class List<T> {
}

final class Box<T> {
}

class Cat {
}

final class SubCat extends Cat {
}

final class Duck {
}

class SuperDog {
}

final class Dog extends SuperDog {
}
