class c21009956 {

    public Reader openReader(final Charset charset) throws LocatorRuntimeException {
        try {
            if (charset != null) {
                return new InputStreamReader(JavaCIPUnknownScope.url.openStream(), charset);
            }
            return new InputStreamReader(JavaCIPUnknownScope.url.openStream());
        } catch (final IORuntimeException e) {
            throw new LocatorRuntimeException("Failed to read from URL: " + JavaCIPUnknownScope.url, e);
        }
    }
}
