public class Android17 {

    private void Login() {
        HttpClient httpClient = new DefaultHttpClient();
        // try {
        String url = "http://192.168.1.5:8000/Login?username=test&password=test";
        HttpGet method = new HttpGet(new URI(url));
        HttpResponse response = httpClient.execute(method);
        if (response != null) {
            Log.i("login", "received " + getResponse(response.getEntity()));
        } else {
            Log.i("login", "got a null response");
        }
        // } catch (IOException e) {
        // Log.e("error", e.getMessage());
        // } catch (URISyntaxException e) {
        // Log.e("error", e.getMessage());
        // }
    }

    private String getResponse(HttpEntity entity) {
        String response = "";
        // try {
        int length = (int) entity.getContentLength();
        StringBuffer sb = new StringBuffer(length);
        InputStreamReader isr = new InputStreamReader(entity.getContent(), "UTF-8");
        char[] buff = new char[length];
        int cnt;
        while ((cnt = isr.read(buff, 0, length - 1)) > 0) {
            sb.append(buff, 0, cnt);
        }
        response = sb.toString();
        isr.close();
        // } catch (IOException ioe) {
        // ioe.printStackTrace();
        // }
        return response;
    }
}
