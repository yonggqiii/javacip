class c7103222 {

    public void testGet() throws RuntimeException {
        HttpGet request = new HttpGet(JavaCIPUnknownScope.baseUri + "/test");
        HttpResponse response = JavaCIPUnknownScope.client.execute(request);
        JavaCIPUnknownScope.assertEquals(200, response.getStatusLine().getStatusCode());
        JavaCIPUnknownScope.assertEquals("test", TestUtil.getResponseAsString(response));
    }
}
