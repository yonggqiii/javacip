class c5682569 {

    public void metodo1() {
        int temp;
        boolean flagDesordenado = true;
        while (flagDesordenado) {
            flagDesordenado = false;
            for (int i = 0; i < JavaCIPUnknownScope.tamanoTabla - 1; i++) {
                if (JavaCIPUnknownScope.tabla[i] > JavaCIPUnknownScope.tabla[i + 1]) {
                    flagDesordenado = true;
                    temp = JavaCIPUnknownScope.tabla[i];
                    JavaCIPUnknownScope.tabla[i] = JavaCIPUnknownScope.tabla[i + 1];
                    JavaCIPUnknownScope.tabla[i + 1] = temp;
                }
            }
        }
    }
}
