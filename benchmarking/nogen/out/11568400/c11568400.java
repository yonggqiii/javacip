class c11568400 {

    private InputStream getInputStream(URI uri) throws IORuntimeException {
        if (Logging.SHOW_FINE && JavaCIPUnknownScope.LOG.isLoggable(Level.FINE)) {
            JavaCIPUnknownScope.LOG.fine("Loading ACL : " + uri.toString());
        }
        URL url = uri.toURL();
        URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        return connection.getInputStream();
    }
}
