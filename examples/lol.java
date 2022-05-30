import java.util.List;

interface Xi<T, U> extends A<T>, B<U> {
}

interface Y<T> extends Z<Xi<T, Object>> {
}

interface A<T> {

}

interface B<T> {
}

interface D extends A<Object> {
}

interface Z<T> {
}

interface E<T> extends A<List<T>> {
}

interface X<T> extends Y<T> {
}
