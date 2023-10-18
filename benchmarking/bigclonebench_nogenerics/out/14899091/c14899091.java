class c14899091 {

    protected long getURLLastModified(final URL url) throws IORuntimeException {
        final URLConnection con = url.openConnection();
        long lastModified = con.getLastModified();
        try {
            con.getInputStream().close();
        } catch (IORuntimeException ignored) {
        }
        return lastModified;
    }
}
