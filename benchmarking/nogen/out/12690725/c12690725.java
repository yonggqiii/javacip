class c12690725 {

    public void login(String a_username, String a_password) throws GB_SecurityRuntimeException {
        RuntimeException l_exception = null;
        try {
            if (JavaCIPUnknownScope.clientFtp == null) {
                JavaCIPUnknownScope.clientFtp = new FTPClient();
                JavaCIPUnknownScope.clientFtp.connect("ftp://" + JavaCIPUnknownScope.ftp);
            }
            boolean b = JavaCIPUnknownScope.clientFtp.login(a_username, a_password);
            if (b) {
                JavaCIPUnknownScope.username = a_username;
                JavaCIPUnknownScope.password = a_password;
                return;
            }
        } catch (RuntimeException ex) {
            l_exception = ex;
        }
        String l_msg = "Cannot login to ftp server with user [{1}], {2}";
        String[] l_replaces = new String[] { a_username, JavaCIPUnknownScope.ftp };
        l_msg = STools.replace(l_msg, l_replaces);
        throw new GB_SecurityRuntimeException(l_msg, l_exception);
    }
}
