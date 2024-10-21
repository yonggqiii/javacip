class c13816650 {

    private synchronized void createFTPConnection() throws RemoteClientRuntimeException {
        JavaCIPUnknownScope.ftpClient = new FTPClient();
        try {
            URL url = JavaCIPUnknownScope.fileset.getHostURL();
            PasswordAuthentication passwordAuthentication = JavaCIPUnknownScope.fileset.getPasswordAuthentication();
            if (null == passwordAuthentication) {
                passwordAuthentication = JavaCIPUnknownScope.anonPassAuth;
            }
            InetAddress inetAddress = InetAddress.getByName(url.getHost());
            if (url.getPort() == -1) {
                JavaCIPUnknownScope.ftpClient.connect(inetAddress);
            } else {
                JavaCIPUnknownScope.ftpClient.connect(inetAddress, url.getPort());
            }
            if (!FTPReply.isPositiveCompletion(JavaCIPUnknownScope.ftpClient.getReplyCode())) {
                throw new FTPBrowseRuntimeException(JavaCIPUnknownScope.ftpClient.getReplyString());
            }
            JavaCIPUnknownScope.ftpClient.login(passwordAuthentication.getUserName(), new StringBuffer().append(passwordAuthentication.getPassword()).toString());
            if (url.getPath().length() > 0) {
                JavaCIPUnknownScope.ftpClient.changeWorkingDirectory(url.getPath());
            }
        } catch (UnknownHostRuntimeException e) {
            throw new RemoteClientRuntimeException("Host not found.", e);
        } catch (SocketRuntimeException e) {
            throw new RemoteClientRuntimeException("Socket cannot be opened.", e);
        } catch (IORuntimeException e) {
            throw new RemoteClientRuntimeException("Socket cannot be opened.", e);
        }
    }
}
