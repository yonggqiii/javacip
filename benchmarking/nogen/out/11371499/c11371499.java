class c11371499 {

    public void sortArray() {
        int a;
        for (int i = 0; i < JavaCIPUnknownScope.array.length; i++) {
            for (int j = 0; j < JavaCIPUnknownScope.array.length - 1; j++) {
                if (JavaCIPUnknownScope.array[j] < JavaCIPUnknownScope.array[j + 1]) {
                    a = JavaCIPUnknownScope.array[j];
                    JavaCIPUnknownScope.array[j] = JavaCIPUnknownScope.array[j + 1];
                    JavaCIPUnknownScope.array[j + 1] = a;
                }
            }
        }
    }
}
