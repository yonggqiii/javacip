class c5770148 {

    public static void pingSearchEngine(String engineURL) throws MalformedURLRuntimeException, UnsupportedEncodingRuntimeException {
        if ((ConfigurationManager.getProperty("http.proxy.host") != null) && (ConfigurationManager.getProperty("http.proxy.port") != null)) {
            System.setProperty("proxySet", "true");
            System.setProperty("proxyHost", ConfigurationManager.getProperty("http.proxy.host"));
            System.getProperty("proxyPort", ConfigurationManager.getProperty("http.proxy.port"));
        }
        String sitemapURL = ConfigurationManager.getProperty("dspace.url") + "/sitemap";
        URL url = new URL(engineURL + URLEncoder.encode(sitemapURL, "UTF-8"));
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer resp = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                resp.append(inputLine).append("\n");
            }
            in.close();
            if (connection.getResponseCode() == 200) {
                JavaCIPUnknownScope.log.info("Pinged " + url.toString() + " successfully");
            } else {
                JavaCIPUnknownScope.log.warn("Error response pinging " + url.toString() + ":\n" + resp);
            }
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.log.warn("Error pinging " + url.toString(), e);
        }
    }
}
