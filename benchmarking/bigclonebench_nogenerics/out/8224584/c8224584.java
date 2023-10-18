class c8224584 {

    public boolean checkConnection() {
        int status = 0;
        try {
            URL url = new URL(TupeloProxy.endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            status = conn.getResponseCode();
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.severe("Connection test failed with code:" + status);
            e.printStackTrace();
        }
        return status > 199 && status < 400;
    }
}
