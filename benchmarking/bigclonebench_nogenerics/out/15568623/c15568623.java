class c15568623 {

    public Resultado procesar() {
        if (JavaCIPUnknownScope.resultado != null)
            return JavaCIPUnknownScope.resultado;
        int[] a = new int[JavaCIPUnknownScope.elems.size()];
        Iterator iter = JavaCIPUnknownScope.elems.iterator();
        int w = 0;
        while (iter.hasNext()) {
            a[w] = ((Integer) iter.next()).intValue();
            w++;
        }
        int n = a.length;
        long startTime = System.currentTimeMillis();
        int i, j, temp;
        for (i = 0; i < n - 1; i++) {
            for (j = i; j < n - 1; j++) {
                if (a[i] > a[j + 1]) {
                    temp = a[i];
                    a[i] = a[j + 1];
                    a[j + 1] = temp;
                    JavaCIPUnknownScope.pasos++;
                }
            }
        }
        long endTime = System.currentTimeMillis();
        JavaCIPUnknownScope.resultado = new Resultado((int) (endTime - startTime), JavaCIPUnknownScope.pasos, a.length);
        System.out.println("Resultado BB: " + JavaCIPUnknownScope.resultado);
        return JavaCIPUnknownScope.resultado;
    }
}
