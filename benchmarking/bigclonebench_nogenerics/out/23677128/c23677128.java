class c23677128 {

    public FTPClient sample3a(String ftpserver, int ftpport, String proxyserver, int proxyport, String username, String password) throws SocketRuntimeException, IORuntimeException {
        FTPHTTPClient ftpClient = new FTPHTTPClient(proxyserver, proxyport);
        ftpClient.connect(ftpserver, ftpport);
        ftpClient.login(username, password);
        return ftpClient;
    }
}
