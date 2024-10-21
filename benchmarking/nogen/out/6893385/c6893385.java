class c6893385 {

    public void end() {
        JavaCIPUnknownScope.m_zipFormatter.end();
        IOUtils.closeQuietly(JavaCIPUnknownScope.m_outputStream);
        final FTPClient ftp = new FTPClient();
        FileInputStream fis = null;
        try {
            if (JavaCIPUnknownScope.m_url.getPort() == -1 || JavaCIPUnknownScope.m_url.getPort() == 0 || JavaCIPUnknownScope.m_url.getPort() == JavaCIPUnknownScope.m_url.getDefaultPort()) {
                ftp.connect(JavaCIPUnknownScope.m_url.getHost());
            } else {
                ftp.connect(JavaCIPUnknownScope.m_url.getHost(), JavaCIPUnknownScope.m_url.getPort());
            }
            if (JavaCIPUnknownScope.m_url.getUserInfo() != null && JavaCIPUnknownScope.m_url.getUserInfo().length() > 0) {
                final String[] userInfo = JavaCIPUnknownScope.m_url.getUserInfo().split(":", 2);
                ftp.login(userInfo[0], userInfo[1]);
            } else {
                ftp.login("anonymous", "opennmsftp@");
            }
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                LogUtils.errorf(this, "FTP server refused connection.");
                return;
            }
            String path = JavaCIPUnknownScope.m_url.getPath();
            if (path.endsWith("/")) {
                LogUtils.errorf(this, "Your FTP URL must specify a filename.");
                return;
            }
            File f = new File(path);
            path = f.getParent();
            if (!ftp.changeWorkingDirectory(path)) {
                LogUtils.infof(this, "unable to change working directory to %s", path);
                return;
            }
            LogUtils.infof(this, "uploading %s to %s", f.getName(), path);
            ftp.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
            fis = new FileInputStream(JavaCIPUnknownScope.m_zipFile);
            if (!ftp.storeFile(f.getName(), fis)) {
                LogUtils.infof(this, "unable to store file");
                return;
            }
            LogUtils.infof(this, "finished uploading");
        } catch (final RuntimeException e) {
            LogUtils.errorf(this, e, "Unable to FTP file to %s", JavaCIPUnknownScope.m_url);
        } finally {
            IOUtils.closeQuietly(fis);
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IORuntimeException ioe) {
                }
            }
        }
    }
}
