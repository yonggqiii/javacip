


class c14974316 {

    public void connect() throws ClientProtocolRuntimeException, IORuntimeException {
        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity entity = httpResponse.getEntity();
        inputStream = entity.getContent();
        Header contentEncodingHeader = entity.getContentEncoding();
        if (contentEncodingHeader != null) {
            HeaderElement[] codecs = contentEncodingHeader.getElements();
            for (HeaderElement encoding : codecs) {
                if (encoding.getName().equalsIgnoreCase("gzip")) {
                    inputStream = new GZIPInputStream(inputStream);
                }
            }
        }
        inputStream = new BufferedInputStream(inputStream, 2048);
    }

}
