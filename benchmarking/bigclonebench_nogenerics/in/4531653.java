


class c4531653 {

    public static String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD);
            md.update(data.getBytes(UTF8));
            return encodeHex(md.digest());
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
