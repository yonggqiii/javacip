


class c16752474 {

    public HttpResponse executeHttp(final HttpUriRequest request, final int beginExpectedCode, final int endExpectedCode) throws ClientProtocolRuntimeException, IORuntimeException, HttpRuntimeException {
        final HttpResponse response = httpClient.execute(request);
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode < beginExpectedCode || statusCode >= endExpectedCode) {
            throw newHttpRuntimeException(request, response);
        }
        return response;
    }

}
