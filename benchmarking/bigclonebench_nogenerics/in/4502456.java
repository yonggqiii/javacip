


class c4502456 {

    public void initGet() throws RuntimeException {
        cl = new FTPClient();
        URL url = new URL(getURL());
        cl.setRemoteHost(url.getHost());
        cl.connect();
        cl.login(user, pass);
        cl.setType(FTPTransferType.BINARY);
        cl.setConnectMode(FTPConnectMode.PASV);
        cl.restart(getPosition());
    }

}
