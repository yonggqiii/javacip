class c10606028 {

    public void bubble() {
        boolean test = false;
        int kars = 0, tas = 0;
        while (true) {
            for (int j = 0; j < JavaCIPUnknownScope.dizi.length - 1; j++) {
                kars++;
                if (JavaCIPUnknownScope.dizi[j] > JavaCIPUnknownScope.dizi[j + 1]) {
                    int temp = JavaCIPUnknownScope.dizi[j];
                    JavaCIPUnknownScope.dizi[j] = JavaCIPUnknownScope.dizi[j + 1];
                    JavaCIPUnknownScope.dizi[j + 1] = temp;
                    test = true;
                    tas++;
                }
            }
            if (!test) {
                break;
            } else {
                test = false;
            }
        }
        System.out.print(kars + " " + tas);
    }
}
