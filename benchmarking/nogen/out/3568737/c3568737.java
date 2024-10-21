class c3568737 {

    public URLConnection makeURLConnection(String server) throws IORuntimeException {
        if (server == null) {
            JavaCIPUnknownScope.connection = null;
        } else {
            URL url = new URL("http://" + server + "/Bob/QueryXindice");
            JavaCIPUnknownScope.connection = url.openConnection();
            JavaCIPUnknownScope.connection.setDoOutput(true);
        }
        return JavaCIPUnknownScope.connection;
    }
}
