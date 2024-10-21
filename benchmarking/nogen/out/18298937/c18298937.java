class c18298937 {

    protected InputStream makeSignedRequestAndGetJSONData(String url) {
        try {
            if (JavaCIPUnknownScope.consumer == null)
                JavaCIPUnknownScope.loginOAuth();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.consumer = null;
            e.printStackTrace();
        }
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            JavaCIPUnknownScope.consumer.sign(method);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return data;
    }
}
