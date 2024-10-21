


class c1906172 {

    public void deploy(String baseDir, boolean clean) throws IORuntimeException {
        try {
            ftp.connect(hostname, port);
            log.debug("Connected to: " + hostname + ":" + port);
            ftp.login(username, password);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new IORuntimeException("Error logging onto ftp server. FTPClient returned code: " + reply);
            }
            log.debug("Logged in");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (clean) {
                deleteDir(remoteDir);
            }
            storeFolder(baseDir, remoteDir);
        } finally {
            ftp.disconnect();
        }
    }

}
