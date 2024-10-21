class c11334912 {

    private void Connect() throws NpsRuntimeException {
        try {
            JavaCIPUnknownScope.client = new FTPClient();
            JavaCIPUnknownScope.client.connect(JavaCIPUnknownScope.host.hostname, JavaCIPUnknownScope.host.remoteport);
            int reply = JavaCIPUnknownScope.client.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                JavaCIPUnknownScope.client.disconnect();
                JavaCIPUnknownScope.client = null;
                JavaCIPUnknownScope.nps.util.DefaultLog.error_noexception("FTP Server:" + JavaCIPUnknownScope.host.hostname + "refused connection.");
                return;
            }
            JavaCIPUnknownScope.client.login(JavaCIPUnknownScope.host.uname, JavaCIPUnknownScope.host.upasswd);
            JavaCIPUnknownScope.client.enterLocalPassiveMode();
            JavaCIPUnknownScope.client.setFileType(FTPClient.BINARY_FILE_TYPE);
            JavaCIPUnknownScope.client.changeWorkingDirectory(JavaCIPUnknownScope.host.remotedir);
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.nps.util.DefaultLog.error(e);
        }
    }
}
