class c2016232 {

    protected void initConnection() {
        JavaCIPUnknownScope.connection = null;
        try {
            JavaCIPUnknownScope.connection = (HttpURLConnection) JavaCIPUnknownScope.url.openConnection();
            JavaCIPUnknownScope.connection.setRequestProperty("Range", "bytes=" + JavaCIPUnknownScope.downloadedSize + "-");
            JavaCIPUnknownScope.prepareConnectionBeforeConnect();
            JavaCIPUnknownScope.connection.connect();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.status = JavaCIPUnknownScope.STATUS_ERROR;
            Logger.getRootLogger().error("problem in connection", e);
        }
    }
}
