class c5620476 {

    private String[] verifyConnection(Socket clientConnection) throws Exception {
        List<String> requestLines = new ArrayList<String>();
        InputStream is = clientConnection.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringTokenizer st = new StringTokenizer(in.readLine());
        if (!st.hasMoreTokens()) {
            throw new IllegalArgumentException("There's no method token in this connection");
        }
        String method = st.nextToken();
        if (!st.hasMoreTokens()) {
            throw new IllegalArgumentException("There's no URI token in this connection");
        }
        String s = st.nextToken();
        String uri = JavaCIPUnknownScope.decodePercent(s);
        if (!st.hasMoreTokens()) {
            throw new IllegalArgumentException("There's no version token in this connection");
        }
        String version = st.nextToken();
        Properties parms = new Properties();
        int qmi = uri.indexOf('?');
        if (qmi >= 0) {
            JavaCIPUnknownScope.decodeParms(uri.substring(qmi + 1), parms);
            uri = JavaCIPUnknownScope.decodePercent(uri.substring(0, qmi));
        }
        String params = "";
        if (parms.size() > 0) {
            params = "?";
            for (Object key : parms.keySet()) {
                params = params + key + "=" + parms.getProperty(((String) key)) + "&";
            }
            params = params.substring(0, params.length() - 1);
            params = params.replace(" ", "%20");
        }
        JavaCIPUnknownScope.logger.debug("HTTP Request: " + method + " " + uri + params + " " + version);
        requestLines.add(method + " " + uri + params + " " + version);
        Properties headerVars = new Properties();
        String additionalData = "";
        for (Object var : headerVars.keySet()) {
            requestLines.add(var + ": " + headerVars.get(var));
        }
        if (!additionalData.equals("")) {
            requestLines.add("ADDITIONAL" + additionalData);
        }
        return requestLines.toArray(new String[requestLines.size()]);
    }
}
