


class c18629449 {

    private InputStream getSearchInputStream(String name) {
        URL url = null;
        try {
            url = new URL(TheMovieDBXmlPullFeedParser.SEARCH_FEED_URL + URLEncoder.encode(name));
            Log.d(Constants.LOG_TAG, "Movie search URL: " + url);
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
