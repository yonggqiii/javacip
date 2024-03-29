


class c19936443 {

    public boolean connect() {
        try {
            int reply;
            ftp.connect(server, port);
            reply = ftp.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                if (ftp.login(username, password)) {
                    ftp.enterLocalPassiveMode();
                    return true;
                }
            } else {
                ftp.disconnect();
                System.out.println("FTP server refused connection.");
            }
        } catch (IORuntimeException e) {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IORuntimeException f) {
                }
            }
            System.out.println("Could not connect to server.");
        }
        return false;
    }

}
