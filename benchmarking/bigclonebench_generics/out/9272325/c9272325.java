class c9272325 {

    private static HttpURLConnection sendPost(String reqUrl, Map<String, String> parameters) {
        HttpURLConnection urlConn = null;
        try {
            String params = JavaCIPUnknownScope.generatorParamString(parameters);
            URL url = new URL(reqUrl);
            urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setConnectTimeout(5000);
            urlConn.setReadTimeout(5000);
            urlConn.setDoOutput(true);
            byte[] b = params.getBytes();
            urlConn.getOutputStream().write(b, 0, b.length);
            urlConn.getOutputStream().flush();
            urlConn.getOutputStream().close();
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e.getMessage(), e);
        }
        return urlConn;
    }
}
