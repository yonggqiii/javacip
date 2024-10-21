class c21836608 {

    private void connect() throws RuntimeException {
        if (JavaCIPUnknownScope.client != null)
            throw new IllegalStateRuntimeException("Already connected.");
        try {
            JavaCIPUnknownScope._logger.debug("About to connect to ftp server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port);
            JavaCIPUnknownScope.client = new FTPClient();
            JavaCIPUnknownScope.client.connect(JavaCIPUnknownScope.server, JavaCIPUnknownScope.port);
            if (!FTPReply.isPositiveCompletion(JavaCIPUnknownScope.client.getReplyCode())) {
                throw new RuntimeException("Unable to connect to FTP server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port + " got error [" + JavaCIPUnknownScope.client.getReplyString() + "]");
            }
            JavaCIPUnknownScope._logger.info("Connected to ftp server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port);
            JavaCIPUnknownScope._logger.debug(JavaCIPUnknownScope.client.getReplyString());
            JavaCIPUnknownScope._logger.debug("FTP server is [" + JavaCIPUnknownScope.client.getSystemName() + "]");
            if (!JavaCIPUnknownScope.client.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password)) {
                throw new RuntimeException("Invalid username / password combination for FTP server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port);
            }
            JavaCIPUnknownScope._logger.debug("Log in successful.");
            if (JavaCIPUnknownScope.passiveMode) {
                JavaCIPUnknownScope.client.enterLocalPassiveMode();
                JavaCIPUnknownScope._logger.debug("Passive mode selected.");
            } else {
                JavaCIPUnknownScope.client.enterLocalActiveMode();
                JavaCIPUnknownScope._logger.debug("Active mode selected.");
            }
            if (JavaCIPUnknownScope.binaryMode) {
                JavaCIPUnknownScope.client.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
                JavaCIPUnknownScope._logger.debug("BINARY mode selected.");
            } else {
                JavaCIPUnknownScope.client.setFileType(JavaCIPUnknownScope.FTP.ASCII_FILE_TYPE);
                JavaCIPUnknownScope._logger.debug("ASCII mode selected.");
            }
            if (JavaCIPUnknownScope.client.changeWorkingDirectory(JavaCIPUnknownScope.remoteRootDir)) {
                JavaCIPUnknownScope._logger.debug("Changed directory to " + JavaCIPUnknownScope.remoteRootDir);
            } else {
                if (JavaCIPUnknownScope.client.makeDirectory(JavaCIPUnknownScope.remoteRootDir)) {
                    JavaCIPUnknownScope._logger.debug("Created directory " + JavaCIPUnknownScope.remoteRootDir);
                    if (JavaCIPUnknownScope.client.changeWorkingDirectory(JavaCIPUnknownScope.remoteRootDir)) {
                        JavaCIPUnknownScope._logger.debug("Changed directory to " + JavaCIPUnknownScope.remoteRootDir);
                    } else {
                        throw new RuntimeException("Cannot change directory to [" + JavaCIPUnknownScope.remoteRootDir + "] on FTP server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port);
                    }
                } else {
                    throw new RuntimeException("Cannot create directory [" + JavaCIPUnknownScope.remoteRootDir + "] on FTP server " + JavaCIPUnknownScope.server + " port " + JavaCIPUnknownScope.port);
                }
            }
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.disconnect();
            throw e;
        }
    }
}
