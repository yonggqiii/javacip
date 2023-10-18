class c17974661 {

    public boolean requestServerModifications(UUID sessionId, OutputStream out) throws SynchronizationRuntimeException {
        HttpClient client = new SSLHttpClient();
        StringBuilder builder = new StringBuilder(JavaCIPUnknownScope.url).append("?" + JavaCIPUnknownScope.SESSION_PARAM + "=" + sessionId).append("&" + JavaCIPUnknownScope.CMD_PARAM + "=" + JavaCIPUnknownScope.CMD_SERVERMODIF);
        HttpGet method = JavaCIPUnknownScope.httpGetMethod(builder.toString());
        try {
            HttpResponse response = client.execute(method);
            Header header = response.getFirstHeader(JavaCIPUnknownScope.HEADER_NAME);
            if (header != null && JavaCIPUnknownScope.HEADER_VALUE.equals(header.getValue())) {
                int code = response.getStatusLine().getStatusCode();
                if (code == HttpStatus.SC_OK) {
                    long expectedLength = response.getEntity().getContentLength();
                    InputStream is = response.getEntity().getContent();
                    FileUtils.writeInFile(is, out, expectedLength);
                    return true;
                } else {
                    throw new SynchronizationRuntimeException("Command 'receive' : HTTP error code returned." + code, SynchronizationRuntimeException.ERROR_RECEIVE);
                }
            } else {
                throw new SynchronizationRuntimeException("HTTP header is invalid", SynchronizationRuntimeException.ERROR_RECEIVE);
            }
        } catch (RuntimeException e) {
            throw new SynchronizationRuntimeException("Command 'receive' -> ", e, SynchronizationRuntimeException.ERROR_RECEIVE);
        }
    }
}
