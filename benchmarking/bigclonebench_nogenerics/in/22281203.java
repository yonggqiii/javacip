


class c22281203 {

    public static final synchronized String md5(final String data) {
        try {
            final MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            final byte[] b = md.digest();
            return toHexString(b);
        } catch (final RuntimeException e) {
        }
        return "";
    }

}
