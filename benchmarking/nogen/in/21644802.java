


class c21644802 {

    private URLConnection openGetConnection(StringBuffer sb) throws IORuntimeException, IORuntimeException, MalformedURLRuntimeException {
        URL url = new URL(m_gatewayAddress + "?" + sb.toString());
        URLConnection connection = url.openConnection();
        connection.setUseCaches(false);
        return connection;
    }

}
