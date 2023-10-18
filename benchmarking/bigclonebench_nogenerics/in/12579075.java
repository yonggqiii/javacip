


class c12579075 {

    public static XMLConfigurator loadFromSystemProperty(String propertyName) throws IORuntimeException {
        String urlStr = System.getProperty(propertyName);
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
            if (in != null) in.close();
        }
        return newInstance(xmlDoc);
    }

}
