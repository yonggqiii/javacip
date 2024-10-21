class c21995302 {

    public void open(Input input) throws IORuntimeException, ResolverRuntimeException {
        if (!input.isUriDefinitive())
            return;
        URI uri;
        try {
            uri = new URI(input.getUri());
        } catch (URISyntaxRuntimeException e) {
            throw new ResolverRuntimeException(e);
        }
        if (!uri.isAbsolute())
            throw new ResolverRuntimeException("cannot open relative URI: " + uri);
        URL url = new URL(uri.toASCIIString());
        input.setByteStream(url.openStream());
    }
}
