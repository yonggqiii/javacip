class c21930715 {

    public void testImageshackUpload() throws RuntimeException {
        JavaCIPUnknownScope.request.setUrl("http://www.imageshack.us/index.php");
        JavaCIPUnknownScope.request.addParameter("xml", "yes");
        JavaCIPUnknownScope.request.setFile("fileupload", JavaCIPUnknownScope.file);
        HttpResponse response = JavaCIPUnknownScope.httpClient.execute(JavaCIPUnknownScope.request);
        JavaCIPUnknownScope.assertTrue(response.is2xxSuccess());
        JavaCIPUnknownScope.assertTrue(response.getResponseHeaders().size() > 0);
        String body = IOUtils.toString(response.getResponseBody());
        JavaCIPUnknownScope.assertTrue(body.contains("<image_link>"));
        JavaCIPUnknownScope.assertTrue(body.contains("<thumb_link>"));
        JavaCIPUnknownScope.assertTrue(body.contains("<image_location>"));
        JavaCIPUnknownScope.assertTrue(body.contains("<image_name>"));
        response.close();
    }
}
