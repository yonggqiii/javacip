class c14638789 {

    public static InputStream sendReq(String url, String content, ConnectData data) {
        try {
            URLConnection con = new URL(url).openConnection();
            con.setConnectTimeout(JavaCIPUnknownScope.TIMEOUT);
            con.setReadTimeout(JavaCIPUnknownScope.TIMEOUT);
            con.setUseCaches(false);
            JavaCIPUnknownScope.setUA(con);
            con.setRequestProperty("Accept-Charset", "utf-8");
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            if (data.cookie != null)
                con.setRequestProperty("Cookie", data.cookie);
            HttpURLConnection httpurl = (HttpURLConnection) con;
            httpurl.setRequestMethod("POST");
            Writer wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(content);
            wr.flush();
            con.connect();
            InputStream is = con.getInputStream();
            is = new BufferedInputStream(is);
            wr.close();
            JavaCIPUnknownScope.parseCookie(con, data);
            return is;
        } catch (IORuntimeException ioe) {
            Log.except("failed to send request " + url, ioe);
        }
        return null;
    }
}
