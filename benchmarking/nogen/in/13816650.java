


class c13816650 {

    private synchronized void createFTPConnection() throws RemoteClientRuntimeException {
        ftpClient = new FTPClient();
        try {
            URL url = fileset.getHostURL();
            PasswordAuthentication passwordAuthentication = fileset.getPasswordAuthentication();
            if (null == passwordAuthentication) {
                passwordAuthentication = anonPassAuth;
            }
            InetAddress inetAddress = InetAddress.getByName(url.getHost());
            if (url.getPort() == -1) {
                ftpClient.connect(inetAddress);
            } else {
                ftpClient.connect(inetAddress, url.getPort());
            }
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                throw new FTPBrowseRuntimeException(ftpClient.getReplyString());
            }
            ftpClient.login(passwordAuthentication.getUserName(), new StringBuffer().append(passwordAuthentication.getPassword()).toString());
            if (url.getPath().length() > 0) {
                ftpClient.changeWorkingDirectory(url.getPath());
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
