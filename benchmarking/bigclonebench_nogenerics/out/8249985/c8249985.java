class c8249985 {

    public void initGet() throws RuntimeException {
        URL url = new URL(JavaCIPUnknownScope.getURL());
        JavaCIPUnknownScope.con = (HttpURLConnection) url.openConnection();
        JavaCIPUnknownScope.con.setRequestProperty("Accept", "*/*");
        JavaCIPUnknownScope.con.setRequestProperty("Range", "bytes=" + JavaCIPUnknownScope.getPosition() + "-" + JavaCIPUnknownScope.getRangeEnd());
        JavaCIPUnknownScope.con.setUseCaches(false);
        JavaCIPUnknownScope.con.connect();
        JavaCIPUnknownScope.setInputStream(JavaCIPUnknownScope.con.getInputStream());
    }
}
