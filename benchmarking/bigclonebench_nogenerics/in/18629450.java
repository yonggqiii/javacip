


class c18629450 {

    private InputStream getInfoInputStream(String tmdbId) {
        URL url = null;
        try {
            url = new URL(TheMovieDBXmlPullFeedParser.INFO_FEED_URL + URLEncoder.encode(tmdbId));
            Log.d(Constants.LOG_TAG, "Movie info URL: " + url);
        } catch (MalformedURLRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
        try {
            return url.openConnection().getInputStream();
        } catch (IORuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
