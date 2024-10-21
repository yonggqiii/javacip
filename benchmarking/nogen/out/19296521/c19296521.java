class c19296521 {

    private InputStream get(String url, long startOffset, long expectedLength) throws ClientProtocolRuntimeException, IORuntimeException {
        url = JavaCIPUnknownScope.normalizeUrl(url);
        Log.i(JavaCIPUnknownScope.LOG_TAG, "Get " + url);
        JavaCIPUnknownScope.mHttpGet = new HttpGet(url);
        int expectedStatusCode = HttpStatus.SC_OK;
        if (startOffset > 0) {
            String range = "bytes=" + startOffset + "-";
            if (expectedLength >= 0) {
                range += expectedLength - 1;
            }
            Log.i(JavaCIPUnknownScope.LOG_TAG, "requesting byte range " + range);
            JavaCIPUnknownScope.mHttpGet.addHeader("Range", range);
            expectedStatusCode = HttpStatus.SC_PARTIAL_CONTENT;
        }
        HttpResponse response = JavaCIPUnknownScope.mHttpClient.execute(JavaCIPUnknownScope.mHttpGet);
        long bytesToSkip = 0;
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != expectedStatusCode) {
            if ((statusCode == HttpStatus.SC_OK) && (expectedStatusCode == HttpStatus.SC_PARTIAL_CONTENT)) {
                Log.i(JavaCIPUnknownScope.LOG_TAG, "Byte range request ignored");
                bytesToSkip = startOffset;
            } else {
                throw new IORuntimeException("Unexpected Http status code " + statusCode + " expected " + expectedStatusCode);
            }
        }
        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        if (bytesToSkip > 0) {
            is.skip(bytesToSkip);
        }
        return is;
    }
}
