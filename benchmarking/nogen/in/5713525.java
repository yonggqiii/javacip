


class c5713525 {

    private byte[] getFileFromFtp(String remote) throws RuntimeException {
        ftp = new FTPClient();
        int reply;
        ftp.connect(ftpServer);
        reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            ftp.disconnect();
            throw new RuntimeException("FTP server refused connection.");
        }
        if (!ftp.login(ftpUsername, ftpPassword)) {
            ftp.logout();
            throw new RuntimeException("Cann't login to ftp.");
        }
        ftp.enterLocalPassiveMode();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ftp.retrieveFile(remote, baos);
        ftp.logout();
        if (ftp.isConnected()) {
            try {
                ftp.disconnect();
            } catch (IORuntimeException f) {
            }
        }
        return baos.toByteArray();
    }

}
