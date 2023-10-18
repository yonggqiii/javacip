class c16064530 {

    private void bubbleSort() {
        for (int i = 0; i < JavaCIPUnknownScope.testfield.length; i++) {
            for (int j = 0; j < JavaCIPUnknownScope.testfield.length - i - 1; j++) if (JavaCIPUnknownScope.testfield[j] > JavaCIPUnknownScope.testfield[j + 1]) {
                short temp = JavaCIPUnknownScope.testfield[j];
                JavaCIPUnknownScope.testfield[j] = JavaCIPUnknownScope.testfield[j + 1];
                JavaCIPUnknownScope.testfield[j + 1] = temp;
            }
        }
    }
}
