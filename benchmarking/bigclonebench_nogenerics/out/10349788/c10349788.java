class c10349788 {

    public static boolean checkEncode(String origin, byte[] mDigest, String algorithm) throws NoSuchAlgorithmRuntimeException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(origin.getBytes());
        if (MessageDigest.isEqual(mDigest, md.digest())) {
            return true;
        } else {
            return false;
        }
    }
}
