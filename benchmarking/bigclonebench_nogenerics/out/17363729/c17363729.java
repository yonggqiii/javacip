class c17363729 {

    private String sendImpl(String from, String destNumber, String text) throws IORuntimeException {
        final QueryStringBuilder query = new QueryStringBuilder();
        query.append("user", JavaCIPUnknownScope.username);
        query.append("password", JavaCIPUnknownScope.password);
        query.append("api_id", JavaCIPUnknownScope.apiId);
        query.append("to", destNumber);
        if (from != null) {
            query.append("from", from);
        }
        query.append("text", text);
        final URL url = new URL(JavaCIPUnknownScope.CLICKATELL_GATEWAY_URL + "sendmsg" + query.toString());
        final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.connect();
        final BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        try {
            return br.readLine();
        } finally {
            br.close();
        }
    }
}
