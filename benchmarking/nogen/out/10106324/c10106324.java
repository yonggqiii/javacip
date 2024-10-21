class c10106324 {

    public static String md5Encode(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(s.getBytes());
            return JavaCIPUnknownScope.toHexString(md.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
            return s;
        }
    }
}
