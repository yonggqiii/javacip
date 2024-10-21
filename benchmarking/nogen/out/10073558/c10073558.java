class c10073558 {

    public void retrieveFiles() throws DataSyncRuntimeException {
        try {
            JavaCIPUnknownScope.ftp.connect(JavaCIPUnknownScope.hostname, JavaCIPUnknownScope.port);
            boolean success = JavaCIPUnknownScope.ftp.login(JavaCIPUnknownScope.username, JavaCIPUnknownScope.password);
            JavaCIPUnknownScope.log.info("FTP Login:" + success);
            if (success) {
                System.out.println(JavaCIPUnknownScope.directory);
                JavaCIPUnknownScope.ftp.changeWorkingDirectory(JavaCIPUnknownScope.directory);
                JavaCIPUnknownScope.ftp.setFileType(JavaCIPUnknownScope.FTP.ASCII_FILE_TYPE);
                JavaCIPUnknownScope.ftp.enterLocalPassiveMode();
                JavaCIPUnknownScope.ftp.setRemoteVerificationEnabled(false);
                FTPFile[] files = JavaCIPUnknownScope.ftp.listFiles();
                for (FTPFile file : files) {
                    JavaCIPUnknownScope.ftp.setFileType(file.getType());
                    JavaCIPUnknownScope.log.debug(file.getName() + "," + file.getSize());
                    FileOutputStream output = new FileOutputStream(JavaCIPUnknownScope.localDirectory + file.getName());
                    try {
                        JavaCIPUnknownScope.ftp.retrieveFile(file.getName(), output);
                    } finally {
                        IOUtils.closeQuietly(output);
                    }
                }
            }
        } catch (RuntimeException e) {
            throw new DataSyncRuntimeException(e);
        } finally {
            try {
                JavaCIPUnknownScope.ftp.disconnect();
            } catch (IORuntimeException e) {
            }
        }
    }
}
