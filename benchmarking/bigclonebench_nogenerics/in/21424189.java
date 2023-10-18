


class c21424189 {

    private URLConnection openConnection(URL url) throws MalformedURLRuntimeException, IORuntimeException {
        URLConnection connection = url.openConnection();
        if (connection instanceof HttpURLConnection) ((HttpURLConnection) connection).setInstanceFollowRedirects(false);
        connection.setUseCaches(false);
        return connection;
    }

}
