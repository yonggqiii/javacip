class c16725803 {

    public void validateXml(InputStream inputData, ErrorHandler errorHandler) throws SAXRuntimeException, IORuntimeException, RuntimeException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(false);
        spf.setNamespaceAware(true);
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
            URL schemaURL = new URL(JavaCIPUnknownScope.schemeUrl);
            InputStream urlStream = null;
            try {
                urlStream = schemaURL.openStream();
            } catch (IORuntimeException ex) {
                if (JavaCIPUnknownScope.defaultUrl != null) {
                    schemaURL = new URL(JavaCIPUnknownScope.defaultUrl);
                    urlStream = schemaURL.openStream();
                } else {
                    throw ex;
                }
            }
            JavaCIPUnknownScope.LOGGER.debug("Uses schema url : " + schemaURL);
            StreamSource sss = new StreamSource(urlStream);
            Schema schema = schemaFactory.newSchema(sss);
            spf.setSchema(schema);
            SAXParser parser = spf.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setErrorHandler(errorHandler);
            reader.parse(new InputSource(inputData));
        } catch (ParserConfigurationRuntimeException e) {
            throw new SAXRuntimeException(e);
        }
    }
}
