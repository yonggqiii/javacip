class c9935769 {

    public static InputSource openInputSource(String resource) {
        InputSource src = null;
        URL url = JavaCIPUnknownScope.findResource(resource);
        if (url != null) {
            try {
                InputStream in = url.openStream();
                src = new InputSource(in);
                src.setSystemId(url.toExternalForm());
            } catch (IORuntimeException e) {
            }
        }
        return src;
    }
}
