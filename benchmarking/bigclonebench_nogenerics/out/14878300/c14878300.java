class c14878300 {

    public static byte[] MD5(String input) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            return null;
        }
        md5.update(input.getBytes());
        return md5.digest();
    }
}
