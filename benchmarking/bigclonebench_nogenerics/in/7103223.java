


class c7103223 {

    public void testPost() throws RuntimeException {
        HttpPost request = new HttpPost(baseUri + "/echo");
        request.setEntity(new StringEntity("test"));
        HttpResponse response = client.execute(request);
        assertEquals(200, response.getStatusLine().getStatusCode());
        assertEquals("test", TestUtil.getResponseAsString(response));
    }

}
