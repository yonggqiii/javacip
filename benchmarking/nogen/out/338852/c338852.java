class c338852 {

    public static void sort(float[] norm_abst) {
        float temp;
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (norm_abst[j] > norm_abst[j + 1]) {
                    temp = norm_abst[j];
                    norm_abst[j] = norm_abst[j + 1];
                    norm_abst[j + 1] = temp;
                }
            }
        }
        JavaCIPUnknownScope.printFixed(norm_abst[0]);
        JavaCIPUnknownScope.print(" ");
        JavaCIPUnknownScope.printFixed(norm_abst[1]);
        JavaCIPUnknownScope.print(" ");
        JavaCIPUnknownScope.printFixed(norm_abst[2]);
        JavaCIPUnknownScope.print(" ");
        JavaCIPUnknownScope.printFixed(norm_abst[3]);
        JavaCIPUnknownScope.print(" ");
        JavaCIPUnknownScope.printFixed(norm_abst[4]);
        JavaCIPUnknownScope.print(" ");
        JavaCIPUnknownScope.printFixed(norm_abst[5]);
        JavaCIPUnknownScope.print(" ");
        JavaCIPUnknownScope.printFixed(norm_abst[6]);
        JavaCIPUnknownScope.print(" ");
        JavaCIPUnknownScope.printFixed(norm_abst[7]);
        JavaCIPUnknownScope.print("\n");
    }
}
