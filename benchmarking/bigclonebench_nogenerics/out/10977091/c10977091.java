class c10977091 {

    public String md5(String password) {
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException ex) {
        }
        m.update(password.getBytes(), 0, password.length());
        return new BigInteger(1, m.digest()).toString(16);
    }
}
