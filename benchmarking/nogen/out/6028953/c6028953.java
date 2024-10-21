class c6028953 {

    public final int connectAndLogin(Uri u, boolean cwd) throws UnknownHostRuntimeException, IORuntimeException, InterruptedRuntimeException {
        if (JavaCIPUnknownScope.ftp.isLoggedIn()) {
            if (cwd) {
                String path = u.getPath();
                if (path != null)
                    JavaCIPUnknownScope.ftp.setCurrentDir(path);
            }
            return JavaCIPUnknownScope.WAS_IN;
        }
        int port = u.getPort();
        if (port == -1)
            port = 21;
        String host = u.getHost();
        if (JavaCIPUnknownScope.ftp.connect(host, port)) {
            if (JavaCIPUnknownScope.theUserPass == null || JavaCIPUnknownScope.theUserPass.isNotSet())
                JavaCIPUnknownScope.theUserPass = new FTPCredentials(u.getUserInfo());
            if (JavaCIPUnknownScope.ftp.login(JavaCIPUnknownScope.theUserPass.getUserName(), JavaCIPUnknownScope.theUserPass.getPassword())) {
                if (cwd) {
                    String path = u.getPath();
                    if (path != null)
                        JavaCIPUnknownScope.ftp.setCurrentDir(path);
                }
                return JavaCIPUnknownScope.LOGGED_IN;
            } else {
                JavaCIPUnknownScope.ftp.logout(true);
                JavaCIPUnknownScope.ftp.disconnect();
                Log.w(JavaCIPUnknownScope.TAG, "Invalid credentials.");
                return JavaCIPUnknownScope.NO_LOGIN;
            }
        }
        return JavaCIPUnknownScope.NO_CONNECT;
    }
}
