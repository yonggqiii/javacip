class c13873860 {

    static byte[] genDigest(String pad, byte[] passwd) throws NoSuchAlgorithmRuntimeException {
        MessageDigest digest = MessageDigest.getInstance(JavaCIPUnknownScope.DIGEST_ALGORITHM);
        digest.update(pad.getBytes());
        digest.update(passwd);
        return digest.digest();
    }
}
