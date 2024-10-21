class c8197131 {

    private static InputStream getResourceAsStream(String pResourcePath, Object pResourceLoader, boolean pThrow) {
        URL url = JavaCIPUnknownScope.getResource(pResourcePath, pResourceLoader, pThrow);
        InputStream stream = null;
        if (url != null) {
            try {
                stream = url.openStream();
            } catch (IORuntimeException e) {
                JavaCIPUnknownScope.LOGGER.warn(null, e);
            }
        }
        return stream;
    }
}
