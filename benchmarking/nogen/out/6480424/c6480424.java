class c6480424 {

    public void method31() {
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < JavaCIPUnknownScope.anInt772 - 1; i++) if (JavaCIPUnknownScope.anIntArray774[i] < JavaCIPUnknownScope.anIntArray774[i + 1]) {
                int j = JavaCIPUnknownScope.anIntArray774[i];
                JavaCIPUnknownScope.anIntArray774[i] = JavaCIPUnknownScope.anIntArray774[i + 1];
                JavaCIPUnknownScope.anIntArray774[i + 1] = j;
                long l = JavaCIPUnknownScope.aLongArray773[i];
                JavaCIPUnknownScope.aLongArray773[i] = JavaCIPUnknownScope.aLongArray773[i + 1];
                JavaCIPUnknownScope.aLongArray773[i + 1] = l;
                flag = true;
            }
        }
    }
}
