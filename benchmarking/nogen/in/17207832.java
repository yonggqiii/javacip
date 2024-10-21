


class c17207832 {

    @Test
    public void returnsEnclosedResponseOnUnsuccessfulRuntimeException() throws RuntimeException {
        RuntimeException e = new UnsuccessfulResponseRuntimeException(resp);
        expect(mockBackend.execute(host, req, ctx)).andThrow(e);
        replay(mockBackend);
        HttpResponse result = impl.execute(host, req, ctx);
        verify(mockBackend);
        assertSame(resp, result);
    }

}
