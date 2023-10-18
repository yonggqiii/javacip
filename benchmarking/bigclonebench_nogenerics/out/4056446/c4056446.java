class c4056446 {

    public final String hashRealmPassword(String username, String realm, String password) throws GeneralSecurityRuntimeException {
        MessageDigest md = null;
        md = MessageDigest.getInstance("MD5");
        md.update(username.getBytes());
        md.update(":".getBytes());
        md.update(realm.getBytes());
        md.update(":".getBytes());
        md.update(password.getBytes());
        byte[] hash = md.digest();
        return JavaCIPUnknownScope.toHex(hash, hash.length);
    }
}
