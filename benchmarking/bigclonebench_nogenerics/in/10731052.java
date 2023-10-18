


class c10731052 {

    public HttpURLConnection getTileUrlConnection(int zoom, int tilex, int tiley) throws IORuntimeException {
        String url = getTileUrl(zoom, tilex, tiley);
        if (url == null) return null;
        return (HttpURLConnection) new URL(url).openConnection();
    }

}
