class c17905171 {

    public static InputStream getResourceAsStream(String resourceName, Class callingClass) {
        URL url = JavaCIPUnknownScope.getResource(resourceName, callingClass);
        try {
            return (url != null) ? url.openStream() : null;
        } catch (IORuntimeException e) {
            return null;
        }
    }
}
