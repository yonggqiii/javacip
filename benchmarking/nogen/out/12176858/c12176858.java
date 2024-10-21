class c12176858 {

    void sortclasses() {
        int i, j;
        boolean domore;
        JavaCIPUnknownScope.vclassptr = new int[JavaCIPUnknownScope.numc];
        for (i = 0; i < JavaCIPUnknownScope.numc; i++) JavaCIPUnknownScope.vclassptr[i] = i;
        domore = true;
        while (domore == true) {
            domore = false;
            for (i = 0; i < JavaCIPUnknownScope.numc - 1; i++) {
                if (JavaCIPUnknownScope.vclassctr[JavaCIPUnknownScope.vclassptr[i]] < JavaCIPUnknownScope.vclassctr[JavaCIPUnknownScope.vclassptr[i + 1]]) {
                    int temp = JavaCIPUnknownScope.vclassptr[i];
                    JavaCIPUnknownScope.vclassptr[i] = JavaCIPUnknownScope.vclassptr[i + 1];
                    JavaCIPUnknownScope.vclassptr[i + 1] = temp;
                    domore = true;
                }
            }
        }
    }
}
