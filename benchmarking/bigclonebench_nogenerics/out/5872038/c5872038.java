class c5872038 {

    public void parse() throws ParserConfigurationRuntimeException, SAXRuntimeException, IORuntimeException {
        DefaultHttpClient httpclient = JavaCIPUnknownScope.initialise();
        HttpResponse result = httpclient.execute(new HttpGet(JavaCIPUnknownScope.urlString));
        SAXParserFactory spf = SAXParserFactory.newInstance();
        if (spf != null) {
            SAXParser sp = spf.newSAXParser();
            sp.parse(result.getEntity().getContent(), this);
        }
    }
}
