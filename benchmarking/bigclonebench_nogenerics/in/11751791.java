


class c11751791 {

    private void connect(URL url) throws IORuntimeException {
        String protocol = url.getProtocol();
        if (!protocol.equals("http")) throw new IllegalArgumentRuntimeException("URL must use 'http:' protocol");
        int port = url.getPort();
        if (port == -1) port = 80;
        fileName = url.getFile();
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        toServer = new OutputStreamWriter(conn.getOutputStream());
        fromServer = conn.getInputStream();
    }

}
