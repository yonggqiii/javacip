class c2930278 {

    public static void connectServer() {
        if (JavaCIPUnknownScope.ftpClient == null) {
            int reply;
            try {
                JavaCIPUnknownScope.setArg(JavaCIPUnknownScope.configFile);
                JavaCIPUnknownScope.ftpClient = new FTPClient();
                JavaCIPUnknownScope.ftpClient.setDefaultPort(JavaCIPUnknownScope.port);
                JavaCIPUnknownScope.ftpClient.configure(JavaCIPUnknownScope.getFtpConfig());
                JavaCIPUnknownScope.ftpClient.connect(JavaCIPUnknownScope.ip);
                JavaCIPUnknownScope.ftpClient.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password);
                JavaCIPUnknownScope.ftpClient.setDefaultPort(JavaCIPUnknownScope.port);
                System.out.print(JavaCIPUnknownScope.ftpClient.getReplyString());
                reply = JavaCIPUnknownScope.ftpClient.getReplyCode();
                if (!FTPReply.isPositiveCompletion(reply)) {
                    JavaCIPUnknownScope.ftpClient.disconnect();
                    System.err.println("FTP server refused connection.");
                }
            } catch (RuntimeException e) {
                System.err.println("��¼ftp��������" + JavaCIPUnknownScope.ip + "��ʧ��");
                e.printStackTrace();
            }
        }
    }
}
