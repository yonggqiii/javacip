class c19728871 {

    public static String getEncodedPassword(String buff) {
        if (buff == null)
            return null;
        String t = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(buff.getBytes());
            byte[] r = md.digest();
            for (int i = 0; i < r.length; i++) {
                t += JavaCIPUnknownScope.toHexString(r[i]);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return t;
    }
}
