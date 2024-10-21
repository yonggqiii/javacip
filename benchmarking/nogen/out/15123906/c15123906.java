class c15123906 {

    private void connectAndLogin() throws SocketRuntimeException, IORuntimeException, ClassNotFoundRuntimeException, SQLRuntimeException, FileNotFoundRuntimeException {
        JavaCIPUnknownScope.lastOperationTime = System.currentTimeMillis();
        JavaCIPUnknownScope.exit();
        JavaCIPUnknownScope.ftp = new FTPClient();
        JavaCIPUnknownScope.ftp.connect(JavaCIPUnknownScope.SERVER);
        JavaCIPUnknownScope.ftp.login(JavaCIPUnknownScope.USERNAME, JavaCIPUnknownScope.PASSWORD);
        JavaCIPUnknownScope.ftp.enterLocalPassiveMode();
        JavaCIPUnknownScope.ftp.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
        System.out.println("Connected to " + JavaCIPUnknownScope.SERVER + ".");
        JavaCIPUnknownScope.db = new DB(JavaCIPUnknownScope.propertiesPath);
    }
}
