class c19296519 {

    private long getSize(String url) throws ClientProtocolRuntimeException, IORuntimeException {
        url = JavaCIPUnknownScope.normalizeUrl(url);
        Log.i(JavaCIPUnknownScope.LOG_TAG, "Head " + url);
        HttpHead httpGet = new HttpHead(url);
        HttpResponse response = JavaCIPUnknownScope.mHttpClient.execute(httpGet);
        if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
            throw new IORuntimeException("Unexpected Http status code " + response.getStatusLine().getStatusCode());
        }
        Header[] clHeaders = response.getHeaders("Content-Length");
        if (clHeaders.length > 0) {
            Header header = clHeaders[0];
            return Long.parseLong(header.getValue());
        }
        return -1;
    }
}
