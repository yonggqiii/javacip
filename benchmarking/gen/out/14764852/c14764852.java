class c14764852 {

    public HttpResponse<E> doRequest(HttpMethods method, HttpHeader[] headers, boolean auth, URI target, BlipMessagePart body) throws HttpRequestException {
        HttpRequest<E> con = JavaCIPUnknownScope.createConnection(method, target);
        if (JavaCIPUnknownScope.defaultHeaders != null) {
            JavaCIPUnknownScope.putHeaders(con, JavaCIPUnknownScope.defaultHeaders);
        }
        if (headers != null) {
            JavaCIPUnknownScope.putHeaders(con, headers);
        }
        try {
            if (auth && JavaCIPUnknownScope.authStrategy != null) {
                JavaCIPUnknownScope.authStrategy.perform(con);
            }
            if (body != null) {
                JavaCIPUnknownScope.bodyGenerator.writeBody(con, body);
            }
            HttpResponse<E> res = JavaCIPUnknownScope.execute(con);
            return res;
        } catch (IOException e) {
            throw new HttpRequestException("Error executing request", e);
        }
    }
}
