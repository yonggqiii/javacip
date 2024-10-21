class c22516784 {

    public static void ftpUpload(FTPConfig config, String directory, File file, String remoteFileName) throws IORuntimeException {
        FTPClient server = new FTPClient();
        server.connect(config.host, config.port);
        JavaCIPUnknownScope.assertValidReplyCode(server.getReplyCode(), server);
        server.login(config.userName, config.password);
        JavaCIPUnknownScope.assertValidReplyCode(server.getReplyCode(), server);
        JavaCIPUnknownScope.assertValidReplyCode(server.cwd(directory), server);
        server.setFileTransferMode(JavaCIPUnknownScope.FTP.IMAGE_FILE_TYPE);
        server.setFileType(JavaCIPUnknownScope.FTP.IMAGE_FILE_TYPE);
        server.storeFile(remoteFileName, new FileInputStream(file));
        JavaCIPUnknownScope.assertValidReplyCode(server.getReplyCode(), server);
        server.sendNoOp();
        server.disconnect();
    }
}
