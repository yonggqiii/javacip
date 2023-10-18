class c16475859 {

    public void ftpUpload() {
        FTPClient ftpclient = null;
        InputStream is = null;
        try {
            ftpclient = new FTPClient();
            ftpclient.connect(JavaCIPUnknownScope.host, JavaCIPUnknownScope.port);
            if (JavaCIPUnknownScope.logger.isDebugEnabled()) {
                JavaCIPUnknownScope.logger.debug("FTP连接远程服务器：" + JavaCIPUnknownScope.host);
            }
            ftpclient.login(JavaCIPUnknownScope.user, JavaCIPUnknownScope.password);
            if (JavaCIPUnknownScope.logger.isDebugEnabled()) {
                JavaCIPUnknownScope.logger.debug("登陆用户：" + JavaCIPUnknownScope.user);
            }
            ftpclient.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
            ftpclient.changeWorkingDirectory(JavaCIPUnknownScope.remotePath);
            is = new FileInputStream(JavaCIPUnknownScope.localPath + File.separator + JavaCIPUnknownScope.filename);
            ftpclient.storeFile(JavaCIPUnknownScope.filename, is);
            JavaCIPUnknownScope.logger.info("上传文件结束...路径：" + JavaCIPUnknownScope.remotePath + "，文件名：" + JavaCIPUnknownScope.filename);
            is.close();
            ftpclient.logout();
        } catch (IORuntimeException e) {
            JavaCIPUnknownScope.logger.error("上传文件失败", e);
        } finally {
            if (ftpclient.isConnected()) {
                try {
                    ftpclient.disconnect();
                } catch (IORuntimeException e) {
                    JavaCIPUnknownScope.logger.error("断开FTP出错", e);
                }
            }
            ftpclient = null;
        }
    }
}
