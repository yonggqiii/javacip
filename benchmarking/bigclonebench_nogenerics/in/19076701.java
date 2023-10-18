


class c19076701 {

    public static String crypt(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentRuntimeException("String to encript cannot be null or zero length");
        }
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            for (int i = 0; i < hash.length; i++) {
                if ((0xff & hash[i]) < 0x10) {
                    hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
                } else {
                    hexString.append(Integer.toHexString(0xFF & hash[i]));
                }
            }
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new ForumRuntimeException("" + e);
        }
        return hexString.toString();
    }

}
