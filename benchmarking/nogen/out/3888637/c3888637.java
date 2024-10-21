class c3888637 {

    public VeecheckResult performRequest(VeecheckVersion version, String uri) throws ClientProtocolRuntimeException, IORuntimeException, IllegalStateRuntimeException, SAXRuntimeException {
        HttpClient client = new DefaultHttpClient();
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, JavaCIPUnknownScope.CONNECTION_TIMEOUT);
        HttpConnectionParams.setSoTimeout(params, JavaCIPUnknownScope.SO_TIMEOUT);
        HttpGet request = new HttpGet(version.substitute(uri));
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        try {
            StatusLine line = response.getStatusLine();
            if (line.getStatusCode() != 200)
                throw new IORuntimeException("Request failed: " + line.getReasonPhrase());
            Header header = response.getFirstHeader(JavaCIPUnknownScope.HTTP.CONTENT_TYPE);
            Encoding encoding = JavaCIPUnknownScope.identityEncoding(header);
            VeecheckResult handler = new VeecheckResult(version);
            Xml.parse(entity.getContent(), encoding, handler);
            return handler;
        } finally {
            entity.consumeContent();
        }
    }
}
