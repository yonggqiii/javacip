class c20325581 {

    public static byte[] generateHash(String strPassword, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance(JavaCIPUnknownScope.HASH_ALGORITHM);
            md.update(strPassword.getBytes(JavaCIPUnknownScope.CHAR_ENCODING));
            md.update(salt);
            return md.digest();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
