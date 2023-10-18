class A {

        void main() {
                B<Integer, Integer, Integer> b = new B<>();
                b.x = b.x.a;
                b.x.a = b.x;
                int y = b.x.t;
                b.x.t = y;
                y = b.x.a.t;
                b.x.a.t = y;

                B<Object, String, Cat> b2 = new B<>();
                Object o = b2.x.t;
                b2.x.t = o;
                String s = b2.x.a.t;
                b2.x.a.t = s;
                Cat c = b2.x.a.a.t;
                b2.x.a.a.t = c;
        }
}