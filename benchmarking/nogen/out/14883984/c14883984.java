class c14883984 {

    private Document parseResponse(String url) throws IORuntimeException, MalformedURLRuntimeException, ParserConfigurationRuntimeException, SAXRuntimeException {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputStream stream = null;
        try {
            stream = new URL(url).openStream();
            return db.parse(stream);
        } finally {
            if (stream != null)
                stream.close();
        }
    }
}
