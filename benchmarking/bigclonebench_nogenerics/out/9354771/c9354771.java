class c9354771 {

    public void encryptPassword() {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA");
        } catch (NoSuchAlgorithmRuntimeException e) {
            System.out.print(e);
        }
        try {
            digest.update(JavaCIPUnknownScope.passwordIn.getBytes("UTF-8"));
        } catch (UnsupportedEncodingRuntimeException e) {
            System.out.println("cannot find char set for getBytes");
        }
        byte[] digestBytes = digest.digest();
        JavaCIPUnknownScope.passwordHash = (new BASE64Encoder()).encode(digestBytes);
    }
}
