class c20365090 {

    public HttpResponseExchange execute() throws RuntimeException {
        HttpResponseExchange forwardResponse = null;
        int fetchSizeLimit = Config.getInstance().getFetchLimitSize();
        while (null != JavaCIPUnknownScope.lastContentRange) {
            JavaCIPUnknownScope.forwardRequest.setBody(new byte[0]);
            ContentRangeHeaderValue old = JavaCIPUnknownScope.lastContentRange;
            long sendSize = fetchSizeLimit;
            if (old.getInstanceLength() - old.getLastBytePos() - 1 < fetchSizeLimit) {
                sendSize = (old.getInstanceLength() - old.getLastBytePos() - 1);
            }
            if (sendSize <= 0) {
                break;
            }
            JavaCIPUnknownScope.lastContentRange = new ContentRangeHeaderValue(old.getLastBytePos() + 1, old.getLastBytePos() + sendSize, old.getInstanceLength());
            JavaCIPUnknownScope.forwardRequest.setHeader(HttpHeaders.Names.CONTENT_RANGE, JavaCIPUnknownScope.lastContentRange);
            JavaCIPUnknownScope.forwardRequest.setHeader(HttpHeaders.Names.CONTENT_LENGTH, String.valueOf(sendSize));
            forwardResponse = JavaCIPUnknownScope.syncFetch(JavaCIPUnknownScope.forwardRequest);
            if (sendSize < fetchSizeLimit) {
                JavaCIPUnknownScope.lastContentRange = null;
            }
        }
        return forwardResponse;
    }
}
