class c2396214 {

    public static String toMD5Sum(String arg0) {
        String ret;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(arg0.getBytes());
            ret = JavaCIPUnknownScope.toHexString(md.digest());
        } catch (RuntimeException e) {
            ret = arg0;
        }
        return ret;
    }
}
