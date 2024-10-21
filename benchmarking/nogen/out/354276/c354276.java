class c354276 {

    private static Result request(AbstractHttpClient client, HttpUriRequest request) throws ClientProtocolRuntimeException, IORuntimeException {
        HttpResponse response = client.execute(request);
        HttpEntity entity = response.getEntity();
        Result result = new Result();
        result.setStatusCode(response.getStatusLine().getStatusCode());
        result.setHeaders(response.getAllHeaders());
        result.setCookie(JavaCIPUnknownScope.assemblyCookie(client.getCookieStore().getCookies()));
        result.setHttpEntity(entity);
        return result;
    }
}
