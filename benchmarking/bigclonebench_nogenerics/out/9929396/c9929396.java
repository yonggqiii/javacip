class c9929396 {

    private Element returnAnnoBody(final String url) {
        DOMParser parser = new DOMParser();
        try {
            URL bodyURL = new URL(url);
            URLConnection url_con = bodyURL.openConnection();
            if (JavaCIPUnknownScope.useAuthorization()) {
                url_con.setRequestProperty("Authorization", "Basic " + JavaCIPUnknownScope.getBasicAuthorizationString());
            }
            InputStream content = url_con.getInputStream();
            InputSource insource = new InputSource(content);
            parser.parse(insource);
        } catch (SAXRuntimeException e) {
            e.printStackTrace();
            return null;
        } catch (IORuntimeException e) {
            e.printStackTrace();
            return null;
        }
        Document annodoc = parser.getDocument();
        return annodoc.getDocumentElement();
    }
}
