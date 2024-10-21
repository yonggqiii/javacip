class c16375558 {

    private void sort() {
        for (int i = 0; i < JavaCIPUnknownScope.density.length; i++) {
            for (int j = JavaCIPUnknownScope.density.length - 2; j >= i; j--) {
                if (JavaCIPUnknownScope.density[j] > JavaCIPUnknownScope.density[j + 1]) {
                    KDNode n = JavaCIPUnknownScope.nonEmptyNodesArray[j];
                    JavaCIPUnknownScope.nonEmptyNodesArray[j] = JavaCIPUnknownScope.nonEmptyNodesArray[j + 1];
                    JavaCIPUnknownScope.nonEmptyNodesArray[j + 1] = n;
                    double d = JavaCIPUnknownScope.density[j];
                    JavaCIPUnknownScope.density[j] = JavaCIPUnknownScope.density[j + 1];
                    JavaCIPUnknownScope.density[j + 1] = d;
                }
            }
        }
    }
}
