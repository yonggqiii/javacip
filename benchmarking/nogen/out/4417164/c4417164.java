class c4417164 {

    private InputStream connectURL(String aurl) throws IORuntimeException {
        InputStream in = null;
        int response = -1;
        URL url = new URL(aurl);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IORuntimeException("Not an HTTP connection.");
        HttpURLConnection httpConn = (HttpURLConnection) conn;
        response = JavaCIPUnknownScope.getResponse(httpConn);
        if (response == HttpURLConnection.HTTP_OK) {
            in = httpConn.getInputStream();
        } else
            throw new IORuntimeException("Response Code: " + response);
        return in;
    }
}
