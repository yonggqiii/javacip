class c23677117 {

    public static void copyFile4(File srcFile, File destFile) throws IORuntimeException {
        InputStream in = new FileInputStream(srcFile);
        OutputStream out = new FileOutputStream(destFile);
        IOUtils.copy(in, out);
        in.close();
        out.close();
    }
}
