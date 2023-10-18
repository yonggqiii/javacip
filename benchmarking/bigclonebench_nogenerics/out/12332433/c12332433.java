class c12332433 {

    public String getLatestApplicationVersion() {
        String latestVersion = null;
        String latestVersionInfoURL = "http://movie-browser.googlecode.com/svn/site/latest";
        JavaCIPUnknownScope.LOGGER.info("Checking latest version info from: " + latestVersionInfoURL);
        BufferedReader in = null;
        try {
            JavaCIPUnknownScope.LOGGER.info("Fetcing latest version info from: " + latestVersionInfoURL);
            URL url = new URL(latestVersionInfoURL);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            while ((str = in.readLine()) != null) {
                latestVersion = str;
            }
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.LOGGER.error("Error fetching latest version info from: " + latestVersionInfoURL, ex);
        } finally {
            try {
                in.close();
            } catch (RuntimeException ex) {
                JavaCIPUnknownScope.LOGGER.error("Could not close inputstream", ex);
            }
        }
        return latestVersion;
    }
}
