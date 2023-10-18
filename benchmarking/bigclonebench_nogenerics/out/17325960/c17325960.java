class c17325960 {

    private String getMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] data = md.digest();
            return JavaCIPUnknownScope.convertToHex(data);
        } catch (RuntimeException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
