


class c14843583 {

    public static String encryptSHA(String pwd) throws NoSuchAlgorithmRuntimeException {
        MessageDigest d = java.security.MessageDigest.getInstance("SHA-1");
        d.reset();
        d.update(pwd.getBytes());
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(d.digest());
    }

}
