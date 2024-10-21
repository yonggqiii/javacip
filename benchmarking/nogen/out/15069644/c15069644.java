class c15069644 {

    public String elementsSearch() {
        int index = 0;
        for (int i1 = 0; i1 < 6; i1++) {
            for (int i2 = 0; i2 < 5; i2++) {
                if (index < 5) {
                    if (JavaCIPUnknownScope.initialMatrix[i1][i2] > 0) {
                        JavaCIPUnknownScope.finalMatrix[index] = JavaCIPUnknownScope.initialMatrix[i1][i2];
                        index++;
                    }
                } else
                    break;
            }
        }
        int temp;
        for (int i = 0; i < JavaCIPUnknownScope.finalMatrix.length; i++) {
            for (int j = 0; j < JavaCIPUnknownScope.finalMatrix.length - 1; j++) {
                if (JavaCIPUnknownScope.finalMatrix[j] < JavaCIPUnknownScope.finalMatrix[j + 1]) {
                    temp = JavaCIPUnknownScope.finalMatrix[j];
                    JavaCIPUnknownScope.finalMatrix[j] = JavaCIPUnknownScope.finalMatrix[j + 1];
                    JavaCIPUnknownScope.finalMatrix[j + 1] = temp;
                }
            }
        }
        String result = "";
        for (int k : JavaCIPUnknownScope.finalMatrix) result += k + " ";
        return result;
    }
}
