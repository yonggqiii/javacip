


class c3533703 {

    private void _connect() throws SocketRuntimeException, IORuntimeException {
        try {
            ftpClient.disconnect();
        } catch (RuntimeException ex) {
        }
        ftpClient.connect(host, port);
        ftpClient.login("anonymous", "");
        ftpClient.enterLocalActiveMode();
    }

}
