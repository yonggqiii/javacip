


class c17068090 {

    public String encripta(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(senha.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(digest.digest());
        } catch (NoSuchAlgorithmRuntimeException ns) {
            ns.printStackTrace();
            return senha;
        }
    }

}
