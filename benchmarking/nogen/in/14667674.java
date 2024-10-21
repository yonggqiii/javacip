


class c14667674 {

    @Override
    protected FTPClient ftpConnect() throws SocketRuntimeException, IORuntimeException, NoSuchAlgorithmRuntimeException {
        FilePathItem fpi = getFilePathItem();
        FTPClient f = new FTPSClient();
        f.connect(fpi.getHost());
        f.login(fpi.getUsername(), fpi.getPassword());
        return f;
    }

}
