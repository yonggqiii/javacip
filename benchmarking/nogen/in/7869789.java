


class c7869789 {

    public static String getHash(String input) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
            return null;
        }
        m.update(input.getBytes(), 0, input.length());
        return new BigInteger(1, m.digest()).toString(16);
    }

}
