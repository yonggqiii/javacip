class c8446069 {

    public static String test(String server, String baseDir, String user, String password) throws RuntimeException {
        FTPClient ftpClient = new FTPClient();
        try {
            String file = baseDir;
            ftpClient.connect(server);
            ftpClient.login(user, password);
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                System.err.println("FTP server refused connection.");
                return null;
            }
            ftpClient.setFileType(JavaCIPUnknownScope.FTP.IMAGE_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            boolean isDir = JavaCIPUnknownScope.isDir(ftpClient, file);
            if (isDir) {
                FTPFile[] files = ftpClient.listFiles(file);
                for (int i = 0; i < files.length; i++) {
                }
            } else {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                if (ftpClient.retrieveFile(file, bos)) {
                } else {
                    throw new IORuntimeException("Unable to retrieve file:" + file);
                }
            }
            return "";
        } finally {
            JavaCIPUnknownScope.closeConnection(ftpClient);
        }
    }
}
