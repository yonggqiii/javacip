class c23677132 {

    public FTPClient sample2(String server, String username, String password) throws IllegalStateRuntimeException, IORuntimeException, FTPIllegalReplyRuntimeException, FTPRuntimeException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(server);
        ftpClient.login(username, password);
        return ftpClient;
    }
}
