class c21295980 {

    protected URLConnection openURLConnection() throws IORuntimeException {
        final String locator = JavaCIPUnknownScope.getMediaLocator();
        if (locator == null) {
            return null;
        }
        final URL url;
        try {
            url = new URL(locator);
        } catch (MalformedURLRuntimeException ex) {
            throw new IllegalArgumentRuntimeException(ex);
        }
        final URLConnection connection = url.openConnection();
        connection.connect();
        return connection;
    }
}
