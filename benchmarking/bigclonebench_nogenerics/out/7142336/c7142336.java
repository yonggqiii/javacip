class c7142336 {

    public int getHttpStatus(ProxyInfo proxyInfo, String sUrl, String cookie, String host) {
        HttpURLConnection connection = null;
        try {
            if (proxyInfo == null) {
                URL url = new URL(sUrl);
                connection = (HttpURLConnection) url.openConnection();
            } else {
                InetSocketAddress addr = new InetSocketAddress(proxyInfo.getPxIp(), proxyInfo.getPxPort());
                Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
                URL url = new URL(sUrl);
                connection = (HttpURLConnection) url.openConnection(proxy);
            }
            if (!JavaCIPUnknownScope.isStringNull(host))
                JavaCIPUnknownScope.setHttpInfo(connection, cookie, host, "");
            connection.setConnectTimeout(90 * 1000);
            connection.setReadTimeout(90 * 1000);
            connection.connect();
            connection.getInputStream();
            return connection.getResponseCode();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.log.info(proxyInfo + " getHTTPConent Error ");
            return 0;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.log.info(proxyInfo + " getHTTPConent Error ");
            return 0;
        }
    }
}
