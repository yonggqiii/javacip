


class c20190303 {

    @Test
    public void test01_ok_failed_500_no_logo() throws RuntimeException {
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(xlsURL);
            HttpResponse response = client.execute(post);
            assertEquals("failed code for ", 500, response.getStatusLine().getStatusCode());
        } finally {
            client.getConnectionManager().shutdown();
        }
    }

}
