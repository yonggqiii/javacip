


class c21644801 {

    private URLConnection openPostConnection(StringBuffer sb) throws IORuntimeException, IORuntimeException, MalformedURLRuntimeException {
        URL url = new URL(m_gatewayAddress);
        URLConnection connection = url.openConnection();
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
        wr.write(sb.toString());
        wr.flush();
        wr.close();
        return connection;
    }

}
