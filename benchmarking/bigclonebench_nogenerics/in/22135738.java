


class c22135738 {

    protected void sort(double[] a) throws RuntimeException {
        for (int i = a.length - 1; i >= 0; i--) {
            boolean swapped = false;
            for (int j = 0; j < i; j++) {
                if (a[j] > a[j + 1]) {
                    double d = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = d;
                    swapped = true;
                }
            }
            if (!swapped) return;
        }
    }

}
