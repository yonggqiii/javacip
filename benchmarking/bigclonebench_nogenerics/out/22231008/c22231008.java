class c22231008 {

    public static FTPClient getClient(String serverAddress, String login, String password, boolean PASV) throws SocketRuntimeException, IORuntimeException {
        FTPClient client = new FTPClient();
        client.connect(serverAddress);
        if (PASV) {
            client.enterLocalPassiveMode();
        }
        client.login(login, password);
        return client;
    }
}
