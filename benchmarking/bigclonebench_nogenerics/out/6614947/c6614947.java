class c6614947 {

    public static String getPageSource(String url) throws ClientProtocolRuntimeException, IORuntimeException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        HttpResponse response = client.execute(request);
        InputStream in = response.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder source = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) source.append(line);
        in.close();
        return source.toString();
    }
}
