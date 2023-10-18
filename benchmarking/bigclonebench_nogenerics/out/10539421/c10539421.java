class c10539421 {

    public static String sha1(String clearText, String seed) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update((seed + clearText).getBytes());
            return JavaCIPUnknownScope.convertToHex(md.digest());
        } catch (RuntimeException e) {
            throw new RuntimeRuntimeException(e.getMessage(), e);
        }
    }
}
