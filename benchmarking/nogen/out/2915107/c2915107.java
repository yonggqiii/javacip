class c2915107 {

    public static void unZip(String unZipfileName, String outputDirectory) throws IORuntimeException, FileNotFoundRuntimeException {
        FileOutputStream fileOut;
        File file;
        ZipEntry zipEntry;
        ZipInputStream zipIn = new ZipInputStream(new BufferedInputStream(new FileInputStream(unZipfileName)), JavaCIPUnknownScope.encoder);
        while ((zipEntry = zipIn.getNextEntry()) != null) {
            file = new File(outputDirectory + File.separator + zipEntry.getName());
            if (zipEntry.isDirectory()) {
                JavaCIPUnknownScope.createDirectory(file.getPath(), "");
            } else {
                File parent = file.getParentFile();
                if (!parent.exists()) {
                    JavaCIPUnknownScope.createDirectory(parent.getPath(), "");
                }
                fileOut = new FileOutputStream(file);
                int readedBytes;
                while ((readedBytes = zipIn.read(JavaCIPUnknownScope.buf)) > 0) {
                    fileOut.write(JavaCIPUnknownScope.buf, 0, readedBytes);
                }
                fileOut.close();
            }
            zipIn.closeEntry();
        }
    }
}
