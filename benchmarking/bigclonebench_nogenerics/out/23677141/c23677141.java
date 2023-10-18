class c23677141 {

    public static void BubbleSortShort1(short[] num) {
        // set flag to true to begin first pass
        boolean flag = true;
        // holding variable
        short temp;
        while (flag) {
            // set flag to false awaiting a possible swap
            flag = false;
            for (int j = 0; j < num.length - 1; j++) {
                if (// change to > for ascending sort
                num[j] > num[j + 1]) {
                    // swap elements
                    temp = num[j];
                    num[j] = num[j + 1];
                    num[j + 1] = temp;
                    // shows a swap occurred
                    flag = true;
                }
            }
        }
    }
}
