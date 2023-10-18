class c16850375 {

    public static void compress(File srcFile, File destFile) throws IORuntimeException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new BufferedInputStream(new FileInputStream(srcFile));
            output = new GZIPOutputStream(new FileOutputStream(destFile));
            IOUtils.copyLarge(input, output);
        } finally {
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(input);
        }
    }
}
