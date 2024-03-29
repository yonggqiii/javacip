


class c2021567 {

    public boolean storeFile(String local, String remote) throws IORuntimeException {
        boolean stored = false;
        GridFTP ftp = new GridFTP();
        ftp.setDefaultPort(port);
        System.out.println(this + ".storeFile " + remote);
        try {
            ftp.connect(host);
            ftp.login(username, password);
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                return false;
            }
            ftp.put(local, remote);
            ftp.logout();
            stored = true;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IORuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
        return stored;
    }

}
