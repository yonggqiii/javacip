import java.util.ArrayList;
import java.util.List;

class Whatever {
        void main() {
                C<Integer, Integer> asdf;
                D<Integer> c;
                A<Integer> a = c;
                B<Integer> b = c;
                a = asdf.x;
                b = asdf.x;
                asdf.x = c;
                D<String> d;
                A<? super String> x = d;
                B<String> y = d;
                C<String, String> cccc;
                x = cccc.x;
                y = cccc.x;
                cccc.x = d;

                C<List<Object>, Integer> rr;
                E<Object, Integer> e;
                b = e;
                A<? super ArrayList<Object>> aa = e;
                b = rr.x;
                aa = rr.x;
                rr.x = e;

                C<List<String>, Object> gg;
                E<String, Object> ee;
                A<List<String>> aaa = ee;
                B<Object> bb = ee;
                aaa = gg.x;
                bb = gg.x;
                gg.x = ee;
        }
}
