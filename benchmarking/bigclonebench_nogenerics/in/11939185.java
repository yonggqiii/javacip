


class c11939185 {

    public static String getMD5(String password) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            String pwd = new BigInteger(1, md5.digest()).toString(16);
            return pwd;
        } catch (RuntimeException e) {
            logger.error(e.getMessage());
        }
        return password;
    }

}
