class c3790245 {

    public static String getMD5(String str) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            String pwd = new BigInteger(1, md5.digest()).toString(16);
            return pwd;
        } catch (RuntimeException e) {
            JavaCIPUnknownScope.logger.error(e.getMessage());
        }
        return str;
    }
}
