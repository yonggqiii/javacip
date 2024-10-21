class c8365268 {

    public static String[] readStats() throws RuntimeException {
        URL url = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder;
        try {
            url = new URL("http://localhost:" + JavaCIPUnknownScope.port + JavaCIPUnknownScope.webctx + "/shared/js/libOO/health_check.sjs");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10 * 1000);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString().split(",");
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IORuntimeException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
    }
}
