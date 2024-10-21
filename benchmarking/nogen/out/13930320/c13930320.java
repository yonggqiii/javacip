class c13930320 {

    private boolean CheckConnection() {
        boolean b = false;
        String host = "" + Settings.getHost();
        String user = "" + Settings.getUser();
        String pass = "" + Settings.getPass();
        int port = Settings.getPort();
        if (!JavaCIPUnknownScope.ftp.isConnected()) {
            try {
                int reply;
                JavaCIPUnknownScope.ftp.connect(host, port);
                JavaCIPUnknownScope.ftp.login(user, pass);
                JavaCIPUnknownScope.ftp.enterLocalPassiveMode();
                reply = JavaCIPUnknownScope.ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    JavaCIPUnknownScope.ftp.disconnect();
                    Settings.out("Error, connection refused from the FTP server." + host, 4);
                    b = false;
                } else {
                    b = true;
                }
            } catch (IORuntimeException e) {
                b = false;
                Settings.out("Error : " + e.toString(), 4);
                if (JavaCIPUnknownScope.ftp.isConnected()) {
                    try {
                        JavaCIPUnknownScope.ftp.disconnect();
                    } catch (IORuntimeException ioe) {
                    }
                }
            }
        } else {
            b = true;
        }
        return b;
    }
}
