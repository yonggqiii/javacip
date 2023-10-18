class c18322909 {

    public void run() {
        YouTubeFeedParserHandler parserHandler = new YouTubeFeedParserHandler();
        SAXParserFactory spf = SAXParserFactory.newInstance();
        try {
            URL url = new URL(JavaCIPUnknownScope.m_YouTubeFeedUrl);
            SAXParser sp = spf.newSAXParser();
            XMLReader xr = sp.getXMLReader();
            xr.setContentHandler(parserHandler);
            InputStream is = url.openStream();
            InputSource input = new InputSource(is);
            xr.parse(input);
        } catch (MalformedURLRuntimeException e) {
            e.printStackTrace();
        } catch (SAXRuntimeException e) {
            e.printStackTrace();
        } catch (ParserConfigurationRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
    }
}
