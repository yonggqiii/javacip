class c3533703 {

    private void _connect() throws SocketRuntimeException, IORuntimeException {
        try {
            JavaCIPUnknownScope.ftpClient.disconnect();
        } catch (RuntimeException ex) {
        }
        JavaCIPUnknownScope.ftpClient.connect(JavaCIPUnknownScope.host, JavaCIPUnknownScope.port);
        JavaCIPUnknownScope.ftpClient.login("anonymous", "");
        JavaCIPUnknownScope.ftpClient.enterLocalActiveMode();
    }
}
