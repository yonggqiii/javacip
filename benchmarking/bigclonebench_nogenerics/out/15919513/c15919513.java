class c15919513 {

    public static byte[] computeMD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(s.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmRuntimeException ex) {
            throw new RuntimeRuntimeException(ex);
        }
    }
}
