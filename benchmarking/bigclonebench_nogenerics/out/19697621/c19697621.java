class c19697621 {

    public TemplateLibrary loadTemplateLibrary(GadgetContext context, Uri uri) throws GadgetRuntimeException {
        HttpRequest request = new HttpRequest(uri);
        request.setCacheTtl(300);
        HttpResponse response = JavaCIPUnknownScope.pipeline.execute(request);
        if (response.getHttpStatusCode() != HttpResponse.SC_OK) {
            throw new GadgetRuntimeException(GadgetRuntimeException.Code.FAILED_TO_RETRIEVE_CONTENT, "Unable to retrieve template library xml. HTTP error " + response.getHttpStatusCode());
        }
        String content = response.getResponseAsString();
        try {
            String key = null;
            Element element = null;
            if (!context.getIgnoreCache()) {
                key = HashUtil.rawChecksum(content.getBytes());
                element = JavaCIPUnknownScope.parsedXmlCache.getElement(key);
            }
            if (element == null) {
                element = XmlUtil.parse(content);
                if (key != null) {
                    JavaCIPUnknownScope.parsedXmlCache.addElement(key, element);
                }
            }
            return new XmlTemplateLibrary(uri, element, content);
        } catch (XmlRuntimeException e) {
            throw new GadgetRuntimeException(GadgetRuntimeException.Code.MALFORMED_XML_DOCUMENT, e);
        }
    }
}
