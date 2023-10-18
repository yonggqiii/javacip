class c18570189 {

    public ViewedCandidates postViewedCandidates(ViewedCandidates viewedCandidates) throws BookKeeprCommunicationRuntimeException {
        try {
            synchronized (JavaCIPUnknownScope.httpClient) {
                HttpPost req = new HttpPost(JavaCIPUnknownScope.remoteHost.getUrl() + "/cand/viewed");
                req.setHeader("Accept-Encoding", "gzip");
                ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
                XMLWriter.write(out, viewedCandidates);
                ByteArrayInputStream in2 = new ByteArrayInputStream(out.toByteArray());
                req.setEntity(new InputStreamEntity(in2, -1));
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
                        if (xmlable instanceof ViewedCandidates) {
                            ViewedCandidates p = (ViewedCandidates) xmlable;
                            return p;
                        } else {
                            resp.getEntity().consumeContent();
                            throw new BookKeeprCommunicationRuntimeException("BookKeepr returned the wrong thing for ViewedCandidates");
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
