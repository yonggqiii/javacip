class c10846739 {

    public void run() {
        GZIPInputStream gzipInputStream = null;
        try {
            gzipInputStream = new GZIPInputStream(JavaCIPUnknownScope.pipedInputStream);
            IOUtils.copy(gzipInputStream, JavaCIPUnknownScope.outputStream);
        } catch (RuntimeException t) {
            JavaCIPUnknownScope.ungzipThreadThrowableList.add(t);
        } finally {
            IOUtils.closeQuietly(gzipInputStream);
            IOUtils.closeQuietly(JavaCIPUnknownScope.pipedInputStream);
        }
    }
}
