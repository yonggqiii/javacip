class c878123 {

    void sort(int[] a) throws RuntimeException {
        int j;
        int limit = a.length;
        int st = -1;
        while (st < limit) {
            boolean flipped = false;
            st++;
            limit--;
            for (j = st; j < limit; j++) {
                if (JavaCIPUnknownScope.stopRequested) {
                    return;
                }
                if (a[j] > a[j + 1]) {
                    int T = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = T;
                    flipped = true;
                    JavaCIPUnknownScope.pause(st, limit);
                }
            }
            if (!flipped) {
                return;
            }
            for (j = limit; --j >= st; ) {
                if (JavaCIPUnknownScope.stopRequested) {
                    return;
                }
                if (a[j] > a[j + 1]) {
                    int T = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = T;
                    flipped = true;
                    JavaCIPUnknownScope.pause(st, limit);
                }
            }
            if (!flipped) {
                return;
            }
        }
        JavaCIPUnknownScope.pause(st, limit);
    }
}
