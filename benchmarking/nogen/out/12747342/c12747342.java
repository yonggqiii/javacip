class c12747342 {

    public void bubbleSort(final int[] s) {
        JavaCIPUnknownScope.source = s;
        if (JavaCIPUnknownScope.source.length < 2)
            return;
        boolean go = true;
        while (go) {
            go = false;
            for (int i = 0; i < JavaCIPUnknownScope.source.length - 1; i++) {
                int temp = JavaCIPUnknownScope.source[i];
                if (temp > JavaCIPUnknownScope.source[i + 1]) {
                    JavaCIPUnknownScope.source[i] = JavaCIPUnknownScope.source[i + 1];
                    JavaCIPUnknownScope.source[i + 1] = temp;
                    go = true;
                }
            }
        }
    }
}
