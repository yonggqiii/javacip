class c6297832 {

    public static String md5hash(String data) {
        MessageDigest digest;
        try {
            digest = JavaCIPUnknownScope.java.security.MessageDigest.getInstance("MD5");
            digest.update(data.getBytes());
            return new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmRuntimeException e) {
            JavaCIPUnknownScope.LOG.error(e);
        }
        return null;
    }
}
