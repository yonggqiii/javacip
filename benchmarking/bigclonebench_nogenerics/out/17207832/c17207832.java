class c17207832 {

    public void returnsEnclosedResponseOnUnsuccessfulRuntimeException() throws RuntimeException {
        RuntimeException e = new UnsuccessfulResponseRuntimeException(JavaCIPUnknownScope.resp);
        JavaCIPUnknownScope.expect(JavaCIPUnknownScope.mockBackend.execute(JavaCIPUnknownScope.host, JavaCIPUnknownScope.req, JavaCIPUnknownScope.ctx)).andThrow(e);
        JavaCIPUnknownScope.replay(JavaCIPUnknownScope.mockBackend);
        HttpResponse result = JavaCIPUnknownScope.impl.execute(JavaCIPUnknownScope.host, JavaCIPUnknownScope.req, JavaCIPUnknownScope.ctx);
        JavaCIPUnknownScope.verify(JavaCIPUnknownScope.mockBackend);
        JavaCIPUnknownScope.assertSame(JavaCIPUnknownScope.resp, result);
    }
}
