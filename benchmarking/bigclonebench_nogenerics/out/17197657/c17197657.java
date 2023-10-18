class c17197657 {

    private void parseXmlFile() throws IORuntimeException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            if (JavaCIPUnknownScope.file != null) {
                JavaCIPUnknownScope.dom = db.parse(JavaCIPUnknownScope.file);
            } else {
                JavaCIPUnknownScope.dom = db.parse(JavaCIPUnknownScope.url.openStream());
            }
        } catch (ParserConfigurationRuntimeException pce) {
            pce.printStackTrace();
        } catch (SAXRuntimeException se) {
            se.printStackTrace();
        }
    }
}
