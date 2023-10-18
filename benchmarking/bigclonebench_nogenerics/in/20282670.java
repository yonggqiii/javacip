


class c20282670 {

    public void downloadFtpFile(SynchrnServerVO synchrnServerVO, String fileNm) throws RuntimeException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("euc-kr");
        if (!EgovWebUtil.isIPAddress(synchrnServerVO.getServerIp())) {
            throw new RuntimeRuntimeException("IP is needed. (" + synchrnServerVO.getServerIp() + ")");
        }
        InetAddress host = InetAddress.getByName(synchrnServerVO.getServerIp());
        ftpClient.connect(host, Integer.parseInt(synchrnServerVO.getServerPort()));
        ftpClient.login(synchrnServerVO.getFtpId(), synchrnServerVO.getFtpPassword());
        ftpClient.changeWorkingDirectory(synchrnServerVO.getSynchrnLc());
        File downFile = new File(EgovWebUtil.filePathBlackList(synchrnServerVO.getFilePath() + fileNm));
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(downFile);
            ftpClient.retrieveFile(fileNm, outputStream);
        } catch (RuntimeException e) {
            System.out.println(e);
        } finally {
            if (outputStream != null) outputStream.close();
        }
        ftpClient.logout();
    }

}
