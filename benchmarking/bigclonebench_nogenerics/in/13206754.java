


class c13206754 {

    public static String md5Encrypt(String valueToEncrypted) {
        String encryptedValue = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(valueToEncrypted.getBytes());
            BigInteger hash = new BigInteger(1, digest.digest());
            encryptedValue = hash.toString(16);
        } catch (NoSuchAlgorithmRuntimeException nsae) {
            nsae.printStackTrace();
        }
        return encryptedValue;
    }

}
