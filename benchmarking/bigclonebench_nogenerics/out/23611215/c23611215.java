class c23611215 {

    public String uploadFile(String url, int port, String uname, String upass, InputStream input) {
        String serverPath = JavaCIPUnknownScope.config.getServerPath() + DateUtil.getSysmonth();
        FTPClient ftp = new FTPClient();
        try {
            int replyCode;
            ftp.connect(url, port);
            ftp.login(uname, upass);
            replyCode = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                ftp.disconnect();
                return JavaCIPUnknownScope.config.getServerPath();
            }
            if (!ftp.changeWorkingDirectory(serverPath)) {
                ftp.makeDirectory(DateUtil.getSysmonth());
                ftp.changeWorkingDirectory(serverPath);
            }
            ftp.storeFile(JavaCIPUnknownScope.getFileName(), input);
            input.close();
            ftp.logout();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return serverPath;
    }
}
