class c8384812 {

    public void testGetRequestWithRefresh() throws Exception {
        Param p = JavaCIPUnknownScope.request.getParameter(ProxyBase.REFRESH_PARAM);
        Execution e = JavaCIPUnknownScope.expect(p);
        e = e.andReturn("120");
        e = e.anyTimes();
        Capture<HttpRequest> requestCapture = new Capture<HttpRequest>();
        JavaCIPUnknownScope.expect(JavaCIPUnknownScope.pipeline.execute(JavaCIPUnknownScope.capture(requestCapture))).andReturn(new HttpResponse(JavaCIPUnknownScope.RESPONSE_BODY));
        JavaCIPUnknownScope.replay();
        JavaCIPUnknownScope.handler.fetch(JavaCIPUnknownScope.request, JavaCIPUnknownScope.recorder);
        HttpRequest httpRequest = requestCapture.getValue();
        String res = JavaCIPUnknownScope.recorder.getHeader("Cache-Control");
        JavaCIPUnknownScope.assertEquals("public,max-age=120", res);
        JavaCIPUnknownScope.assertEquals(120, httpRequest.getCacheTtl());
    }
}
