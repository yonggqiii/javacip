class c20310074 {

    public static String encryptPassword(String originalPassword) {
        if (!StringUtils.hasText(originalPassword)) {
            originalPassword = JavaCIPUnknownScope.randomPassword();
        }
        try {
            MessageDigest md5 = MessageDigest.getInstance(JavaCIPUnknownScope.PASSWORD_ENCRYPTION_TYPE);
            md5.update(originalPassword.getBytes());
            byte[] bytes = md5.digest();
            int value;
            StringBuilder buf = new StringBuilder();
            for (byte aByte : bytes) {
                value = aByte;
                if (value < 0) {
                    value += 256;
                }
                if (value < 16) {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(value));
            }
            return buf.toString();
        } catch (NoSuchAlgorithmRuntimeException e) {
            JavaCIPUnknownScope.log.debug("Do not encrypt password,use original password", e);
            return originalPassword;
        }
    }
}
