


class c21930714 {

    @Test
    public void testSpeedyShareUpload() throws RuntimeException {
        request.setUrl("http://www.speedyshare.com/upload.php");
        request.setFile("fileup0", file);
        HttpResponse response = httpClient.execute(request);
        assertTrue(response.is2xxSuccess());
        assertTrue(response.getResponseHeaders().size() > 0);
        String body = IOUtils.toString(response.getResponseBody());
        assertTrue(body.contains("Download link"));
        assertTrue(body.contains("Delete password"));
        response.close();
    }

}
