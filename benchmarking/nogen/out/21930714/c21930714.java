class c21930714 {

    public void testSpeedyShareUpload() throws RuntimeException {
        JavaCIPUnknownScope.request.setUrl("http://www.speedyshare.com/upload.php");
        JavaCIPUnknownScope.request.setFile("fileup0", JavaCIPUnknownScope.file);
        HttpResponse response = JavaCIPUnknownScope.httpClient.execute(JavaCIPUnknownScope.request);
        JavaCIPUnknownScope.assertTrue(response.is2xxSuccess());
        JavaCIPUnknownScope.assertTrue(response.getResponseHeaders().size() > 0);
        String body = IOUtils.toString(response.getResponseBody());
        JavaCIPUnknownScope.assertTrue(body.contains("Download link"));
        JavaCIPUnknownScope.assertTrue(body.contains("Delete password"));
        response.close();
    }
}
