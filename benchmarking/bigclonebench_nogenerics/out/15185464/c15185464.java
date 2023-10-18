class c15185464 {

    public void scan() throws Throwable {
        JavaCIPUnknownScope.client = new FTPClient();
        JavaCIPUnknownScope.log.info("connecting to " + JavaCIPUnknownScope.host + "...");
        JavaCIPUnknownScope.client.connect(JavaCIPUnknownScope.host);
        JavaCIPUnknownScope.log.info(JavaCIPUnknownScope.client.getReplyString());
        JavaCIPUnknownScope.log.info("logging in...");
        JavaCIPUnknownScope.client.login("anonymous", "");
        JavaCIPUnknownScope.log.info(JavaCIPUnknownScope.client.getReplyString());
        Date date = Calendar.getInstance().getTime();
        JavaCIPUnknownScope.xmlDocument = new XMLDocument(JavaCIPUnknownScope.host, JavaCIPUnknownScope.dir, date);
        JavaCIPUnknownScope.scanDirectory(JavaCIPUnknownScope.dir);
    }
}
