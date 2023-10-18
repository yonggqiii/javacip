class c5404510 {

    public void testUrlRewriteRules() throws RuntimeException {
        ContentResolver resolver = JavaCIPUnknownScope.getContext().getContentResolver();
        GoogleHttpClient client = new GoogleHttpClient(resolver, "Test", false);
        Settings.Gservices.putString(resolver, "url:test", "http://foo.bar/ rewrite " + JavaCIPUnknownScope.mServerUrl + "new/");
        Settings.Gservices.putString(resolver, "digest", JavaCIPUnknownScope.mServerUrl);
        try {
            HttpGet method = new HttpGet("http://foo.bar/path");
            HttpResponse response = client.execute(method);
            String body = EntityUtils.toString(response.getEntity());
            JavaCIPUnknownScope.assertEquals("/new/path", body);
        } finally {
            client.close();
        }
    }
}
