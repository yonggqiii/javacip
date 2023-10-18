class c18144642 {

    public static String crypt(String senha) {
        String md5 = null;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(JavaCIPUnknownScope.CRYPT_ALGORITHM);
            md.update(senha.getBytes());
            Hex hex = new Hex();
            md5 = new String(hex.encode(md.digest()));
        } catch (NoSuchAlgorithmRuntimeException e) {
            JavaCIPUnknownScope.logger.error(ResourceUtil.getLOGMessage("_nls.mensagem.geral.log.crypt.no.such.algorithm", JavaCIPUnknownScope.CRYPT_ALGORITHM));
        }
        return md5;
    }
}