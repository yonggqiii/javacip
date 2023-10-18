class c14638788 {

    public static InputStream openURL(String url, ConnectData data) {
        try {
            URLConnection con = new URL(url).openConnection();
            con.setConnectTimeout(JavaCIPUnknownScope.TIMEOUT);
            con.setReadTimeout(JavaCIPUnknownScope.TIMEOUT);
            con.setUseCaches(false);
            con.setRequestProperty("Accept-Charset", "utf-8");
            JavaCIPUnknownScope.setUA(con);
            if (data.cookie != null)
                con.setRequestProperty("Cookie", data.cookie);
            InputStream is = con.getInputStream();
            JavaCIPUnknownScope.parseCookie(con, data);
            return new BufferedInputStream(is);
        } catch (IORuntimeException ioe) {
            Log.except("failed to open URL " + url, ioe);
        }
        return null;
    }
}
