class c9385633 {

    protected void setRankOrder() {
        JavaCIPUnknownScope.rankOrder = new int[JavaCIPUnknownScope.values.length];
        for (int i = 0; i < JavaCIPUnknownScope.rankOrder.length; i++) {
            JavaCIPUnknownScope.rankOrder[i] = i;
            assert (!Double.isNaN(JavaCIPUnknownScope.values[i]));
        }
        for (int i = JavaCIPUnknownScope.rankOrder.length - 1; i >= 0; i--) {
            boolean swapped = false;
            for (int j = 0; j < i; j++) if (JavaCIPUnknownScope.values[JavaCIPUnknownScope.rankOrder[j]] < JavaCIPUnknownScope.values[JavaCIPUnknownScope.rankOrder[j + 1]]) {
                int r = JavaCIPUnknownScope.rankOrder[j];
                JavaCIPUnknownScope.rankOrder[j] = JavaCIPUnknownScope.rankOrder[j + 1];
                JavaCIPUnknownScope.rankOrder[j + 1] = r;
            }
        }
    }
}
