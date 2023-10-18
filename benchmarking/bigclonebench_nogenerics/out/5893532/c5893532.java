class c5893532 {

    public static void upload(FTPDetails ftpDetails) {
        FTPClient ftp = new FTPClient();
        try {
            String host = ftpDetails.getHost();
            JavaCIPUnknownScope.logger.info("Connecting to ftp host: " + host);
            ftp.connect(host);
            JavaCIPUnknownScope.logger.info("Received reply from ftp :" + ftp.getReplyString());
            ftp.login(ftpDetails.getUserName(), ftpDetails.getPassword());
            ftp.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
            ftp.makeDirectory(ftpDetails.getRemoterDirectory());
            JavaCIPUnknownScope.logger.info("Created directory :" + ftpDetails.getRemoterDirectory());
            ftp.changeWorkingDirectory(ftpDetails.getRemoterDirectory());
            BufferedInputStream ftpInput = new BufferedInputStream(new FileInputStream(new File(ftpDetails.getLocalFilePath())));
            OutputStream storeFileStream = ftp.storeFileStream(ftpDetails.getRemoteFileName());
            IOUtils.copy(ftpInput, storeFileStream);
            JavaCIPUnknownScope.logger.info("Copied file : " + ftpDetails.getLocalFilePath() + " >>> " + host + ":/" + ftpDetails.getRemoterDirectory() + "/" + ftpDetails.getRemoteFileName());
            ftpInput.close();
            storeFileStream.close();
            ftp.logout();
            ftp.disconnect();
            JavaCIPUnknownScope.logger.info("Logged out. ");
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }
}
