class c8942491 {

    public static URLConnection openProxiedConnection(URL url) throws IORuntimeException {
        if (JavaCIPUnknownScope.proxyHost != null) {
            System.getProperties().put("proxySet", "true");
            System.getProperties().put("proxyHost", JavaCIPUnknownScope.proxyHost);
            System.getProperties().put("proxyPort", JavaCIPUnknownScope.proxyPort);
        }
        URLConnection cnx = url.openConnection();
        if (JavaCIPUnknownScope.proxyUsername != null) {
            cnx.setRequestProperty("Proxy-Authorization", JavaCIPUnknownScope.proxyEncodedPassword);
        }
        return cnx;
    }
}
