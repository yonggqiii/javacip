class c8384812 {

    public void testGetRequestWithRefresh() throws Exception {
        JavaCIPUnknownScope.expect(JavaCIPUnknownScope.request.getParameter(ProxyBase.REFRESH_PARAM)).andReturn("120").anyTimes();
        Capture<HttpRequest> requestCapture = new Capture<HttpRequest>();
        JavaCIPUnknownScope.expect(JavaCIPUnknownScope.pipeline.execute(JavaCIPUnknownScope.capture(requestCapture))).andReturn(new HttpResponse(JavaCIPUnknownScope.RESPONSE_BODY));
        JavaCIPUnknownScope.replay();
        JavaCIPUnknownScope.handler.fetch(JavaCIPUnknownScope.request, JavaCIPUnknownScope.recorder);
        HttpRequest httpRequest = requestCapture.getValue();
        JavaCIPUnknownScope.assertEquals("public,max-age=120", JavaCIPUnknownScope.recorder.getHeader("Cache-Control"));
        JavaCIPUnknownScope.assertEquals(120, httpRequest.getCacheTtl());
    }
}
