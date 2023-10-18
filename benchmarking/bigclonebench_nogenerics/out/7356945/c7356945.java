class c7356945 {

    public void run() {
        try {
            if (JavaCIPUnknownScope.LOGGER.isDebugEnabled()) {
                JavaCIPUnknownScope.LOGGER.debug("Checking for updates at " + JavaCIPUnknownScope.checkUrl);
            }
            URL url = new URL(JavaCIPUnknownScope.checkUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuffer content = new StringBuffer();
                String s = reader.readLine();
                while (s != null) {
                    content.append(s);
                    s = reader.readLine();
                }
                JavaCIPUnknownScope.LOGGER.info("update-available", content.toString());
            } else if (JavaCIPUnknownScope.LOGGER.isDebugEnabled()) {
                JavaCIPUnknownScope.LOGGER.debug("No update available (Response code " + connection.getResponseCode() + ")");
            }
        } catch (RuntimeException e) {
            if (JavaCIPUnknownScope.LOGGER.isDebugEnabled()) {
                JavaCIPUnknownScope.LOGGER.debug("Update check failed", e);
            }
        }
    }
}
