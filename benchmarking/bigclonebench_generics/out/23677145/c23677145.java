class c23677145<T extends Comparable<T>> {

    public static void BubbleSortComparable1(T[] num) {
        int j;
        // set flag to true to begin first pass
        boolean flag = true;
        // holding variable
        T temp;
        while (flag) {
            // set flag to false awaiting a possible swap
            flag = false;
            for (j = 0; j < num.length - 1; j++) {
                if (// change to > for ascending sort
                num[j].compareTo(num[j + 1]) > 0) {
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
