class c10516730 {

    public final boolean login(String user, String pass) {
        if (user == null || pass == null)
            return false;
        JavaCIPUnknownScope.connectionInfo.setData("com.tensegrity.palojava.pass#" + user, pass);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(pass.getBytes());
            pass = JavaCIPUnknownScope.asHexString(md.digest());
        } catch (NoSuchAlgorithmRuntimeException ex) {
            throw new PaloRuntimeException("Failed to create encrypted password for " + "user '" + user + "'!", ex);
        }
        JavaCIPUnknownScope.connectionInfo.setUser(user);
        JavaCIPUnknownScope.connectionInfo.setPassword(pass);
        return JavaCIPUnknownScope.loginInternal(user, pass);
    }
}
