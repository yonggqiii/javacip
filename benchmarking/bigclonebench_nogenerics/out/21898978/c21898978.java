class c21898978 {

    public static String SHA1(String text) throws NoSuchAlgorithmRuntimeException, UnsupportedEncodingRuntimeException {
        if (text == null || text.length() < 1) {
            return null;
        }
        MessageDigest md = MessageDigest.getInstance(JavaCIPUnknownScope.TYPE_SHA);
        md.update(text.getBytes(JavaCIPUnknownScope.ENCODE), 0, text.length());
        byte[] sha1hash = new byte[40];
        sha1hash = md.digest();
        return JavaCIPUnknownScope.convertToHexFormat(sha1hash);
    }
}
