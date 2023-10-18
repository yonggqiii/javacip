


class c22347366 {

    private static BufferedReader createReaderConnection(String urlString) throws SiteNotFoundRuntimeException {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("User-agent", "Mozilla/4.5");
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Logger.logln("Response code for url [" + urlString + "] was " + conn.getResponseCode() + " [" + conn.getResponseMessage() + "]");
                throw new SiteNotFoundRuntimeException(urlString);
            }
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } catch (IORuntimeException ex) {
            Logger.logln("" + ex);
        }
        return reader;
    }

}
