class c19936443 {

    public boolean connect() {
        try {
            int reply;
            JavaCIPUnknownScope.ftp.connect(JavaCIPUnknownScope.server, JavaCIPUnknownScope.port);
            reply = JavaCIPUnknownScope.ftp.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                if (JavaCIPUnknownScope.ftp.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password)) {
                    JavaCIPUnknownScope.ftp.enterLocalPassiveMode();
                    return true;
                }
            } else {
                JavaCIPUnknownScope.ftp.disconnect();
                System.out.println("FTP server refused connection.");
            }
        } catch (IORuntimeException e) {
            if (JavaCIPUnknownScope.ftp.isConnected()) {
                try {
                    JavaCIPUnknownScope.ftp.disconnect();
                } catch (IORuntimeException f) {
                }
            }
            System.out.println("Could not connect to server.");
        }
        return false;
    }
}
