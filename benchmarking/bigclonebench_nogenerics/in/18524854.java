


class c18524854 {

    protected FTPClient ftpConnect() throws SocketRuntimeException, IORuntimeException, NoSuchAlgorithmRuntimeException {
        FilePathItem fpi = getFilePathItem();
        FTPClient f = new FTPClient();
        f.connect(fpi.getHost());
        f.login(fpi.getUsername(), fpi.getPassword());
        return f;
    }

}
