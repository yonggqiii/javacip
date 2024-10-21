class c13546467 {

    private FTPClient getClient() throws SocketRuntimeException, IORuntimeException {
        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
        ftp.setDefaultPort(JavaCIPUnknownScope.getPort());
        ftp.connect(JavaCIPUnknownScope.getIp());
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            JavaCIPUnknownScope.log.warn("FTP server refused connection: {}", JavaCIPUnknownScope.getIp());
            ftp.disconnect();
            return null;
        }
        if (!ftp.login(JavaCIPUnknownScope.getUsername(), JavaCIPUnknownScope.getPassword())) {
            JavaCIPUnknownScope.log.warn("FTP server refused login: {}, user: {}", JavaCIPUnknownScope.getIp(), JavaCIPUnknownScope.getUsername());
            ftp.logout();
            ftp.disconnect();
            return null;
        }
        ftp.setControlEncoding(JavaCIPUnknownScope.getEncoding());
        ftp.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        return ftp;
    }
}
