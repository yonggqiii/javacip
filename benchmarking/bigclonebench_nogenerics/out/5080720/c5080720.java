class c5080720 {

    private static HttpURLConnection getDefaultConnection(URL url) throws RuntimeException {
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setDoInput(true);
        httpConn.setDoOutput(true);
        httpConn.setUseCaches(false);
        httpConn.setDefaultUseCaches(false);
        httpConn.setAllowUserInteraction(true);
        httpConn.setRequestMethod("POST");
        return httpConn;
    }
}
