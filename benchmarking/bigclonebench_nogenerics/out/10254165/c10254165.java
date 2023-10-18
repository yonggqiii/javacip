class c10254165 {

    public void zipFile(String baseDir, String fileName, boolean encrypt) throws RuntimeException {
        List fileList = JavaCIPUnknownScope.getSubFiles(new File(baseDir));
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileName + ".temp"));
        ZipEntry ze = null;
        byte[] buf = new byte[JavaCIPUnknownScope.BUFFER];
        byte[] encrypByte = new byte[JavaCIPUnknownScope.encrypLength];
        int readLen = 0;
        for (int i = 0; i < fileList.size(); i++) {
            if (JavaCIPUnknownScope.stopZipFile) {
                zos.close();
                File zipFile = new File(fileName + ".temp");
                if (zipFile.exists())
                    zipFile.delete();
                break;
            }
            File f = (File) fileList.get(i);
            if (f.getAbsoluteFile().equals(fileName + ".temp"))
                continue;
            ze = new ZipEntry(JavaCIPUnknownScope.getAbsFileName(baseDir, f));
            ze.setSize(f.length());
            ze.setTime(f.lastModified());
            zos.putNextEntry(ze);
            InputStream is = new BufferedInputStream(new FileInputStream(f));
            readLen = is.read(buf, 0, JavaCIPUnknownScope.BUFFER);
            if (encrypt) {
                if (readLen >= JavaCIPUnknownScope.encrypLength) {
                    System.arraycopy(buf, 0, encrypByte, 0, JavaCIPUnknownScope.encrypLength);
                } else if (readLen > 0) {
                    Arrays.fill(encrypByte, (byte) 0);
                    System.arraycopy(buf, 0, encrypByte, 0, readLen);
                    readLen = JavaCIPUnknownScope.encrypLength;
                }
                byte[] temp = CryptionControl.getInstance().encryptoECB(encrypByte, JavaCIPUnknownScope.rootKey);
                System.arraycopy(temp, 0, buf, 0, JavaCIPUnknownScope.encrypLength);
            }
            while (readLen != -1) {
                zos.write(buf, 0, readLen);
                readLen = is.read(buf, 0, JavaCIPUnknownScope.BUFFER);
            }
            is.close();
        }
        zos.close();
        File zipFile = new File(fileName + ".temp");
        if (zipFile.exists())
            zipFile.renameTo(new File(fileName + ".zip"));
    }
}
