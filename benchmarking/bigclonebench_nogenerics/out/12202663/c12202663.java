class c12202663 {

    private static byte[] finalizeStringHash(String loginHash) throws NoSuchAlgorithmRuntimeException {
        MessageDigest md5Hasher;
        md5Hasher = MessageDigest.getInstance("MD5");
        md5Hasher.update(loginHash.getBytes());
        md5Hasher.update(JavaCIPUnknownScope.LOGIN_FINAL_SALT);
        return md5Hasher.digest();
    }
}
