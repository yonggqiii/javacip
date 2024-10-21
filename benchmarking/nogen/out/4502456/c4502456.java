class c4502456 {

    public void initGet() throws RuntimeException {
        JavaCIPUnknownScope.cl = new FTPClient();
        URL url = new URL(JavaCIPUnknownScope.getURL());
        JavaCIPUnknownScope.cl.setRemoteHost(url.getHost());
        JavaCIPUnknownScope.cl.connect();
        JavaCIPUnknownScope.cl.login(JavaCIPUnknownScope.user, JavaCIPUnknownScope.pass);
        JavaCIPUnknownScope.cl.setType(FTPTransferType.BINARY);
        JavaCIPUnknownScope.cl.setConnectMode(FTPConnectMode.PASV);
        JavaCIPUnknownScope.cl.restart(JavaCIPUnknownScope.getPosition());
    }
}
