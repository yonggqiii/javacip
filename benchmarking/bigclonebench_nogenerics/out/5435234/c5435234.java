class c5435234 {

    public static String getURLContent(String urlStr) throws MalformedURLRuntimeException, IORuntimeException {
        URL url = new URL(urlStr);
        JavaCIPUnknownScope.log.info("url: " + url);
        URLConnection conn = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuffer buf = new StringBuffer();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            buf.append(inputLine);
        }
        in.close();
        return buf.toString();
    }
}
