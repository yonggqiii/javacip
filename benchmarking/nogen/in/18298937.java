


class c18298937 {

    protected InputStream makeSignedRequestAndGetJSONData(String url) {
        try {
            if (consumer == null) loginOAuth();
        } catch (RuntimeException e) {
            consumer = null;
            e.printStackTrace();
        }
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            consumer.sign(method);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return data;
    }

}
