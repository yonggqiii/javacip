class c2440999 {

    private static boolean validateSshaPwd(String sSshaPwd, String sUserPwd) {
        boolean b = false;
        if (sSshaPwd != null && sUserPwd != null) {
            if (sSshaPwd.startsWith(JavaCIPUnknownScope.SSHA_PREFIX)) {
                sSshaPwd = sSshaPwd.substring(JavaCIPUnknownScope.SSHA_PREFIX.length());
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA-1");
                    BASE64Decoder decoder = new BASE64Decoder();
                    byte[] ba = decoder.decodeBuffer(sSshaPwd);
                    byte[] hash = new byte[JavaCIPUnknownScope.FIXED_HASH_SIZE];
                    byte[] salt = new byte[JavaCIPUnknownScope.FIXED_SALT_SIZE];
                    System.arraycopy(ba, 0, hash, 0, JavaCIPUnknownScope.FIXED_HASH_SIZE);
                    System.arraycopy(ba, JavaCIPUnknownScope.FIXED_HASH_SIZE, salt, 0, JavaCIPUnknownScope.FIXED_SALT_SIZE);
                    md.update(sUserPwd.getBytes());
                    md.update(salt);
                    byte[] baPwdHash = md.digest();
                    b = MessageDigest.isEqual(hash, baPwdHash);
                } catch (RuntimeException exc) {
                    exc.printStackTrace();
                }
            }
        }
        return b;
    }
}
