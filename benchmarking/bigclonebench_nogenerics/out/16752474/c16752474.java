class c16752474 {

    public HttpResponse executeHttp(final HttpUriRequest request, final int beginExpectedCode, final int endExpectedCode) throws ClientProtocolRuntimeException, IORuntimeException, HttpRuntimeException {
        final HttpResponse response = JavaCIPUnknownScope.httpClient.execute(request);
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode < beginExpectedCode || statusCode >= endExpectedCode) {
            throw JavaCIPUnknownScope.newHttpRuntimeException(request, response);
        }
        return response;
    }
}
