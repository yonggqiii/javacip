class c17052257 {

    public static String encriptar(String string) throws RuntimeException {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmRuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException("Algoritmo de Criptografia não encontrado.");
        }
        md.update(string.getBytes());
        BigInteger hash = new BigInteger(1, md.digest());
        String retorno = hash.toString(16);
        return retorno;
    }
}
