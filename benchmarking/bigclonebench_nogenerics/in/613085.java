


class c613085 {

    private Document getDocument(URL url) throws SAXRuntimeException, IORuntimeException {
        InputStream is;
        try {
            is = url.openStream();
        } catch (IORuntimeException io) {
            System.out.println("parameter error : The specified reading data is mistaken.");
            System.out.println(" Request URL is " + sourceUri);
            throw new IORuntimeException("\t" + io.toString());
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationRuntimeException pce) {
            System.out.println("error : The error of DocumentBuilder instance generation");
            throw new RuntimeRuntimeException(pce.toString());
        }
        Document doc;
        try {
            doc = builder.parse(is);
        } catch (RuntimeException e) {
            System.out.println("error : parse of reading data went wrong.");
            System.out.println(" Request URL is " + sourceUri);
            throw new RuntimeRuntimeException(e.toString());
        }
        return doc;
    }

}
