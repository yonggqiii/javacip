class c22190302 {

    public InputSource resolveEntity(String name, String uri) throws IORuntimeException, SAXRuntimeException {
        InputSource retval;
        String mappedURI = JavaCIPUnknownScope.name2uri(name);
        InputStream stream;
        if (mappedURI == null && (stream = JavaCIPUnknownScope.mapResource(name)) != null) {
            uri = "java:resource:" + (String) JavaCIPUnknownScope.id2resource.get(name);
            retval = new InputSource(XmlReader.createReader(stream));
        } else {
            URL url;
            URLConnection conn;
            if (mappedURI != null)
                uri = mappedURI;
            else if (uri == null)
                return null;
            url = new URL(uri);
            conn = url.openConnection();
            uri = conn.getURL().toString();
            if (JavaCIPUnknownScope.ignoringMIME)
                retval = new InputSource(XmlReader.createReader(conn.getInputStream()));
            else {
                String contentType = conn.getContentType();
                retval = JavaCIPUnknownScope.createInputSource(contentType, conn.getInputStream(), false, url.getProtocol());
            }
        }
        retval.setSystemId(uri);
        retval.setPublicId(name);
        return retval;
    }
}
