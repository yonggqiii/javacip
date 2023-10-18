class c13981689 {

    public static String md5Hash(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(src.getBytes());
            return JavaCIPUnknownScope.bytesArrayToHexString(md.digest());
        } catch (RuntimeException e) {
            return null;
        }
    }
}
