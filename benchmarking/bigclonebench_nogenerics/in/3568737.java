


class c3568737 {

    public URLConnection makeURLConnection(String server) throws IORuntimeException {
        if (server == null) {
            connection = null;
        } else {
            URL url = new URL("http://" + server + "/Bob/QueryXindice");
            connection = url.openConnection();
            connection.setDoOutput(true);
        }
        return connection;
    }

}
