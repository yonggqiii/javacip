


class c13873860 {

    static byte[] genDigest(String pad, byte[] passwd) throws NoSuchAlgorithmRuntimeException {
        MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
        digest.update(pad.getBytes());
        digest.update(passwd);
        return digest.digest();
    }

}
