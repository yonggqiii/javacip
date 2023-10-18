class c12579074 {

    public static XMLConfigurator loadFromDefaultSystemProperty() throws IORuntimeException {
        String urlStr = System.getProperty(JavaCIPUnknownScope.DEFAULT_SYS_PROP_NAME);
        if (urlStr == null || urlStr.length() == 0) {
            return null;
        }
        InputStream in = null;
        DOMRetriever xmlDoc = null;
        try {
            URL url = new URL(urlStr);
            xmlDoc = new DOMRetriever(in = url.openStream());
        } catch (MalformedURLRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        } finally {
            if (in != null)
                in.close();
        }
        return JavaCIPUnknownScope.newInstance(xmlDoc);
    }
}