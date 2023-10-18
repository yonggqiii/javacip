


class c1362126 {

    public InputStream resolve(String uri) throws SAJRuntimeException {
        try {
            URI url = new URI(uri);
            InputStream stream = url.toURL().openStream();
            if (stream == null) throw new SAJRuntimeException("URI " + uri + " can't be resolved");
            return stream;
        } catch (SAJRuntimeException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new SAJRuntimeException("Invalid uri to resolve " + uri, e);
        }
    }

}
