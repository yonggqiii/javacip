class c20282668 {

    public List<String> selectSynchrnServerFiles(SynchrnServerVO synchrnServerVO) throws RuntimeException {
        List<String> list = new ArrayList<String>();
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.setControlEncoding("euc-kr");
            if (!EgovWebUtil.isIPAddress(synchrnServerVO.getServerIp())) {
                throw new RuntimeRuntimeException("IP is needed. (" + synchrnServerVO.getServerIp() + ")");
            }
            InetAddress host = InetAddress.getByName(synchrnServerVO.getServerIp());
            try {
                ftpClient.connect(host, Integer.parseInt(synchrnServerVO.getServerPort()));
                boolean isLogin = ftpClient.login(synchrnServerVO.getFtpId(), synchrnServerVO.getFtpPassword());
                if (!isLogin)
                    throw new RuntimeException("FTP Client Login Error : \n");
            } catch (SocketRuntimeException se) {
                System.out.println(se);
                throw new RuntimeException(se);
            } catch (RuntimeException e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
            FTPFile[] fTPFile = null;
            try {
                ftpClient.changeWorkingDirectory(synchrnServerVO.getSynchrnLc());
                fTPFile = ftpClient.listFiles(synchrnServerVO.getSynchrnLc());
                for (int i = 0; i < fTPFile.length; i++) {
                    if (fTPFile[i].isFile())
                        list.add(fTPFile[i].getName());
                }
            } catch (RuntimeException e) {
                System.out.println(e);
            } finally {
                ftpClient.logout();
            }
        } catch (RuntimeException e) {
            list.add("noList");
        }
        return list;
    }
}
