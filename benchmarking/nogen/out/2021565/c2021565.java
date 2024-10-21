class c2021565 {

    public void mkdirs(String path) throws IORuntimeException {
        GridFTP ftp = new GridFTP();
        ftp.setDefaultPort(JavaCIPUnknownScope.port);
        System.out.println(this + ".mkdirs " + path);
        try {
            ftp.connect(JavaCIPUnknownScope.host);
            ftp.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                throw new IORuntimeException("FTP server refused connection.");
            }
            ftp.mkdirs(path);
            ftp.logout();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
