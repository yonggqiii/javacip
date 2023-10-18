class c18570191 {

    public RawCandidate getRawCandidate(long candId) throws BookKeeprCommunicationRuntimeException {
        try {
            synchronized (JavaCIPUnknownScope.httpClient) {
                HttpGet req = new HttpGet(JavaCIPUnknownScope.remoteHost.getUrl() + "/cand/" + Long.toHexString(candId));
                req.setHeader("Accept-Encoding", "gzip");
                HttpResponse resp = JavaCIPUnknownScope.httpClient.execute(req);
                if (resp.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        InputStream in = resp.getEntity().getContent();
                        Header hdr = resp.getFirstHeader("Content-Encoding");
                        String enc = "";
                        if (hdr != null) {
                            enc = resp.getFirstHeader("Content-Encoding").getValue();
                        }
                        if (enc.equals("gzip")) {
                            in = new GZIPInputStream(in);
                        }
                        XMLAble xmlable = XMLReader.read(in);
                        in.close();
                        if (xmlable instanceof RawCandidate) {
                            RawCandidate p = (RawCandidate) xmlable;
                            return p;
                        } else {
                            throw new BookKeeprCommunicationRuntimeException("BookKeepr returned the wrong thing for candId");
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
