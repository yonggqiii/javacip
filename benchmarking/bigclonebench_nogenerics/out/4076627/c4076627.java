class c4076627 {

    public void openFtpConnection(String workingDirectory) throws RQLRuntimeException {
        try {
            JavaCIPUnknownScope.ftpClient = new FTPClient();
            JavaCIPUnknownScope.ftpClient.connect(JavaCIPUnknownScope.server);
            JavaCIPUnknownScope.ftpClient.login(JavaCIPUnknownScope.user, JavaCIPUnknownScope.password);
            JavaCIPUnknownScope.ftpClient.changeWorkingDirectory(workingDirectory);
        } catch (IORuntimeException ioex) {
            throw new RQLRuntimeException("FTP client could not be created. Please check attributes given in constructor.", ioex);
        }
    }
}
