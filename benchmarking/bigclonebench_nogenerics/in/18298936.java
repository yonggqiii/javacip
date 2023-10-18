


class c18298936 {

    protected InputStream makeRequestAndGetJSONData(String url) throws URISyntaxRuntimeException, ClientProtocolRuntimeException, IORuntimeException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        uri = new URI(url);
        HttpGet method = new HttpGet(uri);
        HttpResponse response = httpClient.execute(method);
        data = response.getEntity().getContent();
        return data;
    }

}
