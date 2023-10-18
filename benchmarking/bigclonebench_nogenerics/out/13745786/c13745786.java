class c13745786 {

    private String fetchHtml(URL url) throws IORuntimeException {
        URLConnection connection;
        if (StringUtils.isNotBlank(JavaCIPUnknownScope.proxyHost) && JavaCIPUnknownScope.proxyPort != null) {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(JavaCIPUnknownScope.proxyHost, JavaCIPUnknownScope.proxyPort));
            connection = url.openConnection(proxy);
        } else {
            connection = url.openConnection();
        }
        Object content = connection.getContent();
        if (content instanceof InputStream) {
            return IOUtils.toString(InputStream.class.cast(content));
        } else {
            String msg = "Bad content type! " + content.getClass();
            JavaCIPUnknownScope.log.error(msg);
            throw new IORuntimeException(msg);
        }
    }
}
