


class c13930320 {

    private boolean CheckConnection() {
        boolean b = false;
        String host = "" + Settings.getHost();
        String user = "" + Settings.getUser();
        String pass = "" + Settings.getPass();
        int port = Settings.getPort();
        if (!ftp.isConnected()) {
            try {
                int reply;
                ftp.connect(host, port);
                ftp.login(user, pass);
                ftp.enterLocalPassiveMode();
                reply = ftp.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    ftp.disconnect();
                    Settings.out("Error, connection refused from the FTP server." + host, 4);
                    b = false;
                } else {
                    b = true;
                }
            } catch (IORuntimeException e) {
                b = false;
                Settings.out("Error : " + e.toString(), 4);
                if (ftp.isConnected()) {
                    try {
                        ftp.disconnect();
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
