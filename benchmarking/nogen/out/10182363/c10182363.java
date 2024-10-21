class c10182363 {

    public void initGet() throws RuntimeException {
        JavaCIPUnknownScope.cl = new FTPClient();
        JavaCIPUnknownScope.cl.connect(JavaCIPUnknownScope.getHostName());
        Authentication auth = AuthManager.getAuth(JavaCIPUnknownScope.getSite());
        if (auth == null)
            auth = new FTPAuthentication(JavaCIPUnknownScope.getSite());
        while (!JavaCIPUnknownScope.cl.login(auth.getUser(), auth.getPassword())) {
            JavaCIPUnknownScope.ap.setSite(JavaCIPUnknownScope.getSite());
            auth = JavaCIPUnknownScope.ap.promptAuthentication();
            if (auth == null)
                throw new RuntimeException("User Cancelled Auth Operation");
        }
        JavaCIPUnknownScope.cl.connect(JavaCIPUnknownScope.getHostName());
        JavaCIPUnknownScope.cl.login(auth.getUser(), auth.getPassword());
        JavaCIPUnknownScope.cl.enterLocalPassiveMode();
        JavaCIPUnknownScope.cl.setFileType(JavaCIPUnknownScope.FTP.BINARY_FILE_TYPE);
        JavaCIPUnknownScope.cl.setRestartOffset(JavaCIPUnknownScope.getPosition());
        JavaCIPUnknownScope.setInputStream(JavaCIPUnknownScope.cl.retrieveFileStream(new URL(JavaCIPUnknownScope.getURL()).getFile()));
    }
}
