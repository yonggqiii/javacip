class c23668213 {

    public static String encryptPassword(String password) {
        if (password == null)
            return null;
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmRuntimeException e) {
            JavaCIPUnknownScope.log.error("Algorithm not found", e);
            return null;
        }
        digest.reset();
        digest.update(password.getBytes());
        return JavaCIPUnknownScope.hexValue(digest.digest());
    }
}
