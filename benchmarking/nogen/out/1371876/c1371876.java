class c1371876 {

    private HttpURLConnection getConnection() throws IORuntimeException {
        HttpURLConnection conn = (HttpURLConnection) new URL(JavaCIPUnknownScope.url).openConnection();
        conn.setRequestMethod(JavaCIPUnknownScope.method);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        if (JavaCIPUnknownScope.cookie != null)
            conn.setRequestProperty("Cookie", JavaCIPUnknownScope.cookie);
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
        conn.setRequestProperty("User-Agent", Constants.USER_AGENT());
        conn.connect();
        if (!JavaCIPUnknownScope.parameters.equals("")) {
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(JavaCIPUnknownScope.parameters);
            out.flush();
            out.close();
        }
        return conn;
    }
}
