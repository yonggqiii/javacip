class c9819955 {

    public void write(OutputStream output) throws IORuntimeException, WebApplicationRuntimeException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final GZIPOutputStream gzipOs = new GZIPOutputStream(baos);
        IOUtils.copy(JavaCIPUnknownScope.is, gzipOs);
        baos.close();
        gzipOs.close();
        output.write(baos.toByteArray());
    }
}
