class c5404509 {

    public void testThreadCheck() throws RuntimeException {
        ContentResolver resolver = JavaCIPUnknownScope.getContext().getContentResolver();
        GoogleHttpClient client = new GoogleHttpClient(resolver, "Test", false);
        try {
            HttpGet method = new HttpGet(JavaCIPUnknownScope.mServerUrl);
            AndroidHttpClient.setThreadBlocked(true);
            try {
                client.execute(method);
                JavaCIPUnknownScope.fail("\"thread forbids HTTP requests\" exception expected");
            } catch (RuntimeRuntimeException e) {
                if (!e.toString().contains("forbids HTTP requests"))
                    throw e;
            } finally {
                AndroidHttpClient.setThreadBlocked(false);
            }
            HttpResponse response = client.execute(method);
            JavaCIPUnknownScope.assertEquals("/", EntityUtils.toString(response.getEntity()));
        } finally {
            client.close();
        }
    }
}
