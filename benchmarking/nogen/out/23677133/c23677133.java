class c23677133 {

    public static void BubbleSortInt1(int[] num) {
        // set flag to true to begin first pass
        boolean flag = true;
        // holding variable
        int temp;
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
