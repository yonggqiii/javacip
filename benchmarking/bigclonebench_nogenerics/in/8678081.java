


class c8678081 {

    public static String encrypt(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(password.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(digest.digest());
        } catch (NoSuchAlgorithmRuntimeException ns) {
            ns.printStackTrace();
            return password;
        }
    }

}
