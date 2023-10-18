class test3 {

    void main() {
        List<Integer> ls = new ArrayList<>();
        List<String> ls2 = new ArrayList<>();
        C<Integer, Integer> asdf = new C<>(1, 2);
        D<Integer> c = new D<>(ls);
        A<Integer> a = c;
        B<Integer> b = c;
        a = asdf.x;
        b = asdf.x;
        asdf.x = c;
        D<String> d = new D<>(ls2);
        A<? super String> x = d;
        B<String> y = d;
        C<String, String> cccc = new C<>();
        x = cccc.x;
        y = cccc.x;
        cccc.x = d;
        C<List<Object>, Integer> rr = new C<>();
        E<Object, Integer> e = new E<>(a);
        b = e;
        A<? super ArrayList<Object>> aa = e;
        b = rr.x;
        aa = rr.x;
        rr.x = e;
        C<List<String>, Object> gg = new C<>();
        E<String, Object> ee = new E<>();
        A<List<String>> aaa = ee;
        B<Object> bb = ee;
        aaa = gg.x;
        bb = gg.x;
        gg.x = ee;
    }
}
