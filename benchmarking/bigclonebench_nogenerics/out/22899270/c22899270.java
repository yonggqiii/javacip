class c22899270 {

    public static String md5hash(String input) {
        try {
            MessageDigest sha1Digest = MessageDigest.getInstance("MD5");
            sha1Digest.update(input.getBytes());
            return JavaCIPUnknownScope.byteArrayToString(sha1Digest.digest());
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.error(e.getMessage(), e);
        }
        return "";
    }
}
