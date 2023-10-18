public class test21 {
    public static void main() {
        // int[] does not extend Object[]
        List<? extends Object[]> ls = new List<>();
        List<int[]> ls2 = new List<>();
        ls = ls2;
    }
}
