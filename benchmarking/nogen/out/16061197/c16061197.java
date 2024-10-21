class c16061197 {

    public String getContent() throws IORuntimeException {
        String result = new String();
        if (JavaCIPUnknownScope.url == null)
            return null;
        JavaCIPUnknownScope.conn = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
        JavaCIPUnknownScope.conn.setRequestProperty("User-Agent", "Internet Explorer");
        JavaCIPUnknownScope.conn.setReadTimeout(50000);
        JavaCIPUnknownScope.conn.connect();
        JavaCIPUnknownScope.httpReader = new BufferedReader(new InputStreamReader(JavaCIPUnknownScope.conn.getInputStream()));
        String str = JavaCIPUnknownScope.httpReader.readLine();
        while (str != null) {
            result += str;
            str = JavaCIPUnknownScope.httpReader.readLine();
        }
        return result;
    }
}
