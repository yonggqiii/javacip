


class c16061197 {

    public String getContent() throws IORuntimeException {
        String result = new String();
        if (url == null) return null;
        conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("User-Agent", "Internet Explorer");
        conn.setReadTimeout(50000);
        conn.connect();
        httpReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String str = httpReader.readLine();
        while (str != null) {
            result += str;
            str = httpReader.readLine();
        }
        return result;
    }

}
