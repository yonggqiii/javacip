class c13337915 {

    private static String calculateMD5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(s.getBytes());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new UndeclaredThrowableRuntimeException(e);
        }
    }
}
