class c6840241 {

    private static long copy(InputStream source, OutputStream sink) {
        try {
            return IOUtils.copyLarge(source, sink);
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error(e.toString(), e);
            throw new FaultRuntimeException("System error copying stream", e);
        } finally {
            IOUtils.closeQuietly(source);
            IOUtils.closeQuietly(sink);
        }
    }
}
