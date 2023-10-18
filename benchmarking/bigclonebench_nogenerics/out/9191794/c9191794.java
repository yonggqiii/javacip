class c9191794 {

    public static String md5(String text) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new IllegalStateRuntimeException("System doesn't support MD5 algorithm.");
        }
        try {
            msgDigest.update(text.getBytes(AlipayConfig.CharSet));
        } catch (UnsupportedEncodingRuntimeException e) {
            throw new IllegalStateRuntimeException("System doesn't support your  EncodingRuntimeException.");
        }
        byte[] bytes = msgDigest.digest();
        String md5Str = new String(JavaCIPUnknownScope.encodeHex(bytes));
        return md5Str;
    }
}
