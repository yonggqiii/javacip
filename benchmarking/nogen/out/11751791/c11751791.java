class c11751791 {

    private void connect(URL url) throws IORuntimeException {
        String protocol = url.getProtocol();
        if (!protocol.equals("http"))
            throw new IllegalArgumentRuntimeException("URL must use 'http:' protocol");
        int port = url.getPort();
        if (port == -1)
            port = 80;
        JavaCIPUnknownScope.fileName = url.getFile();
        JavaCIPUnknownScope.conn = (HttpURLConnection) url.openConnection();
        JavaCIPUnknownScope.conn.setRequestMethod("POST");
        JavaCIPUnknownScope.conn.setDoOutput(true);
        JavaCIPUnknownScope.conn.setDoInput(true);
        JavaCIPUnknownScope.toServer = new OutputStreamWriter(JavaCIPUnknownScope.conn.getOutputStream());
        JavaCIPUnknownScope.fromServer = JavaCIPUnknownScope.conn.getInputStream();
    }
}
