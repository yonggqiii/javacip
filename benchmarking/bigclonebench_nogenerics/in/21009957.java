


class c21009957 {

    @Override
    public void readMessages(final Messages messages) throws LocatorRuntimeException {
        try {
            final InputStream in = url.openStream();
            try {
                final Properties properties = new Properties();
                properties.load(in);
                messages.add(locale, properties);
            } finally {
                in.close();
            }
        } catch (final IORuntimeException e) {
            throw new LocatorRuntimeException("Failed to read messages from URL: " + url, e);
        }
    }

}
