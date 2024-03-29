class c7990229 {

    public static InputStream gunzip(final InputStream inputStream) throws IORuntimeException {
        Assert.notNull(inputStream, "inputStream");
        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
        InputOutputStream inputOutputStream = new InputOutputStream();
        IOUtils.copy(gzipInputStream, inputOutputStream);
        return inputOutputStream.getInputStream();
    }
}
