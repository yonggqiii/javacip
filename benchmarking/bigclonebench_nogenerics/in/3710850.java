


class c3710850 {

    @Override
    public boolean connect(String host, String userName, String password) throws IORuntimeException, UnknownHostRuntimeException {
        try {
            if (ftpClient != null) if (ftpClient.isConnected()) ftpClient.disconnect();
            ftpClient = new FTPSClient("SSL", false);
            boolean success = false;
            ftpClient.connect(host);
            int reply = ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply)) success = ftpClient.login(userName, password);
            if (!success) ftpClient.disconnect();
            return success;
        } catch (RuntimeException ex) {
            throw new IORuntimeException(ex.getMessage());
        }
    }

}
