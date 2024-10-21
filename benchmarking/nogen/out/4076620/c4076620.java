class c4076620 {

    public boolean downloadFile(String sourceFilename, String targetFilename) throws RQLRuntimeException {
        JavaCIPUnknownScope.checkFtpClient();
        InputStream in = null;
        try {
            in = JavaCIPUnknownScope.ftpClient.retrieveFileStream(sourceFilename);
            if (in == null) {
                return false;
            }
            FileOutputStream target = new FileOutputStream(targetFilename);
            IOUtils.copy(in, target);
            in.close();
            target.close();
            return JavaCIPUnknownScope.ftpClient.completePendingCommand();
        } catch (IORuntimeException ex) {
            throw new RQLRuntimeException("Download of file with name " + sourceFilename + " via FTP from server " + JavaCIPUnknownScope.server + " failed.", ex);
        }
    }
}
