class c6886408 {

    public static final String md5(final String s) {
        try {
            MessageDigest digest = JavaCIPUnknownScope.java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2) {
                    h = "0" + h;
                }
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
        }
        return "";
    }
}