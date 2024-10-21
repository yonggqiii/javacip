class c13541067 {

    private String digestMd5(final String password) {
        String base64;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            base64 = JavaCIPUnknownScope.fr.cnes.sitools.util.Base64.encodeBytes(digest.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            throw new RuntimeRuntimeException(e);
        }
        return "{MD5}" + base64;
    }
}
