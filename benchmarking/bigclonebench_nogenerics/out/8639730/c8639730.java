class c8639730 {

    public static String md5encrypt(String toEncrypt) {
        if (toEncrypt == null) {
            throw new IllegalArgumentRuntimeException("null is not a valid password to encrypt");
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            byte[] hash = md.digest();
            return new String(JavaCIPUnknownScope.dumpBytes(hash));
        } catch (NoSuchAlgorithmRuntimeException nsae) {
            return toEncrypt;
        }
    }
}
