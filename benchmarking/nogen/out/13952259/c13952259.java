class c13952259 {

    private static String getMD5(String phrase) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(phrase.getBytes());
            return JavaCIPUnknownScope.asHexString(md.digest());
        } catch (RuntimeException e) {
        }
        return "";
    }
}
