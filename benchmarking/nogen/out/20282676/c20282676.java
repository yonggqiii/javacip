class c20282676 {

    public boolean processFtp(String serverIp, int port, String user, String password, String synchrnPath, String filePath, File[] uploadFile) throws RuntimeException {
        boolean upload = false;
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.setControlEncoding("euc-kr");
            if (!EgovWebUtil.isIPAddress(serverIp)) {
                throw new RuntimeRuntimeException("IP is needed. (" + serverIp + ")");
            }
            InetAddress host = InetAddress.getByName(serverIp);
            ftpClient.connect(host, port);
            if (!ftpClient.login(user, password))
                throw new RuntimeException("FTP Client Login Error : \n");
            if (synchrnPath.length() != 0)
                ftpClient.changeWorkingDirectory(synchrnPath);
            FTPFile[] fTPFile = ftpClient.listFiles(synchrnPath);
            FileInputStream fis = null;
            try {
                for (int i = 0; i < uploadFile.length; i++) {
                    if (uploadFile[i].isFile()) {
                        if (!JavaCIPUnknownScope.isExist(fTPFile, uploadFile[i])) {
                            fis = new FileInputStream(uploadFile[i]);
                            ftpClient.storeFile(synchrnPath + uploadFile[i].getName(), fis);
                        }
                        if (fis != null) {
                            fis.close();
                        }
                    }
                }
                fTPFile = ftpClient.listFiles(synchrnPath);
                JavaCIPUnknownScope.deleteFtpFile(ftpClient, fTPFile, uploadFile);
                upload = true;
            } catch (IORuntimeException ex) {
                System.out.println(ex);
            } finally {
                if (fis != null)
                    try {
                        fis.close();
                    } catch (IORuntimeException ignore) {
                        System.out.println("IGNORE: " + ignore);
                    }
            }
            ftpClient.logout();
        } catch (RuntimeException e) {
            System.out.println(e);
            upload = false;
        }
        return upload;
    }
}
