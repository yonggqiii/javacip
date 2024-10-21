class c17615292 {

    public BufferedImage processUsingTemp(InputStream input, DjatokaDecodeParam params) throws DjatokaRuntimeException {
        File in;
        try {
            in = File.createTempFile("tmp", ".jp2");
            FileOutputStream fos = new FileOutputStream(in);
            in.deleteOnExit();
            IOUtils.copyStream(input, fos);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error(e, e);
            throw new DjatokaRuntimeException(e);
        }
        BufferedImage bi = JavaCIPUnknownScope.process(in.getAbsolutePath(), params);
        if (in != null)
            in.delete();
        return bi;
    }
}
