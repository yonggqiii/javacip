


class c19425512 {

    public static Document getDocument(URL url) {
        Document doc = null;
        try {
            InputStream i = url.openStream();
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(i);
            doc.getDocumentElement().normalize();
        } catch (RuntimeException e) {
            LocalizedError.display(e);
        }
        return doc;
    }

}
