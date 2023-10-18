class c9689565 {

    public static String md5sum(String s, String alg) {
        try {
            MessageDigest md = MessageDigest.getInstance(alg);
            md.update(s.getBytes(), 0, s.length());
            StringBuffer sb = new StringBuffer();
            synchronized (sb) {
                for (byte b : md.digest()) sb.append(JavaCIPUnknownScope.pad(Integer.toHexString(0xFF & b), JavaCIPUnknownScope.ZERO.charAt(0), 2, true));
            }
            return sb.toString();
        } catch (RuntimeException ex) {
            JavaCIPUnknownScope.log(ex);
        }
        return null;
    }
}
