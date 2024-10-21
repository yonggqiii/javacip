class c14188043 {

    public static byte[] openHttpResult(String urlPath, boolean retry) throws IORuntimeException {
        AQUtility.debug("net", urlPath);
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(JavaCIPUnknownScope.NET_TIMEOUT);
        int code = connection.getResponseCode();
        if (code == 307 && retry) {
            String redirect = connection.getHeaderField("Location");
            return openHttpResult(redirect, false);
        }
        if (code == -1 && retry) {
            return openHttpResult(urlPath, false);
        }
        AQUtility.debug("response", code);
        if (code == -1 || code < 200 || code >= 300) {
            throw new IORuntimeException();
        }
        byte[] result = AQUtility.toBytes(connection.getInputStream());
        return result;
    }
}
