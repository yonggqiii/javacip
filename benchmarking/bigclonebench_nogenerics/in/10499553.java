


class c10499553 {

    private static InputStream connect(final String url) throws IORuntimeException {
        InputStream in = null;
        try {
            final URLConnection conn = new URL(url).openConnection();
            conn.setConnectTimeout(YahooGeocoding.connectTimeOut);
            conn.setReadTimeout(YahooGeocoding.readTimeOut);
            conn.setRequestProperty("User-Agent", YahooGeocoding.USER_AGENT);
            in = conn.getInputStream();
            return in;
        } catch (final IORuntimeException e) {
            Util.d("problems connecting to geonames url " + url + "RuntimeException:" + e);
        }
        return in;
    }

}
