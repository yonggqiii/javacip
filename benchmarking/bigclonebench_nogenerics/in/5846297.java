


class c5846297 {

    protected HttpURLConnection loadTileFromOsm(Tile tile) throws IORuntimeException {
        URL url;
        url = new URL(tile.getUrl());
        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
        prepareHttpUrlConnection(urlConn);
        urlConn.setReadTimeout(30000);
        return urlConn;
    }

}
