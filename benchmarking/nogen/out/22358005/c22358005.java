class c22358005 {

    public InputStream getInputStream() throws IORuntimeException {
        if (JavaCIPUnknownScope.dfos == null) {
            int deferredOutputStreamThreshold = Config.getInstance().getDeferredOutputStreamThreshold();
            JavaCIPUnknownScope.dfos = new DeferredFileOutputStream(deferredOutputStreamThreshold, Definitions.PROJECT_NAME, "." + Definitions.TMP_EXTENSION);
            try {
                IOUtils.copy(JavaCIPUnknownScope.is, JavaCIPUnknownScope.dfos);
            } finally {
                JavaCIPUnknownScope.dfos.close();
            }
        }
        return JavaCIPUnknownScope.dfos.getDeferredInputStream();
    }
}
