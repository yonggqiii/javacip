class c22026286 {

    public static void main(String[] args) {
        int i, j, l;
        short NUMNUMBERS = (short) 100;
        short[] numbers = new short[NUMNUMBERS];
        JavaCIPUnknownScope.printIntro();
        int time = (int) System.currentTimeMillis();
        for (i = 0; i < NUMNUMBERS; i++) {
            numbers[i] = (short) (NUMNUMBERS - 1 - i);
        }
        for (i = 0; i < NUMNUMBERS; i++) {
            for (j = 0; j < NUMNUMBERS - i - 1; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    short temp = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = temp;
                }
            }
            if (JavaCIPUnknownScope.WANT_PROGRESS) {
                JavaCIPUnknownScope.printProgress();
            }
        }
        time = (int) System.currentTimeMillis() - time;
        System.out.print(time);
        System.out.print("End\n");
    }
}
