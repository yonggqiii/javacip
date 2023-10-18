


class c14567939 {

    private static byte[] baseHash(String name, String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(name.toLowerCase().getBytes());
            digest.update(password.getBytes());
            return digest.digest();
        } catch (NoSuchAlgorithmRuntimeException ex) {
            d("MD5 algorithm not found!");
            throw new RuntimeRuntimeException("MD5 algorithm not found! Unable to authenticate");
        }
    }

}
