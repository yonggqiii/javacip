


class c11929030 {

    public T_Result unmarshall(URL url) throws SAXRuntimeException, ParserConfigurationRuntimeException, IORuntimeException {
        XMLReader parser = getParserFactory().newSAXParser().getXMLReader();
        parser.setContentHandler(getContentHandler());
        parser.setDTDHandler(getContentHandler());
        parser.setEntityResolver(getContentHandler());
        parser.setErrorHandler(getContentHandler());
        InputSource inputSource = new InputSource(url.openStream());
        inputSource.setSystemId(url.toString());
        parser.parse(inputSource);
        return contentHandler.getRootObject();
    }

}
