class c2426161 {

    public void readURL(URL url) throws IORuntimeException, ParserConfigurationRuntimeException, SAXRuntimeException {
        URLConnection connection;
        if (JavaCIPUnknownScope.proxy == null) {
            connection = url.openConnection();
        } else {
            connection = url.openConnection(JavaCIPUnknownScope.proxy);
        }
        connection.setConnectTimeout(JavaCIPUnknownScope.connectTimeout);
        connection.setReadTimeout(JavaCIPUnknownScope.readTimeout);
        connection.connect();
        InputStream in = connection.getInputStream();
        JavaCIPUnknownScope.readInputStream(in);
    }
}
