class c18962382 {

    protected void connect() throws SocketRuntimeException, IORuntimeException, LoginFailRuntimeException {
        JavaCIPUnknownScope.logger.info("Connect to FTP Server " + JavaCIPUnknownScope.account.getServer());
        JavaCIPUnknownScope.client = new FTPClient();
        JavaCIPUnknownScope.client.connect(JavaCIPUnknownScope.account.getServer());
        if (JavaCIPUnknownScope.client.login(JavaCIPUnknownScope.account.getId(), JavaCIPUnknownScope.account.getPassword()) == false) {
            JavaCIPUnknownScope.logger.info("Fail to login with id=" + JavaCIPUnknownScope.account.getId());
            throw new LoginFailRuntimeException(JavaCIPUnknownScope.account.getId(), JavaCIPUnknownScope.account.getPassword());
        }
    }
}
