


class c4531654 {

    public static byte[] md5raw(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD);
            md.update(data.getBytes(UTF8));
            return md.digest();
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }

}
