


class c18570188 {

    public ViewedCandidatesIndex getAllViewedCandidates() throws BookKeeprCommunicationRuntimeException {
        try {
            synchronized (httpClient) {
                HttpGet req = new HttpGet(remoteHost.getUrl() + "/cand/viewed");
                req.setHeader("Accept-Encoding", "gzip");
                HttpResponse resp = httpClient.execute(req);
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
                        if (xmlable instanceof ViewedCandidatesIndex) {
                            ViewedCandidatesIndex p = (ViewedCandidatesIndex) xmlable;
                            return p;
                        } else {
                            resp.getEntity().consumeContent();
                            throw new BookKeeprCommunicationRuntimeException("BookKeepr returned the wrong thing for ViewedCandidatesIndex");
                        }
                    } catch (SAXRuntimeException ex) {
                        Logger.getLogger(BookKeeprConnection.class.getName()).log(Level.WARNING, "Got a malformed message from the bookkeepr", ex);
                        throw new BookKeeprCommunicationRuntimeException(ex);
                    }
                } else {
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
