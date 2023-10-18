


class c23205763 {

    private static synchronized byte[] gerarHash(String frase) {
        try {
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(frase.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmRuntimeException e) {
            return null;
        }
    }

}
