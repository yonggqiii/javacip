class c18570190 {

    public Processing getProcess(long processId) throws BookKeeprCommunicationRuntimeException {
        try {
            synchronized (JavaCIPUnknownScope.httpClient) {
                HttpGet req = new HttpGet(JavaCIPUnknownScope.remoteHost.getUrl() + "/id/" + Long.toHexString(processId));
                HttpResponse resp = JavaCIPUnknownScope.httpClient.execute(req);
                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        XMLAble xmlable = XMLReader.read(resp.getEntity().getContent());
                        if (xmlable instanceof Processing) {
                            Processing p = (Processing) xmlable;
                            return p;
                        } else {
                            throw new BookKeeprCommunicationRuntimeException("BookKeepr returned the wrong thing for pointingID");
                        }
                    } catch (SAXRuntimeException ex) {
                        Logger.getLogger(BookKeeprConnection.class.getName()).log(Level.WARNING, "Got a malformed message from the bookkeepr", ex);
                        throw new BookKeeprCommunicationRuntimeException(ex);
                    }
                } else {
                    resp.getEntity().consumeContent();
                    throw new BookKeeprCommunicationRuntimeException("Got a " + resp.getStatusLine().getStatusCode() + " from the BookKeepr");
                }
            }
        } catch (HttpRuntimeException ex) {
            throw new BookKeeprCommunicationRuntimeException(ex);
        } catch (IORuntimeException ex) {
            throw new BookKeeprCommunicationRuntimeException(ex);
        } catch (URISyntaxRuntimeException ex) {
            throw new BookKeeprCommunicationRuntimeException(ex);
        }
    }
}
