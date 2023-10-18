class c8943482 {

    public InputStream getImageAsStream(Photo photo, int size) throws IORuntimeException, FlickrRuntimeException {
        String urlStr = "";
        if (size == Size.SQUARE) {
            urlStr = photo.getSmallSquareUrl();
        } else if (size == Size.THUMB) {
            urlStr = photo.getThumbnailUrl();
        } else if (size == Size.SMALL) {
            urlStr = photo.getSmallUrl();
        } else if (size == Size.MEDIUM) {
            urlStr = photo.getMediumUrl();
        } else if (size == Size.LARGE) {
            urlStr = photo.getLargeUrl();
        } else if (size == Size.ORIGINAL) {
            urlStr = photo.getOriginalUrl();
        } else {
            throw new FlickrRuntimeException("0", "Unknown Photo-size");
        }
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        if (JavaCIPUnknownScope.transport instanceof REST) {
            if (((REST) JavaCIPUnknownScope.transport).isProxyAuth()) {
                conn.setRequestProperty("Proxy-Authorization", "Basic " + ((REST) JavaCIPUnknownScope.transport).getProxyCredentials());
            }
        }
        conn.connect();
        return conn.getInputStream();
    }
}
