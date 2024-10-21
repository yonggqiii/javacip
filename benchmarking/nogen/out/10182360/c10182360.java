class c10182360 {

    public void getDownloadInfo(String _url) throws RuntimeException {
        JavaCIPUnknownScope.cl = new FTPClient();
        Authentication auth = new FTPAuthentication();
        JavaCIPUnknownScope.cl.connect(JavaCIPUnknownScope.getHostName());
        while (!JavaCIPUnknownScope.cl.login(auth.getUser(), auth.getPassword())) {
            JavaCIPUnknownScope.log.debug("getDownloadInfo() - login error state: " + Arrays.asList(JavaCIPUnknownScope.cl.getReplyStrings()));
            JavaCIPUnknownScope.ap.setSite(JavaCIPUnknownScope.getSite());
            auth = JavaCIPUnknownScope.ap.promptAuthentication();
            if (auth == null)
                throw new RuntimeException("User Cancelled Auth Operation");
        }
        AuthManager.putAuth(JavaCIPUnknownScope.getSite(), auth);
        JavaCIPUnknownScope.cl.enterLocalPassiveMode();
        FTPFile file = JavaCIPUnknownScope.cl.listFiles(new URL(_url).getFile())[0];
        JavaCIPUnknownScope.setURL(_url);
        JavaCIPUnknownScope.setLastModified(file.getTimestamp().getTimeInMillis());
        JavaCIPUnknownScope.setSize(file.getSize());
        JavaCIPUnknownScope.setResumable(JavaCIPUnknownScope.cl.rest("0") == 350);
        JavaCIPUnknownScope.setRangeEnd(JavaCIPUnknownScope.getSize() - 1);
    }
}
