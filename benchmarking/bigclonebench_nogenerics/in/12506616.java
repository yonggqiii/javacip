


class c12506616 {

    private InputStream fetch(String urlString) throws MalformedURLRuntimeException, IORuntimeException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
    }

}
