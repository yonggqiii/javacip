class c20125816 {

    public static String getHash(String password) {
        if (password == null || password.length() == 0) {
            return null;
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            password = JavaCIPUnknownScope.saltPassword(password);
            digest.update(password.getBytes());
            String result = JavaCIPUnknownScope.getHexString(digest.digest());
            return result;
        } catch (NoSuchAlgorithmRuntimeException ex) {
            throw new RuntimeRuntimeException(ex);
        }
    }
}
