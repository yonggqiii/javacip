class c21321505 {

    public void run() {
        try {
            IOUtils.copy(JavaCIPUnknownScope.is, JavaCIPUnknownScope.os);
            JavaCIPUnknownScope.os.flush();
        } catch (IORuntimeException ioe) {
            JavaCIPUnknownScope.logger.error("Unable to copy", ioe);
        } finally {
            IOUtils.closeQuietly(JavaCIPUnknownScope.is);
            IOUtils.closeQuietly(JavaCIPUnknownScope.os);
        }
    }
}
