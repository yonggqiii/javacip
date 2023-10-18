class c1906172 {

    public void deploy(String baseDir, boolean clean) throws IORuntimeException {
        try {
            JavaCIPUnknownScope.ftp.connect(JavaCIPUnknownScope.hostname, JavaCIPUnknownScope.port);
            JavaCIPUnknownScope.log.debug("Connected to: " + JavaCIPUnknownScope.hostname + ":" + JavaCIPUnknownScope.port);
            JavaCIPUnknownScope.ftp.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password);
            int reply = JavaCIPUnknownScope.ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                throw new IORuntimeException("Error logging onto ftp server. FTPClient returned code: " + reply);
            }
            JavaCIPUnknownScope.log.debug("Logged in");
            JavaCIPUnknownScope.ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (clean) {
                JavaCIPUnknownScope.deleteDir(JavaCIPUnknownScope.remoteDir);
            }
            JavaCIPUnknownScope.storeFolder(baseDir, JavaCIPUnknownScope.remoteDir);
        } finally {
            JavaCIPUnknownScope.ftp.disconnect();
        }
    }
}
