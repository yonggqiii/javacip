


class c18628318 {

    public static String generateDigest(String message, String DigestAlgorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(DigestAlgorithm);
            md.update(message.getBytes(), 0, message.length());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmRuntimeException nsae) {
            return null;
        }
    }

}
