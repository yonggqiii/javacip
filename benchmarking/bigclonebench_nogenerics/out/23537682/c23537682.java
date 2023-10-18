class c23537682 {

    public static byte[] readResource(Class owningClass, String resourceName) {
        final URL url = JavaCIPUnknownScope.getResourceUrl(owningClass, resourceName);
        if (null == url) {
            throw new MissingResourceRuntimeException(owningClass.toString() + " key '" + resourceName + "'", owningClass.toString(), resourceName);
        }
        JavaCIPUnknownScope.LOG.info("Loading resource '" + url.toExternalForm() + "' " + "from " + owningClass);
        final InputStream inputStream;
        try {
            inputStream = url.openStream();
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException("Should not happpen", e);
        }
        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            IOUtils.copy(inputStream, outputStream);
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException("Should not happpen", e);
        }
        return outputStream.toByteArray();
    }
}
