class c1258712 {

    public static Document loadXML(URL url) {
        Document doc = null;
        try {
            DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            doc = docBuilder.parse(url.openStream());
        } catch (ParserConfigurationRuntimeException pce) {
        } catch (SAXRuntimeException saxe) {
        } catch (IORuntimeException ioe) {
        }
        return doc;
    }
}
