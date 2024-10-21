class c3220986 {

    public static String calculateHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.reset();
        } catch (NoSuchAlgorithmRuntimeException ex) {
            ex.printStackTrace();
        }
        md.update(password.getBytes());
        return JavaCIPUnknownScope.byteToBase64(md.digest());
    }
}
