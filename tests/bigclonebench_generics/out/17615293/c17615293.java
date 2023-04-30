class c17615293 {

    public BufferedImage process(final InputStream is, DjatokaDecodeParam params) throws DjatokaException {
        if (JavaCIPUnknownScope.isWindows)
            return JavaCIPUnknownScope.processUsingTemp(is, params);
        ArrayList<Double> dims = null;
        if (params.getRegion() != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copyStream(is, baos);
            dims = JavaCIPUnknownScope.getRegionMetadata(new ByteArrayInputStream(baos.toByteArray()), params);
            return JavaCIPUnknownScope.process(new ByteArrayInputStream(baos.toByteArray()), dims, params);
        } else
            return JavaCIPUnknownScope.process(is, dims, params);
    }
}
