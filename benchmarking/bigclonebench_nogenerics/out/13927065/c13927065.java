class c13927065 {

    void copyFile(File src, File dst) throws IORuntimeException {
        FileInputStream fis = new FileInputStream(src);
        byte[] buf = new byte[10000];
        int n;
        FileOutputStream fos = new FileOutputStream(dst);
        while ((n = fis.read(buf)) > 0) fos.write(buf, 0, n);
        fis.close();
        fos.close();
        JavaCIPUnknownScope.copied++;
    }
}
