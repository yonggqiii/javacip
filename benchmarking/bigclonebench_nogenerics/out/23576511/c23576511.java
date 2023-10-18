class c23576511 {

    public void connect(String ftpHost, int ftpPort, String ftpUser, String ftpPwd) throws IORuntimeException {
        JavaCIPUnknownScope.ftpClient = new FTPClient();
        JavaCIPUnknownScope.ftpClient.setReaderThread(false);
        if (ftpPort == -1)
            JavaCIPUnknownScope.ftpClient.connect(ftpHost);
        else
            JavaCIPUnknownScope.ftpClient.connect(ftpHost, ftpPort);
        JavaCIPUnknownScope.logger.info("FTP Connection Successful: " + ftpHost);
        JavaCIPUnknownScope.ftpClient.login(ftpUser, ftpPwd);
    }
}
