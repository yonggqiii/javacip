class c8249982 {

    public void getDownloadInfo(String _url) throws RuntimeException {
        URL url = new URL(_url);
        JavaCIPUnknownScope.con = (HttpURLConnection) url.openConnection();
        JavaCIPUnknownScope.con.setRequestProperty("User-Agent", "test");
        JavaCIPUnknownScope.con.setRequestProperty("Accept", "*/*");
        JavaCIPUnknownScope.con.setRequestProperty("Range", "bytes=0-");
        JavaCIPUnknownScope.con.setRequestMethod("HEAD");
        JavaCIPUnknownScope.con.setUseCaches(false);
        JavaCIPUnknownScope.con.connect();
        JavaCIPUnknownScope.con.disconnect();
        if (JavaCIPUnknownScope.mustRedirect())
            JavaCIPUnknownScope.secureRedirect();
        url = JavaCIPUnknownScope.con.getURL();
        JavaCIPUnknownScope.setURL(url.toString());
        JavaCIPUnknownScope.setSize(Long.parseLong(JavaCIPUnknownScope.con.getHeaderField("Content-Length")));
        JavaCIPUnknownScope.setResumable(JavaCIPUnknownScope.con.getResponseCode() == 206);
        JavaCIPUnknownScope.setLastModified(JavaCIPUnknownScope.con.getLastModified());
        JavaCIPUnknownScope.setRangeEnd(JavaCIPUnknownScope.getSize() - 1);
    }
}
