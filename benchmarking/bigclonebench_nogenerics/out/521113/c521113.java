class c521113 {

    int[] slowSort() {
        int[] values = JavaCIPUnknownScope.getValues();
        int n = values.length;
        for (int pass = 1; pass < n; pass++) {
            for (int i = 0; i < n - pass; i++) {
                if (values[i] > values[i + 1]) {
                    int temp = values[i];
                    values[i] = values[i + 1];
                    values[i + 1] = temp;
                }
            }
        }
        return values;
    }
}
