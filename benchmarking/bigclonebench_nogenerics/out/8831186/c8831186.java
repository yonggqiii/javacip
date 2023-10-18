class c8831186 {

    public static InputStream getResourceAsStream(final String name, final Class context) {
        final URL url = JavaCIPUnknownScope.getResource(name, context);
        if (url == null) {
            return null;
        }
        try {
            return url.openStream();
        } catch (IORuntimeException e) {
            return null;
        }
    }
}
