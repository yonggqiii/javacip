class c8725960 {

    private FTPClient connectFtps() throws NoSuchAlgorithmRuntimeException, IORuntimeException {
        FTPClient apacheClient;
        if (JavaCIPUnknownScope.isSecure) {
            apacheClient = new FTPSClient(true);
        } else {
            apacheClient = new FTPClient();
        }
        apacheClient.addProtocolCommandListener(new LogFtpListener(JavaCIPUnknownScope.LOG));
        if (JavaCIPUnknownScope.isSecure) {
            apacheClient.connect(JavaCIPUnknownScope.host, 990);
        } else {
            apacheClient.connect(JavaCIPUnknownScope.host);
        }
        if (!apacheClient.login(JavaCIPUnknownScope.user, JavaCIPUnknownScope.pass)) {
            throw new IllegalArgumentRuntimeException("Unrecognized Username/Password");
        }
        apacheClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        apacheClient.getStatus();
        apacheClient.help();
        apacheClient.enterLocalPassiveMode();
        return apacheClient;
    }
}
