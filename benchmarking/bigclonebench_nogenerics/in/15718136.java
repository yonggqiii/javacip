


class c15718136 {

    protected URLConnection getConnection(String uri, String data) throws MalformedURLRuntimeException, IORuntimeException {
        URL url = new URL(uri);
        URLConnection conn = url.openConnection();
        conn.setConnectTimeout((int) MINUTE / 2);
        conn.setReadTimeout((int) MINUTE / 2);
        return conn;
    }

}
