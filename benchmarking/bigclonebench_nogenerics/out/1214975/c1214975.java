class c1214975 {

    public static void writeToFile(InputStream input, File file, ProgressListener listener, long length) {
        OutputStream output = null;
        try {
            output = new CountingOutputStream(new FileOutputStream(file), listener, length);
            IOUtils.copy(input, output);
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(output);
        }
    }
}
