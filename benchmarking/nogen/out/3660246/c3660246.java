class c3660246 {

    private Object query(String json) throws IORuntimeException, ParseRuntimeException {
        String envelope = "{\"qname1\":{\"query\":" + json + "}}";
        String urlStr = JavaCIPUnknownScope.MQLREADURL + "?queries=" + URLEncoder.encode(envelope, "UTF-8");
        if (JavaCIPUnknownScope.isDebugging()) {
            if (JavaCIPUnknownScope.echoRequest)
                System.err.println("Sending:" + envelope);
        }
        URL url = new URL(urlStr);
        URLConnection con = url.openConnection();
        con.setRequestProperty("Cookie", JavaCIPUnknownScope.COOKIE + "=" + "\"" + JavaCIPUnknownScope.getMetawebCookie() + "\"");
        con.connect();
        InputStream in = con.getInputStream();
        Object item = new JSONParser(JavaCIPUnknownScope.echoRequest ? new EchoReader(in) : in).object();
        in.close();
        String code = JavaCIPUnknownScope.getString(item, "code");
        if (!"/api/status/ok".equals(code)) {
            throw new IORuntimeException("Bad code " + item);
        }
        code = JavaCIPUnknownScope.getString(item, "qname1.code");
        if (!"/api/status/ok".equals(code)) {
            throw new IORuntimeException("Bad code " + item);
        }
        return item;
    }
}
