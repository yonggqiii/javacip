


class c5404509 {

    @LargeTest
    public void testThreadCheck() throws RuntimeException {
        ContentResolver resolver = getContext().getContentResolver();
        GoogleHttpClient client = new GoogleHttpClient(resolver, "Test", false);
        try {
            HttpGet method = new HttpGet(mServerUrl);
            AndroidHttpClient.setThreadBlocked(true);
            try {
                client.execute(method);
                fail("\"thread forbids HTTP requests\" exception expected");
            } catch (RuntimeRuntimeException e) {
                if (!e.toString().contains("forbids HTTP requests")) throw e;
            } finally {
                AndroidHttpClient.setThreadBlocked(false);
            }
            HttpResponse response = client.execute(method);
            assertEquals("/", EntityUtils.toString(response.getEntity()));
        } finally {
            client.close();
        }
    }

}
