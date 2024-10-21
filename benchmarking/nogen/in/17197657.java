


class c17197657 {

    private void parseXmlFile() throws IORuntimeException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            if (file != null) {
                dom = db.parse(file);
            } else {
                dom = db.parse(url.openStream());
            }
        } catch (ParserConfigurationRuntimeException pce) {
            pce.printStackTrace();
        } catch (SAXRuntimeException se) {
            se.printStackTrace();
        }
    }

}
