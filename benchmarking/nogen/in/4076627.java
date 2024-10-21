


class c4076627 {

    public void openFtpConnection(String workingDirectory) throws RQLRuntimeException {
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(server);
            ftpClient.login(user, password);
            ftpClient.changeWorkingDirectory(workingDirectory);
        } catch (IORuntimeException ioex) {
            throw new RQLRuntimeException("FTP client could not be created. Please check attributes given in constructor.", ioex);
        }
    }

}
