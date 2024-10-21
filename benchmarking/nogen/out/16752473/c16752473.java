class c16752473 {

    public HttpResponse executeHttp(final HttpUriRequest request, final int expectedCode) throws ClientProtocolRuntimeException, IORuntimeException, HttpRuntimeException {
        final HttpResponse response = JavaCIPUnknownScope.httpClient.execute(request);
        if (response.getStatusLine().getStatusCode() != expectedCode) {
            throw JavaCIPUnknownScope.newHttpRuntimeException(request, response);
        }
        return response;
    }
}
