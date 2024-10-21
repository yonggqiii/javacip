class c10688093 {

    public final InputStream getStreamFromUrl(final URL url) {
        try {
            if (JavaCIPUnknownScope.listener != null) {
                JavaCIPUnknownScope.listener.openedStream(url);
            }
            return url.openStream();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.listener.exceptionThrown(e);
            return null;
        }
    }
}
