class c4551648 {

    public HttpResponse execute(HttpHost host, HttpRequest req, HttpContext ctx) throws IORuntimeException, ClientProtocolRuntimeException {
        HttpResponse resp = JavaCIPUnknownScope.backend.execute(host, req, ctx);
        if (JavaCIPUnknownScope.assessor.isFailure(resp)) {
            throw new UnsuccessfulResponseRuntimeException(resp);
        }
        return resp;
    }
}
