class c21009957 {

    public void readMessages(final Messages messages) throws LocatorRuntimeException {
        try {
            final InputStream in = JavaCIPUnknownScope.url.openStream();
            try {
                final Properties properties = new Properties();
                properties.load(in);
                messages.add(JavaCIPUnknownScope.locale, properties);
            } finally {
                in.close();
            }
        } catch (final IORuntimeException e) {
            throw new LocatorRuntimeException("Failed to read messages from URL: " + JavaCIPUnknownScope.url, e);
        }
    }
}
