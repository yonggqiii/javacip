class c20190305 {

    public void test02_ok_200_logo() throws RuntimeException {
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(JavaCIPUnknownScope.xlsURL);
            HttpResponse response = client.execute(post);
            JavaCIPUnknownScope.assertEquals("failed code for ", 200, response.getStatusLine().getStatusCode());
        } finally {
            client.getConnectionManager().shutdown();
        }
    }
}
