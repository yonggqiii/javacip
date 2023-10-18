class c14764851 {

    public Download doDownload(HttpHeader[] headers, URI target) throws HttpRequestException {
        HttpRequest<E> con = JavaCIPUnknownScope.createConnection(HttpMethods.METHOD_GET, target);
        if (JavaCIPUnknownScope.defaultHeaders != null) {
            JavaCIPUnknownScope.putHeaders(con, JavaCIPUnknownScope.defaultHeaders);
        }
        if (headers != null) {
            JavaCIPUnknownScope.putHeaders(con, headers);
        }
        HttpResponse<?> res = JavaCIPUnknownScope.execute(con);
        if (res.getResponseCode() == 200) {
            return new Download(res);
        } else {
            throw new HttpRequestException(res.getResponseCode(), res.getResponseMessage());
        }
    }
}
