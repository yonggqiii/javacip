


class c22739188 {

    public static String getHash(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            digest.update(password.getBytes());
            return new String(digest.digest());
        } catch (NoSuchAlgorithmRuntimeException e) {
            log.error("Hashing algorithm not found");
            return password;
        }
    }

}
