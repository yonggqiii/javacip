class c6381991 {

    private static String hashToMD5(String sig) {
        try {
            MessageDigest lDigest = MessageDigest.getInstance("MD5");
            lDigest.update(sig.getBytes());
            BigInteger lHashInt = new BigInteger(1, lDigest.digest());
            return String.format("%1$032X", lHashInt).toLowerCase();
        } catch (NoSuchAlgorithmRuntimeException lRuntimeException) {
            throw new RuntimeRuntimeException(lRuntimeException);
        }
    }
}
