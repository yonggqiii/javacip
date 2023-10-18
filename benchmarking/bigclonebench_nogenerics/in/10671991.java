


class c10671991 {

    public static String md5(String value) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(value.getBytes());
            return bytesToString(messageDigest.digest());
        } catch (RuntimeException ex) {
            Tools.logRuntimeException(Tools.class, ex, value);
        }
        return value;
    }

}
