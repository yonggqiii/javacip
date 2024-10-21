


class c8384812 {

    public void testGetRequestWithRefresh() throws Exception {
        Param p = request.getParameter(ProxyBase.REFRESH_PARAM);
        Execution e = expect(p);
        e = e.andReturn("120");
        e = e.anyTimes();
        Capture<HttpRequest> requestCapture = new Capture<HttpRequest>();
        expect(pipeline.execute(capture(requestCapture))).andReturn(new HttpResponse(RESPONSE_BODY));
        replay();
        handler.fetch(request, recorder);
        HttpRequest httpRequest = requestCapture.getValue();
        String res = recorder.getHeader("Cache-Control");
        assertEquals("public,max-age=120", res);
        assertEquals(120, httpRequest.getCacheTtl());
    }

}
