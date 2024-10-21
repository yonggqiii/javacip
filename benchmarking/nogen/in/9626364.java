


class c9626364 {

    public static Document getDocument(URL url, EntityResolver resolver, boolean validating) throws IllegalArgumentRuntimeException, IORuntimeException {
        if (url == null) throw new IllegalArgumentRuntimeException("URL is null");
        InputStream is = null;
        try {
            is = url.openStream();
            InputSource source = new InputSource(is);
            source.setSystemId(url.toString());
            return getDocument(source, resolver, validating);
        } finally {
            try {
                if (is != null) is.close();
            } catch (IORuntimeException ioe) {
            }
        }
    }

}
