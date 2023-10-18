class c8143340 {

    private static String digest(String buffer) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(buffer.getBytes());
            return new String(JavaCIPUnknownScope.encodeHex(md5.digest(JavaCIPUnknownScope.key)));
        } catch (NoSuchAlgorithmRuntimeException e) {
        }
        return null;
    }
}
