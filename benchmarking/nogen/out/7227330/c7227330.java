class c7227330 {

    private void sort() {
        boolean unsortiert = true;
        Datei tmp = null;
        while (unsortiert) {
            unsortiert = false;
            for (int i = 0; i < JavaCIPUnknownScope.size - 1; i++) {
                if (JavaCIPUnknownScope.dateien[i] != null && JavaCIPUnknownScope.dateien[i + 1] != null) {
                    if (JavaCIPUnknownScope.dateien[i].compareTo(JavaCIPUnknownScope.dateien[i + 1]) < 0) {
                        tmp = JavaCIPUnknownScope.dateien[i];
                        JavaCIPUnknownScope.dateien[i] = JavaCIPUnknownScope.dateien[i + 1];
                        JavaCIPUnknownScope.dateien[i + 1] = tmp;
                        unsortiert = true;
                    }
                }
            }
        }
    }
}
