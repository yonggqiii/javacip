class c3151940 {

    String readRss(URL url) {
        String html = "<html><body><h2>No data</h2></body></html>";
        try {
            JavaCIPUnknownScope.mLogger.info("URL is:" + url.toString());
            BufferedReader inStream = new BufferedReader(new InputStreamReader(url.openStream()), 1024);
            String line;
            StringBuilder rssFeed = new StringBuilder();
            while ((line = inStream.readLine()) != null) {
                rssFeed.append(line);
            }
            html = rssFeed.toString();
        } catch (IORuntimeException ex) {
            JavaCIPUnknownScope.mLogger.warning("Couldn't open an RSS stream");
        }
        return html;
    }
}
