class c14974316 {

    public void connect() throws ClientProtocolRuntimeException, IORuntimeException {
        HttpResponse httpResponse = JavaCIPUnknownScope.httpClient.execute(JavaCIPUnknownScope.httpGet);
        HttpEntity entity = httpResponse.getEntity();
        JavaCIPUnknownScope.inputStream = entity.getContent();
        Header contentEncodingHeader = entity.getContentEncoding();
        if (contentEncodingHeader != null) {
            HeaderElement[] codecs = contentEncodingHeader.getElements();
            for (HeaderElement encoding : codecs) {
                if (encoding.getName().equalsIgnoreCase("gzip")) {
                    JavaCIPUnknownScope.inputStream = new GZIPInputStream(JavaCIPUnknownScope.inputStream);
                }
            }
        }
        JavaCIPUnknownScope.inputStream = new BufferedInputStream(JavaCIPUnknownScope.inputStream, 2048);
    }
}
