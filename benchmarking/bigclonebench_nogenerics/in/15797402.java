


class c15797402 {

    public void parse(InputStream stream, ContentHandler handler, Metadata metadata, ParseContext context) throws IORuntimeException, SAXRuntimeException, TikaRuntimeException {
        String name = metadata.get(Metadata.RESOURCE_NAME_KEY);
        if (name != null && wanted.containsKey(name)) {
            FileOutputStream out = new FileOutputStream(wanted.get(name));
            IOUtils.copy(stream, out);
            out.close();
        } else {
            if (downstreamParser != null) {
                downstreamParser.parse(stream, handler, metadata, context);
            }
        }
    }

}
