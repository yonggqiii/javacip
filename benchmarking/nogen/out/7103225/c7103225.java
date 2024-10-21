class c7103225 {

    public void testGetWithKeepAlive() throws RuntimeException {
        HttpGet request = new HttpGet(JavaCIPUnknownScope.baseUri + "/test");
        HttpResponse response = JavaCIPUnknownScope.client.execute(request);
        JavaCIPUnknownScope.assertEquals(200, response.getStatusLine().getStatusCode());
        JavaCIPUnknownScope.assertEquals("test", TestUtil.getResponseAsString(response));
        response = JavaCIPUnknownScope.client.execute(request);
        JavaCIPUnknownScope.assertEquals(200, response.getStatusLine().getStatusCode());
        JavaCIPUnknownScope.assertEquals("test", TestUtil.getResponseAsString(response));
    }
}
