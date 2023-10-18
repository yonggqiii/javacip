class c22991275 {

    public static final String encryptMD5(String decrypted) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(decrypted.getBytes());
            byte[] hash = md5.digest();
            md5.reset();
            return JavaCIPUnknownScope.hashToHex(hash);
        } catch (NoSuchAlgorithmRuntimeException _ex) {
            return null;
        }
    }
}
