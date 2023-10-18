class c7920005 {

    public void connect() throws RuntimeException {
        if (JavaCIPUnknownScope.client != null) {
            JavaCIPUnknownScope._logger.warn("Already connected.");
            return;
        }
        try {
            JavaCIPUnknownScope._logger.debug("About to connect to ftp server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port);
            JavaCIPUnknownScope.client = new FTPClient();
            JavaCIPUnknownScope.client.connect(JavaCIPUnknownScope.server, JavaCIPUnknownScope.port);
            if (!FTPReply.isPositiveCompletion(JavaCIPUnknownScope.client.getReplyCode()))
                throw new RuntimeException("Unable to connect to FTP server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port + " got error [" + JavaCIPUnknownScope.client.getReplyString() + "]");
            JavaCIPUnknownScope._logger.info("Connected to ftp server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port);
            JavaCIPUnknownScope._logger.debug(JavaCIPUnknownScope.client.getReplyString());
            if (!JavaCIPUnknownScope.client.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password))
                throw new RuntimeException("Invalid username / password combination for FTP server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port);
            JavaCIPUnknownScope._logger.debug("Log in successful.");
            JavaCIPUnknownScope._logger.info("FTP server is [" + JavaCIPUnknownScope.client.getSystemType() + "]");
            if (JavaCIPUnknownScope.passiveMode) {
                JavaCIPUnknownScope.client.enterLocalPassiveMode();
                JavaCIPUnknownScope._logger.info("Passive mode selected.");
            } else {
                JavaCIPUnknownScope.client.enterLocalActiveMode();
                JavaCIPUnknownScope._logger.info("Active mode selected.");
            }
            if (JavaCIPUnknownScope.binaryMode) {
                JavaCIPUnknownScope.client.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
                JavaCIPUnknownScope._logger.info("BINARY mode selected.");
            } else {
                JavaCIPUnknownScope.client.setFileType(JavaCIPUnknownScope.FTP.ASCII_FILE_TYPE);
                JavaCIPUnknownScope._logger.info("ASCII mode selected.");
            }
            if (JavaCIPUnknownScope.client.changeWorkingDirectory(JavaCIPUnknownScope.remoteRootDir)) {
                JavaCIPUnknownScope._logger.info("Changed directory to " + JavaCIPUnknownScope.remoteRootDir);
            } else {
                throw new RuntimeException("Cannot change directory to [" + JavaCIPUnknownScope.remoteRootDir + "] on FTP server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port);
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope._logger.error("Failed to connect to the FTP server " + JavaCIPUnknownScope.server + " on port " + JavaCIPUnknownScope.port, e);
            JavaCIPUnknownScope.disconnect();
            throw e;
        }
    }
}
