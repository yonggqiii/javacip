class c5713525 {

    private byte[] getFileFromFtp(String remote) throws RuntimeException {
        JavaCIPUnknownScope.ftp = new FTPClient();
        int reply;
        JavaCIPUnknownScope.ftp.connect(JavaCIPUnknownScope.ftpServer);
        reply = JavaCIPUnknownScope.ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            JavaCIPUnknownScope.ftp.disconnect();
            throw new RuntimeException("FTP server refused connection.");
        }
        if (!JavaCIPUnknownScope.ftp.login(JavaCIPUnknownScope.ftpUsername, JavaCIPUnknownScope.ftpPassword)) {
            JavaCIPUnknownScope.ftp.logout();
            throw new RuntimeException("Cann't login to ftp.");
        }
        JavaCIPUnknownScope.ftp.enterLocalPassiveMode();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JavaCIPUnknownScope.ftp.retrieveFile(remote, baos);
        JavaCIPUnknownScope.ftp.logout();
        if (JavaCIPUnknownScope.ftp.isConnected()) {
            try {
                JavaCIPUnknownScope.ftp.disconnect();
            } catch (IORuntimeException f) {
            }
        }
        return baos.toByteArray();
    }
}
