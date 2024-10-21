


class c4551648 {

    public HttpResponse execute(HttpHost host, HttpRequest req, HttpContext ctx) throws IORuntimeException, ClientProtocolRuntimeException {
        HttpResponse resp = backend.execute(host, req, ctx);
        if (assessor.isFailure(resp)) {
            throw new UnsuccessfulResponseRuntimeException(resp);
        }
        return resp;
    }

}
