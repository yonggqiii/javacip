class c3330775 {

    public static JSONObject getJSONData(String url) throws JSONRuntimeException {
        JSONObject jObject = null;
        InputStream data = null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        try {
            uri = new URI(url);
            HttpGet httpGet = new HttpGet(uri);
            HttpResponse response = httpClient.execute(httpGet);
            data = response.getEntity().getContent();
            String line;
            StringBuilder builder = new StringBuilder();
            BufferedReader reader;
            reader = new BufferedReader(new InputStreamReader(data), 8192);
            while ((line = reader.readLine()) != null) builder.append(line);
            reader.close();
            jObject = (JSONObject) new JSONTokener(builder.toString()).nextValue();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return jObject;
    }
}
