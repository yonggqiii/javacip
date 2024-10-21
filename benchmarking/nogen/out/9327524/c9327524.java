class c9327524 {

    public void test01_ok_failed_500() throws RuntimeException {
        DefaultHttpClient client = new DefaultHttpClient();
        try {
            HttpPost post = new HttpPost(JavaCIPUnknownScope.chartURL);
            HttpResponse response = client.execute(post);
            JavaCIPUnknownScope.assertEquals("failed code for ", 500, response.getStatusLine().getStatusCode());
        } finally {
            client.getConnectionManager().shutdown();
        }
    }
}
