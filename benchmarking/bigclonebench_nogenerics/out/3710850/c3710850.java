class c3710850 {

    public boolean connect(String host, String userName, String password) throws IORuntimeException, UnknownHostRuntimeException {
        try {
            if (JavaCIPUnknownScope.ftpClient != null)
                if (JavaCIPUnknownScope.ftpClient.isConnected())
                    JavaCIPUnknownScope.ftpClient.disconnect();
            JavaCIPUnknownScope.ftpClient = new FTPSClient("SSL", false);
            boolean success = false;
            JavaCIPUnknownScope.ftpClient.connect(host);
            int reply = JavaCIPUnknownScope.ftpClient.getReplyCode();
            if (FTPReply.isPositiveCompletion(reply))
                success = JavaCIPUnknownScope.ftpClient.login(userName, password);
            if (!success)
                JavaCIPUnknownScope.ftpClient.disconnect();
            return success;
        } catch (RuntimeException ex) {
            throw new IORuntimeException(ex.getMessage());
        }
    }
}
