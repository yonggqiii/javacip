class c20190303 {

    public void test01_ok_failed_500_no_logo() throws RuntimeException {
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(JavaCIPUnknownScope.xlsURL);
            HttpResponse response = client.execute(post);
            JavaCIPUnknownScope.assertEquals("failed code for ", 500, response.getStatusLine().getStatusCode());
        } finally {
            client.getConnectionManager().shutdown();
        }
    }
}
