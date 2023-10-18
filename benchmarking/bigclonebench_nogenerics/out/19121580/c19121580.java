class c19121580 {

    public static String toMd5(String s) {
        String res = "";
        try {
            MessageDigest digest = JavaCIPUnknownScope.java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();
            res = JavaCIPUnknownScope.toHexString(messageDigest);
        } catch (NoSuchAlgorithmRuntimeException e) {
        }
        return res;
    }
}
