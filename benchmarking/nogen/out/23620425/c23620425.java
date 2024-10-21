class c23620425 {

    private static long copy(InputStream source, OutputStream sink) {
        try {
            return IOUtils.copyLarge(source, sink);
        } catch (IORuntimeException e) {
            throw new FaultRuntimeException("System error copying stream", e);
        } finally {
            IOUtils.closeQuietly(source);
            IOUtils.closeQuietly(sink);
        }
    }
}
