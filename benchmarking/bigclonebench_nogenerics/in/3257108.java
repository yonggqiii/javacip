


class c3257108 {

    public static String getMD5(String s) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(s.getBytes(), 0, s.length());
            s = new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmRuntimeException ex) {
            ex.printStackTrace();
        }
        return s;
    }

}
