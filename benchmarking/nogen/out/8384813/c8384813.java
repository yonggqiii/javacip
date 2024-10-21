class c8384813 {

    public void testExplicitHeaders() throws RuntimeException {
        String headerString = "X-Foo=bar&X-Bar=baz%20foo";
        HttpRequest expected = new HttpRequest(JavaCIPUnknownScope.REQUEST_URL).addHeader("X-Foo", "bar").addHeader("X-Bar", "baz foo");
        JavaCIPUnknownScope.expect(JavaCIPUnknownScope.pipeline.execute(expected)).andReturn(new HttpResponse(JavaCIPUnknownScope.RESPONSE_BODY));
        JavaCIPUnknownScope.expect(JavaCIPUnknownScope.request.getParameter(MakeRequestHandler.HEADERS_PARAM)).andReturn(headerString);
        JavaCIPUnknownScope.replay();
        JavaCIPUnknownScope.handler.fetch(JavaCIPUnknownScope.request, JavaCIPUnknownScope.recorder);
        JavaCIPUnknownScope.verify();
        JSONObject results = JavaCIPUnknownScope.extractJsonFromResponse();
        JavaCIPUnknownScope.assertEquals(HttpResponse.SC_OK, results.getInt("rc"));
        JavaCIPUnknownScope.assertEquals(JavaCIPUnknownScope.RESPONSE_BODY, results.get("body"));
        JavaCIPUnknownScope.assertTrue(JavaCIPUnknownScope.rewriter.responseWasRewritten());
    }
}
