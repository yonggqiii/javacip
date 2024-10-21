class c12829832 {

    private Document parseResponse(String url) throws IORuntimeException, MalformedURLRuntimeException, ParserConfigurationRuntimeException, SAXRuntimeException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream stream = null;
        try {
            stream = new URL(url).openStream();
            return db.parse(stream);
        } finally {
            PetstoreUtil.closeIgnoringRuntimeException(stream);
        }
    }
}
