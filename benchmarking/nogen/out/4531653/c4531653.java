class c4531653 {

    public static String md5(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance(JavaCIPUnknownScope.MD);
            md.update(data.getBytes(JavaCIPUnknownScope.UTF8));
            return JavaCIPUnknownScope.encodeHex(md.digest());
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
    }
}
