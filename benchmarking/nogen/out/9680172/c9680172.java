class c9680172 {

    public HttpURLConnection openConnection(String url) throws IORuntimeException {
        if (JavaCIPUnknownScope.isDebugMode())
            System.out.println("open: " + url);
        URL u = new URL(url);
        HttpURLConnection urlConnection;
        if (JavaCIPUnknownScope.proxy != null)
            urlConnection = (HttpURLConnection) u.openConnection(JavaCIPUnknownScope.proxy);
        else
            urlConnection = (HttpURLConnection) u.openConnection();
        urlConnection.setRequestProperty("User-Agent", JavaCIPUnknownScope.userAgent);
        return urlConnection;
    }
}
