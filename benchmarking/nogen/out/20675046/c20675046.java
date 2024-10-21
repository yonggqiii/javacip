class c20675046 {

    public Object getContent(ContentProducerContext context, String ctxAttrName, Object ctxAttrValue) {
        try {
            URL url = (JavaCIPUnknownScope.getURL() != null) ? new URL(JavaCIPUnknownScope.getURL().toExternalForm()) : new URL(((URL) ctxAttrValue).toExternalForm());
            InputStream reader = url.openStream();
            int available = reader.available();
            byte[] contents = new byte[available];
            reader.read(contents, 0, available);
            reader.close();
            return new String(contents);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }
}
