class c529986 {

    void bsort(int[] a, int lo, int hi) throws RuntimeException {
        for (int j = hi; j > lo; j--) {
            for (int i = lo; i < j; i++) {
                if (a[i] > a[i + 1]) {
                    int T = a[i];
                    a[i] = a[i + 1];
                    a[i + 1] = T;
                    JavaCIPUnknownScope.pause();
                }
            }
        }
    }
}
