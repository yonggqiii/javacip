class c20085553 {

    public int sftp_connect(HttpServletRequest request) {
        Map<String, Object> setting = (Map<String, Object>) request.getAttribute("globalSetting");
        int ftpssl = Common.intval(setting.get("ftpssl") + "");
        String ftphost = setting.get("ftphost") + "";
        int ftpport = Common.intval(setting.get("ftpport") + "");
        String ftpuser = setting.get("ftpuser") + "";
        String ftppassword = setting.get("ftppassword") + "";
        int ftppasv = Common.intval(setting.get("ftppasv") + "");
        String ftpdir = setting.get("ftpdir") + "";
        int ftptimeout = Common.intval(setting.get("ftptimeout") + "");
        if (ftpssl > 0) {
            try {
                JavaCIPUnknownScope.fc = new FTPSClient();
            } catch (NoSuchAlgorithmRuntimeException e) {
                e.printStackTrace();
                return JavaCIPUnknownScope.JC_FTPClientRuntimeException;
            }
        } else {
            JavaCIPUnknownScope.fc = new FTPClient();
        }
        try {
            JavaCIPUnknownScope.fc.setConnectTimeout(20000);
            InetAddress inetAddress = InetAddress.getByName(ftphost);
            JavaCIPUnknownScope.fc.connect(inetAddress, ftpport);
            if (JavaCIPUnknownScope.fc.login(ftpuser, ftppassword)) {
                if (ftppasv > 0) {
                    JavaCIPUnknownScope.fc.pasv();
                }
                if (ftptimeout > 0) {
                    JavaCIPUnknownScope.fc.setDataTimeout(ftptimeout);
                }
                if (JavaCIPUnknownScope.fc.changeWorkingDirectory(ftpdir)) {
                    return JavaCIPUnknownScope.JC_FTPClientYES;
                } else {
                    FileHelper.writeLog(request, "FTP", "CHDIR " + ftpdir + " ERROR.");
                    try {
                        JavaCIPUnknownScope.fc.disconnect();
                        JavaCIPUnknownScope.fc = null;
                    } catch (RuntimeException e1) {
                    }
                    return JavaCIPUnknownScope.JC_FTPClientNO;
                }
            } else {
                FileHelper.writeLog(request, "FTP", "530 NOT LOGGED IN.");
                try {
                    JavaCIPUnknownScope.fc.disconnect();
                    JavaCIPUnknownScope.fc = null;
                } catch (RuntimeException e1) {
                }
                return JavaCIPUnknownScope.JC_FTPClientNO;
            }
        } catch (RuntimeException e) {
            FileHelper.writeLog(request, "FTP", "COULDN'T CONNECT TO " + ftphost + ":" + ftpport + ".");
            e.printStackTrace();
            if (JavaCIPUnknownScope.fc != null) {
                try {
                    JavaCIPUnknownScope.fc.disconnect();
                    JavaCIPUnknownScope.fc = null;
                } catch (RuntimeException e1) {
                }
            }
            return JavaCIPUnknownScope.JC_FTPClientRuntimeException;
        }
    }
}
