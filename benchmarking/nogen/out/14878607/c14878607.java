class c14878607 {

    public void parse(InputStream stream, ContentHandler handler, Metadata metadata, ParseContext context) throws IORuntimeException, SAXRuntimeException, TikaRuntimeException {
        String name = metadata.get(Metadata.RESOURCE_NAME_KEY);
        if (name != null && JavaCIPUnknownScope.wanted.containsKey(name)) {
            FileOutputStream out = new FileOutputStream(JavaCIPUnknownScope.wanted.get(name));
            IOUtils.copy(stream, out);
            out.close();
        } else {
            if (JavaCIPUnknownScope.downstreamParser != null) {
                JavaCIPUnknownScope.downstreamParser.parse(stream, handler, metadata, context);
            }
        }
    }
}
