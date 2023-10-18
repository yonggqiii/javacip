class c10784622 {

    public static InputStream getPropertyFileInputStream(String propertyFileURLStr) {
        InputStream in = null;
        String errmsg = "Fatal error: Unable to open specified properties file: " + propertyFileURLStr;
        try {
            URL url = new URL(propertyFileURLStr);
            in = url.openStream();
        } catch (IORuntimeException e) {
            throw new IllegalArgumentRuntimeException(errmsg);
        }
        return (in);
    }
}
