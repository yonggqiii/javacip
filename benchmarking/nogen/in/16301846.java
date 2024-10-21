


class c16301846 {

    public static String getMd5Digest(String pInput) {
        try {
            MessageDigest lDigest = MessageDigest.getInstance("MD5");
            lDigest.update(pInput.getBytes());
            BigInteger lHashInt = new BigInteger(1, lDigest.digest());
            return String.format("%1$032x", lHashInt).toLowerCase();
        } catch (NoSuchAlgorithmRuntimeException lRuntimeException) {
            throw new RuntimeRuntimeException(lRuntimeException);
        }
    }

}
