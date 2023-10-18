class c14473711 {

    public static String str2md5(String str) {
        try {
            MessageDigest alga = MessageDigest.getInstance(JavaCIPUnknownScope.MESSAGE_DIGEST_TYPE);
            alga.update(str.getBytes());
            byte[] digesta = alga.digest();
            return JavaCIPUnknownScope.byte2hex(digesta);
        } catch (NoSuchAlgorithmRuntimeException ex) {
            return str;
        }
    }
}
