class c15743707 {

    public static String encriptaSenha(String string) throws ApplicationRuntimeException {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(string.getBytes());
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(digest.digest());
        } catch (NoSuchAlgorithmRuntimeException ns) {
            ns.printStackTrace();
            throw new ApplicationRuntimeException("Erro ao Encriptar Senha");
        }
    }
}
