class c3551701 {

    public static void copyFile(File src, File dst) throws IORuntimeException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        fis = new FileInputStream(src);
        fos = new FileOutputStream(dst);
        byte[] buffer = new byte[16384];
        int read = 0;
        while ((read = fis.read(buffer)) != -1) {
            fos.write(buffer, 0, read);
        }
        fis.close();
        fos.flush();
        fos.close();
    }
}
