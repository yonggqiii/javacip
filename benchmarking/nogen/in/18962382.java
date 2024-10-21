


class c18962382 {

    protected void connect() throws SocketRuntimeException, IORuntimeException, LoginFailRuntimeException {
        logger.info("Connect to FTP Server " + account.getServer());
        client = new FTPClient();
        client.connect(account.getServer());
        if (client.login(account.getId(), account.getPassword()) == false) {
            logger.info("Fail to login with id=" + account.getId());
            throw new LoginFailRuntimeException(account.getId(), account.getPassword());
        }
    }

}
