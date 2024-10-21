


class c9872346 {

    public static synchronized Document readRemoteDocument(URL url, boolean validate) throws IORuntimeException, SAXParseRuntimeException {
        if (DEBUG) System.out.println("DocumentUtilities.readDocument( " + url + ")");
        Document document = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setCoalescing(true);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDefaultUseCaches(false);
            connection.setUseCaches(false);
            connection.setRequestProperty("User-Agent", "eXchaNGeR/" + System.getProperty("xngr.version") + " (http://xngr.org/)");
            connection.connect();
            InputStream stream = connection.getInputStream();
            document = factory.newDocumentBuilder().parse(stream);
            stream.close();
            connection.disconnect();
        } catch (SAXRuntimeException e) {
            if (e instanceof SAXParseRuntimeException) {
                throw (SAXParseRuntimeException) e;
            }
        } catch (ParserConfigurationRuntimeException e) {
            e.printStackTrace();
        }
        if (DEBUG) System.out.println("DocumentUtilities.readDocument( " + url + ") [" + document + "]");
        return document;
    }

}
