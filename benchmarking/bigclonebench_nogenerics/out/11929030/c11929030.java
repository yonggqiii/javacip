class c11929030 {

    public T_Result unmarshall(URL url) throws SAXRuntimeException, ParserConfigurationRuntimeException, IORuntimeException {
        XMLReader parser = JavaCIPUnknownScope.getParserFactory().newSAXParser().getXMLReader();
        parser.setContentHandler(JavaCIPUnknownScope.getContentHandler());
        parser.setDTDHandler(JavaCIPUnknownScope.getContentHandler());
        parser.setEntityResolver(JavaCIPUnknownScope.getContentHandler());
        parser.setErrorHandler(JavaCIPUnknownScope.getContentHandler());
        InputSource inputSource = new InputSource(url.openStream());
        inputSource.setSystemId(url.toString());
        parser.parse(inputSource);
        return JavaCIPUnknownScope.contentHandler.getRootObject();
    }
}
