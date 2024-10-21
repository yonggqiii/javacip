class c2021569 {

    public boolean getFile(String local, String remote) throws IORuntimeException {
        boolean result = false;
        GridFTP ftp = new GridFTP();
        ftp.setDefaultPort(JavaCIPUnknownScope.port);
        System.out.println(this + ".getFile " + remote);
        try {
            ftp.connect(JavaCIPUnknownScope.host);
            ftp.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                return false;
            }
            ftp.get(local, remote);
            ftp.logout();
            result = true;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(this + ".getFile return " + result);
        return result;
    }
}
