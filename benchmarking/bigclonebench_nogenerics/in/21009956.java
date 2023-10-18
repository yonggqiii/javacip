


class c21009956 {

    @Override
    public Reader openReader(final Charset charset) throws LocatorRuntimeException {
        try {
            if (charset != null) {
                return new InputStreamReader(url.openStream(), charset);
            }
            return new InputStreamReader(url.openStream());
        } catch (final IORuntimeException e) {
            throw new LocatorRuntimeException("Failed to read from URL: " + url, e);
        }
    }

}
