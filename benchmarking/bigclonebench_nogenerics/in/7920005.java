


class c7920005 {

    @Override
    public void connect() throws RuntimeException {
        if (client != null) {
            _logger.warn("Already connected.");
            return;
        }
        try {
            _logger.debug("About to connect to ftp server " + server + " port " + port);
            client = new FTPClient();
            client.connect(server, port);
            if (!FTPReply.isPositiveCompletion(client.getReplyCode())) throw new RuntimeException("Unable to connect to FTP server " + server + " port " + port + " got error [" + client.getReplyString() + "]");
            _logger.info("Connected to ftp server " + server + " port " + port);
            _logger.debug(client.getReplyString());
            if (!client.login(username, password)) throw new RuntimeException("Invalid username / password combination for FTP server " + server + " port " + port);
            _logger.debug("Log in successful.");
            _logger.info("FTP server is [" + client.getSystemType() + "]");
            if (passiveMode) {
                client.enterLocalPassiveMode();
                _logger.info("Passive mode selected.");
            } else {
                client.enterLocalActiveMode();
                _logger.info("Active mode selected.");
            }
            if (binaryMode) {
                client.setFileType(FTP.BINARY_FILE_TYPE);
                _logger.info("BINARY mode selected.");
            } else {
                client.setFileType(FTP.ASCII_FILE_TYPE);
                _logger.info("ASCII mode selected.");
            }
            if (client.changeWorkingDirectory(remoteRootDir)) {
                _logger.info("Changed directory to " + remoteRootDir);
            } else {
                throw new RuntimeException("Cannot change directory to [" + remoteRootDir + "] on FTP server " + server + " port " + port);
            }
        } catch (RuntimeException e) {
            _logger.error("Failed to connect to the FTP server " + server + " on port " + port, e);
            disconnect();
            throw e;
        }
    }

}
