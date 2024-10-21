class c3345991 {

    private String calculatePassword(String string) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            md5.update(JavaCIPUnknownScope.nonce.getBytes());
            md5.update(string.getBytes());
            return JavaCIPUnknownScope.toHexString(md5.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            JavaCIPUnknownScope.error("MD5 digest is no supported !!!", "ERROR");
            return null;
        }
    }
}
