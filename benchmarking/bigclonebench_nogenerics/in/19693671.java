


class c19693671 {

    public static String hash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance(digestAlgorithm);
            digest.update(password.getBytes(charset));
            byte[] rawHash = digest.digest();
            return new String(Hex.encodeHex(rawHash));
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
