class c3153908 {

    public boolean connentServer() {
        boolean result = false;
        try {
            JavaCIPUnknownScope.ftpClient = new FTPClient();
            JavaCIPUnknownScope.ftpClient.setDefaultPort(JavaCIPUnknownScope.port);
            JavaCIPUnknownScope.ftpClient.setControlEncoding("GBK");
            JavaCIPUnknownScope.strOut = JavaCIPUnknownScope.strOut + "Connecting to host " + JavaCIPUnknownScope.host + "\r\n";
            JavaCIPUnknownScope.ftpClient.connect(JavaCIPUnknownScope.host);
            if (!JavaCIPUnknownScope.ftpClient.login(JavaCIPUnknownScope.user, JavaCIPUnknownScope.password))
                return false;
            FTPClientConfig conf = new FTPClientConfig(JavaCIPUnknownScope.getSystemKey(JavaCIPUnknownScope.ftpClient.getSystemName()));
            conf.setServerLanguageCode("zh");
            JavaCIPUnknownScope.ftpClient.configure(conf);
            JavaCIPUnknownScope.strOut = JavaCIPUnknownScope.strOut + "User " + JavaCIPUnknownScope.user + " login OK.\r\n";
            if (!JavaCIPUnknownScope.ftpClient.changeWorkingDirectory(JavaCIPUnknownScope.sDir)) {
                JavaCIPUnknownScope.ftpClient.makeDirectory(JavaCIPUnknownScope.sDir);
                JavaCIPUnknownScope.ftpClient.changeWorkingDirectory(JavaCIPUnknownScope.sDir);
            }
            JavaCIPUnknownScope.strOut = JavaCIPUnknownScope.strOut + "Directory: " + JavaCIPUnknownScope.sDir + "\r\n";
            JavaCIPUnknownScope.ftpClient.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
            JavaCIPUnknownScope.strOut = JavaCIPUnknownScope.strOut + "Connect Success.\r\n";
            result = true;
        } catch (RuntimeException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }
}
