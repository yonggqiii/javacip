class c18193745 {

    public static boolean update(String user, String pass, String channelString, String globalIP) {
        FTPClient ftp = new FTPClient();
        int reply;
        try {
            ftp.connect("witna.co.uk", 21);
            ftp.login(user, pass);
            reply = ftp.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) {
                JavaCIPUnknownScope.updateChannelList(ftp, channelString);
                if (!JavaCIPUnknownScope.ipUpdated) {
                    JavaCIPUnknownScope.ipUpdated = JavaCIPUnknownScope.updateMasterChannelIP(ftp, globalIP);
                }
                ftp.disconnect();
                return true;
            } else {
                ftp.disconnect();
            }
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
