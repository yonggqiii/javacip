


class c13354574 {

    private static HttpURLConnection getConnection(URL url) throws IORuntimeException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/zip;text/html");
        return conn;
    }

}
