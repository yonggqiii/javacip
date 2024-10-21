class c19401776 {

    public HttpURLConnection connect() throws IORuntimeException {
        if (JavaCIPUnknownScope.url == null) {
            return null;
        }
        HttpURLConnection connection = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
        if (JavaCIPUnknownScope.previousETag != null) {
            connection.addRequestProperty("If-None-Match", JavaCIPUnknownScope.previousETag);
        }
        if (JavaCIPUnknownScope.previousLastModified != null) {
            connection.addRequestProperty("If-Modified-Since", JavaCIPUnknownScope.previousLastModified);
        }
        return connection;
    }
}
