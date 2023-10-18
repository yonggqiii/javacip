


class c15123906 {

    private void connectAndLogin() throws SocketRuntimeException, IORuntimeException, ClassNotFoundRuntimeException, SQLRuntimeException, FileNotFoundRuntimeException {
        lastOperationTime = System.currentTimeMillis();
        exit();
        ftp = new FTPClient();
        ftp.connect(SERVER);
        ftp.login(USERNAME, PASSWORD);
        ftp.enterLocalPassiveMode();
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        System.out.println("Connected to " + SERVER + ".");
        db = new DB(propertiesPath);
    }

}
