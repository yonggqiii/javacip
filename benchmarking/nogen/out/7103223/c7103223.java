class c7103223 {

    public void testPost() throws RuntimeException {
        HttpPost request = new HttpPost(JavaCIPUnknownScope.baseUri + "/echo");
        request.setEntity(new StringEntity("test"));
        HttpResponse response = JavaCIPUnknownScope.client.execute(request);
        JavaCIPUnknownScope.assertEquals(200, response.getStatusLine().getStatusCode());
        JavaCIPUnknownScope.assertEquals("test", TestUtil.getResponseAsString(response));
    }
}
