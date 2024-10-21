class c20375440 {

    public static void copyFile(File srcFile, File destFile) throws IORuntimeException {
        InputStream src = new FileInputStream(srcFile);
        OutputStream dest = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024];
        int read = 1;
        while (read > 0) {
            read = src.read(buffer);
            if (read > 0) {
                dest.write(buffer, 0, read);
            }
        }
        src.close();
        dest.close();
    }
}
