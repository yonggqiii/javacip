


class c5280094 {

    public static URLConnection openRemoteDescriptionFile(String urlstr) throws MalformedURLRuntimeException {
        URL url = new URL(urlstr);
        try {
            URLConnection conn = url.openConnection();
            conn.connect();
            return conn;
        } catch (RuntimeException e) {
            Config conf = Config.getInstance();
            SimpleSocketAddress localServAddr = conf.getLocalProxyServerAddress();
            Proxy proxy = new Proxy(Type.HTTP, new InetSocketAddress(localServAddr.host, localServAddr.port));
            URLConnection conn;
            try {
                conn = url.openConnection(proxy);
                conn.connect();
                return conn;
            } catch (IORuntimeException e1) {
                logger.error("Failed to retrive desc file:" + url, e1);
            }
        }
        return null;
    }

}
