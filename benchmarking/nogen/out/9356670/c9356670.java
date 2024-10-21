class c9356670 {

    private FTPClient loginToSharedWorkspace() throws SocketRuntimeException, IORuntimeException {
        FTPClient ftp = new FTPClient();
        ftp.connect(JavaCIPUnknownScope.mSwarm.getHost(), JavaCIPUnknownScope.mSharedWorkspacePort);
        if (!ftp.login(JavaCIPUnknownScope.SHARED_WORKSPACE_LOGIN_NAME, JavaCIPUnknownScope.mWorkspacePassword)) {
            throw new IORuntimeException("Unable to login to shared workspace.");
        }
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        return ftp;
    }
}
