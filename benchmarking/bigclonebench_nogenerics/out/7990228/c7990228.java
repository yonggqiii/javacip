class c7990228 {

    public static InputStream gzip(final InputStream inputStream) throws IORuntimeException {
        Assert.notNull(inputStream, "inputStream");
        InputOutputStream inputOutputStream = new InputOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(inputOutputStream);
        IOUtils.copy(inputStream, gzipOutputStream);
        gzipOutputStream.close();
        return inputOutputStream.getInputStream();
    }
}
