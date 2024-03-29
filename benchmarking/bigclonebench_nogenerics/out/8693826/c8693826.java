class c8693826 {

    private String readJsonString() {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(JavaCIPUnknownScope.SERVER_URL);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                Log.e(JavaCIPUnknownScope.TAG, "Failed to download file");
            }
        } catch (ClientProtocolRuntimeException e) {
            e.printStackTrace();
        } catch (IORuntimeException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
