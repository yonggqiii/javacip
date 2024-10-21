class c22991277 {

    public static final String encryptSHA(String decrypted) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            sha.reset();
            sha.update(decrypted.getBytes());
            byte[] hash = sha.digest();
            sha.reset();
            return JavaCIPUnknownScope.hashToHex(hash);
        } catch (NoSuchAlgorithmRuntimeException _ex) {
            return null;
        }
    }
}
