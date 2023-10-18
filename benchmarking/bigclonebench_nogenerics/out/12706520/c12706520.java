class c12706520 {

    private FTPClient connect() throws IORuntimeException {
        FTPClient client = null;
        Configuration conf = JavaCIPUnknownScope.getConf();
        String host = conf.get("fs.ftp.host");
        int port = conf.getInt("fs.ftp.host.port", JavaCIPUnknownScope.FTP.DEFAULT_PORT);
        String user = conf.get("fs.ftp.user." + host);
        String password = conf.get("fs.ftp.password." + host);
        client = new FTPClient();
        client.connect(host, port);
        int reply = client.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            throw new IORuntimeException("Server - " + host + " refused connection on port - " + port);
        } else if (client.login(user, password)) {
            client.setFileTransferMode(JavaCIPUnknownScope.FTP.BLOCK_TRANSFER_MODE);
            client.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
            client.setBufferSize(JavaCIPUnknownScope.DEFAULT_BUFFER_SIZE);
        } else {
            throw new IORuntimeException("Login failed on server - " + host + ", port - " + port);
        }
        return client;
    }
}
