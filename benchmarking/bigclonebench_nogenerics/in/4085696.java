


class c4085696 {

    private static FTPClient getFtpClient(String ftpHost, String ftpUsername, String ftpPassword) throws SocketRuntimeException, IORuntimeException {
        FTPClient ftp = new FTPClient();
        ftp.connect(ftpHost);
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            return null;
        }
        if (!ftp.login(ftpUsername, ftpPassword)) {
            return null;
        }
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        ftp.enterLocalPassiveMode();
        return ftp;
    }

}
