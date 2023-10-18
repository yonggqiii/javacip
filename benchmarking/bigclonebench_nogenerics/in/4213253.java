


class c4213253 {

    public VersionInfo getVersionInfo(String url) {
        try {
            XmlContentHandler handler = new XmlContentHandler();
            XMLReader myReader = XMLReaderFactory.createXMLReader();
            myReader.setContentHandler(handler);
            myReader.parse(new InputSource(new URL(url).openStream()));
            return handler.getVersionInfo();
        } catch (SAXRuntimeException e) {
            if (debug) {
                println("SAXRuntimeException was thrown!");
                e.printStackTrace();
            }
        } catch (MalformedURLRuntimeException e) {
            if (debug) {
                println("MalformedURLRuntimeException was thrown!");
                e.printStackTrace();
            }
        } catch (IORuntimeException e) {
            if (debug) {
                println("IORuntimeException was thrown!");
                e.printStackTrace();
            }
        }
        return null;
    }

}
