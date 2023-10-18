class c15984793 {

    public InputStream getPage(String page) throws IORuntimeException {
        URL url = new URL(JavaCIPUnknownScope.hattrickServerURL + "/Common/" + page);
        HttpURLConnection huc = (HttpURLConnection) url.openConnection();
        huc.setRequestProperty("Cookie", JavaCIPUnknownScope.sessionCookie);
        return huc.getInputStream();
    }
}
