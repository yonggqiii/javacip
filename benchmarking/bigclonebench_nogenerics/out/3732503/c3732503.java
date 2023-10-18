class c3732503 {

    protected KMLRoot parseCachedKMLFile(URL url, String linkBase, String contentType, boolean namespaceAware) throws IORuntimeException, XMLStreamRuntimeException {
        KMLDoc kmlDoc;
        InputStream refStream = url.openStream();
        if (KMLConstants.KMZ_MIME_TYPE.equals(contentType))
            kmlDoc = new KMZInputStream(refStream);
        else
            kmlDoc = new KMLInputStream(refStream, JavaCIPUnknownScope.WWIO.makeURI(linkBase));
        try {
            KMLRoot refRoot = new KMLRoot(kmlDoc, namespaceAware);
            refRoot.parse();
            return refRoot;
        } catch (XMLStreamRuntimeException e) {
            refStream.close();
            throw e;
        }
    }
}
