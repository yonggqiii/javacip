class c18570193 {

    public Psrxml getPsrxmlForCandidateList(CandidateList clist) throws BookKeeprCommunicationRuntimeException {
        try {
            synchronized (JavaCIPUnknownScope.httpClient) {
                long psrxmlid = clist.getPsrxmlId();
                HttpGet req = new HttpGet(JavaCIPUnknownScope.remoteHost.getUrl() + "/id/" + StringConvertable.ID.toString(psrxmlid));
                HttpResponse resp = JavaCIPUnknownScope.httpClient.execute(req);
                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        InputStream in = resp.getEntity().getContent();
                        XMLAble xmlable = XMLReader.read(in);
                        in.close();
                        if (xmlable instanceof Psrxml) {
                            Psrxml psrxml = (Psrxml) xmlable;
                            return psrxml;
                        } else {
                            throw new BookKeeprCommunicationRuntimeException("BookKeepr returned the wrong thing for psrxml id " + psrxmlid);
                        }
                    } catch (SAXRuntimeException ex) {
                        Logger.getLogger(BookKeeprConnection.class.getName()).log(Level.WARNING, "Got a malformed message from the bookkeepr", ex);
                        throw new BookKeeprCommunicationRuntimeException(ex);
                    }
                } else {
                    resp.getEntity().consumeContent();
                    throw new BookKeeprCommunicationRuntimeException("Got a " + resp.getStatusLine().getStatusCode() + " from the BookKeepr  (" + JavaCIPUnknownScope.remoteHost.getUrl() + "/id/" + StringConvertable.ID + ")");
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
