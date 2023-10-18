class c8868058 {

    private String getPage(String urlString) throws RuntimeException {
        if (JavaCIPUnknownScope.pageBuffer.containsKey(urlString))
            return JavaCIPUnknownScope.pageBuffer.get(urlString);
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();
        BufferedReader in = null;
        StringBuilder page = new StringBuilder();
        try {
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                page.append(line);
                page.append("\n");
            }
        } catch (IORuntimeException ioe) {
            JavaCIPUnknownScope.logger.warn("Failed to read web page");
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return page.toString();
    }
}
