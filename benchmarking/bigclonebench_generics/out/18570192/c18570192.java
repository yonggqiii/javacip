class c18570192 {

    public List<CandidateListStub> getAllCandLists() throws BookKeeprCommunicationException {
        try {
            synchronized (JavaCIPUnknownScope.httpClient) {
                HttpGet req = new HttpGet(JavaCIPUnknownScope.remoteHost.getUrl() + "/cand/lists");
                HttpResponse resp = JavaCIPUnknownScope.httpClient.execute(req);
                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        InputStream in = resp.getEntity().getContent();
                        XMLAble xmlable = XMLReader.read(in);
                        in.close();
                        if (xmlable instanceof CandidateListIndex) {
                            CandidateListIndex idx = (CandidateListIndex) xmlable;
                            return idx.getCandidateListStubList();
                        } else {
                            throw new BookKeeprCommunicationException("BookKeepr returned the wrong thing for /cand/lists");
                        }
                    } catch (SAXException ex) {
                        Logger.getLogger(BookKeeprConnection.class.getName()).log(Level.WARNING, "Got a malformed message from the bookkeepr", ex);
                        throw new BookKeeprCommunicationException(ex);
                    }
                } else {
                    resp.getEntity().consumeContent();
                    throw new BookKeeprCommunicationException("Got a " + resp.getStatusLine().getStatusCode() + " from the BookKeepr");
                }
            }
        } catch (HttpException ex) {
            throw new BookKeeprCommunicationException(ex);
        }
    }
}
