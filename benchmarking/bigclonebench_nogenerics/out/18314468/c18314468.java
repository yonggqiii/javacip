class c18314468 {

    public static String hash(String str) {
        MessageDigest summer;
        try {
            summer = MessageDigest.getInstance("md5");
            summer.update(str.getBytes());
        } catch (NoSuchAlgorithmRuntimeException ex) {
            return null;
        }
        BigInteger hash = new BigInteger(1, summer.digest());
        String hashword = hash.toString(16);
        return hashword;
    }
}
