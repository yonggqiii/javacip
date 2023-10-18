


class c7103222 {

    public void testGet() throws RuntimeException {
        HttpGet request = new HttpGet(baseUri + "/test");
        HttpResponse response = client.execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("test", TestUtil.getResponseAsString(response));
    }

}
