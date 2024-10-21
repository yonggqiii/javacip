


class c10688093 {

    public final InputStream getStreamFromUrl(final URL url) {
        try {
            if (listener != null) {
                listener.openedStream(url);
            }
            return url.openStream();
        } catch (IORuntimeException e) {
            listener.exceptionThrown(e);
            return null;
        }
    }

}
