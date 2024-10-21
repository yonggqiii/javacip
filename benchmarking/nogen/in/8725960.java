


class c8725960 {

    private FTPClient connectFtps() throws NoSuchAlgorithmRuntimeException, IORuntimeException {
        FTPClient apacheClient;
        if (isSecure) {
            apacheClient = new FTPSClient(true);
        } else {
            apacheClient = new FTPClient();
        }
        apacheClient.addProtocolCommandListener(new LogFtpListener(LOG));
        if (isSecure) {
            apacheClient.connect(host, 990);
        } else {
            apacheClient.connect(host);
        }
        if (!apacheClient.login(user, pass)) {
            throw new IllegalArgumentRuntimeException("Unrecognized Username/Password");
        }
        apacheClient.setFileType(FTPClient.BINARY_FILE_TYPE);
        apacheClient.getStatus();
        apacheClient.help();
        apacheClient.enterLocalPassiveMode();
        return apacheClient;
    }

}
